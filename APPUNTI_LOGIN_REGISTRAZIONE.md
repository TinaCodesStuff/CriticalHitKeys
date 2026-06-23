# Appunti login e registrazione

## Cosa ho implementato

- Login e registrazione stanno nella servlet `AuthServlet`, che fa da Controller.
- I dati utente stanno in `Utente`, `UtenteDAO` e `PasswordUtil`, quindi nel Model.
- La pagina `auth.jsp` e i file CSS/JS sono la View.
- Le password non sono salvate in chiaro: prima del salvataggio viene calcolato l'hash SHA-1.
- Il form e controllato lato client con HTML/JavaScript e lato server nella servlet.
- Dopo il login viene creata una sessione con l'utente loggato.

## Credenziali di prova

- `Geralt90` / `pass123`
- `DragonBorn` / `shout01`
- `Ciri_05` / `sw0rd99`
- `VaultDweller` / `nuka111`
- `Arthur_M` / `outlaw22`

## Avvio senza IntelliJ IDEA su Arch

Sul portatile va chiuso l'IDE e va usato Tomcat direttamente.

Percorsi trovati su questa macchina:

- Tomcat: `/usr/share/tomcat10`
- Webapps: `/var/lib/tomcat10/webapps`
- Config: `/etc/tomcat10/server.xml`
- Servizio systemd: `tomcat10`

Comandi da terminale:

```bash
cd /home/domenico/unisa/cooking/tsw/boccuti-marino-sorrentino_pj/CriticalHitKeys
./mvnw clean package
sudo cp target/CriticalHitKeys-1.0-SNAPSHOT.war /var/lib/tomcat10/webapps/CriticalHitKeys.war
sudo systemctl restart tomcat10
sudo systemctl status tomcat10 --no-pager
sudo journalctl -u tomcat10 -f
```

URL da aprire:

```text
https://localhost:8443/CriticalHitKeys/
```

Se Tomcat non parte in automatico:

```bash
sudo systemctl enable --now tomcat10
```

Se HTTPS non e ancora attivo, in `/etc/tomcat10/server.xml` va abilitato il connector su `8443`. Nel file c'e gia un blocco HTTPS commentato: va decommentato e collegato a un keystore.

Esempio di keystore locale:

```bash
sudo keytool -genkeypair -alias tomcat -keyalg RSA -keysize 2048 -validity 3650 -keystore /etc/tomcat10/localhost-rsa.jks
sudo chown tomcat10:tomcat10 /etc/tomcat10/localhost-rsa.jks
sudo chmod 640 /etc/tomcat10/localhost-rsa.jks
sudo systemctl restart tomcat10
```

Dentro il connector HTTPS di `server.xml`, il path deve puntare a:

```xml
<Certificate certificateKeystoreFile="/etc/tomcat10/localhost-rsa.jks"
             certificateKeystorePassword="PASSWORD_SCELTA" type="RSA" />
```

Per caricare il database:

```bash
mysql -u root -p < /home/domenico/unisa/cooking/tsw/boccuti-marino-sorrentino_pj/CriticalHitKeys_Database/CreazioneDatabaseCHK.sql
mysql -u root -p < /home/domenico/unisa/cooking/tsw/boccuti-marino-sorrentino_pj/CriticalHitKeys_Database/InsertCHK.sql
```

## Domande possibili del professore

**Dove si vede il modello MVC?**

Il Controller e `AuthServlet`, il Model e composto da `Utente`, `UtenteDAO` e `PasswordUtil`, mentre la View e `auth.jsp`.

**Dove viene cifrata la password?**

In `PasswordUtil.hashPassword()`. La servlet riceve la password dal form, chiama quel metodo e salva nel database solo l'hash.

**Se volessi usare SHA-256 invece di SHA-1?**

Cambierei la costante `HASH_ALGORITHM` in `PasswordUtil` da `SHA-1` a `SHA-256`. Poi nel database allargherei `Password_Ut` da `VARCHAR(40)` a `VARCHAR(64)`, perche SHA-256 in esadecimale occupa 64 caratteri.

**Perche controlli il form sia client che server?**

Il client migliora l'esperienza utente, ma non e affidabile per la sicurezza. La servlet ripete i controlli perche un utente potrebbe inviare richieste manuali senza passare dal browser.

**Come eviti SQL Injection?**

Uso `PreparedStatement` in `UtenteDAO`: i valori dell'utente non vengono concatenati direttamente nelle query SQL.

**Come riduci il rischio XSS nella login?**

La pagina non ristampa nel markup i valori inseriti dall'utente. I messaggi mostrati sono stringhe decise dalla servlet, non input libero.

**Come gestisci la sessione?**

Dopo il login invalido la vecchia sessione e ne creo una nuova con `utenteLoggato`. Questo aiuta anche contro la session fixation.

**Dove usi JavaScript per modificare il DOM?**

In `auth.js` cambio la classe del contenitore per il flip login/registrazione e mostro il messaggio se le password non coincidono. In `catalogo.js` uso lo scroll e `offsetTop` per aggiungere una classe ai prodotti quando entrano nello schermo.

**Hai usato JQuery o Bootstrap?**

No. CSS e JavaScript sono scritti manualmente.

**I cookie sono protetti per HTTPS?**

Nel `web.xml` il cookie di sessione e `HttpOnly` e `Secure`, quindi e pensato per girare su Tomcat configurato in HTTPS.

**Che cosa succede se username o email esistono gia?**

La servlet controlla con `UtenteDAO.existsByUsername()` e `UtenteDAO.existsByEmail()`. Se trova un duplicato, non salva il nuovo utente.
