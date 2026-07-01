const priceInput = document.getElementById("prezzoOriginale");
const discountInput = document.getElementById("sconto");
const finalPrice = document.getElementById("final-price");
const form = document.getElementById("product-form");

function updateFinalPrice() {
    const price = Number(priceInput.value || 0);
    const discount = Number(discountInput.value || 0);
    const result = Math.max(0, price * (100 - discount) / 100);
    finalPrice.textContent = result.toLocaleString("it-IT", {style: "currency", currency: "EUR"});
}

priceInput.addEventListener("input", updateFinalPrice);
discountInput.addEventListener("input", updateFinalPrice);
updateFinalPrice();

form.addEventListener("submit", event => {
    ["generi", "piattaforme", "modalita"].forEach(name => {
        const inputs = [...form.querySelectorAll(`input[name="${name}"]`)];
        inputs.forEach(input => input.setCustomValidity(""));
        if (!inputs.some(input => input.checked)) inputs[0].setCustomValidity("Seleziona almeno un valore.");
    });
    if (!form.checkValidity()) {
        event.preventDefault();
        form.reportValidity();
    }
});

document.querySelectorAll(".remove-product").forEach(button => {
    button.addEventListener("click", async () => {
        if (!confirm(`Rimuovere ${button.dataset.name}?`)) return;
        button.disabled = true;
        const notice = document.getElementById("ajax-notice");
        try {
            const body = new URLSearchParams({id: button.dataset.id});
            const response = await fetch(contextPath + "/admin/prodotti/rimuovi", {method: "POST", body});
            if (!response.ok) throw new Error();
            const data = await response.json();
            const row = button.closest("tr");
            if (data.status === "deleted") {
                row.remove();
                notice.textContent = "Prodotto eliminato definitivamente.";
            } else if (data.status === "unavailable") {
                row.classList.add("is-unavailable");
                const status = row.querySelector(".status");
                status.className = "status unavailable";
                status.textContent = "Non disponibile";
                notice.textContent = "Il prodotto era referenziato ed è stato reso non disponibile.";
            } else throw new Error();
            notice.hidden = false;
        } catch (_) {
            notice.textContent = "Impossibile rimuovere il prodotto.";
            notice.classList.add("notice-error");
            notice.hidden = false;
            button.disabled = false;
        }
    });
});
