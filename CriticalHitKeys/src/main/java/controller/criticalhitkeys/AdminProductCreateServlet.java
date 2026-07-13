package controller.criticalhitkeys;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Stream;

@WebServlet("/admin/prodotti/crea")
@MultipartConfig(maxFileSize = 5_242_880, maxRequestSize = 52_428_800)
public class AdminProductCreateServlet extends HttpServlet {
    private static final long DIMENSIONE_MASSIMA_IMMAGINE = 5L * 1024 * 1024;
    private static final Set<String> TIPI_IMMAGINE = Set.of("image/jpeg", "image/png", "image/webp");
    private final AdminProdottoDAO dao = new AdminProdottoDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        Path cartellaUpload = null;
        try {
            // converte i parametri del form in un prodotto
            Prodotto prodotto = AdminProductForm.parse(request);

            Amministratore amministratore = (Amministratore) request.getSession()
                    .getAttribute("amministratoreLoggato");

            if (amministratore == null) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }


            // associa il prodotto all'amministratore autenticato
            prodotto.seteMailAmm(amministratore.getEmail());


            // crea una cartella separata per le immagini del prodotto
            String percorsoUpload = getServletContext().getRealPath("/uploads/products");
            if (percorsoUpload == null) {
                throw new IllegalStateException("Il server deve usare il war exploded per salvare le immagini.");
            }
            String nomeCartella = UUID.randomUUID().toString();
            cartellaUpload = Paths.get(percorsoUpload, nomeCartella);
            Files.createDirectories(cartellaUpload);


            // salva prima la copertina e poi le immagini aggiuntive
            List<AdminMedia> listaMedia = new ArrayList<>();
            Part copertina = request.getPart("copertina");
            // se non è stata caricata la copertina, lancia un errore
            if (copertina == null || copertina.getSize() == 0) {
                throw new IllegalArgumentException("La copertina è obbligatoria.");
            }
            listaMedia.add(saveImage(copertina, cartellaUpload, nomeCartella));

            for (Part parte : request.getParts()) {
                if (parte.getName().equals("galleria") && parte.getSize() > 0) {
                    listaMedia.add(saveImage(parte, cartellaUpload, nomeCartella));
                }
            }


            dao.doSave(prodotto, listaMedia);
            request.getSession().setAttribute("adminMessage", "Prodotto creato.");
        } catch (IllegalArgumentException e) {
            deleteDirectory(cartellaUpload);
            request.getSession().setAttribute("adminError", e.getMessage());
        } catch (RuntimeException e) {
            deleteDirectory(cartellaUpload);
            request.getSession().setAttribute("adminError", "Impossibile creare il prodotto.");
        }
        response.sendRedirect(request.getContextPath() + "/admin/prodotti");
    }

    private AdminMedia saveImage(Part parte, Path cartellaUpload, String nomeCartella) throws IOException {
        String tipo = parte.getContentType();
        if (tipo == null) {
            tipo = "";
        }
        tipo = tipo.toLowerCase(Locale.ROOT);

        if (!TIPI_IMMAGINE.contains(tipo)) {
            throw new IllegalArgumentException("Formato immagine non consentito.");
        }
        if (parte.getSize() > DIMENSIONE_MASSIMA_IMMAGINE) {
            throw new IllegalArgumentException("Immagine troppo grande.");
        }

        String estensione;
        if (tipo.equals("image/jpeg")) {
            estensione = ".jpg";
        } else if (tipo.equals("image/png")) {
            estensione = ".png";
        } else {
            estensione = ".webp";
        }

        String nomeFile = UUID.randomUUID().toString() + estensione;
        Path destinazione = cartellaUpload.resolve(nomeFile);
        try (InputStream input = parte.getInputStream()) {
            Files.copy(input, destinazione, StandardCopyOption.REPLACE_EXISTING);
        }

        AdminMedia media = new AdminMedia();
        media.setIdMedia("M" + UUID.randomUUID().toString().replace("-", "").substring(0, 9));
        media.setTipo("image");
        media.setUrlMedia("uploads/products/" + nomeCartella + "/" + nomeFile);
        return media;
    }

    private void deleteDirectory(Path cartellaUpload) {
        if (cartellaUpload == null || !Files.exists(cartellaUpload)) {
            return;
        }

        try (Stream<Path> percorsi = Files.walk(cartellaUpload)) {
            percorsi.sorted(Comparator.reverseOrder()).forEach(percorso -> {
                try {
                    Files.deleteIfExists(percorso);
                } catch (IOException ignored) {
                }
            });
        } catch (IOException ignored) {
        }
    }
}
