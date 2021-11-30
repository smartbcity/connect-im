FROM jboss/keycloak:15.0.1

WORKDIR /app

COPY i2-keycloak/config/standalone-ha.xml /opt/jboss/keycloak/standalone/configuration/standalone-ha.xml

COPY i2-keycloak/config/themes/i2 /opt/jboss/keycloak/themes/i2

ENTRYPOINT [ "/opt/jboss/tools/docker-entrypoint.sh" ]

CMD ["-b", "0.0.0.0"]
