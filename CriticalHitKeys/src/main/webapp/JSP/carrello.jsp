<%@ page import="model.Prodotto" %>
<%@ page import="java.util.*" %>

<%--
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
    <a href="carrello-servlet"><img src="${pageContext.request.contextPath}/img/iconaCarrello.png" width="40px" height="40px" alt="Carrello in cui sono salvati i prodotti"></a>
  </li>
</ul>
<%
  List<Prodotto> lista = (List<Prodotto>) request.getAttribute("listaProdotti");
  Map<Integer, Integer> quantita = (Map<Integer, Integer>) request.getAttribute("quantita");
%>
<div class="carrello-container">
  <h2>Il tuo carrello</h2>

  <%  //qui calcoliamo il totale dei prodotti, nel caso di carrello vuoto mostrerà 0
    double totale = 0;

    if(lista != null && quantita != null){
      for(Prodotto p : lista){
        int q = quantita.get(p.getID_Prodotto()) != null
                ? quantita.get(p.getID_Prodotto())
                : 1; //qui prende la quantità di ogni prodotto e se non è definità metterà di default 1

        totale += p.getPrezzo_scontato() * q; //qui calcola l'effettivo prezzo totale di ogni prodotto moltiplicandolo per la quanittà richiesta
      }
    }

  %>

  <div class="container-prodotti">
    <% if(lista != null && !lista.isEmpty()){ %>
    <ol>
      <% for(Prodotto p : lista){ //questo ciclo ci serve per pendere le quantità di ogni prodotto che dopo mostreremo nella pagina
        int q = 1;
        if(quantita != null && quantita.get(p.getID_Prodotto()) != null){
          q = quantita.get(p.getID_Prodotto());
        }
      %>

      <li>
        <span class="nome-prodotto"><%= p.getNome() %></span>

        <div class="prezzi">
          <span class="prezzo-originale">€ <%= p.getPrezzo_OG() %></span>
          <span class="prezzo-scontato">€ <%= p.getPrezzo_scontato() %></span>
          <span class="sconto">-<%= p.getSconto() %>%</span>
          <select name="quantita">
            <% for(int i = 1; i <= 10; i++){ //qui creiamo il select per le quantità dei prodotti, inoltre vediamo se i == q allora è lo imposta a "select", altrimenti no. Inoltre le quantità di stesso prodotto sono massimo 10%>
            <option value="<%= i %>" <%= (i == q) ? "selected" : "" %>>
              <%= i %>
            </option>
            <% } %>
          </select>
        </div>

      </li>

      <% } %>
    </ol>

    <% } else { %>

    <div class="carrello-vuoto">
      Il tuo carrello è vuoto.
    </div>
    <% } %>
    <div class="totale-carrello">
      <div class="totale-testo">
        Totale: € <%= String.format("%.2f", totale) %>
      </div>

      <button class="btn-acquisto">
        Procedi con l'acquisto
      </button>
    </div>
  </div>
</div>

<div class="footer">
  <p>© 2026 Critical Hit Keys. Tutti i diritti riservati.</p>
</div>
</body>
</html>
