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

        List<Prodotto> listaProdotti = new ArrayList<>();   //listaProdotti in caso si stia usando gli utenti del DB, oppure nel caso non avvengano modifiche
        Map<Integer, Integer> quantita = new HashMap<>();   //inzializziamo la quanità che è rappresentata come una HashMap con la coppia (ID_Prodotto -> quantita)

        Utente u = (Utente) session.getAttribute("utenteLoggato");
        if(u != null){  //caso in cui ci siamo loggati o Registrati.
            CarrelloDAO carrelloDAO = new CarrelloDAO();    //istanziamo il DAO del carrello
            ContieneDAO contieneDAO = new ContieneDAO();    //istanziamo il DAO del contiene, ci serve per la quantità dei prodotti nel carrello
            int id_Carrello = carrelloDAO.doRetrieveID_Carrello(u); //con il metodo doRetrieveID_Carrello possiamo individuare l'id del carrello dell'utente, questo torna utile per determinare la quantità dei Prodotti

            listaProdotti = carrelloDAO.doRetrieveAllByUtente(u);
            quantita = contieneDAO.doRetrieveQuantitaById_Carrello(id_Carrello);

            if(!listaProdotti.isEmpty() && id > 0){
                    ProdottoDAO prodottoDAO = new ProdottoDAO();
                    boolean presente = false;
                    for (Prodotto p : listaProdotti) {
                        if (p.getID_Prodotto() == id) {
                            presente = true;
                            quantita.merge(id, 1, Integer::sum); //qui usiamo la HashTable, e incrementiamo di 1 il valore della quanità

                            Contiene new_contiene = contieneDAO.findByID_Carrello(id_Carrello); //qui prendo il vecchio Contiene che non ha la quanità aggiornata
                            new_contiene.setQuantita(quantita.get(id)); //qui aggiorno la quantità creando effettivamente il "new_contiene"

                            contieneDAO.doUpdate(new_contiene);    //memorizziamo la nuova quanittà del prodotto nel carrello, uso findByID_Carrello per trovare la specifica riga di Contiene che necessito
                            break;
                        }
                    }

                    if (!presente) {
                        listaProdotti.add(prodottoDAO.doRetrieveById(id));
                        quantita.merge(id, 1, Integer::sum);
                        contieneDAO.doSave(new Contiene(id, id_Carrello, quantita.get(id)));    //memorizziamo il nuovo prodotto nel carrello con la quantità usando la tabella associativa Contiene
                    }



                request.setAttribute("listaProdotti", listaProdotti);
                request.setAttribute("quantita", quantita);
            }
            else{   //caso in cui non ci sono prodotti
                if(id > 0){ //verifico  se ci sta un prodotto da aggiungere
                    ProdottoDAO prodottoDAO = new ProdottoDAO();
                    listaProdotti.add(prodottoDAO.doRetrieveById(id));  //aggiungo il prodotto nella lista
                    quantita.merge(id, 1, Integer::sum);
                    contieneDAO.doSave(new Contiene(id, id_Carrello, quantita.get(id)));    //memorizziamo il nuovo prodotto nel carrello con la quantità usando la tabella associativa Contiene

                }
                request.setAttribute("listaProdotti", listaProdotti);   //qui vi è solo ArrayList inizializzato senza alcun prodotto, altrimenti mi restituisce la lista con il prodotto aggiunto
                request.setAttribute("quantita", quantita); //in questo caso essendo solo inizializzata, non ci sarà nulla dentro. Se ho aggiunto un prodotto al carrello, allora viene restiuita la lista con il nuovo prodotto

            }
        }
        else if(id != 0){ //controlliamo il caso in cui l'utente non è registrato/loggato, ma aggiunge qualcosa al carrello

            ProdottoDAO prodottoDAO = new ProdottoDAO();

            quantita = (Map<Integer, Integer>) session.getAttribute("quantita"); //usiamo una HashTable in cui le coppie saranno (id -> quanità)

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
