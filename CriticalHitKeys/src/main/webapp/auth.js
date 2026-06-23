document.addEventListener('DOMContentLoaded', function () {
    var authMain = document.getElementsByClassName('auth-main')[0];
    var switchButtons = document.getElementsByClassName('switch-button');
    var registerForm = document.getElementsByClassName('auth-face-back')[0];
    var registerMessage = document.getElementsByClassName('inline-message')[0];
    var password = document.getElementById('register-password');
    var confirmPassword = document.getElementById('register-confirm');

    for (var i = 0; i < switchButtons.length; i++) {
        switchButtons[i].addEventListener('click', function () {
            var target = this.getAttribute('data-auth-target');

            if (target === 'register') {
                authMain.classList.add('show-register');
            } else {
                authMain.classList.remove('show-register');
            }
        });
    }

    if (registerForm) {
        registerForm.addEventListener('submit', function (event) {
            if (password.value !== confirmPassword.value) {
                event.preventDefault();
                registerMessage.textContent = 'Le password non coincidono.';
                confirmPassword.focus();
            } else {
                registerMessage.textContent = '';
            }
        });
    }
});
