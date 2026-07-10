<%@ page import="model.Prodotto" %>
<%@ page import="java.util.List" %>
<%@ page import="model.ProdottoDAO" %>
<%@ page import="model.Media" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/ricerca.css"/>
    <title>LISTA PRODOTTI</title>
</head>
<body>
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
    List<Prodotto> lista = (List<Prodotto>) request.getAttribute("listaProdotti");
%>
<h1 class="catalogo">CATALOGO DEI PRODOTTI</h1>
<aside class="sidebar-filtri">
    <h2>Filtri - Ricerca Avanzata</h2>

    <h3>Prezzo</h3>

    <form method="get" action="ricerca-servlet">
        <p>Min: <span id="minValue">0</span>€</p>
        <input type="range" id="prezzoMin" min="0" max="100" value="0" name="min">

        <p>Max: <span id="maxValue">100</span>€</p>
        <input type="range" id="prezzoMax" min="0" max="100" value="100" name = "max">

        <p>Prezzo selezionato:</p>
        <p><strong><span id="actualMin">0</span>€ - <span id="actualMax">100</span>€</strong></p>

        <h3>Modalità Gioco</h3>
        <label><input type="radio" name="mod_gioco" value="Single Player"> Single Player</label>
        <label><input type="radio" name="mod_gioco" value="Multiplayer"> Multi Player</label>
        <label><input type="radio" name="mod_gioco" value="Single/Multi"> Single / Multi Player</label>

        <h3>Casa Sviluppatrice</h3>
        <label>Casa Sviluppatrice - Esempio: FromSoftware <br><input type="text" name="casa_svilupp"></label>

        <h3>Genere</h3>
        <%
            List<String> generi = (List<String>) request.getAttribute("listaGeneri");
            for(String g : generi){ %>
                <label><input type="radio" value = "<%=g%>" name="genere"> <%=g%></label>
            <%}
        %>

        <input type="submit" value="Applica filtri" class="btn-filtri">

    </form>
</aside>
<div class="container-bottoneCerca">
    <form>
        <input type="search" name="searchText" placeholder="Cerca un prodotto!">
        <input type="submit" value="Cerca" id="bottoneInvioRicerca">
    </form>
</div>

<div class="container-prodotti">
    <%  if(lista != null && lista.size()>0){
        for(Prodotto p : lista){%>
    <a href="paginaProd?id=<%=p.getID_Prodotto()%>" style="text-decoration: none;"> <!-- FACCIO COSI PERCHE' WRAPPO OGNI ELEMENTO IN UN LINK CHE SI RIFA ALLA SERVLET -->
        <div class="prodotto"><img <% if(request.getAttribute("mediaP-" + p.getID_Prodotto()) == null) {%> src="${pageContext.request.contextPath}/img/placeholder.jpg" <% } else { %> src = <%=request.getAttribute("mediaP-"+p.getID_Prodotto())%> <%}%>><div class="nome-prezzi-Box"> <b><%=p.getNome() %></b> <div class="prezzi-Box"><%=p.getPrezzo_scontato()%>€  <div class="sconto-Box"><%= p.getSconto()%>%</div></div></div></div>
    </a>
    <%}} else{%>
    <h2 style="margin-top:150px; color:white; text-align:center; font-size:40px;">
        Nessun prodotto trovato
    </h2>
    <%}%>
</div>

<div class = "footer">
    <p>© 2026 Critical Hit Keys. Tutti i diritti riservati.</p>
</div>
<script src="${pageContext.request.contextPath}/catalogo.js"></script>
<script src="${pageContext.request.contextPath}/ControlloAggiornamentoPrezzo.js"></script>
<script src="${pageContext.request.contextPath}/Burger-Vis.js"></script>
</body>
</html>
