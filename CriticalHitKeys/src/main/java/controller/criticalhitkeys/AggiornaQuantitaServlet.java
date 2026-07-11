package controller.criticalhitkeys;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

//ATTENZIONE: questa Servlet viene ussata SOLO nella chiamata AJAX

@WebServlet(name = "AggiornaQuantitaServlet", value = "/aggiorna-quantita")
public class AggiornaQuantitaServlet extends HttpServlet {
    public void init() {

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idStr = request.getParameter("id_prod");
        String qStr = request.getParameter("quantita");
        int id_prod;
        int quantita;
        // converto i parametri ajax e scarto valori non numerici
        try {
            id_prod = Integer.parseInt(idStr);
            quantita = Integer.parseInt(qStr);
        } catch (RuntimeException e) {
            sendError(response);
            return;
        }

        // la select del carrello permette solo quantità da 1 a 10
        if (id_prod <= 0 || quantita < 1 || quantita > 10) {
            sendError(response);
            return;
        }

        double totale = 0;

        CarrelloDAO carrelloDAO = new CarrelloDAO();
        ContieneDAO contieneDAO = new ContieneDAO();

        List<Prodotto> lista = null;
        HashMap<Integer, Integer> quantitaMap = null;

        HttpSession session = request.getSession();
        Utente u = (Utente) session.getAttribute("utenteLoggato");

        if (u != null) {    //ramificazione in caso l'utente sia loggato, allora aggiorno e prendo le info dal DB
            lista = carrelloDAO.doRetrieveAllByUtente(u);   //recupero la lista dell'utente richiesto
            int id_carrello = carrelloDAO.doRetrieveID_Carrello(u); //recupero l'ID_Carrello associato all'utente

            contieneDAO.doUpdate(new Contiene(id_prod, id_carrello, quantita)); //aggiorno la quanità con quella data, per il prodotto dato

            quantitaMap = (HashMap<Integer, Integer>) contieneDAO.doRetrieveQuantitaById_Carrello(id_carrello); //alla fine ottengo la quanittà

        } else {    //ramificazione in caso l'utente NON sia loggato, aggiorno e prendo le info dalla sessione
            lista = (List<Prodotto>) session.getAttribute("listaProdotti"); //prendo dalla sessione sia la lista dei prodotti che la quantità
            quantitaMap = (HashMap<Integer, Integer>) session.getAttribute("quantita");

            if (quantitaMap == null) {
                quantitaMap = new HashMap<>();
                session.setAttribute("quantita", quantitaMap);
            }

            quantitaMap.put(id_prod, quantita); //aggiorno la quantita per il prodotto dato
        }

        if (lista != null) {    //qui avviene il vero calcolo
            for (Prodotto p : lista) {

                int q = 1;

                if (quantitaMap != null && quantitaMap.get(p.getID_Prodotto()) != null) {
                    q = quantitaMap.get(p.getID_Prodotto());    //prendo la quantità del prodotto p
                }

                totale += p.getPrezzo_scontato() * q;   //calcolo il totale per tutti i prodotti
            }
        }

        totale = Math.round(totale*100.0) /100.0;

        //qui avviene la costruzione del JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(
                "{\"ok\":true, \"totale\":" + totale + "}"  //qui inviamo i dati del totale calcolato, che poi verrà visualizzato nella servlet
        );
    }

    public void destroy() {
    }

    // risposta usata quando la richiesta ajax non supera i controlli
    private void sendError(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"ok\":false}");
    }
}
