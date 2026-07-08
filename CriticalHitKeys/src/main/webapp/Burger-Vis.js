const burgerBtn = document.getElementById("burger-btn");
const menu = document.getElementById("mobile-menu");

    burgerBtn.addEventListener("click", () => {
    if (menu.style.display === "block") {
        menu.style.display = "none";
    } else {
        menu.style.display = "block";
    }
});
