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

MSG_SEPARATOR := "*********************************************************************"
MSG_IDENT := "    "


.SILENT:

help:
	echo "\n${MSG_SEPARATOR}\n$(LIGHT_VIOLET)$(PROJECT_NAME)$(NO_COLOUR)\n${MSG_SEPARATOR}\n"
	echo "${MSG_IDENT}=======   ✨  BASIC   =====================================\n   "
	echo "${MSG_IDENT}  ⚠️   Requirements : Java 21 \n"
	echo "${MSG_IDENT}  init                    -  Copy needed .env files from .env-sample if they don't exist"
	echo
	echo "${MSG_IDENT}=======   🐳  DOCKER   =====================================\n"
	echq "${MSG_IDENT}  ℹ️   To work with $(PROJECT_NAME) running alone in a container"
	echo "${MSG_IDENT}  dk-database-up          -  🐘  Build and starts a docker image with the database PostgreSQL"
	echo


######################################################################
########################   BASIC    ##################################
######################################################################

init:
	if [ ! -f ../../.env ]; then \
		cp "${ROOT_DIR}/../../.env-sample" "${ROOT_DIR}/../../.env" ; \
		echo "${LIGHT_VIOLET}CREATED - File .env in root project:${NO_COLOUR}"; \
	fi

	if [ ! -f .env ]; then \
		cp "${ROOT_DIR}/.env-sample" "${ROOT_DIR}/.env" ; \
		echo "${LIGHT_VIOLET}CREATED - File .env in project main:${NO_COLOUR}"; \
	fi


######################################################################
########################   🐳 DOCKER    ##############################
######################################################################

dk-database-up:
	echo "\n\n${MSG_SEPARATOR}\n\n 🐘  Starting database \n\n${MSG_SEPARATOR}\n"
	docker compose -f ../../etc/docker/postgres/docker-compose.yml up -d --force-recreate
