# 이미지 정의
FROM openjdk:17-alpine

# 작업공간
WORKDIR /app

# build 된 파일을 app.jar 로 복사 (빌드경로가 다르기 때문에 gradle과 해당부분 다름)
COPY target/*.jar app.jar

# 포트 열기
EXPOSE 8080

# 이미지가 실행될 때 앱을 구동할 명령어
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]