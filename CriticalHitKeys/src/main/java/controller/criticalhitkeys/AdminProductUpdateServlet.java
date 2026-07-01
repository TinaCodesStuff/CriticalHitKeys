package controller.criticalhitkeys;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Prodotto;
import model.ProdottoDAO;

import java.io.IOException;

@WebServlet("/admin/prodotti/modifica")
public class AdminProductUpdateServlet extends HttpServlet {
    private final ProdottoDAO dao = new ProdottoDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Prodotto existing = dao.doRetrieveById(id);
            if (existing == null) throw new IllegalArgumentException("Prodotto inesistente.");
            Prodotto product = AdminProductSupport.parse(request, dao);
            product.setID_Prodotto(id);
            product.seteMailAmm(existing.geteMailAmm());
            dao.update(product);
            response.sendRedirect(request.getContextPath() + "/admin/prodotti?message=updated");
        } catch (IllegalArgumentException e) {
            request.getSession().setAttribute("adminError", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/admin/prodotti");
        }
    }
}
