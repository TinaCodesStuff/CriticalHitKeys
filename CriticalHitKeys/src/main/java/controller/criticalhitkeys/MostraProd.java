package controller.criticalhitkeys;

import java.io.*;
import java.util.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Media;
import model.Prodotto;
import model.ProdottoDAO;

@WebServlet(name = "MostraProd", value = "/mostraProd")
public class MostraProd extends HttpServlet {


    public void init() {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ProdottoDAO service = new ProdottoDAO();
        List<Prodotto> listaProdotti = service.doRetrieveAll();
        for (Prodotto prodotto : listaProdotti) {
            List<Media> listaMedia = service.doRetrieveMediaByProdotto(prodotto.getID_Prodotto());
            if(!listaMedia.isEmpty()){
                request.setAttribute("mediaP-" + prodotto.getID_Prodotto(), listaMedia.getFirst().getUrlMedia());
            }
        }
        request.setAttribute("listaProdotti", listaProdotti);

        RequestDispatcher p = request.getRequestDispatcher("JSP/show-all.jsp");
        p.forward(request, response);
    }

    public void destroy() {
    }
}
