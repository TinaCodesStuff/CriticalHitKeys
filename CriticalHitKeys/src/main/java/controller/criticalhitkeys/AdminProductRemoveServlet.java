package controller.criticalhitkeys;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.ProdottoDAO;

import java.io.IOException;
import java.nio.file.*;

@WebServlet("/admin/prodotti/rimuovi")
public class AdminProductRemoveServlet extends HttpServlet {
    private final ProdottoDAO dao = new ProdottoDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            ProdottoDAO.RemovalResult result = dao.removeOrDisable(id);
            if ("deleted".equals(result.outcome())) {
                for (String url : result.mediaUrls()) {
                    String realPath = getServletContext().getRealPath("/" + url);
                    if (realPath != null) Files.deleteIfExists(Paths.get(realPath));
                }
            }
            response.getWriter().write("{\"status\":\"" + result.outcome() + "\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"error\"}");
        }
    }
}
