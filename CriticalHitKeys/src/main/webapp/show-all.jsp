<%@ page import="model.Prodotto" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: teamt
  Date: 15/05/2026
  Time: 16:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>LISTA PRODOTTI</title>
</head>
<body>
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
