# 1단계: 빌드 단계 (Node.js 환경)
FROM node:18 AS build
WORKDIR /app

# 라이브러리 설치를 위해 설정 파일 복사
COPY package*.json ./
RUN npm install

# 전체 소스 코드 복사 및 빌드 실행
COPY . .
RUN npm run build

# 2단계: 실행 단계 (Nginx 환경)
FROM nginx:stable-alpine

# 위에서 빌드된 결과물(dist 폴더)만 Nginx로 복사
# 만약 Vite가 아니라 CRA(Create React App)라면 /app/build 로 수정하세요.
COPY --from=build /app/dist /usr/share/nginx/html

# Nginx의 기본 80포트를 외부에 알림
EXPOSE 80

# Nginx 실행
CMD ["nginx", "-g", "daemon off;"]