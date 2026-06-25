document.addEventListener("DOMContentLoaded", function() {
    const track = document.querySelector('.carousel-track');
    const items = document.querySelectorAll('.carousel-item');
    const nextBtn = document.querySelector('.next-btn');
    const prevBtn = document.querySelector('.prev-btn');

    let currentIndex = 0;
    const maxIndex = items.length - 1;

    // Funzione per aggiornare la posizione del carosello
    function updateCarousel() {
        // Sposta il binario per le foto verso sinistra in base all'indice corrente
        track.style.transform = `translateX(-${currentIndex * 100}%)`;
    }

    /* Listener per il click con il mouse dell'utente */
    nextBtn.addEventListener('click', () => {
        if (currentIndex < maxIndex) {
            currentIndex++;
        } else {
            currentIndex = 0; // Torna alla prima immagine se si è alla fine
        }
        updateCarousel();
    });

    prevBtn.addEventListener('click', () => {
        if (currentIndex > 0) {
            currentIndex--;
        } else {
            currentIndex = maxIndex; // Va all'ultima immagine se si clicca indietro sulla prima
        }
        updateCarousel();
    });
});