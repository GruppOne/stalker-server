FROM mysql:8.0

LABEL maintainer="gruppone.swe@gmail.com"

COPY ./stalker-rdb-config-file.cnf /etc/mysql/conf.d/

# TODO not sure we should create the db tables here. what happens when we spin up a new container?
COPY create.sql /docker-entrypoint-initdb.d

# XXX temporarily add some test data
COPY test-data.sql /docker-entrypoint-initdb.d

EXPOSE 3306
