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

MONOREPO_NAME := influ-news
AWS_LOCALSTACK_CONTAINER_NAME := local-aws

MSG_SEPARATOR := "*********************************************************************"
MSG_IDENT := "    "


.SILENT:

help:
	echo "\n${MSG_SEPARATOR}\n$(LIGHT_VIOLET)$(MONOREPO_NAME)$(NO_COLOUR)\n${MSG_SEPARATOR}\n"
	echo "${MSG_IDENT}=======   ✨  BASIC   =====================================\n   "
	echo "${MSG_IDENT}  init                    -  Copy needed .env files from .env-sample if they don't exist"
	echo
	echo "${MSG_IDENT}=======   🐳  AWS LocalStack - DOCKER   =====================================\n"
	echo "${MSG_IDENT}  ℹ️   To work with $(MONOREPO_NAME) using AWS LocalStack"
	echo "${MSG_IDENT}  ⚠️   Requirements : docker \n"
	echo "${MSG_IDENT}  localstack-build        -  📦  Build docker image with name '${AWS_LOCALSTACK_CONTAINER_NAME}'"
	echo "${MSG_IDENT}  localstack-up           -  🚀  Start container ${AWS_LOCALSTACK_CONTAINER_NAME}"
	echo "${MSG_IDENT}  localstack-down         -  🛑  Stop container ${AWS_LOCALSTACK_CONTAINER_NAME}"
	echo

init:
	if [ ! -f .env ]; then \
		cp "${ROOT_DIR}/.env-sample" "${ROOT_DIR}/.env" ; \
		echo "${LIGHT_VIOLET}CREATED - File .env in root project:${NO_COLOUR}"; \
	fi

######################################################################
########################   🐳 DOCKER    ##############################
######################################################################

## AWS Local Stack
localstack-build:
	echo "\n\n${MSG_SEPARATOR}\n\n 🐳 localstack-build => Building the AWS LocalStack docker image with name '${AWS_LOCALSTACK_CONTAINER_NAME}' ...\n\n${MSG_SEPARATOR}\n\n"
	docker compose -f "${ROOT_DIR}/etc/docker/localstack/docker-compose.yml" build --force-rm

localstack-up:
	echo "\n\n${MSG_SEPARATOR}\n\n 🐳 localstack-up => 🚀 Start container '${AWS_LOCALSTACK_CONTAINER_NAME}' \n\n${MSG_SEPARATOR}\n\n"
	docker compose -f "${ROOT_DIR}/etc/docker/localstack/docker-compose.yml" up -d
	echo "\n${MSG_SEPARATOR}\n\n"

localstack-down:
	echo "\n\n${MSG_SEPARATOR}\n\n 🐳 localstack-down => 🛑 Stop container ${AWS_LOCALSTACK_CONTAINER_NAME} \n\n${MSG_SEPARATOR}\n\n"
	docker compose -f "${ROOT_DIR}/etc/docker/localstack/docker-compose.yml" down --remove-orphans
