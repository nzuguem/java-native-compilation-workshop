default: help

help: ## Display this help.
	@awk 'BEGIN {FS = ":.*##"; printf "\nUsage:\n  make \033[36m<target>\033[0m\n"} /^[a-zA-Z_0-9-]+:.*?##/ { printf "  \033[36m%-15s\033[0m %s\n", $$1, $$2 } /^##@/ { printf "\n\033[1m%s\033[0m\n", substr($$0, 5) } ' $(MAKEFILE_LIST)

run-native: ## Launch Native Application
	@./build/quarkus-cli-1.0-runner

build-native-image: ## Build the native image - The native image generated is highly dependent on the OS on which the command is run (GraalVM is installed on this OS)
	@./gradlew build -Dquarkus.package.type=native -Dquarkus.native.additional-build-args="--enable-sbom=classpath","--emit=build-report","-Ob"
	@cp ./build/quarkus-cli-1.0-runner hello

build-native-image-static: ## Build the native image - In Static Link Mode
	@./gradlew build -Dquarkus.package.type=native -Dquarkus.native.additional-build-args="--enable-sbom=classpath","--static","--libc=musl","-H:BuildOutputJSONFile=/tmp/native-image-build-output.json","--emit=build-report","-Ob"

build-native-image-static-ci: ## Build the native image - In Static Link Mode
	@./gradlew build -Dquarkus.package.type=native -Dquarkus.native.additional-build-args="--enable-sbom=classpath","--static","--libc=musl","-H:BuildOutputJSONFile=/tmp/native-image-build-output.json","--emit=build-report"

generate-sbom-native-with-syft: ## Extraction of the SBOM embedded in the native executable, using the syft tool
	@syft packages -o json=quarkus-cli-sbom.json build/quarkus-cli-1.0-runner

generate-sbom-native-with-native-inspect: ## Extraction of the SBOM embedded in the native executable, using native-image-inspect
	@native-image-inspect --sbom build/quarkus-cli-1.0-runner > quarkus-cli-sbom.json

generate-vulnerability-report: generate-sbom-native-with-syft ## Generation of the vulnerability report
	@grype sbom:./quarkus-cli-sbom.json