<%@ page import="model.Prodotto" %>
<%@ page import="model.Recensione" %>
<%@ page import="java.util.*" %>
<%@ page import="model.Media" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/paginaProdotto.css"/> <%

    Prodotto prod = (Prodotto) request.getAttribute("prodotto");
%>
    <title><%= prod != null ? prod.getNome() : "Dettaglio Prodotto" %></title>
</head>
<body>
<div class="background-sito"></div>
<ul>
    <li class="menu-left"><a href="${pageContext.request.contextPath}/index.html"><img src="${pageContext.request.contextPath}/img/logoExtended5.png" width="120px" height="100px" alt="Logo piccolo di Critical Hit Keys"></a></li>

    <li class="menu-center">
        <a href="">ASSISTENZA</a>
        <a href="${pageContext.request.contextPath}/aboutus.html">ABOUT US</a>
        <a href="mostraProd">CATALOGO</a>
    </li>

    <li class="menu-right">
        <a href="auth"><img src="${pageContext.request.contextPath}/img/iconaUtente.png" width="40px" height="40px" alt="Accesso per utenti/admin"></a>
        <a href=""><img src="${pageContext.request.contextPath}/img/iconaCarrello.png" width="40px" height="40px" alt="Carrello in cui sono salvati i prodotti"></a>
    </li>
</ul>

<div class="box-prodotto">
    <% if (prod != null) { %>

    <div class="product-left">
            <%
    List<Media> listaMedia = (List<Media>) request.getAttribute("listaMedia");
%>

        <div class="carousel-container">
            <div class="carousel-track">
                <%
                    if (listaMedia != null && !listaMedia.isEmpty()) {
                        for (Media m : listaMedia) {
                %>
                <div class="carousel-item">
                    <% if ("video".equals(m.getTipo())) { %>
                    <video src="<%= m.getUrlMedia() %>" controls preload="metadata"></video>
                    <% } else { %>
                    <img src="<%= m.getUrlMedia() %>" alt="Screenshot Gioco">
                    <% } %>
                </div>
                <%
                    }
                } else {
                %>
                <div class="carousel-item">
                    <img src="img/placeholder.jpg" alt="Placeholder">
                </div>
                <% } %>
            </div>
    <h2> Riguardo al prodotto: </h2>
    <p> <%=prod.getDescrizione() != null ? prod.getDescrizione() : "Nessuna descrizione disponibile per questo titolo."%> </p>
    </div>
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


    <div class="product-center">
        <button type="button" onclick="mostraTextArea()">Inserisci una recensione! </button><br>
        <form method="get">
            <div id = "boxTextarea" style="display: none">
            <textarea name="testoRecensione">Questo gioco è stato molto toccante per me...</textarea>
                <input type = "submit" value = "Invia recensione!">
            </div>
        </form>

        <% List<Recensione> lista = (List<Recensione>) request.getAttribute("listaRecensione"); %>

        <% for(Recensione r : lista){%>
        <p><%=r.getUsername_Ut()%></p>
        <p><%=r.getVoto()%></p>
        <p><%=r.getDescrizione_Rec()%></p>
        <%}%>
    </div>
</div>

    <% } else { %>
    <h1>Prodotto non trovato!</h1>
    <a href="paginaProd" style="color: white;">Torna al Catalogo</a>
    <% } %>
</div>

<div class="footer">
    <p>© 2026 Critical Hit Keys. Tutti i diritti riservati.</p>
</div>

<script>
    function mostraTextArea(){
        const box = document.getElementById("boxTextarea");

        if(box.style.display === "none" || box.style.display === ""){
            box.style.display = "block";
        } else {
            box.style.display = "none";
        }
    }
</script>
</body>
</html>