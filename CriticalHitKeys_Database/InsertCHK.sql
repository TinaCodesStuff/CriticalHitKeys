USE CriticalHitKeys;

-- 1. Popolamento Amministratore (3 record)
INSERT INTO Amministratore (Email_Amm, Username_Amm, Password_Amm) VALUES
('admin1@chk.it', 'BossLevel', 'f865b53623b121fd34ee5426c792e5c33af8c227'),
('admin2@chk.it', 'GamerDev', 'cf2e875d70c402e4aaf32ceb64b1fa6f7396af59'),
('staff@chk.it', 'ModMaster', '5d43e3169f06cf2a04a0ee870b5ac2aff3c558ff');

-- 2. Popolamento Utente (5 record)
-- Password salvate come hash SHA-1 senza salt.
INSERT INTO Utente (Username_Ut, Email_Ut, Password_Ut) VALUES
('Geralt90', 'geralt@email.it', 'aafdc23870ecbcd3d557b6423a8982134e17927e'), -- password: pass123
('DragonBorn', 'dovah@skyrim.com', '96be154ca64a3080b17768a41ecc005ecbe49885'), -- password: shout01
('Ciri_05', 'ciri@kaermorhen.org', 'e8a9ca85f21c2c8cb82298d975dc70adaa521058'), -- password: sw0rd99
('VaultDweller', 'fallout@vault.com', '007e41a7c21873bff6ae8de2710922262c86ec1d'), -- password: nuka111
('Arthur_M', 'morgan@reddead.it', '0b125fe2d4b39f076975d00a80bbf491a99a01e2'); -- password: outlaw22

-- 3. Popolamento Prodotto (5 record)
-- Collegati agli Amministratori
INSERT INTO Prodotto (ID_Prodotto, Nome, Descrizione_Prod, Prezzo_OG, Prezzo_Scontato, Modalita_Gioco, Casa_Sviluppatrice, Sconto, Email_Amm) VALUES
(101, 'Elden Ring', 'Open world fantasy action RPG.', 59.99, 44.99, 'Single/Multi', 'FromSoftware', 25, 'admin1@chk.it'),
(102, 'Cyberpunk 2077', 'Sci-fi RPG in Night City.', 49.99, 24.99, 'Single Player', 'CD Projekt Red', 50, 'admin1@chk.it'),
(103, 'Minecraft', 'Sandbox building game.', 29.99, 29.99, 'Multiplayer', 'Mojang', 0, 'admin2@chk.it'),
(104, 'Stray', 'Adventure game featuring a cat.', 26.99, 18.89, 'Single Player', 'BlueTwelve Studio', 30, 'staff@chk.it'),
(105, 'FIFA 23', 'Football simulation game.', 69.99, 34.99, 'Multiplayer', 'EA Sports', 50, 'admin2@chk.it');

-- 4. Popolamento Ticket (5 record)
-- Collegati ad Amministratori e Utenti
INSERT INTO Ticket (ID_Ticket, Campo, Descrizione_Ticket, Email_Amm, Username_Ut, Email_Ut) VALUES
(1, 'Pagamento', 'Errore durante la transazione PayPal.', 'admin1@chk.it', 'Geralt90', 'geralt@email.it'),
(2, 'Tecnico', 'La chiave risulta già utilizzata.', 'admin2@chk.it', 'DragonBorn', 'dovah@skyrim.com'),
(3, 'Account', 'Vorrei cambiare la mia email.', 'staff@chk.it', 'Ciri_05', 'ciri@kaermorhen.org'),
(4, 'Rimborso', 'Acquisto errato, chiedo reso.', 'admin1@chk.it', 'VaultDweller', 'fallout@vault.com'),
(5, 'Info', 'Quando torna disponibile God of War?', 'staff@chk.it', 'Arthur_M', 'morgan@reddead.it');

-- 5. Popolamento Recensione (5 record)
INSERT INTO Recensione (ID_Recensione, Voto, Descrizione_Rec, Username_Ut, Email_Ut) VALUES
(1, 5, 'Consegna istantanea, ottimo prezzo!', 'Geralt90', 'geralt@email.it'),
(2, 4, 'Tutto ok, ma il supporto è lento.', 'DragonBorn', 'dovah@skyrim.com'),
(3, 5, 'Elden Ring a metà prezzo, incredibile.', 'Ciri_05', 'ciri@kaermorhen.org'),
(4, 1, 'Chiave non funzionante, attendo risposta.', 'VaultDweller', 'fallout@vault.com'),
(5, 5, 'Sito affidabile, consigliato.', 'Arthur_M', 'morgan@reddead.it');

-- 6. Popolamento Ordine (5 record)
INSERT INTO Ordine (ID_Ordine, Quantita, Username_Ut, Email_Ut) VALUES
(501, 1, 'Geralt90', 'geralt@email.it'),
(502, 2, 'DragonBorn', 'dovah@skyrim.com'),
(503, 1, 'Ciri_05', 'ciri@kaermorhen.org'),
(504, 1, 'VaultDweller', 'fallout@vault.com'),
(505, 3, 'Arthur_M', 'morgan@reddead.it');

-- 7. Popolamento Aggiunge (Relazione Ordine-Prodotto)
INSERT INTO Aggiunge (ID_Ordine, ID_Prodotto) VALUES
(501, 101),
(502, 102),
(502, 103),
(503, 104),
(504, 105);

-- 8. Popolamento Genere (5 record)
INSERT INTO Genere (ID_Genere, ID_Prodotto) VALUES
(1, 101), -- Action RPG
(2, 102), -- RPG / Sci-fi
(3, 103), -- Sandbox
(4, 104), -- Adventure
(5, 105); -- Sport

-- 9. Popolamento ChiaveDigitale (Esempi di chiavi finte)
INSERT INTO ChiaveDigitale (ID_Prodotto, Chiave) VALUES
(101, 'ELDN-RING-44-X1'),
(102, 'CYBR-PNK-20-77'),
(104, 'STRY-CAT-99-PUR');

-- 10. Popolamento Account (Esempi di credenziali finte)
INSERT INTO Account (ID_Prodotto, Credenziali) VALUES
(103, 'mc_user:block_pass_2024'),
(105, 'ea_sports_fan:goal_2023_psn');
