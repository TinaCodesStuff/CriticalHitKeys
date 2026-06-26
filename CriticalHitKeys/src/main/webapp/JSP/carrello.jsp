<%@ page import="model.Prodotto" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: luigikarolsorrentino
  Date: 26/06/2026
  Time: 21:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Carrello</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/carrello.css"/>
</head>
<body>

<div class="background-sito"></div>

<ul>
  <li class="menu-left"><a href="${pageContext.request.contextPath}/index.html"><img src="${pageContext.request.contextPath}/img/logoExtended5.png" width="120px" height="100px" alt="Logo piccolo di Critical Hit Keys"></a></li>

  <li class="menu-center">
    <a href="ticket-servlet">ASSISTENZA</a>
    <a href="${pageContext.request.contextPath}/aboutus.html">ABOUT US</a>
    <a href="mostraProd">CATALOGO</a>
  </li>

  <li class="menu-right">
    <a href="auth"><img src="${pageContext.request.contextPath}/img/iconaUtente.png" width="40px" height="40px" alt="Accesso per utenti/admin"></a>
    <a href=""><img src="${pageContext.request.contextPath}/img/iconaCarrello.png" width="40px" height="40px" alt="Carrello in cui sono salvati i prodotti"></a>
  </li>
</ul>
<%
  List<Prodotto> lista = (List<Prodotto>) request.getAttribute("listaProdotti");%>
<div class="carrello-container">

  <h2>Il tuo carrello</h2>

  <div class="container-prodotti">
    <% if(lista != null && !lista.isEmpty()){ %>
    <ol>
      <% for(Prodotto p : lista){ %>
      <li>
        <span class="nome-prodotto"><%= p.getNome() %></span>
        <div class="prezzi">
          <span class="prezzo-originale">€ <%= p.getPrezzo_OG() %></span>
          <span class="prezzo-scontato">€ <%= p.getPrezzo_scontato() %></span>
          <span class="sconto">-<%= p.getSconto() %>%</span>
        </div>
      </li>
      <% } %>
    </ol>
    <% } else { %>
    <div class="carrello-vuoto">
      Il tuo carrello è vuoto.
    </div>
    <% } %>
  </div>

</div>

<div class="footer">
  <p>© 2026 Critical Hit Keys. Tutti i diritti riservati.</p>
</div>
</body>
</html>
