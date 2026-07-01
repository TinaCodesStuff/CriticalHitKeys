package controller.criticalhitkeys;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Prodotto;
import model.ProdottoDAO;
import model.Recensione;
import model.RecensioneDAO;

import java.io.IOException;

@WebServlet(name = "RecensioneServlet", value = "/recensione-servlet")
public class RecensioneServlet extends HttpServlet {


    public void init() {

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String id =  request.getParameter("id_ut");
        String username =  request.getParameter("username_ut");
        String commento = (String) request.getParameter("testoRecensione");
        int voto = Integer.parseInt(request.getParameter("voto"));

        if( id != null && username != null) {
            RecensioneDAO dao = new RecensioneDAO();
            HttpSession session = request.getSession();

            Recensione recensione = new Recensione();
            Prodotto prodotto = new Prodotto();
            prodotto = (Prodotto) session.getAttribute("prodotto-afterRecensione");
            if (prodotto == null || new ProdottoDAO().doRetrieveAvailableById(prodotto.getID_Prodotto()) == null) {
                response.sendError(HttpServletResponse.SC_CONFLICT, "Il prodotto non è disponibile");
                return;
            }

            System.out.println(prodotto.getID_Prodotto());
            recensione.setDescrizione_Rec(commento);
            recensione.setID_Prodotto(prodotto.getID_Prodotto());
            recensione.setEmail_Ut(id);
            recensione.setUsername_Ut(username);
            recensione.setVoto(voto);

            dao.doSave(recensione);


            request.setAttribute("prodotto", session.getAttribute("prodotto-afterRecensione"));
            request.setAttribute("listaMedia", session.getAttribute("media-afterRecensione"));
            request.setAttribute("listaRecensione", dao.doRetrieveByProdotto(prodotto.getID_Prodotto()));


            session.removeAttribute("media-afterRecensione");
            session.removeAttribute("recensione-afterRecensione");
            session.removeAttribute("prodotto-afterRecensione");

            RequestDispatcher dispatcher = request.getRequestDispatcher("JSP/paginaProdotto.jsp");
            dispatcher.forward(request, response);
        }
        else{
            RequestDispatcher dispatcher = request.getRequestDispatcher("JSP/auth.jsp");
            dispatcher.forward(request, response);
        }
    }

    public void destroy() {
    }
}
