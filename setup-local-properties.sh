#!/bin/bash

# 로컬 개발 환경의 경우, JSON 파일에서 값을 가져옵니다.
if [ -z "$CI" ]; then
  source ./load-env-from-json.sh
else
  # CI 환경의 경우, 환경 변수에서 값을 가져옵니다.
  NATIVE_APP_KEY=${{ secrets.NATIVE_APP_KEY }}
  KEY_ALIAS=${{ secrets.KEY_ALIAS }}
  KEY_PASSWORD=${{ secrets.KEY_PASSWORD }}
  STORE_FILE=${{ secrets.STORE_FILE }}
  STORE_PASSWORD=${{ secrets.STORE_PASSWORD }}
fi

# local.properties 파일 생성
cat <<EOF > ./app/local.properties
NATIVE_APP_KEY="$NATIVE_APP_KEY"
#release.keystore
KEY_ALIAS=$KEY_ALIAS
KEY_PASSWORD=$KEY_PASSWORD
STORE_FILE=$STORE_FILE
STORE_PASSWORD=$STORE_PASSWORD
EOF