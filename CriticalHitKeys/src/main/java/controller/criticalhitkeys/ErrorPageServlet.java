package controller.criticalhitkeys;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "paginaErrore", value = "/pagina-errore")
public class ErrorPageServlet extends HttpServlet {
    public void init () {

    }

    public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("JSP/errorpage.jsp");
        dispatcher.forward(request, response);
    }

    public void destroy () {

    }
}
