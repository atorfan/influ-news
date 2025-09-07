FROM eclipse-temurin:21-jre

ENV NAME_JAR="image-rescale"
ENV packageFile="/var/app/${NAME_JAR}.jar"

RUN mkdir /app
WORKDIR /app
COPY /apps/image-rescale/build/libs/${NAME_JAR}*.jar /app/${NAME_JAR}.jar

ENTRYPOINT exec java -jar -Dfile.encoding="UTF-8" ${packageFile}
