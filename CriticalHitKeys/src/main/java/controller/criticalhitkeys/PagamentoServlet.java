package controller.criticalhitkeys;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.*;

import javax.sound.midi.SysexMessage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@WebServlet(name="PagamentoServlet", value="/pagamento-servlet")
public class PagamentoServlet extends HttpServlet {
    public void init () {

    }

    @Override
    public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String username = (String) session.getAttribute("usernameUtente");
        Utente u = (Utente) session.getAttribute("utenteLoggato");


        if (username != null) {
            ChiaveDigitaleDAO chiaveDAO = new ChiaveDigitaleDAO();

            /*TENTATIVI DI DEBUG*/
            List<Prodotto> listaProdotti = (List<Prodotto>) session.getAttribute("listaProdotti");
            System.out.println("[DEBUG-Pagamento] Username rilevato: " + username);
            System.out.println("[DEBUG-Pagamento] Lista recuperata da SESSIONE: " + listaProdotti);
            if (listaProdotti != null) {
                System.out.println("[DEBUG-Pagamento] La lista è vuota? " + listaProdotti.isEmpty());
            }
         /*   if (listaProdotti == null) {
                listaProdotti = (List<Prodotto>) request.getAttribute("listaProdotti");
            }*/

            List<ChiaveDigitale> listaChiavi = new ArrayList<>();
            if (listaProdotti.isEmpty())
            {
               System.out.println("Lista prodotti vuotaa :(((");
            }
            else
            {
                System.out.println(listaProdotti.toString());
            }
            if (listaProdotti != null && !listaProdotti.isEmpty()) {
                for (Prodotto prod: listaProdotti) {
                    ChiaveDigitale nuovaChiave = new ChiaveDigitale();

                    try {
                        System.out.println(chiaveDAO.doRetrieveChiaveByID_Prodotto(prod.getID_Prodotto()).getChiave());
                        if(chiaveDAO.doRetrieveChiaveByID_Prodotto(prod.getID_Prodotto()).getChiave() != null){  //verifico se il prodotto ha già una chiave nel DB
                            ChiaveDigitale chiaveActual = new ChiaveDigitale();
                            chiaveActual = chiaveDAO.doRetrieveChiaveByID_Prodotto(prod.getID_Prodotto());
                            listaChiavi.add(chiaveActual);
                        }
                        else{   //se la chiave non è già presente nel DB, allora procede a generarla e poi ad aggiungerla al DB
                            nuovaChiave.setID_Prodotto(prod.getID_Prodotto());
                            nuovaChiave.setChiave(chiaveDAO.generaCodiceRandomico());

                            chiaveDAO.doSave(nuovaChiave);


                            listaChiavi.add(nuovaChiave);
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    //DEBUG
                   if (Objects.equals(nuovaChiave.getChiave(), ""))
                   {
                       System.out.println("Chiave vuota :///s");
                   }
                   else
                   {
                       System.out.println(nuovaChiave.toString());
                   }
                }
                ContieneDAO contieneDAO = new ContieneDAO();
                CarrelloDAO carrelloDAO = new CarrelloDAO();
                try {
                    contieneDAO.removeProdottiByID_Carrello(carrelloDAO.doRetrieveID_Carrello(u));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            request.setAttribute("chiaviAcquistate", listaChiavi);

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


    public void destroy () {

    }
}
