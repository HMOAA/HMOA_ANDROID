#!/bin/bash

# 로컬 개발 환경의 경우, JSON 파일에서 값을 가져옵니다.
if [ -z "$CI" ]; then
  source ./load-env-from-json.sh
else
  # CI 환경의 경우, 환경 변수에서 값을 가져옵니다.
  NATIVE_APP_KEY=${{ secrets.NATIVE_APP_KEY }}
  REDIRECTION_PATH=${{ secrets.REDIRECTION_PATH }}
  KEY_ALIAS=${{ secrets.KEY_ALIAS }}
  KEY_PASSWORD=${{ secrets.KEY_PASSWORD }}
  STORE_FILE="./app/release.keystore"
  STORE_PASSWORD=${{ secrets.STORE_PASSWORD }}
  BASE_URL=${{ secrets.BASE_URL }}
  AUTH_NATIVE_APP_KEY=${{ secrets.AUTH_NATIVE_APP_KEY }}
  DEBUG_KEY=${{ secrets.DEBUG_KEY }}
  RELEASE_KEY=${{ secrets.RELEASE_KEY }}
  RELEASE_KEY_ALIAS=${{ secrets.RELEASE_KEY_ALIAS }}
  GOOGLE_CLOUD_OAUTH_CLIENT_ID=${{ secrets.GOOGLE_CLOUD_OAUTH_CLIENT_ID }}
  GOOGLE_CLOUD_CLIENT_SECRET=${{ secrets.GOOGLE_CLOUD_CLIENT_SECRET }}
  REDIRECT_URI=${{ secrets.REDIRECT_URI }}
  GRANT_TYPE=${{ secrets.GRANT_TYPE }}
  KAKAO_CHAT_PROFILE=${{ secrets.KAKAO_CHAT_PROFILE }}
  PRIVACY_POLICY_URI=${{ secrets.PRIVACY_POLICY_URI }}
fi


# 필요한 디렉토리가 존재하지 않으면 생성
mkdir -p ./app
mkdir -p ./core-network
mkdir -p ./feature-authentication
mkdir -p ./feature-userInfo

# app/local.properties 파일 생성
cat <<EOF > ./app/local.properties
NATIVE_APP_KEY="$NATIVE_APP_KEY"
REDIRECTION_PATH="$REDIRECTION_PATH"
#release.keystore
KEY_ALIAS="$KEY_ALIAS"
KEY_PASSWORD="$KEY_PASSWORD"
STORE_FILE="$STORE_FILE"
STORE_PASSWORD="$STORE_PASSWORD"
EOF

# core-network/local.properties 파일 생성
cat <<EOF > ./core-network/local.properties
BASE_URL="$BASE_URL"
EOF

# feature-authentication/local.properties 파일 생성
cat <<EOF > ./feature-authentication/local.properties
NATIVE_APP_KEY="$AUTH_NATIVE_APP_KEY"
DEBUG_KEY="$DEBUG_KEY"
RELEASE_KEY="$RELEASE_KEY"
RELEASE_KEY_ALIAS="$RELEASE_KEY_ALIAS"
GOOGLE_CLOUD_OAUTH_CLIENT_ID="$GOOGLE_CLOUD_OAUTH_CLIENT_ID"
GOOGLE_CLOUD_CLIENT_SECRET="$GOOGLE_CLOUD_CLIENT_SECRET"
REDIRECT_URI="$REDIRECT_URI"
GRANT_TYPE="$GRANT_TYPE"
EOF

# feature-userInfo/local.properties 파일 생성
cat <<EOF > ./feature-userInfo/local.properties
KAKAO_CHAT_PROFILE="$KAKAO_CHAT_PROFILE"
PRIVACY_POLICY_URI="$PRIVACY_POLICY_URI"
EOF

echo "local.properties 파일들이 생성되었습니다."
