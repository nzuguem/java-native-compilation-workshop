FROM scratch

# https://github.com/spring-projects/spring-boot/issues/8578#issuecomment-285949594
COPY <<EOF /tmp/.emptyFile
EOF
COPY <<EOF /static/.emptyFile
EOF

# --chmod option requires BuildKit
COPY --chmod=777 build/native/nativeCompile/spring-native /application
EXPOSE 8000
ENTRYPOINT ["/application"]