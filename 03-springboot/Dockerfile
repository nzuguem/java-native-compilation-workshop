FROM gcr.io/distroless/java-base-debian12
# --chmod option requires BuildKit
COPY --chmod=777 build/native/nativeCompile/spring-native /application
EXPOSE 8000
ENTRYPOINT ["/application"]