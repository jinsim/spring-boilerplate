# 11버전에서는 alpine을 아직 지원하지 않는다.
FROM adoptopenjdk/openjdk11

# /usr/src/app/ 을 컨테이너 작업 기본 디렉터리로 설정한다.
ENV APP_HOME=/usr/src/app/
WORKDIR $APP_HOME

# 생성된 jar 파일을 /usr/src/app/app.jar 로 옮긴다
ARG JAR_FILE_PATH=build/libs/*.jar
COPY ${JAR_FILE_PATH} ./app.jar

# app.jar 파일을 실행한다.
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","./app.jar"]