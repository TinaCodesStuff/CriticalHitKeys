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

@WebServlet(name = "RicercaServlet", value = "/ricerca-servlet")
public class RicercaProdottoServlet extends HttpServlet {


    public void init() {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String searchText = request.getParameter("searchText"); //prendiamo la stringa che rappresenta il nome del Prodotto da ricercare
        String prezzoMinimo_txt = request.getParameter("min");
        String prezzoMaximo_txt = request.getParameter("max");
        float prezzoMinimo = 0;
        float prezzoMaximo = 0;
        if(prezzoMinimo_txt != null && prezzoMaximo_txt != null) {
            prezzoMinimo = Float.parseFloat(prezzoMinimo_txt);
            prezzoMaximo = Float.parseFloat(prezzoMaximo_txt);
        }
        String mod_gioco = request.getParameter("mod_gioco");
        String genere = request.getParameter("genere");
        String casa_svilupp = request.getParameter("casa_svilupp");

        List<Prodotto> listaProdottiTrovati = new ArrayList<>();
        ProdottoDAO prodottoDAO = new ProdottoDAO();

        if(searchText != null){
            if(!searchText.isEmpty()){
                listaProdottiTrovati = prodottoDAO.doRetrieveProdottoByNome(searchText);
                for (Prodotto prodotto : listaProdottiTrovati) {
                    List<Media> listaMedia = prodottoDAO.doRetrieveMediaByProdotto(prodotto.getID_Prodotto());
                    if(!listaMedia.isEmpty()){
                        request.setAttribute("mediaP-" + prodotto.getID_Prodotto(), listaMedia.getFirst().getUrlMedia());
                    }
                }
            }
        }
        else{   //ricerca avanzate, che fa una nuova ricerca sulla base pero' dei filtri
            listaProdottiTrovati = prodottoDAO.filtraProdotti(genere, casa_svilupp, prezzoMinimo, prezzoMaximo, mod_gioco);
            System.out.println(listaProdottiTrovati.size());
            for (Prodotto prodotto : listaProdottiTrovati) {
                List<Media> listaMedia = prodottoDAO.doRetrieveMediaByProdotto(prodotto.getID_Prodotto());
                if(!listaMedia.isEmpty()){
                    request.setAttribute("mediaP-" + prodotto.getID_Prodotto(), listaMedia.getFirst().getUrlMedia());
                }
            }
        }

        request.setAttribute("listaProdotti", listaProdottiTrovati);

        RequestDispatcher rd = getServletContext().getRequestDispatcher("/JSP/ricerca.jsp");
        rd.forward(request, response);
    }

    public void destroy() {
    }
}