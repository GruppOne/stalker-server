# Lista di cose da fare e suggerimenti per l'API

Stoplight studio rimuove i commenti dal file, quindi scriviamo qui documentazione e cose da fare
che non sono esprimibili nel file di specifica.

## esempio di LocationInfo

```json
{
  "timestampMs": 1585906080346,
  "userId": 34753543,
  "anonymous": false,
  "inside": true,
  "placeIds": [453785436, 71984147938]
}
```

## todos

### TODO usare i plurali: /users, not /user

### TODO dichiarare schema esplicito dei permessi per i ruoli della web app

### TODO usare http status 202 "accepted" per le operazioni asincrone lente

### TODO definire proprietà "links" <https://swagger.io/docs/specification/links/> per ottenere "the full glory of REST"

### TODO aggiungere json della risposta in #/components/responses

### FIXME NON DARE TUTTA LA PROPRIETA' organization.ldapConfiguration AGLI UTENTI

### FIXME UnauthenticatedUser patterns are unneeded. the format already specifies a type that we will define elsewhere
