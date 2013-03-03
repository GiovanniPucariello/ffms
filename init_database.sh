#! /bin/bash

SCRIPTS_PATH=`pwd`
USER_NAME="root"
PASSWORD="password"
SCHEMA_NAME="ffmstest"

echo "Writing data, please wait..."
mysql -u ${USER_NAME} -p${PASSWORD} ${SCHEMA_NAME}< ${SCRIPTS_PATH}/deployment/1_creation_SQL.sql
mysql -u ${USER_NAME} -p${PASSWORD} ${SCHEMA_NAME}< ${SCRIPTS_PATH}/deployment/2_SP_SQL.sql
mysql -u ${USER_NAME} -p${PASSWORD} ${SCHEMA_NAME}< ${SCRIPTS_PATH}/deployment/3_initial_user_SQL.sql
echo "Date wrote successfully."