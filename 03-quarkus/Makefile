default: help

JVM_COMMAND="java -Xmx256m -jar target/quarkus-native-1.0-runner.jar"
NATIVE_IMAGE_COMMAND="./target/quarkus-native-1.0-runner -Xmx256m"

help: ## Display this help.
	@awk 'BEGIN {FS = ":.*##"; printf "\nUsage:\n  make \033[36m<target>\033[0m\n"} /^[a-zA-Z_0-9-]+:.*?##/ { printf "  \033[36m%-15s\033[0m %s\n", $$1, $$2 } /^##@/ { printf "\n\033[1m%s\033[0m\n", substr($$0, 5) } ' $(MAKEFILE_LIST)

build: ## Build the application
	@./mvnw package -Dquarkus.package.type=uber-jar

build-native-image: ## Build the native image - The native image generated is highly dependent on the OS on which the command is run (GraalVM is installed on this OS)
	@./mvnw package -Pnative

load-jvm: ## Simulate a load of 1 million requests on the application in JVM mode
	@../scripts/load.sh $(JVM_COMMAND)

load-native: ## Simulate a load of 1 million requests on the application in Native mode
	@../scripts/load.sh $(NATIVE_IMAGE_COMMAND)

start-jvm: ## Run application in JVM mode with performance output
	@../scripts/start.sh $(JVM_COMMAND)

start-native: ## Run application in Native Image mode with performance output
	@../scripts/start.sh $(NATIVE_IMAGE_COMMAND)

record-jvm: ##  Run application in JVM mode with performance & Plot output
	@../scripts/record.sh $(JVM_COMMAND)

record-native: ##  Run application in Native Image mode with performance & Plot output
	@../scripts/record.sh $(NATIVE_IMAGE_COMMAND)

build-container-image-native: ## Build container image based on native image
	@./mvnw package -Pnative -Dquarkus.native.container-build=true -Dquarkus.container-image.build=true

