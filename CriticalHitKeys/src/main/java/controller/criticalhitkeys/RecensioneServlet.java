package controller.criticalhitkeys;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Prodotto;
import model.Recensione;
import model.RecensioneDAO;
import model.Utente;

import java.io.IOException;

@WebServlet(name = "RecensioneServlet", value = "/recensione-servlet")
public class RecensioneServlet extends HttpServlet {


    public void init() {

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utenteLoggato");
        String commento = clean(request.getParameter("testoRecensione"));
        int voto;
        try {
            voto = Integer.parseInt(request.getParameter("voto"));
        } catch (RuntimeException e) {
            voto = 0;
        }

        if(utente != null) {
            RecensioneDAO dao = new RecensioneDAO();

            Recensione recensione = new Recensione();
            Prodotto prodotto = (Prodotto) session.getAttribute("prodotto-afterRecensione");

            if (prodotto == null || commento.isEmpty() || commento.length() > 500 || voto < 1 || voto > 5) {
                request.setAttribute("recensioneError", "Recensione non valida.");
                request.setAttribute("prodotto", prodotto);
                request.setAttribute("listaMedia", session.getAttribute("media-afterRecensione"));
                if (prodotto != null) {
                    request.setAttribute("listaRecensione", dao.doRetrieveByProdotto(prodotto.getID_Prodotto()));
                }
                RequestDispatcher dispatcher = request.getRequestDispatcher("JSP/paginaProdotto.jsp");
                dispatcher.forward(request, response);
                return;
            }

            System.out.println(prodotto.getID_Prodotto());
            recensione.setDescrizione_Rec(commento);
            recensione.setID_Prodotto(prodotto.getID_Prodotto());
            recensione.setEmail_Ut(utente.getEmail_Ut());
            recensione.setUsername_Ut(utente.getUsername_Ut());
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

    private String clean(String value) {
        if (value == null) {
            return "";
        }
        return value.trim();
    }
}
