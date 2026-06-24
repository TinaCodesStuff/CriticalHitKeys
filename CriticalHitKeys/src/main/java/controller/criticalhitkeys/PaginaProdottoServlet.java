package controller.criticalhitkeys;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Prodotto;
import model.ProdottoDAO;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "PaginaProdotto", value = "/paginaProd")
public class PaginaProdottoServlet extends HttpServlet {

    public void init () {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        ProdottoDAO service = new ProdottoDAO();
        String id = request.getParameter("id");

        if (id != null && !id.isEmpty()) {
            int idProdotto = Integer.parseInt(id);
            Prodotto prodotto = service.doRetrieveById(idProdotto);

            if (prodotto != null) {
                request.setAttribute("prodotto", prodotto);

                RequestDispatcher dispatcher = request.getRequestDispatcher("paginaProdotto.jsp");
                dispatcher.forward(request, response);
            }
        }

    }

    public void destroy() {
    }

}
