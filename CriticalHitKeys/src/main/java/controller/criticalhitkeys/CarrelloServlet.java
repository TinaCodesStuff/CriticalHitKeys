package controller.criticalhitkeys;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.*;

@WebServlet(name = "CarrelloServlet", value = "/carrello-servlet")
public class CarrelloServlet extends HttpServlet{
    public void init() {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String val = request.getParameter("id_prod");
        int id = 0;
        if(val != null){
            id = Integer.parseInt(val);
        }

        HttpSession session = request.getSession();


        System.out.println("id: "+ id);
        CarrelloDAO carrelloDAO = new CarrelloDAO();
        List<Prodotto> listaProdotti = new ArrayList<>();
        Utente u = (Utente) session.getAttribute("utenteLoggato");
        if(u != null){
            listaProdotti = carrelloDAO.doRetrieveAllByUtente(u);
            System.out.println(u.getUsername_Ut());
            if(!listaProdotti.isEmpty()){
                request.setAttribute("listaProdotti", listaProdotti);
            }
            else{
                request.setAttribute("listaProdotti", null);
            }
        }
        else if(id != 0){ //controlliamo il caso in cui l'utente non è registrato/loggato, ma aggiunge qualcosa al carrello

            ProdottoDAO prodottoDAO = new ProdottoDAO();
            System.out.println(session.getAttribute("listaProdotti"));

            Map<Integer, Integer> quantita =
                    (Map<Integer, Integer>) session.getAttribute("quantita"); //usiamo una HashTable in cui le coppie saranno (id -> quanità)

            if (quantita == null) {
                quantita = new HashMap<>();
            }

            if(session.getAttribute("listaProdotti") != null){ //controlliamo che esista la lista dei prodotti nella sessione
                List<Prodotto> prodottiSessione = (List<Prodotto>) session.getAttribute("listaProdotti");
                boolean presente = false;

                for (Prodotto p : prodottiSessione) {
                    if (p.getID_Prodotto() == id) {
                        presente = true;
                        quantita.merge(id, 1, Integer::sum); //qui usiamo la HashTable, e incrementiamo di 1 il valore della quanità
                        break;
                    }
                }

                if (!presente) {
                    prodottiSessione.add(prodottoDAO.doRetrieveById(id));
                    quantita.merge(id, 1, Integer::sum);
                }

                session.setAttribute("quantita", quantita);

                request.setAttribute("listaProdotti", prodottiSessione);
                request.setAttribute("quantita", quantita);
            }
            else{ //essendo che non esistono altri prodotti in sessione, allora creiamo la lista e la ssalviamo con le relative quanittà in sessione
                List<Prodotto> prodottiSessione = new ArrayList<>();

                prodottiSessione.add( prodottoDAO.doRetrieveById(id));
                quantita.put(id, 1);

                session.setAttribute("listaProdotti", prodottiSessione);
                session.setAttribute("quantita", quantita);

                request.setAttribute("listaProdotti", prodottiSessione);
                request.setAttribute("quantita", quantita);
            }
        }
        else{
            if(session.getAttribute("listaProdotti") != null && session.getAttribute("quantita") != null){
                request.setAttribute("listaProdotti", session.getAttribute("listaProdotti"));
                request.setAttribute("quantita", session.getAttribute("quantita"));
            }
            else{
                request.setAttribute("listaProdotti", listaProdotti);

            }
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("JSP/carrello.jsp");
        dispatcher.forward(request, response);

    }

    public void destroy() {
    }
}
