🎨 Board Frontend (React)
이 폴더는 게시판 서비스의 사용자 화면(UI)을 담당하는 React 프로젝트입니다.

🛠️ Tech Stack
Framework: React 18

Build Tool: Vite

Styling: Tailwind CSS

HTTP Client: Axios

🚀 로컬 개발 환경 실행
로컬에서 화면을 띄워보고 싶을 때 사용하세요.

Bash

# 의존성 설치
npm install

# 개발 서버 실행 (localhost:5173)
npm run dev
🐳 Docker를 이용한 배포 (CI 과정)
젠킨스가 빌드할 때 사용하는 Dockerfile 설정입니다.

Base Image: node:20 (Build stage) & nginx:stable-alpine (Production stage)

Port: 80

Bash

# 수동 빌드 테스트 시
docker build -t board-frontend:v1 .
docker run -d -p 8080:80 board-frontend:v1
📡 API 연동 설정
백엔드 API 주소는 환경 변수 또는 nginx.conf 프록시 설정을 통해 전달됩니다.

기본 경로: /api
