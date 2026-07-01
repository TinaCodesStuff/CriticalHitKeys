<%@ page import="model.ChiaveDigitale" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Acquisto Completato - Critical Hit Keys</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/pagamento.css"/>
</head>
<body>
<div class="background-sito"></div>

<ul>
    <li class="menu-left">
        <a href="init-servlet">
            <img src="${pageContext.request.contextPath}/img/logoExtended5.png" width="120" height="100" alt="Logo di Critical Hit Keys">
        </a>
    </li>
    <li class="menu-center">
        <a href="ticket-servlet">ASSISTENZA</a>
        <a href="${pageContext.request.contextPath}/aboutus.html">ABOUT US</a>
        <a href="mostraProd">CATALOGO</a>
    </li>
    <li class="menu-right">
        <a href="auth">
            <img src="${pageContext.request.contextPath}/img/iconaUtente.png" width="40" height="40" alt="Profilo">
        </a>
        <a href="carrello-servlet">
            <img src="${pageContext.request.contextPath}/img/iconaCarrello.png" width="40" height="40" alt="Carrello">
        </a>
    </li>
</ul>

<main class="pagamento-container">

    <div class="icona-success">✓</div>
    <h2>Pagamento Effettuato!</h2>
    <p>Grazie per il tuo acquisto. Sotto trovi le tue chiavi digitali pronte per l'attivazione:</p>

    <ul class="lista-chiavi">
        <%
            List<ChiaveDigitale> chiavi = (List<ChiaveDigitale>) request.getAttribute("chiaviAcquistate");
            if (chiavi != null && !chiavi.isEmpty()) {
                for (ChiaveDigitale c : chiavi) {
                    String nomeGioco = (String) request.getAttribute("nome_" + c.getChiave());
        %>
        <li class="item-chiave">
            <span class="titolo-gioco"><%= nomeGioco != null ? nomeGioco : "Prodotto #" + c.getID_Prodotto() %></span>
            <div class="codice-chiave"><%= c.getChiave() %></div>
        </li>
        <%
            }
        } else {
        %>
        <li class="nessuna-chiave">
            <p>Nessuna chiave digitale generata per questo ordine.</p>
        </li>
        <%
            }
        %>
    </ul>

    <div class="azioni-pagamento">
        <a href="mostraProd" class="torna-catalogo">Torna al catalogo</a>
    </div>
</main>



<div class="footer">
    <p>© 2026 Critical Hit Keys. Tutti i diritti riservati.</p>
</div>
</body>
</html>
