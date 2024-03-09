FROM scratch
# --chmod option requires BuildKit
COPY --chmod=777 target/quarkus-native-1.0-runner /application
EXPOSE 8000
ENTRYPOINT ["/application"]