# 1. 빌드 이미지 설정 (Java 21 기준)
FROM eclipse-temurin:21-jdk-focal

# 2. JAR 파일 위치 변수 설정
ARG JAR_FILE=build/libs/*-SNAPSHOT.jar
# 3. JAR 파일을 컨테이너 내부로 복사
COPY ${JAR_FILE} app.jar
# 4. 앱 실행 명령어
ENTRYPOINT ["java", "-jar", "/app.jar"]
