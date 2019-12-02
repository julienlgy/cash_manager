
FROM maven:3.5.2-jdk-8-alpine AS MAVEN_TOOL_CHAIN
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn package

FROM tomcat:9.0-jre8-alpine
RUN rm -rf $CATALINA_HOME/webapps/ROOT
COPY --from=MAVEN_TOOL_CHAIN /tmp/target/cashmanager*.war $CATALINA_HOME/webapps/ROOT.war
CMD ["catalina.sh", "run"]

HEALTHCHECK --interval=1m --timeout=3s CMD wget --quiet --tries=1 --spider http://localhost:8080/api/v1/users/ || exit 1