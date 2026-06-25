DROP DATABASE IF EXISTS CriticalHitKeys;
CREATE DATABASE IF NOT EXISTS CriticalHitKeys;
USE CriticalHitKeys;

CREATE TABLE IF NOT EXISTS Amministratore (
    Email_Amm VARCHAR(30) PRIMARY KEY,
    Username_Amm VARCHAR(20),
    Password_Amm VARCHAR(40)
);

CREATE TABLE IF NOT EXISTS Utente (
    Username_Ut VARCHAR(20) NOT NULL UNIQUE,
    Email_Ut VARCHAR(30) NOT NULL UNIQUE,
    Password_Ut VARCHAR(40) NOT NULL,
    PRIMARY KEY(Username_Ut, Email_Ut)
);

CREATE TABLE IF NOT EXISTS Ticket (
    ID_Ticket INT AUTO_INCREMENT PRIMARY KEY ,
    Campo VARCHAR(20),
    Descrizione_Ticket TEXT,
    Email_Amm VARCHAR(30),
    Username_Ut VARCHAR(20),
    Email_Ut VARCHAR(30),
    FOREIGN KEY (Email_Amm) REFERENCES Amministratore(Email_Amm)
    ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (Username_Ut, Email_Ut) REFERENCES Utente (Username_Ut, Email_Ut)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Prodotto (
    ID_Prodotto INT AUTO_INCREMENT PRIMARY KEY,
    Nome VARCHAR(30),
    Descrizione_Prod TEXT,
    Prezzo_OG FLOAT,
    Prezzo_Scontato FLOAT,
    Modalita_Gioco VARCHAR(20),
    Casa_Sviluppatrice VARCHAR(30),
    Sconto INT,
    Email_Amm VARCHAR(30),
    FOREIGN KEY (Email_Amm) REFERENCES Amministratore(Email_Amm)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Recensione (
    ID_Recensione INT AUTO_INCREMENT PRIMARY KEY,
    ID_Prodotto INT NOT NULL,
    Voto INT CHECK (Voto >= 1 AND Voto <= 5),
    Descrizione_Rec TEXT,
    Username_Ut VARCHAR(20),
    Email_Ut VARCHAR(30),
    FOREIGN KEY (Username_Ut, Email_Ut) REFERENCES Utente (Username_Ut, Email_Ut)
    ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(ID_Prodotto) REFERENCES Prodotto(ID_Prodotto)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Carrello (
    ID_Carrello INT AUTO_INCREMENT PRIMARY KEY,
    Username_Ut VARCHAR(20) NOT NULL,
    Email_Ut VARCHAR(30) NOT NULL,
    FOREIGN KEY (Username_Ut, Email_Ut) REFERENCES Utente (Username_Ut, Email_Ut)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Contiene (
    ID_Carrello INT NOT NULL,
    ID_Prodotto INT NOT NULL,
    Quantita INT NOT NULL DEFAULT 1,
    PRIMARY KEY (ID_Carrello, ID_Prodotto),
    FOREIGN KEY (ID_Carrello) REFERENCES Carrello(ID_Carrello)
        ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (ID_Prodotto) REFERENCES Prodotto(ID_Prodotto)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CHECK (Quantita > 0)
);

CREATE TABLE IF NOT EXISTS Ordine (
    ID_Ordine INT AUTO_INCREMENT PRIMARY KEY,
    Importo_tot FLOAT,
    DataUltimaModifica DATETIME,
    ID_Carrello INT NOT NULL UNIQUE,
    FOREIGN KEY (ID_Carrello) REFERENCES Carrello(ID_Carrello)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Genere (
    ID_Genere INT AUTO_INCREMENT PRIMARY KEY,
    ID_Prodotto INT,
    FOREIGN KEY (ID_Prodotto) REFERENCES Prodotto (ID_Prodotto)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS ChiaveDigitale (
    ID_Prodotto INT  PRIMARY KEY,
    Chiave VARCHAR(15),
    FOREIGN KEY (ID_Prodotto) REFERENCES Prodotto (ID_Prodotto)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Account (
    ID_Prodotto INT  PRIMARY KEY,
    Credenziali VARCHAR(40),
    FOREIGN KEY (ID_Prodotto) REFERENCES Prodotto (ID_Prodotto)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE MediaProdotto (
    ID_Media VARCHAR(10) PRIMARY KEY,
    ID_Prodotto INT,
    Tipo VARCHAR(10),
    URL_Media VARCHAR(255),
    FOREIGN KEY (ID_Prodotto) REFERENCES Prodotto (ID_Prodotto)
    ON DELETE CASCADE
);