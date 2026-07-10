<%@ page import="model.ChiaveDigitale" %>
<%@ page import="java.util.List" %>
<%@ page import="model.AccountGioco" %>
<%@ page import="model.ProdottoDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Acquisto Completato - Critical Hit Keys</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/pagamento.css"/>
</head>
<body>
<div class="background-sito"></div>

<ul id ="navbar">
    <li class="menu-left">
        <a href="init-servlet">
            <img src="${pageContext.request.contextPath}/img/logoExtended5.png" width="120" height="100" alt="Logo di Critical Hit Keys">
        </a>
    </li>
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
            <a href="auth">
                <img src="${pageContext.request.contextPath}/img/iconaUtente.png" width="40" height="40" alt="Profilo">
            </a>
            <a href="carrello-servlet">
                <img src="${pageContext.request.contextPath}/img/iconaCarrello.png" width="40" height="40" alt="Carrello">
            </a>
        </li>
    </div>
</ul>

<main class="pagamento-container">

    <div class="icona-success">✓</div>
    <h2>Pagamento Effettuato!</h2>
    <p>Grazie per il tuo acquisto. Sotto trovi le tue chiavi digitali pronte per l'attivazione:</p>

    <ul class="lista-chiavi">
        <li class="item-chiave">
        <%
            List<ChiaveDigitale> chiavi = (List<ChiaveDigitale>) request.getAttribute("chiaviAcquistate");
            List<AccountGioco> acc = (List<AccountGioco>) request.getAttribute("accountAcquistati");
            ProdottoDAO pd = new ProdottoDAO();
            if (chiavi != null && !chiavi.isEmpty()) {
                for (ChiaveDigitale c : chiavi) {
                    String nomeGioco = pd.doRetrieveById(c.getID_Prodotto()).getNome();
        %>
            <span class="titolo-gioco"><%= nomeGioco != null ? nomeGioco : "Prodotto #" + c.getID_Prodotto() %></span>
            <div class="codice-chiave"><%= c.getChiave() %></div>
        <%
            }
        } if (acc != null && !acc.isEmpty()) {
                for (AccountGioco a : acc) {
                    String nomeGioco = pd.doRetrieveById(a.getID_Prodotto()).getNome();
        %>
            <span class="titolo-gioco"><%= nomeGioco != null ? nomeGioco : "Prodotto #" + a.getID_Prodotto() %></span>
            <div class="codice-chiave"><%= "Nome - Credenziali:" + a.getCredenziali().replace(":", " - ") %></div>
        <% }
        }if((acc == null && acc.isEmpty()) && (chiavi == null && chiavi.isEmpty())){ %>
        </li>
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
<script src="${pageContext.request.contextPath}/Burger-Vis.js"></script>
</body>
</html>
