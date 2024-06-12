#!/bin/bash

# JSON 파일 경로
CONFIG_FILE="config.json"

# jq를 사용하여 JSON 파일에서 값을 읽어 환경 변수로 설정
export NATIVE_APP_KEY=$(jq -r '.["app"]["NATIVE_APP_KEY"]' "$CONFIG_FILE")
export KEY_ALIAS=$(jq -r '.["app"]["KEY_ALIAS"]' "$CONFIG_FILE")
export KEY_PASSWORD=$(jq -r '.["app"]["KEY_PASSWORD"]' "$CONFIG_FILE")
export STORE_FILE=$(jq -r '.["app"]["STORE_FILE"]' "$CONFIG_FILE")
export STORE_PASSWORD=$(jq -r '.["app"]["STORE_PASSWORD"]' "$CONFIG_FILE")

export BASE_URL=$(jq -r '.["core-network"]["BASE_URL"]' "$CONFIG_FILE")

export AUTH_NATIVE_APP_KEY=$(jq -r '.["feature-authentication"]["NATIVE_APP_KEY"]' "$CONFIG_FILE")
export DEBUG_KEY=$(jq -r '.["feature-authentication"]["DEBUG_KEY"]' "$CONFIG_FILE")
export RELEASE_KEY=$(jq -r '.["feature-authentication"]["RELEASE_KEY"]' "$CONFIG_FILE")
export RELEASE_KEY_ALIAS=$(jq -r '.["feature-authentication"]["RELEASE_KEY_ALIAS"]' "$CONFIG_FILE")
export GOOGLE_CLOUD_OAUTH_CLIENT_ID=$(jq -r '.["feature-authentication"]["GOOGLE_CLOUD_OAUTH_CLIENT_ID"]' "$CONFIG_FILE")
export GOOGLE_CLOUD_CLIENT_SECRET=$(jq -r '.["feature-authentication"]["GOOGLE_CLOUD_CLIENT_SECRET"]' "$CONFIG_FILE")
export REDIRECT_URI=$(jq -r '.["feature-authentication"]["REDIRECT_URI"]' "$CONFIG_FILE")
export GRANT_TYPE=$(jq -r '.["feature-authentication"]["GRANT_TYPE"]' "$CONFIG_FILE")

export KAKAO_CHAT_PROFILE=$(jq -r '.["feature-userInfo"]["KAKAO_CHAT_PROFILE"]' "$CONFIG_FILE")
export PRIVACY_POLICY_URI=$(jq -r '.["feature-userInfo"]["PRIVACY_POLICY_URI"]' "$CONFIG_FILE")
# 디버깅을 위한 출력
echo "NATIVE_APP_KEY=$NATIVE_APP_KEY"
echo "KEY_ALIAS=$KEY_ALIAS"
echo "KEY_PASSWORD=$KEY_PASSWORD"
echo "STORE_FILE=$STORE_FILE"
echo "STORE_PASSWORD=$STORE_PASSWORD"
echo "BASE_URL=$BASE_URL"
echo "AUTH_NATIVE_APP_KEY=$AUTH_NATIVE_APP_KEY"
echo "DEBUG_KEY=$DEBUG_KEY"
echo "RELEASE_KEY=$RELEASE_KEY"
echo "RELEASE_KEY_ALIAS=$RELEASE_KEY_ALIAS"
echo "GOOGLE_CLOUD_OAUTH_CLIENT_ID=$GOOGLE_CLOUD_OAUTH_CLIENT_ID"
echo "GOOGLE_CLOUD_CLIENT_SECRET=$GOOGLE_CLOUD_CLIENT_SECRET"
echo "REDIRECT_URI=$REDIRECT_URI"
echo "GRANT_TYPE=$GRANT_TYPE"
echo "KAKAO_CHAT_PROFILE=$KAKAO_CHAT_PROFILE"
echo "PRIVACY_POLICY_URI=$PRIVACY_POLICY_URI"