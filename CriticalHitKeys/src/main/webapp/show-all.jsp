<%@ page import="model.Prodotto" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="CSS/catalogo.css"/>
    <title>LISTA PRODOTTI</title>
</head>
<body>
<ul>
    <li class="menu-left"><a href=""><img src="img/logoProv.png" width="40px" height="40px" alt="Logo piccolo di Critical Hit Keys"></a></li>

    <li class="menu-center">
        <a href="">ASSISTENZA</a>
        <a href="">ABOUT US</a>
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

<ul>
    <%for(Prodotto p : lista){%>
    <li><%=p.getNome() %> <%=p.getDescrizione()%>  <%=p.getID_Prodotto()%>  <%=p.getPrezzo_OG()%>   <%=p.getPrezzo_scontato()%>  <%= p.getSconto()%></li>
    <%}%>
</ul>
</body>
</html>
