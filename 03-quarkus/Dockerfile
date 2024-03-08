FROM gcr.io/distroless/java-base-debian12
COPY target/quarkus-native-1.0-runner /application
EXPOSE 8000
ENTRYPOINT ["/application"]