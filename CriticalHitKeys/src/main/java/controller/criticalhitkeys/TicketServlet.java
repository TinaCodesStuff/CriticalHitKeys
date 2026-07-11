package controller.criticalhitkeys;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Ticket;
import model.TicketDAO;
import model.Utente;

import java.io.IOException;
import java.util.List;

@WebServlet(name="TicketServlet", value="/ticket-servlet")
public class TicketServlet extends HttpServlet {

    public void init () {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente u = (Utente) session.getAttribute("utenteLoggato");

        if (u != null) {
            TicketDAO ticketDAO = new TicketDAO();
            // Recuperiamo i vecchi ticket dell'utente loggato
            List<Ticket> storici = ticketDAO.doRetrieveByUsernameUtente(u.getUsername_Ut());
            request.setAttribute("listaTicket", storici);

            request.getRequestDispatcher("JSP/assistenza.jsp").forward(request, response);
        } else {
            // Se non è loggato, rimandalo al login
            request.getRequestDispatcher("auth").forward(request, response);
        }
    }

    public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utenteLoggato");
        String campo = clean(request.getParameter("campo"));
        String descrizione = clean(request.getParameter("descrizione-ticket"));

        if (utente != null) {
            TicketDAO ticketDAO = new TicketDAO();

            if (campo.isEmpty() || campo.length() > 20 || descrizione.isEmpty() || descrizione.length() > 500) {
                request.setAttribute("ticketError", "Dati del ticket non validi.");
                request.setAttribute("listaTicket", ticketDAO.doRetrieveByUsernameUtente(utente.getUsername_Ut()));
                RequestDispatcher dispatcher = request.getRequestDispatcher("JSP/assistenza.jsp");
                dispatcher.forward(request, response);
                return;
            }

            Ticket ticket = new Ticket();
            ticket.setUsernameUtente(utente.getUsername_Ut());
            ticket.setEmailUtente(utente.getEmail_Ut());
            ticket.setCampo(campo);
            ticket.setDescrizioneTicket(descrizione);

            ticketDAO.doSave(ticket);

            request.setAttribute("listaTicket", ticketDAO.doRetrieveByUsernameUtente(ticket.getUsernameUtente()));

            RequestDispatcher dispatcher = request.getRequestDispatcher("JSP/assistenza.jsp");
            dispatcher.forward(request, response);
        }
        else{
            RequestDispatcher dispatcher = request.getRequestDispatcher("JSP/auth.jsp");
            dispatcher.forward(request, response);
        }
    }





    public void destroy () {

    }

    private String clean(String value) {
        if (value == null) {
            return "";
        }
        return value.trim();
    }
}
