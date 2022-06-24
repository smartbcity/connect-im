# IM

---

# Description

IM is a micro-service for Identity and Access Management. It is built upon a Keycloak instance.

It allows you to create and manage: Users, Organizations and Roles. These Users and Organizations can then be used to authenticate to a Keycloak instance.

# Architecture

IM interacts with Keycloak using [Keycloak’s Admin Client](https://mvnrepository.com/artifact/org.keycloak/keycloak-admin-client). IM also exposes entrypoints to allow communications with any applications.

<img src="https://docs.smartb.city/s3/docs/im/diagrams/architecture.png" alt="drawing" width="300"/>

# Getting started

IM works with a running Keycloak instance. It can easily be deployed as a docker container, within a docker-compose file: ([link to docker repo](https://hub.docker.com/r/smartbcity/i2-gateway))

```yaml
version: "3.7"
services:
  im-gateway:
    image: smartbcity/im-gateway:${VERSION}
    container_name: im-gateway
    environment:
      server_port: 8004
      i2_issuers[0]_uri: ${ISSUER_URI}
      i2_issuers[0]_authUrl: ${KEYCLOAK_URL}
      i2_issuers[0]_realm: ${KEYCLOAK_REALM}
      i2_issuers[0]_clientId: ${CLIENT_ID}
      i2_issuers[0]_clientSecret: ${CLIENT_SECRET}
    ports:
      - "8004:8004"
```

Functionalities are divided into 3 entities: User, Organization and Role.

If you are in a java environment all the necessary models and commands for an entity can be found here:

- [User SDK](https://mvnrepository.com/artifact/city.smartb.im/user-domain)
- [Organization SDK](https://mvnrepository.com/artifact/city.smartb.im/organization-domain)
- [Role SDK](https://mvnrepository.com/artifact/city.smartb.im/role-domain)

```kotlin
implementation("city.smartb.im:user-domain:${Versions.im}")
implementation("city.smartb.im:organization-domain:${Versions.im}")
implementation("city.smartb.im:role-domain:${Versions.im}")
```

SDK that sugarcoats the http requests are also available:
- [User Client](https://mvnrepository.com/artifact/city.smartb.im/user-client)
- [OrganizationClient](https://mvnrepository.com/artifact/city.smartb.im/organization-client)
- [Role Client](https://mvnrepository.com/artifact/city.smartb.im/role-client)

```kotlin
implementation("city.smartb.im:user-client:${Versions.im}")
implementation("city.smartb.im:organization-client:${Versions.im}")
implementation("city.smartb.im:role-client:${Versions.im}")
```

The client provided in the SDK uses Ktor under the hood and should be a singleton in your application in order to prevent any memory leak. Ex:

```kotlin
@Configuration
class ImConfig(
    private val tokenProvider: TokenProvider
) {

    @Value("\${im.url}")
    lateinit var imUrl: String

    @Bean
    fun userClient() = UserClient(
        url = imUrl,
        generateBearerToken = tokenProvider::getToken
    )

    @Bean
    fun organizationClient() = OrganizationClient(
        url = imUrl,
        generateBearerToken = tokenProvider::getToken
    )

    @Bean
    fun roleClient() = RoleClient(
        url = imUrl,
        generateBearerToken = tokenProvider::getToken
    )
}
```

# Endpoints & Role

## Organizations

| Endpoint | Associated role       |
| --- |-----------------------|
| organizationCreate | super_admin           |
| organizationUpdate | im_write_organization |
| organizationGet | im_read_organization  |
| organizationGetBySiret | im_read_organization  |
| organizationPage | im_read_organization  |
| organizationRefGetAll | im_read_organization  |

| Roles                 | Endpoints |
|-----------------------| --- |
| im_write_organization | organizationUpdate |
| im_read_organization  | organizationGet, organizationGetBySiret, organizationPage, organizationRefGetAll |
| super_admin           | * |

## Users

| Endpoints | Associated role |
| --- |--------------|
| userCreate | im_write_user |
| userUpdate | im_write_user |
| userResetPassword | im_write_user |
| userGet | im_read_user |
| userPage | im_read_user |

| Roles         | Endpoints |
|---------------|---|
| im_write_user | userCreate, userUpdate, userResetPassword |
| im_read_user  | userGet, userPage |
| super_admin   | * |

## Roles

| Endpoints | Associated role |
| --- |-----------------|
| roleAddComposites | super_admin     |
| roleCreate | super_admin     |
| roleUpdate | super_admin     |

| Roles       | Endpoints |
|-------------| --- |
| super_admin | * |

# Configuration

Properties prefix: `i2.issuers[]`

| Property                   | Description                             | Example                                  | Default                                |
|----------------------------|-----------------------------------------|------------------------------------------|----------------------------------------|
| uri                        | Issuer URI accepted by IM               | https://auth.smart-b.io/auth/realms/development | http://localhost:8080/auth/realms/test |
| authUrl                    | Keycloak server URL                     | https://auth.smart-b.io/auth             | http://localhost:8080/auth             |
| realm                      | Keycloak realm to authenticate to       | development                              | test                                   |
| clientId     | Client ID used to authenticate with     | smartclient                              | i2-api                                 |
| clientSecret | Client Secret used to authenticate with | smartsecret                              | xxxxx                                  |

`i2.issuers[]` should define a list of the above configuration.

For instance:
- In your ```application.yml``` file:
```
i2:
  issuers:
    -
      uri: http://localhost:8080/auth/realms/test
      authUrl: http://localhost:8080/auth
      realm: test
      clientId: i2-api
      clientSecret: clientSecret
    -
      uri: http://localhost:8080/auth/realms/test2
      authUrl: http://localhost:8080/auth
      realm: test2
      clientId: i2-api
      clientSecret: clientSecret2
```

- By overriding in your docker-compose file:
```
version: "3.7"
services:
  im-gateway:
    image: smartbcity/im-gateway:${VERSION}
    container_name: im-gateway
    environment:
      server_port: 8004
      i2_issuers[0]_uri: ${ISSUER_URI}
      i2_issuers[0]_authUrl: ${KEYCLOAK_URL}
      i2_issuers[0]_realm: ${KEYCLOAK_REALM}
      i2_issuers[0]_clientId: ${CLIENT_ID}
      i2_issuers[0]_clientSecret: ${CLIENT_SECRET}
      i2_issuers[1]_uri: ${ISSUER_URI_1}
      i2_issuers[1]_authUrl: ${KEYCLOAK_URL_1}
      i2_issuers[1]_realm: ${KEYCLOAK_REALM_1}
      i2_issuers[1]_clientId: ${CLIENT_ID_1}
      i2_issuers[1]_clientSecret: ${CLIENT_SECRET_1}
    ports:
      - "8004:8004"
```

Properties prefix: `i2.organization.insee`

| Property     | Description                | Example                                  | Default                                |
|--------------|----------------------------|------------------------------------------|----------------------------------------|
| token        | Token used to authenticate | c6ef3de5-3ef1-330a-8917-21f6750dcb09 | c6ef3de5-3ef1-330a-8917-21f6750dcb09 |
| sirene-api   | Sirene-api URL             | https://api.insee.fr/entreprises/sirene/V3             | https://api.insee.fr/entreprises/sirene/V3             |

# Errors