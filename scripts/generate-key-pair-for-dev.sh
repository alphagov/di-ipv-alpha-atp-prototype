#!/usr/bin/env bash
KEY_PATH="src/main/resources/keys"
[[ -d $KEY_PATH ]] || mkdir $KEY_PATH

openssl genrsa -out $KEY_PATH/generic-atp-service-signing-key.pem 2048

openssl pkcs8 -topk8 -inform PEM -outform PEM -in $KEY_PATH/generic-atp-service-signing-key.pem \
  -out $KEY_PATH/private-generic-atp-service-signing-key.pem -nocrypt

openssl rsa -in $KEY_PATH/generic-atp-service-signing-key.pem \
  -pubout -out $KEY_PATH/public-generic-atp-service-signing-key.pem