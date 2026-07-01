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
        int id_prod = Integer.parseInt(idStr);
        int quantita = Integer.parseInt(qStr);
        Prodotto product = new ProdottoDAO().doRetrieveById(id_prod);
        if (product == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
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

            quantitaMap = (HashMap<Integer, Integer>) contieneDAO.doRetrieveQuantitaById_Carrello(id_carrello); //alla fine ottengo la quanittà
            int current = quantitaMap.getOrDefault(id_prod, 0);
            if (!product.isDisponibile() && quantita > current) {
                response.sendError(HttpServletResponse.SC_CONFLICT, "Prodotto non disponibile");
                return;
            }
            if (quantita == 0) {
                contieneDAO.doDelete(id_carrello, id_prod);
                quantitaMap.remove(id_prod);
                lista.removeIf(p -> p.getID_Prodotto() == id_prod);
            } else {
                contieneDAO.doUpdate(new Contiene(id_prod, id_carrello, quantita));
                quantitaMap.put(id_prod, quantita);
            }

        } else {    //ramificazione in caso l'utente NON sia loggato, aggiorno e prendo le info dalla sessione
            lista = (List<Prodotto>) session.getAttribute("listaProdotti"); //prendo dalla sessione sia la lista dei prodotti che la quantità
            quantitaMap = (HashMap<Integer, Integer>) session.getAttribute("quantita");

            if (quantitaMap == null) {
                quantitaMap = new HashMap<>();
                session.setAttribute("quantita", quantitaMap);
            }

            int current = quantitaMap.getOrDefault(id_prod, 0);
            if (!product.isDisponibile() && quantita > current) {
                response.sendError(HttpServletResponse.SC_CONFLICT, "Prodotto non disponibile");
                return;
            }
            if (quantita == 0) {
                quantitaMap.remove(id_prod);
                if (lista != null) lista.removeIf(p -> p.getID_Prodotto() == id_prod);
            } else {
                quantitaMap.put(id_prod, quantita);
            }
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
                "{\"ok\":true, \"removed\":" + (quantita == 0) + ", \"totale\":" + totale + "}"
        );
    }

    public void destroy() {
    }
}
