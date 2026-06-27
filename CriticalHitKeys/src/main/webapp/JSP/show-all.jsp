<%@ page import="model.Prodotto" %>
<%@ page import="java.util.List" %>
<%@ page import="model.ProdottoDAO" %>
<%@ page import="model.Media" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/catalogo.css"/>
    <title>LISTA PRODOTTI</title>
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
ProdottoDAO service = new ProdottoDAO();
%>
<h1 class="catalogo">- Catalogo dei prodotti -</h1>
<div class="container-prodotti">
        <%
            for(Prodotto p : lista){%>
    <a href="paginaProd?id=<%=p.getID_Prodotto()%>" style="text-decoration: none;"> <!-- FACCIO COSI PERCHE' WRAPPO OGNI ELEMENTO IN UN LINK CHE SI RIFA ALLA SERVLET -->
    <div class="prodotto"><img <% if(request.getAttribute("mediaP-" + p.getID_Prodotto()) == null) {%> src="${pageContext.request.contextPath}/img/placeholder.jpg" <% } else { %> src = <%=request.getAttribute("mediaP-"+p.getID_Prodotto())%> <%}%>><div class="nome-prezzi-Box"> <b><%=p.getNome() %></b> <div class="prezzi-Box"><%=p.getPrezzo_scontato()%>€  <div class="sconto-Box"><%= p.getSconto()%>%</div></div></div></div>
    </a>
            <%}%>
</div>

<div class = "footer">
    <p>© 2026 Critical Hit Keys. Tutti i diritti riservati.</p>
</div>
<script src="${pageContext.request.contextPath}/catalogo.js"></script>
</body>
</html>
