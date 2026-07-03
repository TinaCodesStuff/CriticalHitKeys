<%@ page import="model.Utente" %>
<%@ page import="model.Prodotto" %>
<%@ page import="model.Ticket" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

    <li class="menu-center">
        <a href="ticket-servlet">ASSISTENZA</a>
        <a href="${pageContext.request.contextPath}/aboutus.html">ABOUT US</a>
        <a href="mostraProd">CATALOGO</a>
    </li>

    <li class="menu-right">
        <a href="auth"><img src="${pageContext.request.contextPath}/img/iconaUtente.png" width="40px" height="40px" alt="Accesso per utenti/admin"></a>
        <a href="carrello-servlet"><img src="${pageContext.request.contextPath}/img/iconaCarrello.png" width="40px" height="40px" alt="Carrello in cui sono salvati i prodotti"></a>
    </li>
</ul>
<%
    List<Ticket> listaTicket = (List<Ticket>) request.getAttribute("listaTicket");
%>
<div class="box-ticket">
    <h1>QUALCOSA É ANDATO STORTO? </h1>
    <form method="post" action="ticket-servlet">
        <% if(u != null) { %>
        <input name="email-ut" type="hidden" value="<%=u.getEmail_Ut()%>">
        <input name="username-ut" type="hidden" value="<%=u.getUsername_Ut()%>">
        <% } %>

        <label>Qual è il campo in cui hai avuto una problematica?: </label>
        <input name="campo" type="text" required>

        <label>Descrivi la problematica:</label>
        <input type="text" name="descrizione-ticket" class="descrizione-ticket" required>

        <input type="submit" value="Crea ticket" class="submit-recensione">
    </form>

    <% if (listaTicket != null && !listaTicket.isEmpty()) { %>
    <h2 class="titolo-storico">I TUOI TICKET APERTI</h2>
    <div class="lista-ticket">
        <% for(Ticket ticket : listaTicket) { %>
        <div class="ticket-card">
            <strong class="ticket-ambito">Ambito: <%= ticket.getCampo() %></strong>
            <p class="ticket-descrizione"><%= ticket.getDescrizioneTicket() %></p>
        </div>
        <% } %>
    </div>
    <% } %>
</div>
</body>
</html>
