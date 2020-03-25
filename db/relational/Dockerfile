FROM mariadb:10.4

LABEL maintainer="gruppone.swe@gmail.com"

# TODO not sure we should create the db tables here. what happens when we spin up a new container?
COPY create.sql /docker-entrypoint-initdb.d

EXPOSE 3306
