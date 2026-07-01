package controller.criticalhitkeys;

import jakarta.servlet.http.HttpServletRequest;
import model.Prodotto;
import model.ProdottoDAO;

import java.util.Arrays;
import java.util.List;

final class AdminProductSupport {
    private AdminProductSupport() {}

    static Prodotto parse(HttpServletRequest request, ProdottoDAO dao) {
        String name = clean(request.getParameter("nome"));
        String description = clean(request.getParameter("descrizione"));
        String developer = clean(request.getParameter("sviluppatore"));
        int discount;
        float originalPrice;
        try {
            originalPrice = Float.parseFloat(request.getParameter("prezzoOriginale"));
            discount = Integer.parseInt(request.getParameter("sconto"));
        } catch (Exception e) {
            throw new IllegalArgumentException("Prezzo e sconto devono essere numerici.");
        }
        List<String> genres = values(request, "generi");
        List<String> platforms = values(request, "piattaforme");
        List<String> modes = values(request, "modalita");
        if (name.length() < 2 || name.length() > 30) throw new IllegalArgumentException("Il nome deve contenere da 2 a 30 caratteri.");
        if (description.length() < 20) throw new IllegalArgumentException("La descrizione deve contenere almeno 20 caratteri.");
        if (developer.length() < 2 || developer.length() > 30) throw new IllegalArgumentException("Casa sviluppatrice non valida.");
        if (originalPrice <= 0 || originalPrice > 9999) throw new IllegalArgumentException("Prezzo originale non valido.");
        if (discount < 0 || discount > 100) throw new IllegalArgumentException("Lo sconto deve essere compreso tra 0 e 100.");
        validateSelection(genres, dao.retrieveGenres(), "genere");
        validateSelection(platforms, dao.retrievePlatforms(), "piattaforma");
        validateSelection(modes, dao.retrieveModes(), "modalità");

        Prodotto product = new Prodotto();
        product.setNome(name);
        product.setDescrizione(description);
        product.setCasa_sviluppatrice(developer);
        product.setPrezzo_OG(originalPrice);
        product.setSconto(discount);
        product.setPrezzo_scontato(Math.round(originalPrice * (100 - discount)) / 100f);
        product.setGeneri(genres);
        product.setPiattaforme(platforms);
        product.setModalita(modes);
        return product;
    }

    private static List<String> values(HttpServletRequest request, String name) {
        String[] values = request.getParameterValues(name);
        return values == null ? List.of() : Arrays.stream(values).distinct().toList();
    }

    private static void validateSelection(List<String> selected, List<String> allowed, String label) {
        if (selected.isEmpty()) throw new IllegalArgumentException("Seleziona almeno un valore per " + label + ".");
        if (!allowed.containsAll(selected)) throw new IllegalArgumentException("Selezione " + label + " non valida.");
    }

    private static String clean(String value) { return value == null ? "" : value.trim(); }
}
