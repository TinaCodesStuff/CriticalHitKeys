package controller.criticalhitkeys;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Prodotto;
import model.ProdottoDAO;

import java.io.IOException;

@WebServlet("/admin/prodotti")
public class AdminProductsServlet extends HttpServlet {
    private final ProdottoDAO dao = new ProdottoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object error = request.getSession().getAttribute("adminError");
        if (error != null) {
            request.setAttribute("adminError", error);
            request.getSession().removeAttribute("adminError");
        }
        request.setAttribute("prodotti", dao.doRetrieveAll());

        String editId = request.getParameter("edit");

        if (editId != null) {
            try {
                int id = Integer.parseInt(editId);
                Prodotto prodotto = dao.doRetrieveById(id);

                if (prodotto != null) {
                    request.setAttribute("prodottoModifica", prodotto);
                }

            } catch (NumberFormatException e) {
                throw new NumberFormatException();
            }
        }

        request.getRequestDispatcher("/JSP/admin-prodotto.jsp").forward(request, response);
    }
}