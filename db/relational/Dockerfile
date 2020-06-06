FROM mysql:8.0

LABEL maintainer="gruppone.swe@gmail.com"

ENV TZ="Europe/Rome"

COPY ./stalker-rdb-config-file.cnf /etc/mysql/conf.d/

COPY create.sql /docker-entrypoint-initdb.d

# add some test data
COPY test-data.sql /docker-entrypoint-initdb.d

EXPOSE 3306
