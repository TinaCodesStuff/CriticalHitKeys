<%@ page import="model.Prodotto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="CSS/paginaProdotto.css"/> <%

    Prodotto prod = (Prodotto) request.getAttribute("prodotto");
%>
    <title><%= prod != null ? prod.getNome() : "Dettaglio Prodotto" %></title>
</head>
<body>
<div class="background-sito"></div>
<ul>
    <li class="menu-left"><a href="index.html"><img src="img/logoExtended5.png" width="120px" height="100px" alt="Logo piccolo di Critical Hit Keys"></a></li>

    <li class="menu-center">
        <a href="">ASSISTENZA</a>
        <a href="aboutus.html">ABOUT US</a>
        <a href="mostraProd">CATALOGO</a>
    </li>

    <li class="menu-right">
        <a href="auth"><img src="img/iconaUtente.png" width="40px" height="40px" alt="Accesso per utenti/admin"></a>
        <a href=""><img src="img/iconaCarrello.png" width="40px" height="40px" alt="Carrello in cui sono salvati i prodotti"></a>
    </li>
</ul>

<div class="box-prodotto">
    <% if (prod != null) { %>

    <div class="product-left">
    <div class="immagini">
        <img src="img/placeholder.jpg" style="height: 300px; width: auto;">
    </div>
    <h2> Riguardo al prodotto: </h2>
    <p> <%=prod.getDescrizione() != null ? prod.getDescrizione() : "Nessuna descrizione disponibile per questo titolo."%> </p>
    </div>

<div class="product-right">
    <h1><%= prod.getNome() %></h1>
    <span class="casa-sviluppatrice"><%= prod.getCasa_sviluppatrice() %></span>
    <span class="modalita-gioco">Modalità: <%= prod.getModalita_Gioco() %></span>

    <div class="box-sconto">
        <span class="prezzo-scontato"><%= prod.getPrezzo_scontato() %>€</span>
        <span class="prezzo-originale"><%= prod.getPrezzo_OG() %>€</span>
        <span class="sconto-tag">-<%= prod.getSconto() %>%</span>
    </div>

    <button class="aggiuntaCarrello">
        AGGIUNGI AL CARRELLO
    </button>
</div>

    <% } else { %>
    <h1>Prodotto non trovato!</h1>
    <a href="paginaProd" style="color: white;">Torna al Catalogo</a>
    <% } %>
</div>

<div class="footer">
    <p>© 2026 Critical Hit Keys. Tutti i diritti riservati.</p>
</div>
</body>
</html>