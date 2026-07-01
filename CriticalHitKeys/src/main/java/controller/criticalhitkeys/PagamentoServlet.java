package controller.criticalhitkeys;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ChiaveDigitale;
import model.ChiaveDigitaleDAO;
import model.Prodotto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="PagamentoServlet", value="/pagamento-servlet")
public class PagamentoServlet extends HttpServlet {
    public void init () {

    }

    @Override
    public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String username = (String) session.getAttribute("usernameUtente");

        System.out.println(username);
        if (username != null) {
            ChiaveDigitaleDAO chiaveDAO = new ChiaveDigitaleDAO();

            List<Prodotto> listaProdotti = (List<Prodotto>) session.getAttribute("listaProdotti");
            if (listaProdotti == null) {
                listaProdotti = (List<Prodotto>) request.getAttribute("listaProdotti");
            }

            List<ChiaveDigitale> chiaviGenerate = new ArrayList<>();

            if (listaProdotti != null && !listaProdotti.isEmpty()) {
                for (Prodotto prod: listaProdotti) {
                    ChiaveDigitale nuovaChiave = new ChiaveDigitale();
                    nuovaChiave.setID_Prodotto(prod.getID_Prodotto());
                    nuovaChiave.setChiave(chiaveDAO.generaCodiceRandomico());

                    chiaveDAO.doSave(nuovaChiave);

                    request.setAttribute("nome_" + nuovaChiave.getChiave(), prod.getNome());

                    chiaviGenerate.add(nuovaChiave);
                }
                //Per svuotare il carrello dopo il pagamento
                listaProdotti.clear();
            }
            request.setAttribute("chiaviAcquistate", chiaviGenerate);

            RequestDispatcher dispatcher = request.getRequestDispatcher("JSP/pagamento.jsp");
            dispatcher.forward(request, response);
        }
        else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("JSP/auth.jsp");
            dispatcher.forward(request, response);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void destroy() {

    }
}
