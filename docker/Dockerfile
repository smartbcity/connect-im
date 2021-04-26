FROM jboss/keycloak:12.0.4

WORKDIR /app

COPY i2-keycloak/config/standalone-ha.xml /opt/jboss/keycloak/standalone/configuration/standalone-ha.xml

ENTRYPOINT [ "/opt/jboss/tools/docker-entrypoint.sh" ]

CMD ["-b", "0.0.0.0"]
