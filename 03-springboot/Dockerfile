FROM gcr.io/distroless/java-base-debian12
COPY build/native/nativeCompile/spring-native /application
EXPOSE 8000
ENTRYPOINT ["/application"]