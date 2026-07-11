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
('Arthur_M', 'morgan@reddead.it', '0b125fe2d4b39f076975d00a80bbf491a99a01e2'), -- password: outlaw22
('Kratos_Gamer', 'kratos@olympus.it', SHA1('blades123')),
('LinkHero', 'link@hyrule.it', SHA1('master001')),
('Ellie_W', 'ellie@jackson.it', SHA1('survive22')),
('DoomSlayer', 'slayer@hell.it', SHA1('riptear99')),
('ShepardN7', 'shepard@normandy.it', SHA1('massrelay7')),
('AloyHunter', 'aloy@machine.it', SHA1('focus2026')),
('CloudStrife', 'cloud@midgar.it', SHA1('buster999')),
('ArthurMorgan2', 'arthur2@rdr.it', SHA1('cowboy77'));

-- 3. Popolamento Prodotto (5 record)
-- Collegati agli Amministratori
INSERT INTO Prodotto (ID_Prodotto, Nome, Descrizione_Prod, Prezzo_OG, Prezzo_Scontato, Modalita_Gioco, Casa_Sviluppatrice, Sconto, Email_Amm, Disponibile, Genere) VALUES
(1, 'Elden Ring', 'UN NUOVO ACTION RPG FANTASY. Alzati, Senzaluce, e fatti guidare dalla grazia per brandire il potere dell''Anello ancestrale. Un mondo mozzafiato ricco di emozioni e mistero. L''Interregno fa parte di un vasto continente in cui magnifici spazi aperti ed enormi segrete con un complesso design 3D sono perfettamente integrati. Durante l''esplorazione, sarai accolto dalla gioia di scoprire travolgenti minacce sconosciute.', 59.99, 44.99, 'Single/Multi', 'FromSoftware', 25, 'admin1@chk.it', TRUE, 'Action RPG'),

(2, 'Cyberpunk 2077', 'Cyberpunk 2077 è un GDR d''azione open-world ambientato nella megalopoli Night City, dove vesti i panni di un mercenario cyberpunk implicato in una lotta per la sopravvivenza a colpi di fanta-scienza. Splendidamente aggiornato pensando al futuro e contenente tutti i contenuti aggiuntivi gratuiti, personalizza il tuo personaggio e il tuo stile di gioco mentre accetti lavori, costruisci una reputazione e sblocchi potenziamenti.', 49.99, 24.99, 'Single Player', 'CD Projekt Red', 50, 'admin1@chk.it', TRUE, 'GDR'),

(3, 'Minecraft', 'Preparati a un''avventura dalle infinite possibilità: costruisci, scava, combatti creature ed esplora il panorama di Minecraft in continuo mutamento. Crea ed esplora il tuo personale mondo, dove l''unico limite è la tua immaginazione. Assicurati solo di costruire un rifugio prima che arrivi la notte per rimanere al sicuro dai mostri.', 29.99, 23.99, 'Multiplayer', 'Mojang', 20, 'admin2@chk.it', TRUE, 'Sandbox'),

(4, 'Stray', 'Un gatto randagio, smarrito e separato dalla famiglia, deve risolvere un antico mistero per sfuggire a una cyber-città ormai dimenticata. Stray è un gioco di avventura in terza persona ambientato tra i vicoli illuminati al neon di una cyber-città in decadenza e i suoi bassifondi più cupi e squallidi. Guarda il mondo attraverso gli occhi di un gatto randagio e interagisci con l''ambiente in modo giocoso.', 26.99, 18.89, 'Single Player', 'BlueTwelve Studio', 30, 'staff@chk.it', TRUE, 'Avventura'),

(5, 'FIFA 26', 'EA SPORTS FC 26 ti offre l''esperienza calcistica più autentica su PC. Gestisci i club più importanti del pianeta, sfida i tuoi amici o crea la squadra dei tuoi sogni in Ultimate Team. Grazie a una fedeltà grafica straordinaria e a un gameplay rifinito basato sulla reattività e sulla tattica di squadra, vivi ogni singola partita come se fossi davvero sul rettangolo di gioco.', 69.99, 34.99, 'Multiplayer', 'EA Sports', 50, 'admin2@chk.it', TRUE, 'Sport'),

(6, 'The Wolf Among Us', 'Dai creatori del gioco dell''anno 2012: The Walking Dead, arriva un thriller cupo, violento e maturo basato sui pluripremiati fumetti di Fables. Nei panni di Bigby Wolf (il Grande Lupo Cattivo) scoprirai che un brutale e sanguinoso omicidio è solo un assaggio di ciò che verrà in una serie di giochi in cui ogni tua singola decisione può avere enormi conseguenze.', 14.99, 7.49, 'Single Player', 'Telltale Games', 50, 'admin1@chk.it', TRUE, 'Avventura Grafica'),

(7, 'Baldur''s Gate 3', 'Raduna il tuo gruppo e torna nei Reami Dimenticati in una storia di amicizia e tradimento, sacrificio e sopravvivenza, e sul fascino del potere assoluto. Abilità misteriose si stanno risvegliando dentro di te, derivanti da un parassita dei mind flayer piantato nel tuo cervello. Resisti e rivolta l''oscurità contro se stessa, oppure abbraccia la corruzione e diventa il male supremo.', 59.99, 47.99, 'Single/Multi', 'Larian Studios', 20, 'admin1@chk.it', TRUE, 'GDR'),

(8, 'Life is Strange', 'Life is Strange è una storia in cinque parti che si propone di rivoluzionare i giochi basati su scelte e conseguenze, permettendo al giocatore di riavvolgere il tempo e influenzare il passato, il presente e il futuro. Segui la storia di Max Caulfield, una fotografa che scopre di poter riavvolgere il tempo mentre salva la sua migliore amica Chloe Price.', 19.99, 3.99, 'Single Player', 'Dontnod Entertainment', 80, 'staff@chk.it', TRUE, 'Avventura Grafica'),

(9, 'Devil May Cry 5', 'Il cacciatore di demoni più sfrontato torna con stile nel gioco che i fan dell''azione stavano aspettando. Una nuova invasione demoniaca ha inizio quando i semi di un "albero demoniaco" mettono radici a Red Grave City. Questa infernale incursione attira l''attenzione del giovane cacciatore di demoni Nero, un alleato di Dante che ora si trova privato del suo braccio demoniaco.', 29.99, 11.99, 'Single Player', 'Capcom', 60, 'admin2@chk.it', TRUE, 'Action'),

(10, 'The Witcher 3: Wild Hunt', 'Sei Geralt di Rivia, cacciatore di mostri mercenario. Davanti a te si estende un continente devastato dalla guerra e infestato da mostri, che puoi esplorare a piacimento. Il tuo contratto attuale? Trovare Ciri, la Figlia della Profezia, un''arma vivente capace di alterare la forma del mondo prima che ci riesca la Caccia Selvaggia.', 29.99, 7.49, 'Single Player', 'CD Projekt Red', 75, 'admin1@chk.it', TRUE, 'GDR'),

(11, 'Red Dead Redemption 2', 'Vincitore di oltre 175 premi come Gioco dell''Anno, Red Dead Redemption 2 è l''epica storia del fuorilegge Arthur Morgan e della banda di Van der Linde, in fuga attraverso l''America all''alba dell''era moderna. Include anche l''accesso al mondo condiviso di Red Dead Online.', 59.99, 19.79, 'Single/Multi', 'Rockstar Games', 67, 'admin2@chk.it', TRUE, 'Azione/Avventura'),

(12, 'Hollow Knight', 'Affronta le profondità di un regno dimenticato. Sotto la città calante di Dirtmouth giace un antico regno in rovina. Molti vengono attirati sotto la superficie in cerca di ricchezze, gloria o risposte a vecchi segreti. Esplora i sistemi di caverne interconnessi, combatti bizzarre creature contaminate e stringi amicizia con insetti stravaganti.', 14.99, 7.49, 'Single Player', 'Team Cherry', 50, 'staff@chk.it', TRUE, 'Metroidvania'),

(13, 'Hades', 'Sfida il dio dei morti in questo dungeon crawler rogue-like hack & slash dai creatori di Bastion e Transistor. Nei panni dell''immortale Principe degli Inferi, brandirai i poteri e le armi mitologiche dell''Olimpo per liberarti dalle grinfie del dio dei morti in persona, diventando più forte e scoprendo nuovi dettagli della storia a ogni tentativo di fuga.', 24.50, 12.25, 'Single Player', 'Supergiant Games', 50, 'admin1@chk.it', TRUE, 'Rogue-like'),

(14, 'God of War', 'Avendo lasciato alle spalle la sua vendetta contro gli dèi dell''Olimpo, Kratos vive ora nella terra delle divinità e dei mostri norreni. In questo mondo duro e spietato, deve combattere per sopravvivere e insegnare a suo figlio a fare lo stesso, affrontando una nuova prospettiva e una visuale sopra la spalla che porta l''azione più vicina che mai.', 49.99, 24.99, 'Single Player', 'Santa Monica Studio', 50, 'admin2@chk.it', TRUE, 'Azione/Avventura'),

(15, 'Resident Evil 4', 'La sopravvivenza è solo l''inizio. Sono passati sei anni dal disastro biologico di Raccoon City. L''agente Leon S. Kennedy, uno dei sopravvissuti all''incidente, è stato inviato a salvare la figlia rapita del presidente degli Stati Uniti. La individua in un villaggio europeo isolato, dove la gente del posto è affetta da qualcosa di terribilmente sbagliato.', 39.99, 29.99, 'Single Player', 'Capcom', 25, 'admin2@chk.it', TRUE, 'Survival Horror'),

(16, 'Detroit: Become Human', 'Fino a dove ti spingerai per essere libero? Detroit, 2038. Gli androidi, macchine dalle sembianze umane, hanno sostituito gli operai umani. Non si stancano, non disobbediscono e non dicono mai di no... finché qualcosa non cambia. Alcuni di loro iniziano a manifestare sentimenti ed emozioni, diventando "devianti". Controlla three androidi nel loro viaggio.', 39.99, 15.99, 'Single Player', 'Quantic Dream', 60, 'staff@chk.it', TRUE, 'Avventura Grafica'),

(17, 'NieR:Automata', 'NieR:Automata racconta la storia degli androidi 2B, 9S e A2 e della loro feroce battaglia per riconquistare una distopia guidata dalle macchine e invasa da potenti armi meccaniche provenienti da un altro mondo. L''umanità è stata scacciata dalla Terra da esseri meccanici. In un ultimo sforzo, la resistenza umana invia una forza di fanteria androide.', 39.99, 15.99, 'Single Player', 'Square Enix', 60, 'admin1@chk.it', TRUE, 'Action RPG'),

(18, 'Sekiro: Shadows Die Twice', 'Esplora il Giappone della fine del XVI secolo, nel periodo Sengoku, un periodo brutale di costante conflitto tra la vita e la morte. Nei panni del "lupo con un solo braccio", un guerriero deturpato e salvato dalla morte, hai giurato di proteggere un fragile signore. Quando viene catturato, nulla ti fermerà nella tua ricerca, nemmeno la morte stessa.', 59.99, 29.99, 'Single Player', 'FromSoftware', 50, 'admin1@chk.it', TRUE, 'Action'),

(19, 'Outer Wilds', 'Outer Wilds è un gioco misterioso a mondo aperto incentrato su un sistema solare intrappolato in un ciclo temporale infinito. Unisciti al programma spaziale Outer Wilds Ventures, l''ultima agenzia spaziale nata per cercare risposte in un sistema solare bizzarro e in costante evoluzione. Chi ha costruito le rovine sulla luna? Il ciclo può essere fermato?', 22.99, 13.79, 'Single Player', 'Mobius Digital', 40, 'staff@chk.it', TRUE, 'Avventura'),

(20, 'Disco Elysium - The Final Cut', 'Disco Elysium - The Final Cut è un gioco di ruolo rivoluzionario. Sei un detective con un sistema di abilità unico a tua disposizione e un intero quartiere cittadino da esplorare. Interroga personaggi indimenticabili, risolvi omicidi o accetta mazzette. Diventa un eroe o un completo disastro di essere umano.', 39.99, 9.99, 'Single Player', 'ZA/UM', 75, 'admin2@chk.it', TRUE, 'GDR'),

(21, 'Armored Core VI', 'ARMORED CORE VI FIRES OF RUBICON combina la grande esperienza di FromSoftware nei giochi di mech con la solidità del loro tipico gameplay d''azione, per offrire un''esperienza ad altissimo tasso di adrenalina. I giocatori guideranno il proprio mech in frenetiche battaglie omnidirezionali, sfruttando i vasti scenari e la mobilità del proprio mezzo sulla terra e in aria per assicurarsi la vittoria.', 59.99, 41.99, 'Single/Multi', 'FromSoftware', 30, 'admin1@chk.it', TRUE, 'Action'),

(22, 'Europa Universalis IV', 'Paradox Development Studio torna con il quarto capitolo del pluripremiato gioco che ha fatto la storia del genere strategico. Europa Universalis IV ti mette alla guida di una nazione nel corso degli anni per creare un impero globale dominante. Governa la tua nazione attraverso i secoli, con una libertà, una profondità e un''accuratezza storica senza precedenti.', 39.99, 9.99, 'Single/Multi', 'Paradox Interactive', 75, 'admin2@chk.it', TRUE, 'Strategia'),

(23, 'The Last of Us Part II', 'Cinque anni dopo un viaggio pericoloso attraverso gli Stati Uniti post-pandemici, Ellie e Joel si sono stabiliti a Jackson, nel Wyoming. La vita in una fiorente comunità di superstiti ha scosso la loro stabilità, nonostante la costante minaccia degli infetti e di altri superstiti ancora più disperati. Quando un evento violento interrompe quella pace, Ellie intraprende un viaggio implacabile per farsi giustizia.', 49.99, 39.99, 'Single Player', 'Naughty Dog', 20, 'staff@chk.it', TRUE, 'Azione/Avventura'),

(24, 'Watch Dogs 2', 'Gioca nei panni di Marcus Holloway, un brillante giovane hacker che vive nella culla della rivoluzione tecnologica, la baia di San Francisco. Unisciti al famigerato gruppo di hacker DedSec per compiere il più grande attacco informatico della storia: abbattere il ctOS 2.0, un sistema operativo invasivo utilizzato da menti criminali per monitorare e manipolare i cittadini su vasta scala.', 59.99, 8.99, 'Single/Multi', 'Ubisoft', 85, 'admin2@chk.it', TRUE, 'Azione/Avventura'),

(25, 'Pragmata', 'Pragmata è un titolo d''azione e avventura fantascientifico che presenta un profondo mondo distopico e una visione unica del futuro, ambientato sulla Luna della Terra. Il gioco sfrutterà appieno le funzionalità delle piattaforme di nuova generazione, offrendo una grafica mozzafiato grazie al ray-tracing e un''immersione narrativa mai vista prima.', 69.99, 55.99, 'Single Player', 'Capcom', 20, 'admin1@chk.it', TRUE, 'Azione/Avventura'),

(26, 'Monster Hunter: World', 'Benvenuto in un nuovo mondo! Entra nei panni di un cacciatore e uccidi mostri feroci in un ecosistema vivente e pulsante, dove potrai sfruttare il panorama e i suoi diversi abitanti per avere la meglio. Caccia da solo o in cooperativa con un massimo di altri tre giocatori, e usa i materiali raccolti dai nemici caduti per equipaggiare armi e armature sempre più potenti.', 29.99, 14.99, 'Single/Multi', 'Capcom', 50, 'admin2@chk.it', TRUE, 'Action RPG'),

(27, 'Doom Eternal', 'Le armate dell''inferno hanno invaso la Terra. Diventa lo Slayer in un''epica campagna per giocatore singolo, sconfiggi i demoni attraverso le dimensioni e ferma la distruzione finale dell''umanità. L''unica cosa che temono... sei tu. Sperimenta il mix supremo di velocità e potenza in DOOM Eternal, il prossimo balzo in avanti nel combattimento in prima persona.', 39.99, 9.99, 'Single/Multi', 'id Software', 75, 'staff@chk.it', TRUE, 'Sparatutto'),

(28, 'Alan Wake 2', 'Una serie di omicidi rituali minaccia Bright Falls, una comunità di una piccola città circondata dal deserto del Pacifico nord-occidentale. Saga Anderson, un''esperta agente dell''FBI nota per aver risolto casi impossibili, arriva a indagare sugli omicidi. Il caso di Anderson si trasforma in un incubo quando scopre le pagine di una storia dell''orrore che inizia a avverarsi intorno a lei.', 49.99, 34.99, 'Single Player', 'Remedy Entertainment', 30, 'admin1@chk.it', TRUE, 'Survival Horror'),

(29, 'Star Wars Jedi: Survivor', 'La storia di Cal Kestis continua in Star Wars Jedi: Survivor, un gioco d''avventura e d''azione in terza persona sviluppato da Respawn Entertainment. Questo titolo per giocatore singolo, incentrato sulla narrativa, riprende cinque anni dopo gli eventi di Star Wars Jedi: Fallen Order e segue la lotta sempre più disperata di Cal mentre la galassia scende ulteriormente nell''oscurità.', 49.99, 24.99, 'Single Player', 'Respawn Entertainment', 50, 'admin2@chk.it', TRUE, 'Azione/Avventura'),

(30, 'Persona 5 Royal', 'Preparati per l''esperienza GDR definitiva pluripremiata in questa edizione definitiva di Persona 5 Royal, ricca di tesori e contenuti scaricabili inclusi! Indossa la maschera di Joker e unisciti ai Ladri Fantasma di Cuori per organizzare colpi grandiosi, infiltrarti nelle menti dei corrotti e spingerli a cambiare vita nella vibrante città di Tokyo.', 59.99, 23.99, 'Single Player', 'Atlus', 60, 'staff@chk.it', TRUE, 'GDR'),

(31, 'Mass Effect Legendary Edition', 'Una persona è l''unica cosa che si frappone tra l''umanità e la più grande minaccia che abbia mai affrontato. Rivivi la leggenda di uno dei più acclamati franchise videoludici con la Mass Effect Legendary Edition. Include i contenuti di base per giocatore singolo e oltre 40 contenuti scaricabili dei tre celebri giochi, tutti rimasterizzati e ottimizzati in splendido 4K.', 59.99, 11.99, 'Single Player', 'BioWare', 80, 'admin1@chk.it', TRUE, 'GDR'),

(32, 'Death Stranding Director''s Cut', 'Dal leggendario autore Hideo Kojima arriva un''esperienza che sfida ogni definizione di genere, ora espansa in questa DIRECTOR''S CUT definitiva. Nei panni di Sam Porter Bridges, il tuo compito è quello di offrire speranza all''umanità connettendo gli ultimi sopravvissuti di un''America decimata. Riuscirai a ricomporre un mondo andato in frantumi, un passo alla volta?', 39.99, 19.99, 'Single Player', 'Kojima Productions', 50, 'staff@chk.it', TRUE, 'Azione/Avventura');

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
(5, 'Arthur_M', 'morgan@reddead.it'),
(6, 'Kratos_Gamer', 'kratos@olympus.it'),
(7, 'LinkHero', 'link@hyrule.it'),
(8, 'Ellie_W', 'ellie@jackson.it'),
(9, 'DoomSlayer', 'slayer@hell.it'),
(10, 'ShepardN7', 'shepard@normandy.it'),
(11, 'AloyHunter', 'aloy@machine.it'),
(12, 'CloudStrife', 'cloud@midgar.it'),
(13, 'ArthurMorgan2', 'arthur2@rdr.it');

-- 7. Popolamento Contiene (relazione Carrello-Prodotto con Quantita)
INSERT INTO Contiene (ID_Carrello, ID_Prodotto, Quantita) VALUES
(1, 1, 1), -- Geralt90 ha Elden Ring
(2, 2, 1), -- DragonBorn ha Cyberpunk 2077
(2, 3, 1), -- DragonBorn ha anche Minecraft
(3, 4, 1), -- Ciri_05 ha Stray
(4, 5, 1), -- VaultDweller ha FIFA 26
(5, 1, 1),
(5, 3, 1),
(5, 5, 1), -- Arthur_M ha 3 prodotti nel carrello
-- Kratos
(6, 14, 1),
(6, 15, 1),
-- Link
(7, 30, 1),
(7, 12, 1),
-- Ellie
(8, 23, 1),
(8, 28, 1),
-- Doom Slayer
(9, 27, 1),
(9, 18, 1),
-- Shepard
(10, 31, 1),
(10, 22, 1),
-- Aloy
(11, 26, 1),
(11, 19, 1),
-- Cloud
(12, 17, 1),
(12, 20, 1),
-- Arthur
(13, 11, 1),
(13, 10, 1);

-- 8. Popolamento Ordine (collegato al carrello + descrizione storica)
INSERT INTO Ordine 
(ID_Ordine, Importo_tot, DataOrdine, ID_Carrello, Descrizione_Acquisto) VALUES
(1, 44.99, '2026-06-20 10:15:00', 1,
'ID: 1 - Elden Ring - 44.99€ - Qta.: 1;'),

(2, 54.98, '2026-06-21 12:30:00', 2,
'ID: 2 - Cyberpunk 2077 - 24.99€ - Qta.: 1; ID: 3 - Minecraft - 23.99€ - Qta.: 1;'),

(3, 18.89, '2026-06-22 16:45:00', 3,
'ID: 4 - Stray - 18.89€ - Qta.: 1;'),

(4, 34.99, '2026-06-23 09:10:00', 4,
'ID: 5 - FIFA 26 - 34.99€ - Qta.: 1;'),

(5, 109.97, '2026-06-24 20:05:00', 5,
'ID: 1 - Elden Ring - 44.99€ - Qta.: 1; ID: 3 - Minecraft - 23.99€ - Qta.: 1; ID: 5 - FIFA 26 - 34.99€ - Qta.: 1;'),

(6, 54.98, '2026-06-25 11:20:00', 6,
'ID: 14 - God of War - 24.99€ - Qta.: 1; ID: 15 - Resident Evil 4 - 29.99€ - Qta.: 1;'),

(7, 31.48, '2026-06-26 14:10:00', 7,
'ID: 30 - Persona 5 Royal - 23.99€ - Qta.: 1; ID: 12 - Hollow Knight - 7.49€ - Qta.: 1;'),

(8, 69.98, '2026-06-27 18:00:00', 8,
'ID: 23 - The Last of Us Part II - 39.99€ - Qta.: 1; ID: 28 - Alan Wake 2 - 34.99€ - Qta.: 1;'),

(9, 39.98, '2026-06-28 09:30:00', 9,
'ID: 27 - Doom Eternal - 9.99€ - Qta.: 1; ID: 18 - Sekiro: Shadows Die Twice - 29.99€ - Qta.: 1;'),

(10, 21.98, '2026-06-29 20:15:00', 10,
'ID: 31 - Mass Effect Legendary Edition - 11.99€ - Qta.: 1; ID: 22 - Europa Universalis IV - 9.99€ - Qta.: 1;'),

(11, 28.78, '2026-06-30 12:00:00', 11,
'ID: 26 - Monster Hunter: World - 14.99€ - Qta.: 1; ID: 19 - Outer Wilds - 13.79€ - Qta.: 1;'),

(12, 19.98, '2026-07-01 16:40:00', 12,
'ID: 17 - NieR:Automata - 15.99€ - Qta.: 1; ID: 20 - Disco Elysium - The Final Cut - 9.99€ - Qta.: 1;'),

(13, 26.99, '2026-07-02 21:10:00', 13,
'ID: 11 - Red Dead Redemption 2 - 19.79€ - Qta.: 1; ID: 10 - The Witcher 3: Wild Hunt - 7.49€ - Qta.: 1;');

-- 9. Popolamento ChiaveDigitale (Esempi di chiavi finte)
INSERT INTO ChiaveDigitale (ID_Prodotto, Chiave) VALUES
(1, 'ELDEN-RING-KEY'),
(2, 'CYBER-2077-KEY'),
(7, 'BG3-KEY-77XY'),
(11, 'RDR2-ARTHUR-KEY'),
(14, 'GOW-KEY-2026'),
(18, 'SEKIRO-SHADOW'),
(23, 'TLOU2-ELLIE'),
(27, 'DOOM-ETERNAL'),
(31, 'ME-LEGEND-N7'),
(30, 'P5-JOKER-KEY');

-- 10. Popolamento Account (Esempi di credenziali finte)
INSERT INTO Account (ID_Prodotto, Credenziali) VALUES
(3, 'minecraft:DragonBlock2026'),
(5, 'ea:UltimateTeam2026'),
(7, 'bg3:TavHero2026'),
(13, 'hades:OlympusRun2026'),
(17, 'nier:Android2B2026'),
(20, 'disco:DetectiveRevachol'),
(22, 'paradox:EuropaMaster'),
(26, 'mhworld:HunterRank99');

-- 11. Popolamento dei media per i prodotti
INSERT INTO MediaProdotto (ID_Media, ID_Prodotto, Tipo, URL_Media) VALUES
('M1', 1, 'image', 'img/giochi/eldenring/eldenringCopertina.png'),
('M2', 1, 'image', 'img/giochi/eldenring/eldenringScreenshot1.jpg'),


('M4', 2, 'image', 'img/giochi/cyberpunk/cyberpunkCopertina.jpg'),
('M5', 2, 'image', 'img/giochi/cyberpunk/cyberpunkScreenshot1.png'),


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
('M50', 24, 'image', 'img/giochi/watchdogs2/watchdogs2Screenshot1.jpg'),

('M51', 25, 'image', 'img/giochi/pragmata/pragmataCopertina.jpg'),
('M52', 25, 'image', 'img/giochi/pragmata/pragmataScreenshot1.jpg'),

('M53', 26, 'image', 'img/giochi/monsterhunterworld/monsterhunterworldCopertina.jpg'),
('M54', 26, 'image', 'img/giochi/monsterhunterworld/monsterhunterworldScreenshot1.jpg'),

('M55', 27, 'image', 'img/giochi/doometernal/doometernalCopertina.jpg'),
('M56', 27, 'image', 'img/giochi/doometernal/doometernalScreenshot1.jpg'),

('M57', 28, 'image', 'img/giochi/alanwake2/alanwake2Copertina.jpg'),
('M58', 28, 'image', 'img/giochi/alanwake2/alanwake2Screenshot1.jpg'),

('M59', 29, 'image', 'img/giochi/starwarsjedi/starwarsjediCopertina.jpg'),
('M60', 29, 'image', 'img/giochi/starwarsjedi/starwarsjediScreenshot1.jpg'),

('M61', 30, 'image', 'img/giochi/persona5royal/persona5royalCopertina.png'),
('M62', 30, 'image', 'img/giochi/persona5royal/persona5royalScreenshot1.png'),

('M63', 31, 'image', 'img/giochi/masseffect/masseffectCopertina.jpg'),
('M64', 31, 'image', 'img/giochi/masseffect/masseffectScreenshot1.jpg'),

('M65', 32, 'image', 'img/giochi/deathstranding/deathstrandingCopertina.jpg'),
('M66', 32, 'image', 'img/giochi/deathstranding/deathstrandingScreenshot1.jpg');

-- 12. Popolamento delle piattaforme disponibili dei prodotti (Piattaforma, ID_Prodotto)
INSERT INTO Piattaforma (Piattaforma, ID_Prodotto) VALUES
-- Elden Ring
('PC',1),('PS4',1),('PS5',1),('Xbox One',1),('Xbox Series X/S',1),

-- Cyberpunk
('PC',2),('PS5',2),('Xbox Series X/S',2),

-- Minecraft
('PC',3),('PS4',3),('PS5',3),('Xbox One',3),('Xbox Series X/S',3),('Nintendo Switch',3),

-- Stray
('PC',4),('PS4',4),('PS5',4),('Xbox Series X/S',4),

-- FIFA 26
('PC',5),('PS5',5),('Xbox Series X/S',5),

-- The Wolf Among Us
('PC',6),('PS4',6),('Xbox One',6),

-- Baldur''s Gate 3
('PC',7),('PS5',7),('Xbox Series X/S',7),

-- Life is Strange
('PC',8),('PS4',8),('Xbox One',8),

-- Devil May Cry 5
('PC',9),('PS5',9),('Xbox Series X/S',9),

-- The Witcher 3
('PC',10),('PS4',10),('PS5',10),('Xbox One',10),('Xbox Series X/S',10),('Nintendo Switch',10),

-- Red Dead Redemption 2
('PC',11),('PS4',11),('Xbox One',11),

-- Hollow Knight
('PC',12),('PS4',12),('Xbox One',12),('Nintendo Switch',12),

-- Hades
('PC',13),('PS4',13),('PS5',13),('Xbox One',13),('Xbox Series X/S',13),('Nintendo Switch',13),

-- God of War
('PC',14),('PS4',14),('PS5',14),

-- Resident Evil 4
('PC',15),('PS4',15),('PS5',15),('Xbox Series X/S',15),

-- Detroit
('PC',16),('PS4',16),

-- NieR Automata
('PC',17),('PS4',17),('Xbox One',17),('Nintendo Switch',17),

-- Sekiro
('PC',18),('PS4',18),('Xbox One',18),

-- Outer Wilds
('PC',19),('PS4',19),('Xbox One',19),('Nintendo Switch',19),

-- Disco Elysium
('PC',20),('PS4',20),('PS5',20),('Xbox One',20),('Xbox Series X/S',20),('Nintendo Switch',20),

-- Armored Core VI
('PC',21),('PS4',21),('PS5',21),('Xbox One',21),('Xbox Series X/S',21),

-- Europa Universalis IV
('PC',22),

-- The Last of Us Part II
('PS4',23),('PS5',23),

-- Watch Dogs 2
('PC',24),('PS4',24),('Xbox One',24),

-- Pragmata
('PC',25),('PS5',25),('Xbox Series X/S',25),

-- Monster Hunter World
('PC',26),('PS4',26),('Xbox One',26),

-- Doom Eternal
('PC',27),('PS4',27),('PS5',27),('Xbox One',27),('Xbox Series X/S',27),('Nintendo Switch',27),

-- Alan Wake 2
('PC',28),('PS5',28),('Xbox Series X/S',28),

-- Star Wars Jedi Survivor
('PC',29),('PS5',29),('Xbox Series X/S',29),

-- Persona 5 Royal
('PC',30),('PS4',30),('PS5',30),('Xbox One',30),('Xbox Series X/S',30),('Nintendo Switch',30),

-- Mass Effect Legendary Edition
('PC',31),('PS4',31),('Xbox One',31),

-- Death Stranding Director''s Cut
('PC',32),('PS5',32);