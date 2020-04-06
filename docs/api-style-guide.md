# Guida stilistica per l'API di GruppOne

<!-- TODO write this docs file! -->

- le description delle operation devono essere formattate come frasi, con punteggiatura finale e maiuscola all'inizio.

- gli OperationId devono contenere il verbo http a cui si riferiscono e il path. Vanno in camelCase.

- i parametri nel path se sono condivisi tra tutte le operazioni(i.e. put, get, delete ecc.) di un endpoint VANNO
  MESSI subito dopo l'endpoint.

<!-- if a single endpoint has more operation (i.e. get, put, delete exc.), you MUST place it first>
