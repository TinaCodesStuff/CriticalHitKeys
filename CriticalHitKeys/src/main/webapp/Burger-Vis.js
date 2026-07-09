const burgerBtn = document.getElementById("burger-btn");
const menu = document.getElementById("mobile-menu");

    burgerBtn.addEventListener("click", () => { //il codice qui in breve ci permette di visualizzare i link clickando sul burger
    if (menu.style.display === "block") {   //se clicco sul bottone e i link sono mostrati con display = "block" allora li imposterà a menu.style.display="none" per non farli visualizzare
        menu.style.display = "none";
    } else {    //altrimenti clicco sul bottone e link non sono già visualizzati allora li posso visualizzare con menu.style.display = "block"
        menu.style.display = "block";
    }
});
