default: help

JVM_COMMAND="java -Xmx256m -jar build/libs/spring-native-1.0.jar"
JVM_AOT_COMMAND="java -Xmx256m -Dspring.aot.enabled=true -jar build/libs/spring-native-1.0.jar"
NATIVE_IMAGE_COMMAND="./build/native/nativeCompile/spring-native -Xmx256m"

help: ## Display this help.
	@awk 'BEGIN {FS = ":.*##"; printf "\nUsage:\n  make \033[36m<target>\033[0m\n"} /^[a-zA-Z_0-9-]+:.*?##/ { printf "  \033[36m%-15s\033[0m %s\n", $$1, $$2 } /^##@/ { printf "\n\033[1m%s\033[0m\n", substr($$0, 5) } ' $(MAKEFILE_LIST)

build: ## Build the application
	@./gradlew build

build-native-image: ## Build the native image
	@./gradlew nativeCompile

load-jvm: build ## Simulate a load of 1 million requests on the application in JVM mode
	@../scripts/load.sh $(JVM_COMMAND)

load-jvm-aot: build ## Simulate a load of 1 million requests on the application in JVM AOT mode
	@../scripts/load.sh $(JVM_AOT_COMMAND)

load-native: build-native-image ## Simulate a load of 1 million requests on the application in Native mode
	@../scripts/load.sh $(NATIVE_IMAGE_COMMAND)

start-jvm: build ## Run application in JVM mode with performance output
	@time ../scripts/start.sh $(JVM_COMMAND)

start-jvm-aot: build ## Run application in JVM AOT mode with performance output
	@time ../scripts/start.sh $(JVM_AOT_COMMAND)

start-native: build-native-image ## Run application in Native Image mode with performance output
	@time ../scripts/start.sh $(NATIVE_IMAGE_COMMAND)

record-jvm: build ##  Run application in JVM mode with performance & Plot output
	@time ../scripts/record.sh $(JVM_COMMAND)

record-jvm-aot: build ##  Run application in JVM AOT mode with performance & Plot output
	@time ../scripts/record.sh $(JVM_AOT_COMMAND)

record-native: build-native-image ##  Run application in Native Image mode with performance & Plot output
	@time ../scripts/record.sh $(NATIVE_IMAGE_COMMAND)

