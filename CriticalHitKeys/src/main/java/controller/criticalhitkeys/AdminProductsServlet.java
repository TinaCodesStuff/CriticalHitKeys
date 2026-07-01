package controller.criticalhitkeys;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
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
        request.setAttribute("prodotti", dao.doRetrieveAllForAdmin());
        request.setAttribute("generi", dao.retrieveGenres());
        request.setAttribute("piattaforme", dao.retrievePlatforms());
        request.setAttribute("modalita", dao.retrieveModes());
        String editId = request.getParameter("edit");
        if (editId != null && editId.matches("\\d+")) request.setAttribute("prodottoModifica", dao.doRetrieveById(Integer.parseInt(editId)));
        request.getRequestDispatcher("/JSP/admin-prodotti.jsp").forward(request, response);
    }
}
