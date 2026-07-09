package controller.criticalhitkeys;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.AdminUtenteDAO;

import java.io.IOException;

@WebServlet("/admin/utenti")
public class AdminUtentiServlet extends HttpServlet {
    private final AdminUtenteDAO dao = new AdminUtenteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // carica utenti, carrelli correnti e ordini passati
        request.setAttribute("utentiInfo", dao.doRetrieveAll());
        request.getRequestDispatcher("/JSP/admin-utenti.jsp").forward(request, response);
    }
}
