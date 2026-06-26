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
        response.setContentType("text/html");
        HttpSession session = request.getSession();

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
        else{
            request.setAttribute("listaProdotti", new ArrayList<Prodotto>());
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("JSP/carrello.jsp");
        dispatcher.forward(request, response);

    }

    public void destroy() {
    }
}
