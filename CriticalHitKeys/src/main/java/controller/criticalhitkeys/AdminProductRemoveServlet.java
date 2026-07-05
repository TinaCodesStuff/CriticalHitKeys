package controller.criticalhitkeys;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.AdminProdottoDAO;

import java.io.IOException;

@WebServlet("/admin/prodotti/rimuovi")
public class AdminProductRemoveServlet extends HttpServlet {
    private final AdminProdottoDAO dao = new AdminProdottoDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // controlla che la richiesta provenga da un amministratore autenticato
        if (request.getSession().getAttribute("amministratoreLoggato") == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String result = dao.doRemoveOrDisable(id);

            // mostra il risultato della rimozione nella pagina amministratore
            if (result.equals("deleted")) {
                request.getSession().setAttribute("adminMessage", "Prodotto eliminato.");
            } else if (result.equals("disabled")) {
                request.getSession().setAttribute("adminMessage", "Prodotto reso non disponibile perché è già referenziato.");
            } else {
                request.getSession().setAttribute("adminError", "Prodotto inesistente.");
            }
        } catch (IllegalArgumentException e) {
            request.getSession().setAttribute("adminError", "Identificativo del prodotto non valido.");
        } catch (RuntimeException e) {
            request.getSession().setAttribute("adminError", "Impossibile rimuovere il prodotto.");
        }

        response.sendRedirect(request.getContextPath() + "/admin/prodotti");
    }
}
