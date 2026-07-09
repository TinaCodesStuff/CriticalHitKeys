<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="model.Prodotto" %>
<%@ page import="java.util.List" %>


<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>HomePage</title>
    <link rel="stylesheet" href="./CSS/homepage.css">
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100..900;1,100..900&display=swap');
    </style>
</head>
<body>
<div class="background-sito"></div>
    <ul>
        <li class="menu-left"><a href="${pageContext.request.contextPath}/init-servlet"><img src="img/logoExtended5.png" width="120px" height="100px" alt="Logo piccolo di Critical Hit Keys"></a></li>
        <button id="burger-btn" class="burger" aria-label="Apri il menu" aria-expanded="false"> <!-- Questo ci serve per la visualizzazione dei link della navbar quando riduciamo alla grafica a cellulare-->
            ☰
        </button>
        <div id="mobile-menu">
            <li class="menu-center">
                <a href="ticket-servlet">ASSISTENZA</a>
                <a href="aboutus.html">ABOUT US</a>
                <a href="mostraProd">CATALOGO</a>
            </li>

            <li class="menu-right">
                <a href="auth"><img src="img/iconaUtente.png" width="40px" height="40px" alt="Accesso per utenti/admin"></a>
                <a href="carrello-servlet"><img src="img/iconaCarrello.png" width="40px" height="40px" alt="Carrello in cui sono salvati i prodotti"></a>
            </li>
        </div>
    </ul>


    <img id="logo-img" src="img/logoExtended5.png" alt="Logo esteso di Critical Hit Keys">
    <div class="container-bottoneCerca">


        <form method="GET" id ="searchForm">
            <input type="button" name="buttonFiltri" value="Filtri" onclick="window.location.href='ricerca-servlet'">   <!--Riporta alla ricerca con i filtri, quindi salta la ricerca-->
            <input type="search" name="searchText" placeholder="Cerca un prodotto!">
            <input type="submit" value="Cerca" formaction="ricerca-servlet">
        </form>
    </div>
    <% List<Prodotto> lista = (List<Prodotto>) request.getAttribute("listaSuggested");%>
    <h1 class="suggested">PRODOTTI CONSIGLIATI</h1>
    <div class="container-prodotti">
    <% for(Prodotto p : lista){%>
        <a href="paginaProd?id=<%=p.getID_Prodotto()%>" style="text-decoration: none;"> <!-- FACCIO COSI PERCHE' WRAPPO OGNI ELEMENTO IN UN LINK CHE SI RIFA ALLA SERVLET -->
            <div class="prodotto"><img

                    <% if(request.getAttribute("mediaP-" + p.getID_Prodotto()) == null) {%>
                    src="${pageContext.request.contextPath}/img/placeholder.jpg"
                    <% } else { %>
                    src = <%=request.getAttribute("mediaP-"+p.getID_Prodotto())%>
                    <%}%>>

                <div class="nome-prezzi-Box"> <b><%=p.getNome() %></b> <div class="prezzi-Box"><%=p.getPrezzo_scontato()%>€  <div class="sconto-Box"><%= p.getSconto()%>%</div></div></div></div>

        </a>
    <%}%>
    <div class = "footer">
        <p>© 2026 Critical Hit Keys. Tutti i diritti riservati.</p>
    </div>
    <script src="catalogo.js"></script>
    <script src="Burger-Vis.js"></script>
</body>
</html>
