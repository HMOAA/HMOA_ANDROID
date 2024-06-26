#!/bin/bash

#로컬 변수 설정
if [ -z "$CI" ]; then
  # 필수 환경 변수 확인
  if [ -z "$HMOA_ANDROID_SECRET_TOKEN" ]; then
    echo "WARNING ==== 로컬 환경에서 HMOA_ANDROID_SECRET_TOKEN 환경 변수를 설정해야합니다."
    exit 1
  fi
fi

# 변수 정의
REPO="HMOAA/HMOA_ANDROID_SECRET"
FILE_PATHS=(
  "app/local.properties"
  "app/google-services.json"
  "app/release.keystore"
  "feature-userInfo/local.properties"
  "core-network/local.properties"
  "feature-authentication/local.properties"
)
DEST_DIRS=(
  "./app"
  "./app"
  "./app"
  "./feature-userInfo"
  "./core-network"
  "./feature-authentication"
)

# 반복문을 통해 파일을 가져와서 복사
for index in "${!FILE_PATHS[@]}"; do
  FILE_PATH="${FILE_PATHS[$index]}"
  DEST_DIR="${DEST_DIRS[$index]}"

  # GitHub API를 통해 파일 내용 가져오기
  file_contents=$(curl -sSL \
    -H "Authorization: token $HMOA_ANDROID_SECRET_TOKEN" \
    -H "Accept: application/vnd.github.v3.raw" \
    "https://api.github.com/repos/$REPO/contents/$FILE_PATH")

  # 가져올 파일의 이름 추출
  FILE_NAME=$(basename "$FILE_PATH")

  # 파일을 임시 파일에 저장
  temp_file=$(mktemp)
  echo "$file_contents" > "$temp_file"

  # 목적 디렉토리로 복사 (파일 이름 그대로 유지)
  cp "$temp_file" "$DEST_DIR/$FILE_NAME"

  # 임시 파일 삭제
  rm "$temp_file"

  echo "파일을 $DEST_DIR/$FILE_NAME 경로로 복사했습니다."
done