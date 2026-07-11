<%@ page import="model.Utente" %>
<%@ page import="model.Prodotto" %>
<%@ page import="model.Ticket" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
    private String escapeHtml(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;");
    }
%>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/assistenza.css"/> <%

    Prodotto prod = (Prodotto) request.getAttribute("prodotto");
    Utente u = (Utente) session.getAttribute("utenteLoggato");
%>
    <title>Assistenza Clienti</title>
</head>
<body><c:if test="${prodottoModifica.ID_Prodotto == g.ID_Prodotto}">
    checked
</c:if>
<!-- NAVBAR -->
<div class="background-sito"></div>
<ul>
    <li class="menu-left"><a href="init-servlet"><img src="${pageContext.request.contextPath}/img/logoExtended5.png" width="120px" height="100px" alt="Logo piccolo di Critical Hit Keys"></a></li>

    <button id="burger-btn" class="burger" aria-label="Apri il menu" aria-expanded="false"> <!-- Questo ci serve per la visualizzazione dei link della navbar quando riduciamo alla grafica a cellulare-->
        ☰
    </button>

    <div id="mobile-menu">
        <li class="menu-center">
            <a href="ticket-servlet">ASSISTENZA</a>
            <a href="${pageContext.request.contextPath}/aboutus.html">ABOUT US</a>
            <a href="mostraProd">CATALOGO</a>
        </li>

        <li class="menu-right">
            <a href="auth"><img src="${pageContext.request.contextPath}/img/iconaUtente.png" width="40px" height="40px" alt="Accesso per utenti/admin"></a>
            <a href="carrello-servlet"><img src="${pageContext.request.contextPath}/img/iconaCarrello.png" width="40px" height="40px" alt="Carrello in cui sono salvati i prodotti"></a>
        </li>
    </div>
</ul>
<%
    List<Ticket> listaTicket = (List<Ticket>) request.getAttribute("listaTicket");
%>
<div class="box-ticket">
    <h1>QUALCOSA É ANDATO STORTO? </h1>
    <% if (request.getAttribute("ticketError") != null) { %>
    <p style="color: #ffaaaa;"><%= escapeHtml((String) request.getAttribute("ticketError")) %></p>
    <% } %>
    <form method="post" action="ticket-servlet">
        <% if(u != null) { %>
        <input name="email-ut" type="hidden" value="<%=u.getEmail_Ut()%>">
        <input name="username-ut" type="hidden" value="<%=u.getUsername_Ut()%>">
        <% } %>

        <label for="campo-ticket">Qual è il campo in cui hai avuto una problematica?: </label>
        <input id="campo-ticket" name="campo" type="text" maxlength="20" required>

        <label for="descrizione-ticket">Descrivi la problematica:</label>
        <input id="descrizione-ticket" type="text" name="descrizione-ticket" class="descrizione-ticket" maxlength="500" required>

        <input type="submit" value="Crea ticket" class="submit-recensione">
    </form>

    <% if (listaTicket != null && !listaTicket.isEmpty()) { %>
    <h2 class="titolo-storico">I TUOI TICKET APERTI</h2>
    <div class="lista-ticket">
        <% for(Ticket ticket : listaTicket) { %>
        <div class="ticket-card">
            <strong class="ticket-ambito">Ambito: <%= escapeHtml(ticket.getCampo()) %></strong>
            <p class="ticket-descrizione"><%= escapeHtml(ticket.getDescrizioneTicket()) %></p>
        </div>
        <% } %>
    </div>
    <% } %>
</div>
<div class="footer">
    <p>© 2026 Critical Hit Keys. Tutti i diritti riservati.</p>
</div>
<script src="${pageContext.request.contextPath}/Burger-Vis.js"></script>
</body>
</html>
