# Guida allo sviluppo di Stalker Server

Usiamo gradle.
All'interno della repository è presente una coppia di script (gradlew per linux e gradlew.bat per windows) che permette di eseguire i task necessari senza bisogno di installare gradle. il comando da utilizzare per eseguire il server è:

`./gradlew bootRun`

spring è abbastanza sveglio da fare una cosa chiamata LiveReload: quando fate partire il server con quel comando, modificate una classe e la salvate, fa restartare automaticamente il server con la versione nuova ricompilando solo lo stretto necessario.

il server funziona anche senza bisogno del DB attivo sotto, ma chiaramente se volete controllare che le cose vadano dovete farlo partire. Il comando per farlo (da eseguire posizionandosi sulla root sel server) è:

`docker-compose up -d rdb`

Per i dettagli aprite i file `docker-compose.yml` e `docker-compose.override.yml`. rdb (Relational DataBase) è il nome del servizio definito all'interno di quei file. è anche possibile far partire più di un servizio alla volta con

`docker-compose up -d rdb rdb-gui`

(questo comando fa partire anche un istanza di phpmyadmin con cui potete ispezionare il DB)

oppure

`docker-compose up -d`

che li fa partire tutti. `-d`, che sta per `--detached` serve a ridarvi il prompt dopo che docker ha fatto partire il container. Provate anche ad eseguire gli stessi comandi senza il `-d` per vedere la differenza.

il teardown completo dei db lo potete fare con

`docker-compose down --rmi all --remove-orphans --volumes`

Vi consiglio di farlo abbastanza spesso per evitare che il funzionamento del server dipenda da cose che avete fatto su una specifica istanza del db.

## Docker Toolbox - Windows Home

Se avete Windows 10 Pro o Education vi conviene installare Docker Desktop for Windows, ma su quello dovete essere autonomi per capirne il funzionamento.

Chi ha Windows 10 Home è obbligato a usare Docker Toolbox, che lavora dentro una VM, quindi i container non girano su localhost ma sull'IP della VM. La cosa si può risolvere configurando il port forwarding delle porte interessate da VirtualBox:

1. rosso: selezionare la VM su cui sta girando docker.
2. arancione scuro: andare sui setting della VM
3. arancione chiaro: selezionare la tab network
4. cliccare su port forwarding (è dentro il menu "advanced")

![VirtualBox port forwarding 1](./img/virtualbox-port-forwarding-1.png)

- rosso: porta sul pc in cui è installato VirtualBox.
- giallo: porta pubblicata dal container alla VM. Attenzione perché non è necessariamente uguale alla porta dichiarata nell'immagine. Vedere [Docker - Published ports](https://docs.docker.com/config/containers/container-networking/#published-ports#published-ports) per dettagli.

![VirtualBox port forwarding 2](./img/virtualbox-port-forwarding-2.png)
