package controller.criticalhitkeys;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.AdminProdottoDAO;

import java.io.IOException;
import java.util.List;

@WebFilter("/admin/*")
public class AdminProductsFilter implements Filter {
    private final AdminProdottoDAO dao = new AdminProdottoDAO();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        // impedisce agli utenti normali di accedere alle pagine amministratore
        if (session == null || session.getAttribute("amministratoreLoggato") == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/auth");
            return;
        }

        // prepara i valori usati dalle select e dalle checkbox della pagina
        //prende l'URI di tutta la pagina, e verifica se finisce con /admin/prodotti e se è una richiesta get
        if ("GET".equals(httpRequest.getMethod()) &&
                httpRequest.getRequestURI().endsWith("/admin/prodotti")) {
            request.setAttribute("piattaforme", dao.doRetrievePiattaforme());
            request.setAttribute("generi", dao.doRetrieveGenere());
            request.setAttribute("modalita", List.of("Single Player", "Multiplayer", "Single/Multi"));
        }
// lascia continuare la richiesta verso la servlet admin corretta
        chain.doFilter(request, response);
    }
}
