<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Gestione utenti - Critical Hit Keys</title>
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
      <h1>Gestione utenti</h1>
    </div>
  </header>

  <section class="product-workspace">
    <div class="section-title admin-users-title">
      <h2>Carrelli e ordini</h2>
    </div>

    <div class="table-scroll">
      <table>
        <thead>
        <tr>
          <th>Utente</th>
          <th>Carrello attuale</th>
          <th>Ordini passati</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${utentiInfo}" var="info">
          <tr>
            <td>
              <strong><c:out value="${info.utente.username_Ut}" /></strong><br>
              <small><c:out value="${info.utente.email_Ut}" /></small>
            </td>

            <td>
              <c:choose>
                <c:when test="${empty info.prodottiCarrello}">
                  <span>Carrello vuoto</span>
                </c:when>
                <c:otherwise>
                  <ol>
                    <c:forEach items="${info.prodottiCarrello}" var="riga">
                      <li>
                        <c:out value="${riga.prodotto.nome}" />
                        - quantità: <c:out value="${riga.quantita}" />
                        - totale: <fmt:formatNumber value="${riga.prodotto.prezzo_scontato * riga.quantita}" minFractionDigits="2" maxFractionDigits="2" /> €
                      </li>
                    </c:forEach>
                  </ol>
                </c:otherwise>
              </c:choose>
            </td>

            <td>
              <c:choose>
                <c:when test="${empty info.ordini}">
                  <span>Nessun ordine</span>
                </c:when>
                <c:otherwise>
                  <ol>
                    <c:forEach items="${info.ordini}" var="ordine">
                      <li>
                        Ordine #<c:out value="${ordine.ID_Ordine}" /> -
                        <c:out value="${ordine.data_Ordine}" /> -
                        <fmt:formatNumber value="${ordine.importoTot}" minFractionDigits="2" maxFractionDigits="2" /> €
                        <br>
                        <small><c:out value="${ordine.descrizione_Acquisto}" /></small>
                      </li>
                    </c:forEach>
                  </ol>
                </c:otherwise>
              </c:choose>
            </td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </div>
  </section>

</main>

</body>
</html>
