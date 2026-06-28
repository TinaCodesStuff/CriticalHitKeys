document.querySelectorAll(".quantita-AJAX").forEach(select => {

    select.addEventListener("change", function () {

        console.log("Cambio quantità:", this.dataset.id, this.value);

        fetch(contextPath + "/aggiorna-quantita", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
            body:
                "id_prod=" + this.dataset.id +
                "&quantita=" + this.value
        })
            .then(r => {
                if (!r.ok) {
                    throw new Error("Errore HTTP " + r.status);
                }
                return r.json();
            })
            .then(data => {
                console.log("RISPOSTA:", data);
                document.getElementById("totale-provv").innerText =
                    "Totale: € " + data.totale;
            })
            .catch(err => {
                console.error("AJAX ERROR:", err);
            });

    });

});