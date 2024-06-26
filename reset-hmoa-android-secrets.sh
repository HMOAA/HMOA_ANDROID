#!/bin/bash

# 삭제할 파일 목록
FILES=(
  "./app/google-services.json"
  "./app/local.properties"
  "./core-network/local.properties"
  "./feature-authentication/local.properties"
  "./feature-userInfo/local.properties"
)

# 파일 삭제
for FILE in "${FILES[@]}"; do
  if [ -f "$FILE" ]; then
    rm "$FILE"
    echo "$FILE 파일이 삭제되었습니다."
  else
    echo "$FILE 파일이 존재하지 않습니다."
  fi
done