package controller.criticalhitkeys;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import model.Utente;
import model.UtenteDAO;

import java.io.IOException;

@WebServlet(name = "AuthServlet", value = "/auth")
public class AuthServlet extends HttpServlet {
    private final UtenteDAO utenteDAO = new UtenteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("ok".equals(request.getParameter("registered"))) {
            request.setAttribute("authInfo", "Registrazione completata. Ora puoi effettuare il login.");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("JSP/auth.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = clean(request.getParameter("action"));

        if ("register".equals(action)) {
            handleRegister(request, response);
        } else if ("logout".equals(action)) {
            handleLogout(request, response);
        } else {
            handleLogin(request, response);
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailUsername = clean(request.getParameter("emailUsername"));
        String password = request.getParameter("password");

        if (emailUsername.length() < 3 || emailUsername.length() > 30 || !isValidPassword(password)) {
            request.setAttribute("authError", "Credenziali non valide.");
            forwardToAuth(request, response);
            return;
        }

        Utente utente = utenteDAO.doRetrieveUser(emailUsername);

        if (utente == null) {
            request.setAttribute("authError", "Credenziali non valide.");
            forwardToAuth(request, response);
            return;
        }

        String hashedInput = hashPassword(password);

        if (!utente.getPassword_Ut().equals(hashedInput)) {
            request.setAttribute("authError", "Credenziali non valide.");
            forwardToAuth(request, response);
            return;
        }

        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("utenteLoggato", utente);
        session.setAttribute("usernameUtente", utente.getUsername_Ut());
        session.setMaxInactiveInterval(30 * 60);

        response.sendRedirect(request.getContextPath() + "/auth");
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = clean(request.getParameter("username"));
        String email = clean(request.getParameter("email"));
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        request.setAttribute("authMode", "register");

        if (!isValidUsername(username)) {
            request.setAttribute("authError", "Username non valido: usa 3-20 caratteri, lettere, numeri o underscore.");
            forwardToAuth(request, response);
            return;
        }

        if (!isValidEmail(email)) {
            request.setAttribute("authError", "Email non valida o troppo lunga.");
            forwardToAuth(request, response);
            return;
        }

        if (!isValidPassword(password) || !password.equals(confirmPassword)) {
            request.setAttribute("authError", "Password non valida o conferma diversa.");
            forwardToAuth(request, response);
            return;
        }

        if (utenteDAO.existsByUsername(username)) {
            request.setAttribute("authError", "Username gia registrato.");
            forwardToAuth(request, response);
            return;
        }

        if (utenteDAO.existsByEmail(email)) {
            request.setAttribute("authError", "Email gia registrata.");
            forwardToAuth(request, response);
            return;
        }

        Utente utente = new Utente();
        utente.setUsername_Ut(username);
        utente.setEmail_Ut(email);
        utente.setPassword_Ut(hashPassword(password));

        utenteDAO.doSave(utente);

        response.sendRedirect(request.getContextPath() + "/auth?registered=ok");
    }

    private void handleLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        response.sendRedirect(request.getContextPath() + "/auth");
    }

    private void forwardToAuth(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("JSP/auth.jsp");
        dispatcher.forward(request, response);
    }

    private boolean isValidPassword(String password) {
        return password != null && password.length() >= 6 && password.length() <= 40;
    }

    private String clean(String value) {
        return value == null ? "" : value.trim();
    }

    private boolean isValidUsername(String username) {
        if (username.length() < 3 || username.length() > 20) {
            return false;
        }

        for (int i = 0; i < username.length(); i++) {
            char c = username.charAt(i);
            if (!Character.isLetterOrDigit(c) && c != '_') {
                return false;
            }
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        if (email.length() < 5 || email.length() > 30 || email.contains(" ")) {
            return false;
        }

        int at = email.indexOf('@');
        int dot = email.lastIndexOf('.');
        return at > 0 && dot > at + 1 && dot < email.length() - 1;
    }

    public String hashPassword(String password) {
        try {
            MessageDigest digest =
                    MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(password.getBytes(StandardCharsets.UTF_8));
            String passwordHash = String.format("%040x", new
                    BigInteger(1, digest.digest()));
            return passwordHash;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
