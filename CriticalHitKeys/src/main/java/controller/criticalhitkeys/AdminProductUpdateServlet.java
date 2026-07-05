package controller.criticalhitkeys;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.AdminProdottoDAO;
import model.Prodotto;

import java.io.IOException;

@WebServlet("/admin/prodotti/modifica")
@MultipartConfig
public class AdminProductUpdateServlet extends HttpServlet {
    private final AdminProdottoDAO dao = new AdminProdottoDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        if (request.getSession().getAttribute("amministratoreLoggato") == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        try {
            // recupera l'identificativo e i nuovi dati del prodotto
            int id = Integer.parseInt(request.getParameter("id"));
            Prodotto prodotto = AdminProductForm.parse(request);
            prodotto.setID_Prodotto(id);
            if (!dao.doUpdate(prodotto)) {
                request.getSession().setAttribute("adminError", "Prodotto inesistente.");
            } else {
                request.getSession().setAttribute("adminMessage", "Prodotto modificato.");
            }
        } catch (IllegalArgumentException e) {
            request.getSession().setAttribute("adminError", e.getMessage());
        } catch (RuntimeException e) {
            request.getSession().setAttribute("adminError", "Impossibile modificare il prodotto.");
        }
        response.sendRedirect(request.getContextPath() + "/admin/prodotti");
    }
}
