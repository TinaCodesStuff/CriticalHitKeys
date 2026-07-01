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
    <li class="menu-left"><a href="${pageContext.request.contextPath}/init-servlet"><img src="${pageContext.request.contextPath}/img/logoExtended5.png" width="120" height="100" alt="Critical Hit Keys"></a></li>
    <li class="menu-center"><a href="${pageContext.request.contextPath}/mostraProd">CATALOGO</a><a href="${pageContext.request.contextPath}/admin/prodotti" aria-current="page">GESTIONE PRODOTTI</a></li>
    <li class="menu-right">
        <span>${sessionScope.usernameAmministratore}</span>
        <form action="${pageContext.request.contextPath}/auth" method="post"><input type="hidden" name="action" value="logout"><button type="submit">Logout</button></form>
    </li>
</ul>

<main class="admin-main">
    <header class="admin-heading">
        <div><p class="eyebrow">AREA AMMINISTRATORE</p><h1>Gestione prodotti</h1><p>Carica nuovi titoli, aggiorna prezzi e controlla la disponibilità.</p></div>
        <a class="primary-action" href="${pageContext.request.contextPath}/admin/prodotti#editor">+ Nuovo prodotto</a>
    </header>

    <c:if test="${not empty adminError}"><p class="notice notice-error" role="alert">${adminError}</p></c:if>
    <c:if test="${param.message == 'created'}"><p class="notice" role="status">Prodotto creato correttamente.</p></c:if>
    <c:if test="${param.message == 'updated'}"><p class="notice" role="status">Prodotto aggiornato correttamente.</p></c:if>
    <p class="notice" id="ajax-notice" role="status" hidden></p>

    <div class="admin-layout">
        <section class="product-workspace" aria-labelledby="products-title">
            <div class="section-title"><h2 id="products-title">Catalogo completo</h2><span>${prodotti.size()} prodotti</span></div>
            <div class="table-scroll">
                <table>
                    <thead><tr><th>Prodotto</th><th>Prezzo</th><th>Classificazione</th><th>Stato</th><th>Azioni</th></tr></thead>
                    <tbody id="products-body">
                    <c:forEach items="${prodotti}" var="prodotto">
                        <tr data-product-id="${prodotto.ID_Prodotto}" class="${prodotto.disponibile ? '' : 'is-unavailable'}">
                            <td><strong>${prodotto.nome}</strong><small>${prodotto.casa_sviluppatrice}</small></td>
                            <td><strong>${prodotto.prezzo_scontato} €</strong><small>${prodotto.sconto}% di sconto</small></td>
                            <td><small>${prodotto.generi}</small><small>${prodotto.piattaforme}</small></td>
                            <td><span class="status ${prodotto.disponibile ? 'available' : 'unavailable'}">${prodotto.disponibile ? 'Disponibile' : 'Non disponibile'}</span></td>
                            <td class="actions"><a href="${pageContext.request.contextPath}/admin/prodotti?edit=${prodotto.ID_Prodotto}#editor">Modifica</a><button type="button" class="remove-product" data-id="${prodotto.ID_Prodotto}" data-name="${prodotto.nome}">Rimuovi</button></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </section>

        <aside class="editor" id="editor">
            <c:set var="editing" value="${not empty prodottoModifica}"/>
            <div class="section-title"><h2>${editing ? 'Modifica prodotto' : 'Nuovo prodotto'}</h2><c:if test="${editing}"><a href="${pageContext.request.contextPath}/admin/prodotti#editor">Annulla</a></c:if></div>
            <form id="product-form" action="${pageContext.request.contextPath}${editing ? '/admin/prodotti/modifica' : '/admin/prodotti/crea'}" method="post" ${editing ? '' : 'enctype="multipart/form-data"'}>
                <c:if test="${editing}"><input type="hidden" name="id" value="${prodottoModifica.ID_Prodotto}"></c:if>
                <label for="nome">Nome</label><input id="nome" name="nome" value="${prodottoModifica.nome}" minlength="2" maxlength="30" required>
                <label for="descrizione">Descrizione</label><textarea id="descrizione" name="descrizione" minlength="20" required>${prodottoModifica.descrizione}</textarea>
                <label for="sviluppatore">Casa sviluppatrice</label><input id="sviluppatore" name="sviluppatore" value="${prodottoModifica.casa_sviluppatrice}" minlength="2" maxlength="30" required>
                <div class="price-row">
                    <div><label for="prezzoOriginale">Prezzo originale</label><input id="prezzoOriginale" name="prezzoOriginale" type="number" min="0.01" max="9999" step="0.01" value="${prodottoModifica.prezzo_OG}" required></div>
                    <div><label for="sconto">Sconto %</label><input id="sconto" name="sconto" type="number" min="0" max="100" value="${prodottoModifica.sconto}" required></div>
                </div>
                <p class="price-preview">Prezzo finale: <strong id="final-price">0,00 €</strong></p>

                <fieldset><legend>Generi</legend><div class="checks"><c:forEach items="${generi}" var="genere"><label><input type="checkbox" name="generi" value="${genere}" ${prodottoModifica.generi.contains(genere) ? 'checked' : ''}>${genere}</label></c:forEach></div></fieldset>
                <fieldset><legend>Piattaforme</legend><div class="checks"><c:forEach items="${piattaforme}" var="piattaforma"><label><input type="checkbox" name="piattaforme" value="${piattaforma}" ${prodottoModifica.piattaforme.contains(piattaforma) ? 'checked' : ''}>${piattaforma}</label></c:forEach></div></fieldset>
                <fieldset><legend>Modalità</legend><div class="checks"><c:forEach items="${modalita}" var="modo"><label><input type="checkbox" name="modalita" value="${modo}" ${prodottoModifica.modalita.contains(modo) ? 'checked' : ''}>${modo}</label></c:forEach></div></fieldset>

                <c:if test="${not editing}">
                    <div class="media-fields"><label for="copertina">Copertina</label><input id="copertina" name="copertina" type="file" accept="image/jpeg,image/png,image/webp" required>
                    <label for="galleria">Immagini galleria</label><input id="galleria" name="galleria" type="file" accept="image/jpeg,image/png,image/webp" multiple>
                    <label for="video">Video</label><input id="video" name="video" type="file" accept="video/mp4,video/webm" multiple></div>
                </c:if>
                <button class="submit-product" type="submit">${editing ? 'Salva modifiche' : 'Crea prodotto'}</button>
            </form>
        </aside>
    </div>
</main>
<footer>© 2026 Critical Hit Keys. Tutti i diritti riservati.</footer>
<script>const contextPath = "${pageContext.request.contextPath}";</script>
<script src="${pageContext.request.contextPath}/admin-prodotti.js"></script>
</body>
</html>
