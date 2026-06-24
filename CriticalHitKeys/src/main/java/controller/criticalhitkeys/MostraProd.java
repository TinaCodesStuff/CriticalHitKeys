package controller.criticalhitkeys;

import java.io.*;
import java.util.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Prodotto;
import model.ProdottoDAO;

@WebServlet(name = "MostraProd", value = "/mostraProd")
public class MostraProd extends HttpServlet {


    public void init() {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        ProdottoDAO service = new ProdottoDAO();
        List<Prodotto> listaProdotti = service.doRetrieveAll();
        request.setAttribute("listaProdotti", listaProdotti);

        RequestDispatcher p = request.getRequestDispatcher("JSP/show-all.jsp");
        p.forward(request, response);
    }

    public void destroy() {
    }
}
