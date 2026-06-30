package controller.criticalhitkeys;

import java.io.*;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Media;
import model.Prodotto;
import model.ProdottoDAO;

@WebServlet(name = "InitServlet", value = "/init-servlet")
public class InitServlet extends HttpServlet {


    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String[] suggested = {"The Wolf Among Us", "Cyberpunk 2077", "Life is Strange", "Watch Dogs 2", "Baldur's Gate 3", "Devil May Cry 5"};   //nomi dei prodotti consigliati
        ProdottoDAO prodottoDAO = new ProdottoDAO();

        List<Prodotto> listaSuggested = prodottoDAO.doRetrieveSuggestProduct(suggested);    //chiamo la funzione che mi restituirà i Prodotti che sono consigliati

        for (Prodotto prodotto : listaSuggested) {
            List<Media> listaMedia = prodottoDAO.doRetrieveMediaByProdotto(prodotto.getID_Prodotto());
            if(!listaMedia.isEmpty()){
                request.setAttribute("mediaP-" + prodotto.getID_Prodotto(), listaMedia.getFirst().getUrlMedia());
            }
        }

        request.setAttribute("listaSuggested", listaSuggested); //memorizzo nell'attributo della request la lista dei Prodotti consigliati

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/homepage.jsp");
        dispatcher.forward(request, response);
    }

    public void destroy() {
    }
}
