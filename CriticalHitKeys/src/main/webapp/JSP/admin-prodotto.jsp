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
      <img src="${pageContext.request.contextPath}/img/logoExtended5.png" width="120" height="100">
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

  <c:if test="${not empty sessionScope.adminMessage}">
    <p>${sessionScope.adminMessage}</p>
    <c:remove var="adminMessage" scope="session" />
  </c:if>

  <c:if test="${not empty adminError}">
    <p>${adminError}</p>
  </c:if>

  <c:set var="editing" value="${not empty prodottoModifica}" />

  <div class="admin-layout">

  <!-- LISTA PRODOTTI -->
  <section class="product-workspace">
    <div class="section-title">
      <h2>Catalogo</h2>
    </div>

    <div class="table-scroll">
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
      <c:forEach items="${prodotti}" var="p">
        <tr>
          <td>
            <strong>${p.nome}</strong><br>
            <small>${p.casa_sviluppatrice}</small>
          </td>

          <td>${p.prezzo_scontato} €</td>

          <!-- MODALITA (stringa) -->
          <td>${p.modalita_Gioco}</td>

          <!-- PIATTAFORME (List<String>) -->
          <td>
            <c:forEach items="${p.piattaforme}" var="pl" varStatus="st">
              ${pl}<c:if test="${!st.last}"> / </c:if>
            </c:forEach>
          </td>

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

    <h2>
      <c:choose>
        <c:when test="${editing}">Modifica prodotto</c:when>
        <c:otherwise>Nuovo prodotto</c:otherwise>
      </c:choose>
    </h2>

    <form method="post"
          action="${pageContext.request.contextPath}${editing ? '/admin/prodotti/modifica' : '/admin/prodotti/crea'}"
          enctype="multipart/form-data">

      <c:if test="${editing}">
        <input type="hidden" name="id" value="${prodottoModifica.ID_Prodotto}">
      </c:if>

      <!-- NOME -->
      <input name="nome" placeholder="Nome del prodotto"
             value="${prodottoModifica.nome}" required>

      <!-- DESCRIZIONE -->
      <textarea name="descrizione" placeholder="Descrizione del prodotto" required>${prodottoModifica.descrizione}</textarea>

      <!-- CASA SVILUPPATRICE -->
      <input name="sviluppatore" placeholder="Casa sviluppatrice"
             value="${prodottoModifica.casa_sviluppatrice}" required>

      <!-- PREZZO -->
      <input type="number" step="0.01" min="0" name="prezzoOriginale"
             placeholder="Prezzo originale in euro"
             value="${prodottoModifica.prezzo_OG}" required>

      <!-- SCONTO -->
      <input type="number" min="0" max="100" name="sconto"
             placeholder="Sconto percentuale"
             value="${prodottoModifica.sconto}" required>

      <!-- MODALITA (SELECT) -->
      <label>Modalità gioco</label>
      <select name="modalita">
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
        <c:forEach items="${generi}" var="g">
          <label>
            <input type="checkbox" name="generi" value="${g.genere}">
              <!---->
              ${g.genere}
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

      <c:if test="${not editing}">
        <label>Copertina</label>
        <input type="file" name="copertina" accept="image/jpeg,image/png,image/webp" required>

        <label>Immagini aggiuntive</label>
        <input type="file" name="galleria" accept="image/jpeg,image/png,image/webp" multiple>
      </c:if>

      <button type="submit" class="submit-product">
        ${editing ? "Salva modifiche" : "Crea prodotto"}
      </button>

    </form>
  </aside>

  </div>

</main>

</body>
</html>
