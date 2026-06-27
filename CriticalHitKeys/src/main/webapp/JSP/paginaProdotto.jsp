<%@ page import="model.Prodotto" %>
<%@ page import="model.Recensione" %>
<%@ page import="java.util.*" %>
<%@ page import="model.Media" %>
<%@ page import="model.Utente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/paginaProdotto.css"/> <%

    Prodotto prod = (Prodotto) request.getAttribute("prodotto");
    Utente u = (Utente) session.getAttribute("utenteLoggato");
%>
    <title><%= prod != null ? prod.getNome() : "Dettaglio Prodotto" %></title>
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

<div class="box-prodotto">

    <!-- CAROSELLO DELLE IMMAGINI INERENTI AL VIDEOGIOCO -->
    <div class="product-left">
        <% if (prod != null) { %>
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
                    <video src="<%= m.getUrlMedia() %>" controls preload="metadata" class="carosello-media"></video>
                    <% } else { %>
                    <img src="<%= m.getUrlMedia() %>" alt="Screenshot Gioco" class="carosello-media">
                    <% } %>
                </div>
                <%
                    }
                } else { //Se non viene trovato un media adatto, viene sostituito con un placeholder
                %>
                <div class="carousel-item">
                    <img src="img/placeholder.jpg" alt="Placeholder">
                </div>
                <% } %>
                <!-- I bottoni per scorrere il carosello -->
            </div> <button class="carousel-btn prev-btn">&#10094;</button>
            <button class="carousel-btn next-btn">&#10095;</button>

        </div> <div class="descrizione-gioco-box">
        <h2>Riguardo al prodotto:</h2>
        <p class="descrizione-prodotto">
            <%= prod.getDescrizione() != null ? prod.getDescrizione() : "Nessuna descrizione disponibile per questo titolo." %>
        </p>
    </div>

    </div>
<!-- Tutte le informazioni sul gioco si trovano a destra -->
<div class="product-right">
    <h1><%= prod.getNome() %></h1>
    <span class="casa-sviluppatrice"><%= prod.getCasa_sviluppatrice() %></span>
    <span class="modalita-gioco">Modalità: <%= prod.getModalita_Gioco() %></span>

    <div class="box-sconto">
        <span class="prezzo-scontato"><%= prod.getPrezzo_scontato() %>€</span>
        <span class="prezzo-originale"><%= prod.getPrezzo_OG() %>€</span>
        <span class="sconto-tag">-<%= prod.getSconto() %>%</span>
    </div>

    <form action="carrello-servlet" method="GET">
        <input type="hidden" name="id_prod" value="<%= prod.getID_Prodotto() %>">
        <button type="submit" class="aggiuntaCarrello">
            AGGIUNGI AL CARRELLO
        </button>
    </form>


    <div class="product-center">
        <button type="button" class="bottone-recensione" onclick="mostraTextArea()">Inserisci una recensione! </button><br>
        <form method="post" action="recensione-servlet">
            <div id = "boxTextarea" style="display: none">
            <textarea name="testoRecensione">Questo gioco è stato molto toccante per me...</textarea>
                <label>Voto complessivo:</label>
                <input name="voto" type="number" min="1" max="5" step="1" value="1" >
                <% if(u != null) { %>
                <input  name ="id_ut" type="hidden" value = <%=u.getEmail_Ut()%>>
                <input  name ="username_ut" type="hidden" value = <%=u.getUsername_Ut()%>>
                <% } %>
                <input name ="prodotto" type="hidden" value = <%=request.getAttribute("prodotto")%>>
                <%
                    HttpSession sess = request.getSession();
                    //Creo la sessione per salvarmi il contesto della pagina quindi il prodotto stesso e i media per ricaricarli dopo aver aggiunto la recensione
                    sess.setAttribute("prodotto-afterRecensione", prod);
                    sess.setAttribute("media-afterRecensione", request.getAttribute("listaMedia"));
                %>
                <input type = "submit" value = "Invia recensione!" class="submit-recensione">
            </div>
        </form>

        <div class="reviews-list">
            <%
                List<Recensione> lista = (List<Recensione>) request.getAttribute("listaRecensione");
                if (lista != null && !lista.isEmpty()) {
                    for(Recensione r : lista) {
            %>
            <div class="recensione-card">
                <div class="recensione-header">
                    <span class="username-utente"><%= r.getUsername_Ut() %></span>
                    <span class="voto-recensione">★ <%= r.getVoto() %>/5</span>
                </div>
                <p class="descrizione-recensione"><%= r.getDescrizione_Rec() %></p>
            </div>
            <%
                }
            } else {
            %>
            <p style="color: #aaa; font-style: italic;">Nessuna recensione per questo gioco. Sii il primo a inserirne una!</p>
            <% } %>
        </div>
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
<script src="${pageContext.request.contextPath}/caroselloImmagini.js"></script>
</body>
</html>