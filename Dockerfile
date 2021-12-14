FROM openjdk:14
COPY build/libs/PracaInz-1.0.0.jar AnimeApp.jar

ENTRYPOINT ["java", "-jar", "/AnimeApp.jar", "-x", "test"]