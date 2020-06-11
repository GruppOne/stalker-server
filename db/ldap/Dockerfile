FROM osixia/openldap:1.3.0

LABEL maintainer="gruppone.swe@gmail.com"

#FIXME 50-bootstrap.ldif doesn't overwrite default file (the name is the same)
COPY 50-bootstrap.ldif /container/service/slapd/assets/config/bootstrap/ldif

