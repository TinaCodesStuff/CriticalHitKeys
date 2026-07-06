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
import java.util.Random;

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
            AccountGiocoDAO accountGiocoDAO = new AccountGiocoDAO();

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
            List<AccountGioco> listaAccountGioco = new ArrayList<>();
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
                    try {
                        ChiaveDigitale chiave = chiaveDAO.doRetrieveChiaveByID_Prodotto(prod.getID_Prodotto());
                        AccountGioco acc = accountGiocoDAO.doRetrieveCredenzialiByID_Prodotto(prod.getID_Prodotto());
                        if(chiave != null && chiave.getID_Prodotto() == prod.getID_Prodotto()){  //verifico se il prodotto ha già una chiave nel DB
                            ChiaveDigitale chiaveActual = new ChiaveDigitale();
                            chiaveActual = chiave;
                            listaChiavi.add(chiaveActual);
                        }
                        else if(acc != null && acc.getID_Prodotto() == prod.getID_Prodotto()){ //verifico se il prodotto ha già un account associato al DB
                            AccountGioco accountActual = new AccountGioco();
                            accountActual = acc;
                            listaAccountGioco.add(accountActual);
                        }
                        else {   //se la chiave non è già presente nel DB, allora procede a generarla e poi ad aggiungerla al DB
                            Random random = new Random();   //in questo caso decide se creare una nuova chiave per il prodotto, oppure un nuovo account con credenziali. In entrambi casi poi memorizzerà la opzione nel DB.

                            if (random.nextBoolean()) {
                                ChiaveDigitale nuovaChiave = new ChiaveDigitale();

                                // Crea una chiave, e setto i parametri generando la chiave
                                nuovaChiave.setID_Prodotto(prod.getID_Prodotto());
                                nuovaChiave.setChiave(chiaveDAO.generaCodiceRandomico());

                                chiaveDAO.doSave(nuovaChiave);
                                listaChiavi.add(nuovaChiave);

                            } else {
                                AccountGioco nuovoAccountGioco = new AccountGioco();// Crea un account di gioco, setto i parametri generando l'account
                                nuovoAccountGioco.setID_Prodotto(prod.getID_Prodotto());
                                nuovoAccountGioco.setCredenziali(accountGiocoDAO.generaCredenziali());

                                accountGiocoDAO.doSave(nuovoAccountGioco);
                                listaAccountGioco.add(nuovoAccountGioco);
                            }
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
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

            System.out.println("[DEBUG-Pagamento] Size-Chiavi: " + listaChiavi.size());
            System.out.println("[DEBUG-Pagamento] Size-Account: " + listaAccountGioco.size());
            request.setAttribute("chiaviAcquistate", listaChiavi);
            request.setAttribute("accountAcquistati", listaAccountGioco);


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
