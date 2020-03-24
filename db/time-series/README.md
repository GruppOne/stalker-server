# InfluxDB

We should find a way to serialize containing some kind of schema definition describing our influxdb instance

We also should prepare some sample data to use when testing.

## Fields set schema

### v1

#### Access Log

<!-- TODO Se e solo è fattibile tenere campi -->
<!-- TODO capire se si può costringere il tipo dei campi ad essere prefissato -->
<!-- TODO trovare i nomi dei data type che ci servono -->

| time      | UserId | AnonKey | OrganizationId | PlaceId | access/exit |
| --------- | ------ | ------- | -------------- | ------- | ----------- |
| TIMESTAMP | string | string  | string         | string  | +1/-1       |
| ...       | ...    | ...     | ...            | ...     | ...         |

### v2

#### Public Access Log

| time      | AnonKey | OrganizationId | PlaceId | access/exit |
| --------- | ------- | -------------- | ------- | ----------- |
| TIMESTAMP | string  | string         | string  | +1/-1       |
| ...       | ...     | ...            | ...     | ...         |

#### Anonymous Access Log

| time      | UserId | OrganizationId | PlaceId | access/exit |
| --------- | ------ | -------------- | ------- | ----------- |
| TIMESTAMP | string | string         | string  | +1/-1       |
| --------- | ------ | -------------- | ------- | ----------- |
| ...       | ...    | ...            | ...     | ...         |
