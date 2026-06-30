const minSlider = document.getElementById("prezzoMin");
const maxSlider = document.getElementById("prezzoMax");

const minValue = document.getElementById("minValue");
const maxValue = document.getElementById("maxValue");

const actualMin = document.getElementById("actualMin");
const actualMax = document.getElementById("actualMax");

function updateUI() {
    let min = parseInt(minSlider.value);
    let max = parseInt(maxSlider.value);

    // blocco logico
    if (min > max) {
        min = max;
        minSlider.value = max;
    }

    if (max < min) {
        max = min;
        maxSlider.value = min;
    }

    // aggiornamento testi sopra slider
    minValue.textContent = min;
    maxValue.textContent = max;

    // aggiornamento "prezzo reale selezionato"
    actualMin.textContent = min;
    actualMax.textContent = max;
}

minSlider.addEventListener("input", updateUI);
maxSlider.addEventListener("input", updateUI);

// inizializzazione
updateUI();