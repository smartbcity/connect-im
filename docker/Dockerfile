FROM quay.io/keycloak/keycloak:18.0.0 as builder
ENV KC_DB=postgres
ENV KC_HTTP_RELATIVE_PATH=/auth
ENV KC_LOG_LEVEL=INFO
RUN /opt/keycloak/bin/kc.sh build

FROM quay.io/keycloak/keycloak:18.0.0
COPY --from=builder /opt/keycloak/lib/quarkus/ /opt/keycloak/lib/quarkus/
WORKDIR /opt/keycloak

ENV KC_PROXY=none
ENV KC_HTTP_ENABLED=true
ENV KC_HOSTNAME_STRICT=false
ENV KC_HOSTNAME=localhost

COPY i2-keycloak/config/themes/i2 /opt/keycloak/themes/i2/

ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start"]

