FROM scratch
# --chmod option requires BuildKit
COPY --chmod=777 build/quarkus-cli-1.0-runner /hello
EXPOSE 8000
ENTRYPOINT ["/hello"]