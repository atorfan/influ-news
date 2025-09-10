-include .env
export

# Get the absolute path to the running Makefile
ROOT_DIR := $(shell dirname $(realpath $(firstword $(MAKEFILE_LIST))))

# Colours
BLUE:=			\033[0;34m
RED:=			\033[0;31m
LIGHT_RED:=		\033[1;31m
WHITE:=			\033[1;37m
LIGHT_VIOLET := \033[1;35m
NO_COLOUR := 	\033[0m

ENV := dev

APP_NAME := image-rescale
DOCKER_IMAGE_NAME := image-rescale

PROJECT_NAME := image-rescale
# Container registry where images will be registered (AWS, Azure...)
IMAGE_URL := image-rescale
AWS_LOCALSTACK_CONTAINER_NAME := local-aws
POSTGRESQL_CONTAINER_NAME := influ-news-pgsql
AWS_LOCALSTACK_S3_BUCKET_NAME := rescaled-images

MSG_SEPARATOR := "*********************************************************************"
MSG_IDENT := "    "

gradlew := ../../gradlew

define CONTAINER_IS_RUNNING
	$(shell echo $(shell docker inspect --format '{{json .State.Running}}' $(1) 2>/dev/null || echo "false") | tr -d '[:blank:]')
endef

.SILENT:

help:
	echo "\n${MSG_SEPARATOR}\n$(LIGHT_VIOLET)$(PROJECT_NAME)$(NO_COLOUR)\n${MSG_SEPARATOR}\n"
	echo "${MSG_IDENT}=======   ✨  BASIC   =====================================\n   "
	echo "${MSG_IDENT}  ⚠️   Requirements : Java 21 \n"
	echo "${MSG_IDENT}  init                    -  Copy needed .env files from .env-sample if they don't exist"
	echo "${MSG_IDENT}  clean                   -  🚮  Erase the 📁 build/"
	echo "${MSG_IDENT}  build                   -  📦  Build the .jar (ignoring tests)"
	echo "${MSG_IDENT}  tests                   -  ✅  Run all tests"
	echo "${MSG_IDENT}  integration-tests       -  ✅  Run Integration tests only"
	echo "${MSG_IDENT}  unit-tests              -  ✅  Run Unit tests only"
	echo "${MSG_IDENT}  run                     -  🚀  Run the app (standalone .jar) with profile '${ENV}'"
	echo "${MSG_IDENT}                                   💡️ To change profile add ENV=[ dev, staging, prod ]\n"
	echo "${MSG_IDENT}    ️                                  > make run ENV=staging"
	echo
	echo "${MSG_IDENT}=======   🐳  DOCKER   =====================================\n"
	echo "${MSG_IDENT}  ℹ️   To work with $(PROJECT_NAME) running alone in a container"
	echo "${MSG_IDENT}  ⚠️   Requirements : docker \n"
	echo "${MSG_IDENT}  localstack-init         -  🪣  Creates the S3 Bucket ${AWS_LOCALSTACK_S3_BUCKET_NAME} in LocalStack"
	echo "${MSG_IDENT}  database-up             -  🐘  Build and starts a docker image with PostgreSQL database"
	echo "${MSG_IDENT}  database-down           -  🐘  Stop the database PostgreSQL server"
	echo "${MSG_IDENT}  dk-build                -  📦  Build a docker image with the .jar"
	echo "${MSG_IDENT}  dk-build-skip-tests     -  📦  Build a docker image with the .jar (skipping tests)"
	echo "${MSG_IDENT}  up                      -  🚀  Start container ${DOCKER_IMAGE_NAME}"
	echo "${MSG_IDENT}  down                    -  🛑  Stop container ${DOCKER_IMAGE_NAME}"
	echo "${MSG_IDENT}  dk-rmi                  -  🧹  Removing image with name ${DOCKER_IMAGE_NAME}"
	echo


######################################################################
########################   BASIC    ##################################
######################################################################

init:
	if [ ! -f .env ]; then \
		cp "${ROOT_DIR}/.env-sample" "${ROOT_DIR}/.env" ; \
		echo "${LIGHT_VIOLET}CREATED - File .env in project main:${NO_COLOUR}"; \
	fi

	if [ ! -f .env.dev ]; then \
		cp "${ROOT_DIR}/.env-sample" "${ROOT_DIR}/.env.dev" ; \
		echo "${LIGHT_VIOLET}CREATED - File .env.dev in project main:${NO_COLOUR}"; \
	fi

check-localstack:
	$(eval isLocalStackRunning := $(call CONTAINER_IS_RUNNING, ${AWS_LOCALSTACK_CONTAINER_NAME}))
	if [ $(isLocalStackRunning) == "false" ]; then \
	    echo "${LIGHT_RED}LocalStack must be running for the application to work properly${NO_COLOUR}"; \
	    exit 1; \
	fi

check-database:
	$(eval isPostgreSqlRunning := $(call CONTAINER_IS_RUNNING, ${POSTGRESQL_CONTAINER_NAME}))
	if [ $(isPostgreSqlRunning) == "false" ]; then \
	    echo "${LIGHT_RED}PostgreSQL must be running for the application to work properly${NO_COLOUR}"; \
	    exit 1; \
	fi

clean: init
	echo "\n\n${MSG_SEPARATOR}\n\n CLEAN => 🚮  Erase the 📁 build/\n\n${MSG_SEPARATOR}\n\n"
	$(gradlew) clean

build: init
	echo "\n\n${MSG_SEPARATOR}\n\n BUILD APPLICATION => 📦\n"
	$(gradlew) bootJar test $(shell [ ! -z ${VERSION} ] && echo "-Pversion=${VERSION}")

build-skip-tests: init
	echo "\n\n${MSG_SEPARATOR}\n\n BUILD APPLICATION IGNORING TESTS => 📦\n"
	$(gradlew) bootJar $(shell [ ! -z ${VERSION} ] && echo "-Pversion=${VERSION}")

tests: unit-tests integration-tests

unit-tests:
	echo "\n\n${MSG_SEPARATOR}\n\n UNIT TESTS \n\n${MSG_SEPARATOR}\n"
	$(gradlew) test --tests "com.newsnow.*.core.*"

integration-tests:
	echo "\n\n${MSG_SEPARATOR}\n\n INTEGRATION TESTS \n\n${MSG_SEPARATOR}\n"
	$(gradlew) test --tests "com.newsnow.*.adapters.*" --tests "com.newsnow.*.infrastructure.*" --tests "com.newsnow.*.imagerescale"

run: check-localstack check-database
	echo "\n\n${MSG_SEPARATOR}\n\n RUN => 🚀 Starting your ☕ app \n\n${MSG_SEPARATOR}\n"
	echo "  - 🔍 Go to - http://localhost:8080/health"
	echo "  - 📑 Documentation - http://localhost:8080/swagger-ui/index.html"
	echo "\n${MSG_SEPARATOR}\n\n"

	$(gradlew) bootRun -Dfile.encoding="UTF-8" -Dspring.profiles.active=dev


######################################################################
########################   🐳 DOCKER    ##############################
######################################################################

localstack-init: init
	echo "\n\n${MSG_SEPARATOR}\n\n Creating S3 Bucket \"${AWS_LOCALSTACK_S3_BUCKET_NAME}\" \n\n${MSG_SEPARATOR}\n\n"
	docker exec local-aws awslocal s3 mb s3://${AWS_LOCALSTACK_S3_BUCKET_NAME}

database-up:
	echo "\n\n${MSG_SEPARATOR}\n\n 🐘  Starting database \n\n${MSG_SEPARATOR}\n"
	docker compose -f ../../etc/docker/postgres/docker-compose.yml build  --force-rm
	docker compose -f ../../etc/docker/postgres/docker-compose.yml up -d

database-down:
	echo "\n\n${MSG_SEPARATOR}\n\n 🐘  Starting database \n\n${MSG_SEPARATOR}\n"
	docker compose -f ../../etc/docker/postgres/docker-compose.yml down --remove-orphans

dk-build-skip-tests: dk-rmi build-skip-tests
	echo "\n\n${MSG_SEPARATOR}\n\n 🐳 dk-build - ${RED}skip tests${NO_COLOUR} => Building the docker image with name ${DOCKER_IMAGE_NAME} ...\n\n${MSG_SEPARATOR}\n\n"

	APP_NAME=$(APP_NAME)
	docker compose -f ../../etc/docker/docker-compose.yml -f ../../etc/docker/docker-compose.dev.yml -f ../../etc/docker/image-rescale/docker-compose.dev.yml build  --force-rm

dk-build: dk-rmi build
	echo "\n\n${MSG_SEPARATOR}\n\n 🐳 dk-build => Building the docker image with name ${DOCKER_IMAGE_NAME} ...\n\n${MSG_SEPARATOR}\n\n"

	APP_NAME=$(APP_NAME)
	docker compose -f ../../etc/docker/docker-compose.yml -f ../../etc/docker/docker-compose.dev.yml -f ../../etc/docker/image-rescale/docker-compose.dev.yml build  --force-rm

dk-build-version: init dk-build-skip-tests
	echo "\n\n${MSG_SEPARATOR}\n\n 🐳 dk-build - ${GREEN}versioned with $(IMAGE_VERSION) and profile $(PROFILE)${NO_COLOUR} => Building the docker image with name ${DOCKER_IMAGE_NAME} ...\n\n${MSG_SEPARATOR}\n\n"
	APP_NAME=$(APP_NAME)
	IMAGE_URL=$(IMAGE_URL)
	IMAGE_VERSION=$(IMAGE_VERSION)

	docker compose -f ../../etc/docker/docker-compose.yml -f ../../etc/docker/docker-compose.override.yml build  --force-rm

dk-push:
	echo "\n\n${MSG_SEPARATOR}\n\n 🐳 dk-push - ${GREEN}versioned with $(IMAGE_VERSION) and profile $(PROFILE)${NO_COLOUR} => Pushing the docker image with name ${DOCKER_IMAGE_NAME} ...\n\n${MSG_SEPARATOR}\n\n"
	APP_NAME=$(APP_NAME)
	IMAGE_URL=$(IMAGE_URL)
	IMAGE_VERSION=$(IMAGE_VERSION)

	docker compose -f ../../etc/docker/docker-compose.yml -f ../../etc/docker/docker-compose.override.yml push

dk-rmi:
	echo "\n\n${MSG_SEPARATOR}\n\n 🐳 dk-rmi => 🧹  Removing the image ${DOCKER_IMAGE_NAME}\n\n${MSG_SEPARATOR}\n\n"

	docker rmi ${DOCKER_IMAGE_NAME} 2> /dev/null || true

up: check-localstack check-database
	echo "\n\n${MSG_SEPARATOR}\n\n 🐳 up => 🚀  Start container ${DOCKER_IMAGE_NAME} \n\n${MSG_SEPARATOR}\n\n"

	docker compose -f ../../etc/docker/docker-compose.yml -f ../../etc/docker/docker-compose.dev.yml -f ../../etc/docker/image-rescale/docker-compose.dev.yml up -d

	echo "\n\n${MSG_SEPARATOR}\n\n  🐳 Your app is running 🚀\n"
	echo ""
	echo "  - ⭐️ Application: ${PROJECT_NAME} -> Port: 8080"
	echo "  - 🔍 Go to - http://localhost:8080/health"
	echo "  - 📑 Documentation - http://localhost:8080/swagger-ui/index.html"
	echo "\n${MSG_SEPARATOR}\n\n"

down:
	echo "\n\n${MSG_SEPARATOR}\n\n 🐳 down => 🚀  Stop container ${DOCKER_IMAGE_NAME} \n\n${MSG_SEPARATOR}\n\n"

	docker compose -f ../../etc/docker/docker-compose.yml -f ../../etc/docker/docker-compose.dev.yml -f ../../etc/docker/image-rescale/docker-compose.dev.yml down --remove-orphans
