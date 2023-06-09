STORYBOOK_DOCKERFILE	:= infra/docker/storybook/Dockerfile
STORYBOOK_NAME	   	 	:= smartbcity/im-storybook
STORYBOOK_IMG	    	:= ${STORYBOOK_NAME}:${VERSION}

IM_APP_NAME	   	 	:= smartbcity/im-gateway
IM_APP_IMG	    	:= ${IM_APP_NAME}:${VERSION}
IM_APP_PACKAGE	   	:= :im-api:api-gateway:bootBuildImage

libs: package-kotlin
docker: docker-build docker-push
docs: docs-build docs-push

docker-build: docker-im-gateway-build
docker-push: docker-im-gateway-push

docs-build: package-im-storybook-build
docs-push: package-im-storybook-push

package-kotlin:
	@gradle clean build publish -x test --stacktrace

docker-im-gateway-build:
	VERSION=${VERSION} ./gradlew build ${IM_APP_PACKAGE} -x test --stacktrace

docker-im-gateway-push:
	@docker push ${IM_APP_IMG}

package-im-storybook-build:
	@docker build --build-arg CI_NPM_AUTH_TOKEN=${CI_NPM_AUTH_TOKEN} -f ${STORYBOOK_DOCKERFILE} -t ${STORYBOOK_IMG} .

package-im-storybook-push:
	@docker push ${STORYBOOK_IMG}
