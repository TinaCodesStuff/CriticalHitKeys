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
(1, 'Elden Ring', 'UN NUOVO ACTION RPG FANTASY. Alzati, Senzaluce, e fatti guidare dalla grazia per brandire il potere dell''Anello ancestrale. Un mondo mozzafiato ricco di emozioni e mistero. L''Interregno fa parte di un vasto continente in cui magnifici spazi aperti ed enormi segrete con un complesso design 3D sono perfettamente integrati. Durante l''esplorazione, sarai accolto dalla gioia di scoprire travolgenti minacce sconosciute.', 59.99, 44.99, 'Single/Multi', 'FromSoftware', 25, 'admin1@chk.it'),

(2, 'Cyberpunk 2077', 'Cyberpunk 2077 è un GDR d''azione open-world ambientato nella megalopoli Night City, dove vesti i panni di un mercenario cyberpunk implicato in una lotta per la sopravvivenza a colpi di fanta-scienza. Splendidamente aggiornato pensando al futuro e contenente tutti i contenuti aggiuntivi gratuiti, personalizza il tuo personaggio e il tuo stile di gioco mentre accetti lavori, costruisci una reputazione e sblocchi potenziamenti.', 49.99, 24.99, 'Single Player', 'CD Projekt Red', 50, 'admin1@chk.it'),

(3, 'Minecraft', 'Preparati a un''avventura dalle infinite possibilità: costruisci, scava, combatti creature ed esplora il panorama di Minecraft in continuo mutamento. Crea ed esplora il tuo personale mondo, dove l''unico limite è la tua immaginazione. Assicurati solo di costruire un rifugio prima che arrivi la notte per rimanere al sicuro dai mostri.', 29.99, 23.99, 'Multiplayer', 'Mojang', 20, 'admin2@chk.it'),

(4, 'Stray', 'Un gatto randagio, smarrito e separato dalla famiglia, deve risolvere un antico mistero per sfuggire a una cyber-città ormai dimenticata. Stray è un gioco di avventura in terza persona ambientato tra i vicoli illuminati al neon di una cyber-città in decadenza e i suoi bassifondi più cupi e squallidi. Guarda il mondo attraverso gli occhi di un gatto randagio e interagisci con l''ambiente in modo giocoso.', 26.99, 18.89, 'Single Player', 'BlueTwelve Studio', 30, 'staff@chk.it'),

(5, 'FIFA 26', 'EA SPORTS FC 26 ti offre l''esperienza calcistica più autentica su PC. Gestisci i club più importanti del pianeta, sfida i tuoi amici o crea la squadra dei tuoi sogni in Ultimate Team. Grazie a una fedeltà grafica straordinaria e a un gameplay rifinito basato sulla reattività e sulla tattica di squadra, vivi ogni singola partita come se fossi davvero sul rettangolo di gioco.', 69.99, 34.99, 'Multiplayer', 'EA Sports', 50, 'admin2@chk.it'),

(6, 'The Wolf Among Us', 'Dai creatori del gioco dell''anno 2012: The Walking Dead, arriva un thriller cupo, violento e maturo basato sui pluripremiati fumetti di Fables. Nei panni di Bigby Wolf (il Grande Lupo Cattivo) scoprirai che un brutale e sanguinoso omicidio è solo un assaggio di ciò che verrà in una serie di giochi in cui ogni tua singola decisione può avere enormi conseguenze.', 14.99, 7.49, 'Single Player', 'Telltale Games', 50, 'admin1@chk.it'),

(7, 'Baldur''s Gate 3', 'Raduna il tuo gruppo e torna nei Reami Dimenticati in una storia di amicizia e tradimento, sacrificio e sopravvivenza, e sul fascino del potere assoluto. Abilità misteriose si stanno risvegliando dentro di te, derivanti da un parassita dei mind flayer piantato nel tuo cervello. Resisti e rivolta l''oscurità contro se stessa, oppure abbraccia la corruzione e diventa il male supremo.', 59.99, 47.99, 'Single/Multi', 'Larian Studios', 20, 'admin1@chk.it'),

(8, 'Life is Strange', 'Life is Strange è una storia in cinque parti che si propone di rivoluzionare i giochi basati su scelte e conseguenze, permettendo al giocatore di riavvolgere il tempo e influenzare il passato, il presente e il futuro. Segui la storia di Max Caulfield, una fotografa che scopre di poter riavvolgere il tempo mentre salva la sua migliore amica Chloe Price.', 19.99, 3.99, 'Single Player', 'Dontnod Entertainment', 80, 'staff@chk.it'),

(9, 'Devil May Cry 5', 'Il cacciatore di demoni più sfrontato torna con stile nel gioco che i fan dell''azione stavano aspettando. Una nuova invasione demoniaca ha inizio quando i semi di un "albero demoniaco" mettono radici a Red Grave City. Questa infernale incursione attira l''attenzione del giovane cacciatore di demoni Nero, un alleato di Dante che ora si trova privato del suo braccio demoniaco.', 29.99, 11.99, 'Single Player', 'Capcom', 60, 'admin2@chk.it'),

(10, 'The Witcher 3: Wild Hunt', 'Sei Geralt di Rivia, cacciatore di mostri mercenario. Davanti a te si estende un continente devastato dalla guerra e infestato da mostri, che puoi esplorare a piacimento. Il tuo contratto attuale? Trovare Ciri, la Figlia della Profezia, un''arma vivente capace di alterare la forma del mondo prima che ci riesca la Caccia Selvaggia.', 29.99, 7.49, 'Single Player', 'CD Projekt Red', 75, 'admin1@chk.it'),

(11, 'Red Dead Redemption 2', 'Vincitore di oltre 175 premi come Gioco dell''Anno, Red Dead Redemption 2 è l''epica storia del fuorilegge Arthur Morgan e della banda di Van der Linde, in fuga attraverso l''America all''alba dell''era moderna. Include anche l''accesso al mondo condiviso di Red Dead Online.', 59.99, 19.79, 'Single/Multi', 'Rockstar Games', 67, 'admin2@chk.it'),

(12, 'Hollow Knight', 'Affronta le profondità di un regno dimenticato. Sotto la città calante di Dirtmouth giace un antico regno in rovina. Molti vengono attirati sotto la superficie in cerca di ricchezze, gloria o risposte a vecchi segreti. Esplora i sistemi di caverne interconnessi, combatti bizzarre creature contaminate e stringi amicizia con insetti stravaganti.', 14.99, 7.49, 'Single Player', 'Team Cherry', 50, 'staff@chk.it'),

(13, 'Hades', 'Sfida il dio dei morti in questo dungeon crawler rogue-like hack & slash dai creatori di Bastion e Transistor. Nei panni dell''immortale Principe degli Inferi, brandirai i poteri e le armi mitologiche dell''Olimpo per liberarti dalle grinfie del dio dei morti in persona, diventando più forte e scoprendo nuovi dettagli della storia a ogni tentativo di fuga.', 24.50, 12.25, 'Single Player', 'Supergiant Games', 50, 'admin1@chk.it'),

(14, 'God of War', 'Avendo lasciato alle spalle la sua vendetta contro gli dèi dell''Olimpo, Kratos vive ora nella terra delle divinità e dei mostri norreni. In questo mondo duro e spietato, deve combattere per sopravvivere e insegnare a suo figlio a fare lo stesso, affrontando una nuova prospettiva e una visuale sopra la spalla che porta l''azione più vicina che mai.', 49.99, 24.99, 'Single Player', 'Santa Monica Studio', 50, 'admin2@chk.it'),

(15, 'Resident Evil 4', 'La sopravvivenza è solo l''inizio. Sono passati sei anni dal disastro biologico di Raccoon City. L''agente Leon S. Kennedy, uno dei sopravvissuti all''incidente, è stato inviato a salvare la figlia rapita del presidente degli Stati Uniti. La individua in un villaggio europeo isolato, dove la gente del posto è affetta da qualcosa di terribilmente sbagliato.', 39.99, 29.99, 'Single Player', 'Capcom', 25, 'admin2@chk.it'),

(16, 'Detroit: Become Human', 'Fino a dove ti spingerai per essere libero? Detroit, 2038. Gli androidi, macchine dalle sembianze umane, hanno sostituito gli operai umani. Non si stancano, non disobbediscono e non dicono mai di no... finché qualcosa non cambia. Alcuni di loro iniziano a manifestare sentimenti ed emozioni, diventando "devianti". Controlla tre androidi nel loro viaggio.', 39.99, 15.99, 'Single Player', 'Quantic Dream', 60, 'staff@chk.it'),

(17, 'NieR:Automata', 'NieR:Automata racconta la storia degli androidi 2B, 9S e A2 e della loro feroce battaglia per riconquistare una distopia guidata dalle macchine e invasa da potenti armi meccaniche provenienti da un altro mondo. L''umanità è stata scacciata dalla Terra da esseri meccanici. In un ultimo sforzo, la resistenza umana invia una forza di fanteria androide.', 39.99, 15.99, 'Single Player', 'Square Enix', 60, 'admin1@chk.it'),

(18, 'Sekiro: Shadows Die Twice', 'Esplora il Giappone della fine del XVI secolo, nel periodo Sengoku, un periodo brutale di costante conflitto tra la vita e la morte. Nei panni del "lupo con un solo braccio", un guerriero deturpato e salvato dalla morte, hai giurato di proteggere un giovane signore. Quando viene catturato, nulla ti fermerà nella tua ricerca, nemmeno la morte stessa.', 59.99, 29.99, 'Single Player', 'FromSoftware', 50, 'admin1@chk.it'),

(19, 'Outer Wilds', 'Outer Wilds è un gioco misterioso a mondo aperto incentrato su un sistema solare intrappolato in un ciclo temporale infinito. Unisciti al programma spaziale Outer Wilds Ventures, l''ultima agenzia spaziale nata per cercare risposte in un sistema solare bizzarro e in costante evoluzione. Chi ha costruito le rovine sulla luna? Il ciclo può essere fermato?', 22.99, 13.79, 'Single Player', 'Mobius Digital', 40, 'staff@chk.it'),

(20, 'Disco Elysium - The Final Cut', 'Disco Elysium - The Final Cut è un gioco di ruolo rivoluzionario. Sei un detective con un sistema di abilità unico a tua disposizione e un intero quartiere cittadino da esplorare. Interroga personaggi indimenticabili, risolvi omicidi o accetta mazzette. Diventa un eroe o un completo disastro di essere umano.', 39.99, 9.99, 'Single Player', 'ZA/UM', 75, 'admin2@chk.it'),
(21, 'Armored Core VI', 'ARMORED CORE VI FIRES OF RUBICON combina la grande esperienza di FromSoftware nei giochi di mech con la solidità del loro tipico gameplay d''azione, per offrire un''esperienza ad altissimo tasso di adrenalina. I giocatori guideranno il proprio mech in frenetiche battaglie omnidirezionali, sfruttando i vasti scenari e la mobilità del proprio mezzo sulla terra e in aria per assicurarsi la vittoria.', 59.99, 41.99, 'Single/Multi', 'FromSoftware', 30, 'admin1@chk.it'),

(22, 'Europa Universalis IV', 'Paradox Development Studio torna con il quarto capitolo del pluripremiato gioco che ha fatto la storia del genere strategico. Europa Universalis IV ti mette alla guida di una nazione nel corso degli anni per creare un impero globale dominante. Governa la tua nazione attraverso i secoli, con una libertà, una profondità e un''accuratezza storica senza precedenti.', 39.99, 9.99, 'Single/Multi', 'Paradox Interactive', 75, 'admin2@chk.it'),

(23, 'The Last of Us Part II', 'Cinque anni dopo un viaggio pericoloso attraverso gli Stati Uniti post-pandemici, Ellie e Joel si sono stabiliti a Jackson, nel Wyoming. La vita in una fiorente comunità di superstiti ha scosso la loro stabilità, nonostante la costante minaccia degli infetti e di altri superstiti ancora più disperati. Quando un evento violento interrompe quella pace, Ellie intraprende un viaggio implacabile per farsi giustizia.', 49.99, 39.99, 'Single Player', 'Naughty Dog', 20, 'staff@chk.it'),

(24, 'Watch Dogs 2', 'Gioca nei panni di Marcus Holloway, un brillante giovane hacker che vive nella culla della rivoluzione tecnologica, la baia di San Francisco. Unisciti al famigerato gruppo di hacker DedSec per compiere il più grande attacco informatico della storia: abbattere il ctOS 2.0, un sistema operativo invasivo utilizzato da menti criminali per monitorare e manipolare i cittadini su vasta scala.', 59.99, 8.99, 'Single/Multi', 'Ubisoft', 85, 'admin2@chk.it'),

(25, 'Pragmata', 'Pragmata è un titolo d''azione e avventura fantascientifico che presenta un profondo mondo distopico e una visione unica del futuro, ambientato sulla Luna della Terra. Il gioco sfrutterà appieno le funzionalità delle piattaforme di nuova generazione, offrendo una grafica mozzafiato grazie al ray-tracing e un''immersione narrativa mai vista prima.', 69.99, 55.99, 'Single Player', 'Capcom', 20, 'admin1@chk.it'),

(26, 'Monster Hunter: World', 'Benvenuto in un nuovo mondo! Entra nei panni di un cacciatore e uccidi mostri feroci in un ecosistema vivente e pulsante, dove potrai sfruttare il panorama e i suoi diversi abitanti per avere la meglio. Caccia da solo o in cooperativa con un massimo di altri tre giocatori, e usa i materiali raccolti dai nemici caduti per equipaggiare armi e armature sempre più potenti.', 29.99, 14.99, 'Single/Multi', 'Capcom', 50, 'admin2@chk.it'),

(27, 'Doom Eternal', 'Le armate dell''inferno hanno invaso la Terra. Diventa lo Slayer in un''epica campagna per giocatore singolo, sconfiggi i demoni attraverso le dimensioni e ferma la distruzione finale dell''umanità. L''unica cosa che temono... sei tu. Sperimenta il mix supremo di velocità e potenza in DOOM Eternal, il prossimo balzo in avanti nel combattimento in prima persona.', 39.99, 9.99, 'Single/Multi', 'id Software', 75, 'staff@chk.it'),

(28, 'Alan Wake 2', 'Una serie di omicidi rituali minaccia Bright Falls, una comunità di una piccola città circondata dal deserto del Pacifico nord-occidentale. Saga Anderson, un''esperta agente dell''FBI nota per aver risolto casi impossibili, arriva a indagare sugli omicidi. Il caso di Anderson si trasforma in un incubo quando scopre le pagine di una storia dell''orrore che inizia a avverarsi intorno a lei.', 49.99, 34.99, 'Single Player', 'Remedy Entertainment', 30, 'admin1@chk.it'),

(29, 'Star Wars Jedi: Survivor', 'La storia di Cal Kestis continua in Star Wars Jedi: Survivor, un gioco d''avventura e d''azione in terza persona sviluppato da Respawn Entertainment. Questo titolo per giocatore singolo, incentrato sulla narrativa, riprende cinque anni dopo gli eventi di Star Wars Jedi: Fallen Order e segue la lotta sempre più disperata di Cal mentre la galassia scende ulteriormente nell''oscurità.', 49.99, 24.99, 'Single Player', 'Respawn Entertainment', 50, 'admin2@chk.it'),

(30, 'Persona 5 Royal', 'Preparati per l''esperienza GDR definitiva pluripremiata in questa edizione definitiva di Persona 5 Royal, ricca di tesori e contenuti scaricabili inclusi! Indossa la maschera di Joker e unisciti ai Ladri Fantasma di Cuori per organizzare colpi grandiosi, infiltrarti nelle menti dei corrotti e spingerli a cambiare vita nella vibrante città di Tokyo.', 59.99, 23.99, 'Single Player', 'Atlus', 60, 'staff@chk.it'),

(31, 'Mass Effect Legendary Edition', 'Una persona è l''unica cosa che si frappone tra l''umanità e la più grande minaccia che abbia mai affrontato. Rivivi la leggenda di uno dei più acclamati franchise videoludici con la Mass Effect Legendary Edition. Include i contenuti di base per giocatore singolo e oltre 40 contenuti scaricabili dei tre celebri giochi, tutti rimasterizzati e ottimizzati in splendido 4K.', 59.99, 11.99, 'Single Player', 'BioWare', 80, 'admin1@chk.it'),

(32, 'Death Stranding Director''s Cut', 'Dal leggendario autore Hideo Kojima arriva un''esperienza che sfida ogni definizione di genere, ora espansa in questa DIRECTOR''S CUT definitiva. Nei panni di Sam Porter Bridges, il tuo compito è quello di offrire speranza all''umanità connettendo gli ultimi sopravvissuti di un''America decimata. Riuscirai a ricomporre un mondo andato in frantumi, un passo alla volta?', 39.99, 19.99, 'Single Player', 'Kojima Productions', 50, 'staff@chk.it');

-- 4. Popolamento Ticket (5 record)
-- Collegati ad Amministratori e Utenti
INSERT INTO Ticket (ID_Ticket, Campo, Descrizione_Ticket, Email_Amm, Username_Ut, Email_Ut) VALUES
(1, 'Pagamento', 'Errore durante la transazione PayPal.', 'admin1@chk.it', 'Geralt90', 'geralt@email.it'),
(2, 'Tecnico', 'La chiave risulta già utilizzata.', 'admin2@chk.it', 'DragonBorn', 'dovah@skyrim.com'),
(3, 'Account', 'Vorrei cambiare la mia email.', 'staff@chk.it', 'Ciri_05', 'ciri@kaermorhen.org'),
(4, 'Rimborso', 'Acquisto errato, chiedo reso.', 'admin1@chk.it', 'VaultDweller', 'fallout@vault.com'),
(5, 'Info', 'Quando torna disponibile God of War?', 'staff@chk.it', 'Arthur_M', 'morgan@reddead.it');

-- 5. Popolamento Recensione (5 record)
INSERT INTO Recensione
(ID_Recensione, ID_Prodotto, Voto, Descrizione_Rec, Username_Ut, Email_Ut)
VALUES
(1, 1, 5, 'Consegna istantanea, ottimo prezzo!', 'Geralt90', 'geralt@email.it'),
(2, 2, 4, 'Tutto ok, ma il supporto è lento.', 'DragonBorn', 'dovah@skyrim.com'),
(3, 1, 5, 'Elden Ring a metà prezzo, incredibile.', 'Ciri_05', 'ciri@kaermorhen.org'),
(4, 5, 1, 'Chiave non funzionante, attendo risposta.', 'VaultDweller', 'fallout@vault.com'),
(5, 3, 5, 'Sito affidabile, consigliato.', 'Arthur_M', 'morgan@reddead.it');

-- 6. Popolamento Carrello (1 carrello per ogni utente)
INSERT INTO Carrello (ID_Carrello, Username_Ut, Email_Ut) VALUES
(1, 'Geralt90', 'geralt@email.it'),
(2, 'DragonBorn', 'dovah@skyrim.com'),
(3, 'Ciri_05', 'ciri@kaermorhen.org'),
(4, 'VaultDweller', 'fallout@vault.com'),
(5, 'Arthur_M', 'morgan@reddead.it');

-- 7. Popolamento Contiene (relazione Carrello-Prodotto con Quantita)
INSERT INTO Contiene (ID_Carrello, ID_Prodotto, Quantita) VALUES
(1, 1, 1), -- Geralt90 ha Elden Ring
(2, 2, 1), -- DragonBorn ha Cyberpunk 2077
(2, 3, 1), -- DragonBorn ha anche Minecraft
(3, 4, 1), -- Ciri_05 ha Stray
(4, 5, 1), -- VaultDweller ha FIFA 26
(5, 1, 1),
(5, 3, 1),
(5, 5, 1); -- Arthur_M ha 3 prodotti nel carrello

-- 8. Popolamento Ordine (collegato al carrello)
INSERT INTO Ordine (ID_Ordine, Importo_tot, DataUltimaModifica, ID_Carrello) VALUES
(1, 44.99, '2026-06-20 10:15:00', 1),
(2, 54.98, '2026-06-21 12:30:00', 2),
(3, 18.89, '2026-06-22 16:45:00', 3),
(4, 34.99, '2026-06-23 09:10:00', 4),
(5, 109.97, '2026-06-24 20:05:00', 5);

-- 9. Popolamento Genere (5 record)
INSERT INTO Genere (Genere, ID_Prodotto) VALUES
('Action RPG', 1), -- Action RPG
('RPG-Sci-fi', 2), -- RPG / Sci-fi
('Sandbox', 3), -- Sandbox
('Adventure', 4), -- Adventure
('Sport', 5); -- Sport

-- 9b. Piattaforme supportate (relazione molti-a-molti)
INSERT INTO Piattaforma (Piattaforma, ID_Prodotto)
SELECT 'Windows', ID_Prodotto FROM Prodotto;

INSERT INTO Piattaforma (Piattaforma, ID_Prodotto) VALUES
('PlayStation', 1), ('Xbox', 1),
('PlayStation', 2), ('Xbox', 2),
('macOS', 3), ('Linux', 3), ('Nintendo Switch', 3),
('PlayStation', 4), ('Xbox', 4),
('PlayStation', 5), ('Xbox', 5), ('Nintendo Switch', 5);

-- 10. Popolamento ChiaveDigitale (Esempi di chiavi finte)
INSERT INTO ChiaveDigitale (ID_Prodotto) VALUES
(1),
(2),
(4);

-- 11. Popolamento Account (Esempi di credenziali finte)
INSERT INTO Account (ID_Prodotto, Credenziali) VALUES
(3, 'mc_user:block_pass_2024'),
(5, 'ea_sports_fan:goal_2023_psn');

-- 12. Popolamento dei media per i prodotti
INSERT INTO MediaProdotto (ID_Media, ID_Prodotto, Tipo, URL_Media) VALUES
('M1', 1, 'image', 'img/giochi/eldenring/eldenRingCopertina.jpeg'),
('M2', 1, 'image', 'img/giochi/eldenring/eldenringLimgrave.jpg'),
('M3', 1, 'video', 'img/giochi/eldenring/eldenringTrailer.mp4'),

('M4', 2, 'image', 'img/giochi/cyberpunk/cyberpunkCopertina.jpg'),
('M5', 2, 'image', 'img/giochi/cyberpunk/cyberpunkNightCity.png'),
('M6', 2, 'video', 'img/giochi/cyberpunk/cyberpunkTrailer.mp4'),

('M7', 3, 'image', 'img/giochi/minecraft/minecraftCopertina.jpg'),
('M8', 3, 'image', 'img/giochi/minecraft/minecraftScreenshot1.png'),

('M9', 4, 'image', 'img/giochi/stray/strayCopertina.png'),
('M10', 4, 'image', 'img/giochi/stray/strayScreenshot1.png'),

('M11', 5, 'image', 'img/giochi/fifa26/fifa26Copertina.jpg'),
('M12', 5, 'image', 'img/giochi/fifa26/fifa26Screenshot1.jpg'),

('M13', 6, 'image', 'img/giochi/thewolfamongus/thewolfamongusCopertina.jpg'),
('M14', 6, 'image', 'img/giochi/thewolfamongus/thewolfamongusScreenshot1.jpg'),

('M15', 7, 'image', 'img/giochi/baldursgate3/baldursgate3Copertina.jpg'),
('M16', 7, 'image', 'img/giochi/baldursgate3/baldursgate3Screenshot1.jpg'),

('M17', 8, 'image', 'img/giochi/lifeisstrange/lifeisstrangeCopertina.jpg'),
('M18', 8, 'image', 'img/giochi/lifeisstrange/lifeisstrangeScreenshot1.jpg'),

('M19', 9, 'image', 'img/giochi/devilmaycry5/devilmaycry5Copertina.jpg'),
('M20', 9, 'image', 'img/giochi/devilmaycry5/devilmaycry5Screenshot1.jpg'),

('M21', 10, 'image', 'img/giochi/thewitcher3/thewitcher3Copertina.jpg'),
('M22', 10, 'image', 'img/giochi/thewitcher3/thewitcher3Screenshot1.jpg'),

('M23', 11, 'image', 'img/giochi/reddeadredemption2/reddeadredemption2Copertina.jpg'),
('M24', 11, 'image', 'img/giochi/reddeadredemption2/reddeadredemption2Screenshot1.png'),

('M25', 12, 'image', 'img/giochi/hollowknight/hollowknightCopertina.jpg'),
('M26', 12, 'image', 'img/giochi/hollowknight/hollowknightScreenshot1.png'),

('M27', 13, 'image', 'img/giochi/hades/hadesCopertina.png'),
('M28', 13, 'image', 'img/giochi/hades/hadesScreenshot1.jpg'),

('M29', 14, 'image', 'img/giochi/godofwar/godofwarCopertina.jpg'),
('M30', 14, 'image', 'img/giochi/godofwar/godofwarScreenshot1.jpg'),

('M31', 15, 'image', 'img/giochi/residentevil4/residentevil4Copertina.jpeg'),
('M32', 15, 'image', 'img/giochi/residentevil4/residentevil4Screenshot1.jpg'),

('M33', 16, 'image', 'img/giochi/detroitbecomehuman/detroitbecomehumanCopertina.jpg'),
('M34', 16, 'image', 'img/giochi/detroitbecomehuman/detroitbecomehumanScreenshot1.png'),

('M35', 17, 'image', 'img/giochi/nierautomata/nierautomataCopertina.jpg'),
('M36', 17, 'image', 'img/giochi/nierautomata/nierautomataScreenshot1.jpg'),

('M37', 18, 'image','img/giochi/sekiro/sekiroCopertina.jpg'),
('M38', 18, 'image', 'img/giochi/sekiro/sekiroScreenshot1.jpg'),

('M39', 19, 'image', 'img/giochi/outerwilds/outerwildsCopertina.jpeg'),
('M40', 19, 'image', 'img/giochi/outerwilds/outerwildsScreenshot1.jpg'),

('M41', 20, 'image', 'img/giochi/discoelysium/discoelysiumCopertina.png'),
('M42', 20, 'image', 'img/giochi/discoelysium/discoelysiumScreenshot1.jpg'),

('M43', 21, 'image', 'img/giochi/armoredcoreVI/armoredcoreVICopertina.png'),
('M44', 21, 'image', 'img/giochi/armoredcoreVI/armoredcoreVIScreenshot1.jpeg'),

('M45', 22, 'image', 'img/giochi/europauniversalisV/europauniversalisVCopertina.jpg'),
('M46', 22, 'image', 'img/giochi/europauniversalisV/europauniversalisVScreenshot1.jpg'),

('M47', 23, 'image', 'img/giochi/thelastofuspart2/thelastofuspart2Copertina.jpg'),
('M48', 23, 'image', 'img/giochi/thelastofuspart2/thelastofuspart2Screenshot1.jpeg'),

('M49', 24, 'image', 'img/giochi/watchdogs2/watchdogs2Copertina.jpg'),
('M50', 25, 'image', 'img/giochi/pragmata/pragmataCopertina.jpg'),
('M51', 26, 'image', 'img/giochi/monsterhunterworld/monsterhunterworldCopertina.jpg'),
('M52', 27, 'image', 'img/giochi/doometernal/doometernalCopertina.jpg'),
('M53', 28, 'image', 'img/giochi/alanwake2/alanwake2Copertina.jpg'),
('M54', 29, 'image', 'img/giochi/starwarsjedi/starwarsjediCopertina.jpg'),
('M55', 30, 'image', 'img/giochi/persona5royal/persona5royalCopertina.png'),
('M56', 31, 'image', 'img/giochi/masseffect/masseffectCopertina.jpg'),
('M57', 32, 'image', 'img/giochi/deathstranding/deathstrandingCopertina.jpg');
