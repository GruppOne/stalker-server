# InfluxDB

We should find a way to serialize containing some kind of schema definition describing our influxdb instance

We also should prepare some sample data to use when testing.

Please, copy all the content of influxdb.conf so that InfluxDB works properly.

## Some notes about InfluxDB

I tipi consentiti da InfluxDB sono

- timestamp (automatico)
- string (di default per tutti i field value di ogni field key)
- float (es. 82)
- integer (82i)
- Boolean(true,false)

Non possono essere prefissati tipi, se non nel momento in cui si fa il primo insert. Da quel punto sono possibili solo alcuni cast.
E non è possibile inserire elementi nulli (NULL), in quanto da sintassi non è previsto.

## Fields set schema

### v1 --> THIS ONE

#### Measurement 'access_log'

| time      | user_id | anonymous_key | organization_id | place_id | inside  |
| --------- | ------- | ------------- | --------------- | -------- | ------- |
| TIMESTAMP | string  | string        | string          | string   | Boolean |
| ...       | ...     | ...           | ...             | ...      | ...     |

### v2

#### Measurement 'public_access_log'

| time      | user_id | organization_id | place_id | inside  |
| --------- | ------- | --------------- | -------- | ------- |
| TIMESTAMP | string  | string          | string   | Boolean |
| ...       | ...     | ...             | ...      | ...     |

#### Measurement 'anonymous_access_log'

| time      | anonymous_key | organization_id | place_id | inside  |
| --------- | ------------- | --------------- | -------- | ------- |
| TIMESTAMP | string        | string          | string   | Boolean |
| ...       | ...           | ...             | ...      | ...     |

## How to use InfluxDB into your system (if you want to start it without Docker)

Dopo aver installato InfluxDB da PowerShell di Windows con il comando "scoop install influxdb", scrivere il comando "influxd", che è il daemon che fa partire l'istanza di InfluxDB.

Aprire un'altra finestra di PowerShell, e digitare il comando "influx", che consente di accedere al time-series database di Influx ed eseguire tutte le operazioni necessarie nei database d'interesse.
