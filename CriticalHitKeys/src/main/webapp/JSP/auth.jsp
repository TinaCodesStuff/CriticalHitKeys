<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accesso utenti - Critical Hit Keys</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/auth.css">
</head>
<body>
<div class="background-sito"></div>
<ul>
    <li class="menu-left"><a href="init-servlet"><img src="${pageContext.request.contextPath}/img/logoExtended5.png" width="120" height="100" alt="Logo piccolo di Critical Hit Keys"></a></li>

    <li class="menu-center">
        <a href="ticket-servlet">ASSISTENZA</a>
        <a href="${pageContext.request.contextPath}/aboutus.html">ABOUT US</a>
        <a href="mostraProd">CATALOGO</a>
    </li>

    <li class="menu-right">
        <a href="auth" aria-current="page"><img src="${pageContext.request.contextPath}/img/iconaUtente.png" width="40" height="40" alt="Accesso per utenti/admin"></a>
        <a href="carrello-servlet"><img src="${pageContext.request.contextPath}/img/iconaCarrello.png" width="40" height="40" alt="Carrello in cui sono salvati i prodotti"></a>
    </li>
</ul>

<main class="auth-main ${authMode == 'register' ? 'show-register' : ''} ${not empty sessionScope.utenteLoggato ? 'is-logged' : ''}">
    <section class="logged-panel" aria-labelledby="logged-title">
        <h1 id="logged-title">Accesso effettuato</h1>
        <p>Benvenuto, ${sessionScope.usernameUtente}. La sessione utente e attiva.</p>
        <form action="auth" method="post">
            <input type="hidden" name="action" value="logout">
            <button type="submit">Logout</button>
        </form>
    </section>

    <section class="auth-card" aria-label="Accesso o registrazione">
        <div class="auth-switch" aria-label="Scegli operazione">
            <button type="button" class="switch-button switch-login" data-auth-target="login">Login</button>
            <button type="button" class="switch-button switch-register" data-auth-target="register">Registrazione</button>
        </div>

        <p class="form-message form-message--info" aria-live="polite">${authInfo}</p>
        <p class="form-message form-message--error" aria-live="polite">${authError}</p>

        <div class="flip-stage">
            <div class="flip-card">
                <form class="auth-face auth-face-front" action="auth" method="post" data-auth-form="login">
                    <input type="hidden" name="action" value="login">

                    <h1>Login</h1>

                    <label for="login-id">Email o username</label>
                    <input id="login-id" name="emailUsername" type="text" minlength="3" maxlength="30" required autocomplete="username">

                    <label for="login-password">Password</label>
                    <input id="login-password" name="password" type="password" minlength="6" maxlength="40" required autocomplete="current-password">

                    <button type="submit">Entra</button>
                </form>

                <form class="auth-face auth-face-back" action="auth" method="post" data-auth-form="register">
                    <input type="hidden" name="action" value="register">

                    <h1>Registrazione</h1>

                    <label for="register-username">Username</label>
                    <input id="register-username" name="username" type="text" minlength="3" maxlength="20" pattern="[A-Za-z0-9_]{3,20}" required autocomplete="username">

                    <label for="register-email">Email</label>
                    <input id="register-email" name="email" type="email" maxlength="30" required autocomplete="email">

                    <label for="register-password">Password</label>
                    <input id="register-password" name="password" type="password" minlength="6" maxlength="40" required autocomplete="new-password">

                    <label for="register-confirm">Conferma password</label>
                    <input id="register-confirm" name="confirmPassword" type="password" minlength="6" maxlength="40" required autocomplete="new-password">

                    <p class="inline-message" data-register-message aria-live="polite"></p>

                    <button type="submit">Crea account</button>
                </form>
            </div>
        </div>
    </section>
</main>

<div class="footer">
    <p>© 2026 Critical Hit Keys. Tutti i diritti riservati.</p>
</div>
<script src="${pageContext.request.contextPath}/auth.js"></script>
</body>
</html>
