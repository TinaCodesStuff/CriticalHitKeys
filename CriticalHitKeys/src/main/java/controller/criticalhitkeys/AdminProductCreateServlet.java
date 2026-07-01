package controller.criticalhitkeys;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Amministratore;
import model.Media;
import model.Prodotto;
import model.ProdottoDAO;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.*;

@WebServlet("/admin/prodotti/crea")
@MultipartConfig(maxFileSize = 52_428_800, maxRequestSize = 220_200_960)
public class AdminProductCreateServlet extends HttpServlet {
    private static final long MAX_IMAGE = 5L * 1024 * 1024;
    private static final long MAX_VIDEO = 50L * 1024 * 1024;
    private static final Set<String> IMAGE_TYPES = Set.of("image/jpeg", "image/png", "image/webp");
    private static final Set<String> VIDEO_TYPES = Set.of("video/mp4", "video/webm");
    private final ProdottoDAO dao = new ProdottoDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        Path uploadDirectory = null;
        try {
            Prodotto product = AdminProductSupport.parse(request, dao);
            Amministratore admin = (Amministratore) request.getSession().getAttribute("amministratoreLoggato");
            product.seteMailAmm(admin.getEmail());

            String realRoot = getServletContext().getRealPath("/uploads/products");
            if (realRoot == null) throw new IllegalStateException("Tomcat deve distribuire il WAR in modalità esplosa per salvare gli upload.");
            String folder = UUID.randomUUID().toString();
            uploadDirectory = Paths.get(realRoot, folder);
            Files.createDirectories(uploadDirectory);

            List<Media> media = new ArrayList<>();
            Part cover = request.getPart("copertina");
            if (cover == null || cover.getSize() == 0) throw new IllegalArgumentException("La copertina è obbligatoria.");
            media.add(savePart(cover, uploadDirectory, folder, 0, true));
            int order = 1;
            for (Part part : request.getParts()) {
                if (part.getSize() == 0) continue;
                if ("galleria".equals(part.getName())) media.add(savePart(part, uploadDirectory, folder, order++, true));
                if ("video".equals(part.getName())) media.add(savePart(part, uploadDirectory, folder, order++, false));
            }
            dao.create(product, media);
            response.sendRedirect(request.getContextPath() + "/admin/prodotti?message=created");
        } catch (IllegalArgumentException | IllegalStateException e) {
            deleteTree(uploadDirectory);
            request.getSession().setAttribute("adminError", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/admin/prodotti");
        } catch (RuntimeException e) {
            deleteTree(uploadDirectory);
            request.getSession().setAttribute("adminError", "Impossibile creare il prodotto. Controlla i dati e riprova.");
            response.sendRedirect(request.getContextPath() + "/admin/prodotti");
        }
    }

    private Media savePart(Part part, Path directory, String folder, int order, boolean image) throws IOException {
        String type = part.getContentType() == null ? "" : part.getContentType().toLowerCase(Locale.ROOT);
        Set<String> allowed = image ? IMAGE_TYPES : VIDEO_TYPES;
        long max = image ? MAX_IMAGE : MAX_VIDEO;
        if (!allowed.contains(type)) throw new IllegalArgumentException("Formato file non consentito: " + part.getSubmittedFileName());
        if (part.getSize() > max) throw new IllegalArgumentException("File troppo grande: " + part.getSubmittedFileName());
        String extension = switch (type) {
            case "image/jpeg" -> ".jpg";
            case "image/png" -> ".png";
            case "image/webp" -> ".webp";
            case "video/mp4" -> ".mp4";
            case "video/webm" -> ".webm";
            default -> throw new IllegalArgumentException("Formato file non consentito.");
        };
        String filename = UUID.randomUUID() + extension;
        try (InputStream input = part.getInputStream()) {
            Files.copy(input, directory.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
        }
        Media media = new Media();
        media.setTipo(image ? "image" : "video");
        media.setUrlMedia("uploads/products/" + folder + "/" + filename);
        media.setOrdineVisualizzazione(order);
        return media;
    }

    private void deleteTree(Path directory) {
        if (directory == null || !Files.exists(directory)) return;
        try (var paths = Files.walk(directory)) {
            paths.sorted(Comparator.reverseOrder()).forEach(path -> {
                try { Files.deleteIfExists(path); } catch (IOException ignored) { }
            });
        } catch (IOException ignored) { }
    }
}
