# ---------------------
# Backend Build
# ---------------------
FROM maven:3.6-jdk-11 as BUILD

COPY src /usr/app/src
COPY pom.xml /usr/app

WORKDIR /usr/app

RUN mvn -f pom.xml -Djar.finalName=app -DskipTests clean package

# ---------------------
# Runtime Container
# ---------------------
FROM openjdk:11-jre-slim

ENV APP_HOME=/app
ENV STAGE="prod"
RUN adduser --system --no-create-home --disabled-password --ingroup root app
COPY --chown=app:root --from=BUILD /usr/app/target/*.jar $APP_HOME/app.jar
COPY --chown=app:root --from=BUILD /usr/app/src/main/resources/www/applicationinsights-agent-2.5.1.jar $APP_HOME/applicationinsights-agent-2.5.1.jar
COPY --chown=app:root --from=BUILD /usr/app/src/main/resources/www/AI-Agent.xml $APP_HOME/AI-Agent.xml


WORKDIR $APP_HOME

USER app

ENV MAX_MEM_PERCENT="80.0"
CMD java -XX:MaxRAMPercentage=${MAX_MEM_PERCENT} -Dspring.profiles.active=$STAGE -javaagent:./applicationinsights-agent-2.5.1.jar -jar app.jar
