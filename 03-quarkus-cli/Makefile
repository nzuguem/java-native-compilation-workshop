default: help

help: ## Display this help.
	@awk 'BEGIN {FS = ":.*##"; printf "\nUsage:\n  make \033[36m<target>\033[0m\n"} /^[a-zA-Z_0-9-]+:.*?##/ { printf "  \033[36m%-15s\033[0m %s\n", $$1, $$2 } /^##@/ { printf "\n\033[1m%s\033[0m\n", substr($$0, 5) } ' $(MAKEFILE_LIST)


build-native-image: ## Build the native image - The native image generated is highly dependent on the OS on which the command is run (GraalVM is installed on this OS)
	@./gradlew build -Dquarkus.package.type=native
	@mv build/quarkus-cli-1.0-runner hello