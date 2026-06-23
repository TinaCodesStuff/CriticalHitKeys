<%@ page import="model.Prodotto" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="CSS/catalogo.css"/>
    <title>LISTA PRODOTTI</title>
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
        <a href=""><img src="img/iconaUtente.png" width="40px" height="40px" alt="Accesso per utenti/admin"></a>
        <a href=""><img src="img/iconaCarrello.png" width="40px" height="40px" alt="Carrello in cui sono salvati i prodotti"></a>
    </li>
</ul>
<%
List<Prodotto> lista = (List<Prodotto>) request.getAttribute("listaProdotti");

%>
<h1 class="catalogo">- Catalogo dei prodotti -</h1>
<div class="container-prodotti">
        <%for(Prodotto p : lista){%>
    <div class="prodotto"><img src="img/placeholder.jpg"><div class="nome-prezzi-Box"> <b><%=p.getNome() %></b> <div class="prezzi-Box"><%=p.getPrezzo_scontato()%>€  <div class="sconto-Box"><%= p.getSconto()%>%</div></div></div></div>
        <%}%>
</div>

<div class = "footer">
    <p>© 2026 Critical Hit Keys. Tutti i diritti riservati.</p>
</div>
</body>
</html>
