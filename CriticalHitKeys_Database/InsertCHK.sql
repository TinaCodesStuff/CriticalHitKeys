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
(101, 'Elden Ring', 'UN NUOVO ACTION RPG FANTASY.
Alzati, Senzaluce, e fatti guidare dalla grazia per brandire il potere dell''Anello ancestrale.
• Un mondo mozzafiato ricco di emozioni e mistero
L''Interregno fa parte di un vasto continente in cui magnifici spazi aperti ed enormi segrete con un complesso design 3D sono perfettamente integrati. Durante l''esplorazione, sarai accolto dalla gioia di scoprire travolgenti minacce sconosciute.
La conoscenza del terreno e dei suoi segreti ti aiuterà a superare i nemici e a sconfiggere boss formidabili... o a condurre i giocatori invasori dritti in trappola.

• Sconfiggi avversari impegnativi in intensi combattimenti
Il combattimento in ELDEN RING è semplice da imparare, ma offre livelli di competenza non facili da raggiungere. Per diventare Lord ancestrale, dovrai trovare il giusto equilibrio tra attaccare ed evitare danni, usare un''ampia varietà di armi, incantesimi ed evocazioni, e perfezionare il tuo tempismo per sfruttare le debolezze degli avversari.

• Crea e sviluppa il tuo personaggio
Oltre a personalizzare l''aspetto del personaggio, ci sono tantissimi modi per combinare armi, corazze, oggetti utilizzabili e magie da equipaggiare. Potrai sviluppare il personaggio in base al tuo stile di gioco.
Non importa se prediligi audaci scontri fisici, incantesimi tattici o la discreta arte della furtività, troverai sempre l''equipaggiamento adatto alle tue scelte.

• Una storia epica nata dalla penna di George R. R. Martin
La mitologia alla base di Elden Ring è stata creata da George R. R. Martin e adattata in una ricca storia a più livelli. L''incrocio di obiettivi e desideri dei personaggi porta a una trama intensa che si ripercuote in tutto l''Interregno. Gli eventi di gioco possono svilupparsi in modi diversi, a seconda dei tuoi interventi.

• Gioca insieme a una vasta comunità globale
La community dei Senzaluce è numerosa e attiva. I tuoi amici potrebbero già farvi parte. Puoi giocare con un massimo di altri due Senzaluce come compagni di squadra in modalità cooperativa, invitandoli con la condivisione di una password o evocandoli dal gruppo di membri della community nelle vicinanze.
Ci sono anche ampie opportunità di affrontare altri giocatori, tramite invasioni in cooperativa, duelli su invito o le numerose opzioni di battaglia tra giocatori disponibili nelle tre arene.
', 59.99, 44.99, 'Single/Multi', 'FromSoftware', 25, 'admin1@chk.it'),
(102, 'Cyberpunk 2077', 'ENTRA NEL FUTURO DISTOPICO
Diventa il mercenario cyber-potenziato V e lotta per sopravvivere cercando gloria nell''acclamato GdR di azione e avventura a mondo aperto, Cyberpunk 2077.

CREA IL CYBERPUNK SUPREMO
Crea uno stile di gioco unico combinando talenti e innesti cibernetici ultrapotenti per diventare il mercenario più inarrestabile di Night City.

ESPLORA LA CITTÀ DEI SOGNI
Esplora la megalopoli di Night City alla scoperta dei suoi personaggi sregolati e pittoreschi, tutti ansiosi di affidarti contratti, missioni e lavori memorabili.

SCRIVI LA TUA LEGGENDA
Relazionati con un ricco cast di personaggi con storie, sogni e tragedie personali, come Keanu Reeves nel ruolo del rockerboy Johnny Silverhand.

TANTI AGGIORNAMENTI E PERFEZIONAMENTI
Scopri missioni e automobili aggiuntive e tante migliorie qualitative con i costanti aggiornamenti che impreziosiscono le tue partite.', 49.99, 24.99, 'Single Player', 'CD Projekt Red', 50, 'admin1@chk.it'),
(103, 'Minecraft', 'Sandbox building game.', 29.99, 29.99, 'Multiplayer', 'Mojang', 0, 'admin2@chk.it'),
(104, 'Stray', 'Adventure game featuring a cat.', 26.99, 18.89, 'Single Player', 'BlueTwelve Studio', 30, 'staff@chk.it'),
(105, 'FIFA 26', 'ESPERIENZA DI GIOCO RIVOLUZIONATA
La nuova sessione di gioco realistica offre l''esperienza più realistica mai provata nella Carriera, mentre quella per il gameplay competitivo, basata su fondamentali affinati, sulla coerenza e sulla reattività migliorata.

CREA LA SQUADRA DEI TUOI SOGNI
Metti alla prova la squadra dei tuoi sogni in Football Ultimate Team™ con tornei ed eventi Live, oltre a una rinnovata esperienza Rivals e Champions. I tornei metteranno alla prova le tue abilità con fino a quattro turni a eliminazione diretta, mentre gli eventi Live aggiungono varietà con competizioni a tema e contenuti per tutta la stagione.

AFFRONTA NUOVE SFIDE LIVE
Vivi la Carriera tecnico come mai prima d''ora con le nuove Sfide tecnico Live. Ottieni premi nel corso della nuova stagione completando diversi scenari reali e storie alternative, che possono impiegare pochi minuti di gioco o persino più stagioni.

ISPIRATI DAI GRANDI DEL CALCIO
Con archetipi ispirati ai grandi del calcio arrivano nuove classi per Club e Carriera professionista, dandoti modo di esaltare l''individualità del tuo fenomeno. Sviluppa le tue abilità durante la stagione migliorando gli attributi e sbloccando nuove specialità archetipo per differenziare la tua stella in campo.

GIOCA IN SOLITARIA O IN COMPAGNIA
Gioca per conto tuo o in compagnia negli eventi Live Rush Club, che introducono nuovi entusiasmanti tornei a eliminazione diretta per Club in EA SPORTS FC™ 26. Con requisiti d''accesso e regole di gioco diverse.

IL CLUB È NELLE TUE MANI
Gioca a EA SPORTS FC™ 26 a modo tuo su PC utilizzando il tuo controller PlayStation® o Xbox.', 69.99, 34.99, 'Multiplayer', 'EA Sports', 50, 'admin2@chk.it');

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

INSERT INTO MediaProdotto (ID_Media, ID_Prodotto, Tipo, URL_Media) VALUES
('M1', 101, 'image', 'img/giochi/eldenring/eldenRingCopertina.jpeg'),
('M2', 101, 'image', 'img/giochi/eldenring/eldenringLimgrave.jpg'),
('M3', 101, 'video', 'img/giochi/eldenring/eldenringTrailer.mp4'),
('M4', 102, 'image', 'img/giochi/cyberpunk/cyberpunkCopertina.jpg'),
('M5', 102, 'image', 'img/giochi/cyberpunk/cyberpunkNightCity.png'),
('M6', 102, 'video', 'img/giochi/cyberpunk/cyberpunkTrailer.mp4');