package controller.criticalhitkeys;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Media;
import model.Prodotto;
import model.ProdottoDAO;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "RicercaServlet", value = "/ricerca-servlet")
public class RicercaProdottoServlet extends HttpServlet {
    private final ProdottoDAO dao = new ProdottoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Float min = parseFloat(request.getParameter("min"));
        Float max = parseFloat(request.getParameter("max"));
        List<String> genres = values(request, "genere");
        List<String> platforms = values(request, "piattaforma");
        List<String> modes = values(request, "mod_gioco");
        boolean includeUnavailable = "true".equals(request.getParameter("includeUnavailable"));

        List<Prodotto> products = dao.search(request.getParameter("searchText"), min, max,
                request.getParameter("casa_svilupp"), genres, platforms, modes, includeUnavailable);
        for (Prodotto product : products) {
            List<Media> media = dao.doRetrieveMediaByProdotto(product.getID_Prodotto());
            if (!media.isEmpty()) request.setAttribute("mediaP-" + product.getID_Prodotto(), media.getFirst().getUrlMedia());
        }
        request.setAttribute("listaProdotti", products);
        request.setAttribute("generiDisponibili", dao.retrieveGenres());
        request.setAttribute("piattaformeDisponibili", dao.retrievePlatforms());
        request.setAttribute("modalitaDisponibili", dao.retrieveModes());
        request.setAttribute("generiSelezionati", genres);
        request.setAttribute("piattaformeSelezionate", platforms);
        request.setAttribute("modalitaSelezionate", modes);
        request.setAttribute("includeUnavailable", includeUnavailable);
        request.getRequestDispatcher("/JSP/ricerca.jsp").forward(request, response);
    }

    private Float parseFloat(String value) {
        if (value == null || value.isBlank()) return null;
        try { return Float.parseFloat(value); } catch (NumberFormatException ignored) { return null; }
    }

    private List<String> values(HttpServletRequest request, String name) {
        String[] values = request.getParameterValues(name);
        return values == null ? List.of() : Arrays.asList(values);
    }
}
