<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Gestione prodotti - Critical Hit Keys</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admin-prodotti.css">
</head>

<body>

<div class="background-sito"></div>

<ul class="navbar">
  <li class="menu-left">
    <a href="${pageContext.request.contextPath}/init-servlet">
      <img src="${pageContext.request.contextPath}/img/logoExtended5.png" width="120" height="100" alt="Logo di Critical Hit Keys">
    </a>
  </li>

  <li class="menu-center">
    <a href="${pageContext.request.contextPath}/mostraProd">CATALOGO</a>
    <a href="${pageContext.request.contextPath}/admin/prodotti">GESTIONE PRODOTTI</a>
    <a href="${pageContext.request.contextPath}/admin/utenti">GESTIONE UTENTI</a>
  </li>

  <li class="menu-right">
    <span>${sessionScope.usernameAmministratore}</span>
    <form action="${pageContext.request.contextPath}/auth" method="post">
      <input type="hidden" name="action" value="logout">
      <button type="submit">Logout</button>
    </form>
  </li>
</ul>

<main class="admin-main">

  <header class="admin-heading">
    <div>
      <h1>Gestione prodotti</h1>
    </div>
  </header>

<!-- Serve per stampare un messaggio dopo una delete/create/update, e poi eliminarlo dalla sessione, c è il tag normale dell'expression language-->
  <c:if test="${not empty sessionScope.adminMessage}">
    <p>${sessionScope.adminMessage}</p>
    <c:remove var="adminMessage" scope="session" />
  </c:if>
<!-- Controllo per gli errori, se avvengono vengono stampati qui -->
  <c:if test="${not empty adminError}">
    <p>${adminError}</p>
  </c:if>

  <c:set var="editing" value="${not empty prodottoModifica}" />

  <div class="admin-layout">

  <!-- LISTA PRODOTTI - Visualizzazione -->
  <section class="product-workspace">
    <div class="section-title">
      <h2>Catalogo</h2>
    </div>

    <div class="table-scroll">
<!-- Viene creata una tabella per tutti i prodotti, thead è l'header della tabella, mentre tr è ciascuna riga della tabella -->
    <table>
      <thead>
      <tr>
        <th>Nome</th>
        <th>Prezzo</th>
        <th>Modalità</th>
        <th>Piattaforme</th>
        <th>Azioni</th>
      </tr>
      </thead>

      <tbody>
      <!-- Si prendono i prodotti tramite l'attributo della richiesta dalla servlet admin -->
      <c:forEach items="${prodotti}" var="p">
        <tr>
          <td>
            <strong>${p.nome}</strong><br>
            <small>${p.casa_sviluppatrice}</small>
          </td>

          <td>${p.prezzo_scontato} €</td>

          <!-- MODALITA (stringa) -->
          <td>${p.modalita_Gioco}</td>

          <!-- PIATTAFORME (List<String>), sono prese dalla request di prodotti con i nomi dei campi piattaforme -->
          <td>
            <c:forEach items="${p.piattaforme}" var="pl" varStatus="st">
              ${pl}<c:if test="${!st.last}"> / </c:if>
            </c:forEach>
          </td>
<!-- Rimanda l'id prodotto in modalità editor, con il fragment, alla AdminProductServlet -->
          <td class="actions">
            <a href="${pageContext.request.contextPath}/admin/prodotti?edit=${p.ID_Prodotto}#editor">
              Modifica
            </a>
            <form method="post"
                  action="${pageContext.request.contextPath}/admin/prodotti/rimuovi"
                  onsubmit="return confirm('Vuoi rimuovere questo prodotto?');">
              <input type="hidden" name="id" value="${p.ID_Prodotto}">
              <button type="submit">Rimuovi</button>
            </form>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
    </div>
  </section>

  <!-- FORM EDIT / CREATE -->
  <aside id="editor" class="editor">
<!-- Check per verificare se si è in modifica o in creazione, tramite la variabile booleana editing -->
    <h2>
      <c:choose>
        <c:when test="${editing}">Modifica prodotto</c:when>
        <c:otherwise>Nuovo prodotto</c:otherwise>
      </c:choose>
    </h2>
<!-- Qui c'è l'effettiva verifica del valore di editing, e l'action dipende da quello -->
    <form method="post"
          action="${pageContext.request.contextPath}${editing ? '/admin/prodotti/modifica' : '/admin/prodotti/crea'}"
          enctype="multipart/form-data">

      <c:if test="${editing}">
        <input type="hidden" name="id" value="${prodottoModifica.ID_Prodotto}">
      </c:if>

      <!-- NOME -->
      <label for="nome-prodotto">Nome prodotto</label>
      <input id="nome-prodotto" name="nome" placeholder="Nome del prodotto"
             value="${prodottoModifica.nome}" required>

      <!-- DESCRIZIONE -->
      <label for="descrizione-prodotto">Descrizione prodotto</label>
      <textarea id="descrizione-prodotto" name="descrizione" placeholder="Descrizione del prodotto" required>${prodottoModifica.descrizione}</textarea>

      <!-- CASA SVILUPPATRICE -->
      <label for="sviluppatore-prodotto">Casa sviluppatrice</label>
      <input id="sviluppatore-prodotto" name="sviluppatore" placeholder="Casa sviluppatrice"
             value="${prodottoModifica.casa_sviluppatrice}" required>

      <!-- PREZZO -->
      <label for="prezzo-originale">Prezzo originale</label>
      <input id="prezzo-originale" type="number" step="0.01" min="0" name="prezzoOriginale"
             placeholder="Prezzo originale in euro"
             value="${prodottoModifica.prezzo_OG}" required>

      <!-- SCONTO -->
      <label for="sconto-prodotto">Sconto percentuale</label>
      <input id="sconto-prodotto" type="number" min="0" max="100" name="sconto"
             placeholder="Sconto percentuale"
             value="${prodottoModifica.sconto}" required>

      <!-- MODALITA (SELECT) -->
      <label for="modalita-prodotto">Modalità gioco</label>
      <select id="modalita-prodotto" name="modalita">
        <c:forEach items="${modalita}" var="m">
          <option value="${m}"
                  <c:if test="${prodottoModifica.modalita_Gioco == m}">selected</c:if>>
              ${m}
          </option>
        </c:forEach>
      </select>

      <!-- GENERI -->
      <fieldset>
        <legend>Generi</legend>

        <div class="checks">
            <!--
            facciamo un foreach per i generi perchè ne possiamo avere banalmente più di uno

            -->
        <c:forEach items="${generi}" var="g">
          <label>
            <input type="radio" name="genere" value="${g}"
              <c:if test="${prodottoModifica.genere == g}">checked</c:if>>
              ${g}
          </label>
        </c:forEach>
        </div>
      </fieldset>

      <!-- PIATTAFORME -->
      <fieldset>
        <legend>Piattaforme</legend>

        <div class="checks">
        <c:forEach items="${piattaforme}" var="p">
          <label>
            <input type="checkbox"
                   name="piattaforme"
                   value="${p}"
            >
              ${p}
          </label>
        </c:forEach>
        </div>
      </fieldset>

        <!-- Non puoi modificare la copertina del prodotto/galleria, possiamo SOLO modificarla in aggiungi
        Come scritto in galleria-prodotto possiamo anche aggiungere più foto.-->
      <c:if test="${not editing}">
        <label for="copertina-prodotto">Copertina</label>
        <input id="copertina-prodotto" type="file" name="copertina" accept="image/jpeg,image/png,image/webp" required>

        <label for="galleria-prodotto">Immagini aggiuntive</label>
        <input id="galleria-prodotto" type="file" name="galleria" accept="image/jpeg,image/png,image/webp" multiple>
      </c:if>

        <!-- Questo bottone cambia il suo valore con "Salva modifiche" oppure con "Crea prodotto"
        in caso di editing true/false-->
      <button type="submit" class="submit-product">
        ${editing ? "Salva modifiche" : "Crea prodotto"}
      </button>

    </form>
  </aside>

  </div>

</main>

</body>
</html>
