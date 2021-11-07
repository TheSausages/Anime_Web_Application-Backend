FROM openjdk:14
COPY build/libs/PracaInz-0.0.1-SNAPSHOT.jar AnimeApp.jar

ENTRYPOINT ["java", "-jar", "/AnimeApp.jar"]