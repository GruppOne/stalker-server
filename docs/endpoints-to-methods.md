# Mapping degli endpoint sui nomi dei metodi Java

La tabella seguente è ordinata alfabeticamente sulle colonne "Operation URL" e "HTTP Method"

<!-- TODO scrivere ruoli minimi -->

| HTTP Method | Operation URL                                           | Java Method Name                         | Ruolo minimo richiesto |
| ----------: | ------------------------------------------------------- | ---------------------------------------- | ---------------------- |
|        POST | /location/update                                        | postLocationUpdate                       |                        |
|      DELETE | /organization/{organizationId}                          | deleteOrganizationById                   |                        |
|         GET | /organization/{organizationId}                          | getOrganizationById                      |                        |
|         PUT | /organization/{organizationId}                          | putOrganizationById                      |                        |
|      DELETE | /organization/{organizationId}/place/{placeId}          | deleteOrganizationByIdPlaceById          |                        |
|         GET | /organization/{organizationId}/place/{placeId}          | getOrganizationByIdPlaceById             |                        |
|         PUT | /organization/{organizationId}/place/{placeId}          | putOrganizationByIdPlaceById             |                        |
|         GET | /organization/{organizationId}/places                   | getOrganizationByIdPlaces                |                        |
|        POST | /organization/{organizationId}/places                   | postOrganizationByIdPlaces               |                        |
|         GET | /organization/{organizationId}/places/report            | getOrganizationByIdPlacesReport          |                        |
|         GET | /organization/{organizationId}/user/{userId}/history    | getOrganizationByIdUserByIdHistory       |                        |
|      DELETE | /organization/{organizationId}/user/{userId}/role       | deleteOrganizationByIdUserByIdRole       |                        |
|        POST | /organization/{organizationId}/user/{userId}/role       | postOrganizationByIdUserByIdRole         |                        |
|         PUT | /organization/{organizationId}/user/{userId}/role       | putOrganizationByIdUserByIdRole          |                        |
|         GET | /organization/{organizationId}/users/connections        | getOrganizationByIdUsersConnections      |                        |
|         GET | /organization/{organizationId}/users/inside             | getOrganizationByIdUsersInside           |                        |
|         GET | /organization/{organizationId}/users/roles              | getOrganizationByIdUsersRoles            |                        |
|         GET | /organizations                                          | getOrganizations                         |                        |
|        POST | /organizations                                          | postOrganizations                        |                        |
|      DELETE | /user/{userId}                                          | deleteUserById                           |                        |
|         GET | /user/{userId}                                          | getUserById                              |                        |
|         PUT | /user/{userId}                                          | putUserById                              |                        |
|         GET | /user/{userId}/history                                  | getUserByIdHistory                       |                        |
|      DELETE | /user/{userId}/organization/{organizationId}/connection | deleteUserByIdOrganizationByIdConnection |                        |
|        POST | /user/{userId}/organization/{organizationId}/connection | postUserByIdOrganizationByIdConnection   |                        |
|         GET | /user/{userId}/organizations/connections                | getUserByIdOrganizationsConnections      |                        |
|         GET | /user/{userId}/organizations/roles                      | getUserByIdOrganizationsRoles            |                        |
|         PUT | /user/{userId}/password                                 | putUserByIdPassword                      |                        |
|        POST | /user/login                                             | postUserLogin                            |                        |
|        POST | /user/password/recovery                                 | postUserPasswordRecovery                 |                        |
|         GET | /users                                                  | getUsers                                 |                        |
|        POST | /users                                                  | postUsers                                |                        |
|         GET | /version                                                | getVersion                               |                        |
