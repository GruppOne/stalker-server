
FROM influxdb:1.8-alpine

LABEL maintainer="gruppone.swe@gmail.com"

COPY ./influxdb.conf /etc/influxdb/influxdb.conf

COPY ./create.iql ./test-data.iql ./test-data2.iql /docker-entrypoint-initdb.d/

EXPOSE 8086
