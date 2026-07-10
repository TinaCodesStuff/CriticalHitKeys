package controller.criticalhitkeys;

import jakarta.servlet.http.HttpServletRequest;
import model.Prodotto;

import java.util.Arrays;
import java.util.List;

final class AdminProductForm {
    private AdminProductForm() { }

    static Prodotto parse(HttpServletRequest request) {
        // legge i campi testuali inviati dal form
        String nome = clean(request.getParameter("nome"));
        String descrizione = clean(request.getParameter("descrizione"));
        String sviluppatore = clean(request.getParameter("sviluppatore"));
        String modalita = clean(request.getParameter("modalita"));
        String genere = clean(request.getParameter("genere"));

        float prezzoOriginale;
        int sconto;
        try {
            prezzoOriginale = Float.parseFloat(request.getParameter("prezzoOriginale"));
            sconto = Integer.parseInt(request.getParameter("sconto"));
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Prezzo e sconto devono essere numerici.");
        }

        // controlla che i dati rispettino i vincoli del database
        if (nome.isEmpty() || nome.length() > 30) {
            throw new IllegalArgumentException("Nome non valido.");
        }
        if (descrizione.isEmpty()) {
            throw new IllegalArgumentException("Descrizione obbligatoria.");
        }
        if (sviluppatore.isEmpty() || sviluppatore.length() > 30) {
            throw new IllegalArgumentException("Casa sviluppatrice non valida.");
        }
        if (modalita.length() > 20) {
            throw new IllegalArgumentException("Modalità di gioco non valida.");
        }
        if (prezzoOriginale < 0) {
            throw new IllegalArgumentException("Prezzo non valido.");
        }
        if (sconto < 0 || sconto > 100) {
            throw new IllegalArgumentException("Sconto non valido.");
        }
        if(genere.isEmpty() || genere.length() >30){
            throw new IllegalArgumentException("Genere non valido.");

        }

        // costruisce il prodotto usando i dati validati
        Prodotto prodotto = new Prodotto();
        prodotto.setNome(nome);
        prodotto.setDescrizione(descrizione);
        prodotto.setCasa_sviluppatrice(sviluppatore);
        prodotto.setModalita_Gioco(modalita);
        prodotto.setPrezzo_OG(prezzoOriginale);
        prodotto.setSconto(sconto);
        prodotto.setGenere(genere);
        float valoreSconto = prezzoOriginale * sconto / 100;
        float prezzoScontato = prezzoOriginale - valoreSconto;
        prodotto.setPrezzo_scontato(Math.round(prezzoScontato * 100) / 100f);
        prodotto.setPiattaforme(valuesOrNull(request, "piattaforme"));
        return prodotto;
    }

    private static List<String> valuesOrNull(HttpServletRequest request, String name) {
        String[] values = request.getParameterValues(name);
        if (values == null) {
            return null;
        }

        return Arrays.stream(values)
                .map(AdminProductForm::clean)
                .filter(value -> !value.isEmpty())
                .distinct()
                .toList();
    }

    private static String clean(String value) {
        if (value == null) {
            return "";
        }
        return value.trim();
    }
}
