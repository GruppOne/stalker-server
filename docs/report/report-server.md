# Report Server

## Scelte implementative

Abbiamo scelto di utilizzare il framework spring webflux per la realizzazione di un server asincrono e facilmente scalabile.

### R2DBC

Per relazionarci con il database relazionale, che si occupa di immagazzinare i dati persistenti di utenti e organizzazioni, abbiamo scelto l'API **R2DBC** che supporta la programmazione reactive usando database SQL.

### InfluxDB

Per gestire le posizioni degli utenti abbiamo utilizzato **InfluxDB**, un database specializzato nel gestire serie temporali di dati e quindi adatto a raccogliere le posizioni degli utenti di Stalker nei vari istanti di tempo.
Questo ci ha consentito di ottenere performance migliori nella gestione di grandi flussi di dati relativi alle posizioni.

## Test

### Test di unità

Abbiamo effettuato test di unità per tutti i controller, servizi e repository del server ottenendo una code coverage complessiva del **99,2%**, in linea con gli obiettivi imposti dal capitolato per il progetto.

## Problemi aperti

Abbiamo individuato i seguenti aspetti del server che potrebbero essere ampliati o migliorati:

- Scalabilità orizzontale utilizzando Kubernetes per la creazione di più container se il carico lo richiede.
- Protocollo https per cifrare le comunicazioni tra app e server
