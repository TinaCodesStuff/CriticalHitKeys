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
    ID_Ticket INT PRIMARY KEY,
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

CREATE TABLE IF NOT EXISTS Recensione (
    ID_Recensione INT PRIMARY KEY,
    Voto INT CHECK (Voto >= 1 AND Voto <= 5),
    Descrizione_Rec TEXT,
    Username_Ut VARCHAR(20),
    Email_Ut VARCHAR(30),
    FOREIGN KEY (Username_Ut, Email_Ut) REFERENCES Utente (Username_Ut, Email_Ut)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Ordine (
    ID_Ordine INT PRIMARY KEY,
    Quantita INT,
    Username_Ut VARCHAR(20),
    Email_Ut VARCHAR(30),
    FOREIGN KEY (Username_Ut, Email_Ut) REFERENCES Utente (Username_Ut, Email_Ut)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Prodotto (
    ID_Prodotto INT PRIMARY KEY,
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

CREATE TABLE IF NOT EXISTS Aggiunge (
    ID_Ordine INT NOT NULL,
    ID_Prodotto INT NOT NULL,
    PRIMARY KEY (ID_Ordine, ID_Prodotto),
    FOREIGN KEY (ID_Ordine) REFERENCES Ordine(ID_Ordine)
    ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (ID_Prodotto) REFERENCES Prodotto (ID_Prodotto)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Genere (
    ID_Genere INT PRIMARY KEY,
    ID_Prodotto INT,
    FOREIGN KEY (ID_Prodotto) REFERENCES Prodotto (ID_Prodotto)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS ChiaveDigitale (
    ID_Prodotto INT PRIMARY KEY,
    Chiave VARCHAR(15),
    FOREIGN KEY (ID_Prodotto) REFERENCES Prodotto (ID_Prodotto)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Account (
    ID_Prodotto INT PRIMARY KEY,
    Credenziali VARCHAR(40),
    FOREIGN KEY (ID_Prodotto) REFERENCES Prodotto (ID_Prodotto)
    ON UPDATE CASCADE ON DELETE CASCADE
);
