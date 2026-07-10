<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<html>
<head>
    <title>Pagina di errore</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/paginaErrore.css">
</head>

<body>
<div class="background-sito"></div>
<ul>
    <li class="menu-left"><a href="init-servlet"><img src="img/logoExtended5.png" width="120px" height="100px" alt="Logo piccolo di Critical Hit Keys"></a></li>

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
<div class="messaggio-box">

    <h1 class="messaggio-errore">SI É VERIFICATO UN ERRORE!</h1>
    <img src="${pageContext.request.contextPath}/img/errore.jpg" alt="Immagine di errore 500, dovuto al server" id="immagine-errore">
    <br>
    <div id="container-errore">
        <%=exception.getMessage()%>
    </div>

    <p>Per ritornare al catalogo, premere sul logo in alto a sinistra.</p>

</div>

<div class = "footer">
    <p>© 2026 Critical Hit Keys. Tutti i diritti riservati.</p>
</div>
<script src="Burger-Vis.js"></script>
</body>
</html>
