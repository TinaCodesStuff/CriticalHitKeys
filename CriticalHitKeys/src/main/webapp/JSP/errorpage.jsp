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
    <%
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");   //prendo il codice di errore
        String message = (String) request.getAttribute("jakarta.servlet.error.message");    //viene impostato quando non c'è un eccezione, ma esiste un messaggio di errore preimpostato da Tomcat
    %>

    <h1 class="messaggio-errore">Errore <%=statusCode%></h1>
    <img src="${pageContext.request.contextPath}/img/errore.jpg" alt="Immagine di errore 500, dovuto al server" id="immagine-errore">
    <br>
    <% if(exception!= null){%>  <!-- si attiva quando è stata lanciata un eccezione, quindi tipo throw new Exception("messaggio") e con getMessage mostra proprio la stringa -->
        <div id="container-errore">
            <%=exception.getMessage()%>
        </div>
    <% } else if(message!= null && !message.isEmpty()){%>   <!-- questo invece si attiva quando il container Tomcat imposta un messaggio oppure viene inviato un errore con response.sendError(...) e viene impostato un messaggio all'interno della funizone -->
        <div id="container-errore">
            <%=message%>
        </div>
    <%} else {%>    <!-- quest'ultimo si attiva quando non vi è eccezione e neanche un messaggio impostato da Tomcat o sendError, quindi stampa il messaggio "Si è verificato un errore generico" -->
        <div id="container-errore">
            Si è verificato un errore generico.
        </div>
    <%}%>

    <p>Per ritornare al catalogo, premere sul logo in alto a sinistra.</p>

</div>

<div class = "footer">
    <p>© 2026 Critical Hit Keys. Tutti i diritti riservati.</p>
</div>
<script src="Burger-Vis.js"></script>
</body>
</html>
