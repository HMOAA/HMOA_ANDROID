#!/bin/bash

# JSON 파일 경로
CONFIG_FILE="config.json"

# jq를 사용하여 JSON 파일에서 값을 읽어 환경 변수로 설정
export NATIVE_APP_KEY=$(jq -r '.NATIVE_APP_KEY' "$CONFIG_FILE")
export KEY_ALIAS=$(jq -r '.KEY_ALIAS' "$CONFIG_FILE")
export KEY_PASSWORD=$(jq -r '.KEY_PASSWORD' "$CONFIG_FILE")
export STORE_FILE=$(jq -r '.STORE_FILE' "$CONFIG_FILE")
export STORE_PASSWORD=$(jq -r '.STORE_PASSWORD' "$CONFIG_FILE")