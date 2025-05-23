default: help

JVM_COMMAND="java -Xmx256m -jar build/libs/spring-native-1.0.jar"
JVM_AOT_COMMAND="java -Xmx256m -Dspring.aot.enabled=true -jar build/libs/spring-native-1.0.jar"
NATIVE_IMAGE_COMMAND="./build/native/nativeCompile/spring-native -Xmx256m"

help: ## Display this help.
	@awk 'BEGIN {FS = ":.*##"; printf "\nUsage:\n  make \033[36m<target>\033[0m\n"} /^[a-zA-Z_0-9-]+:.*?##/ { printf "  \033[36m%-15s\033[0m %s\n", $$1, $$2 } /^##@/ { printf "\n\033[1m%s\033[0m\n", substr($$0, 5) } ' $(MAKEFILE_LIST)

.PHONY: build
build: ## Build the application
	@./gradlew build

run: ## Launch Application
	@./gradlew bootRun

run-native: ## Launch Native Application
	@./build/native/nativeCompile/spring-native

run-native-for-prod: ## Launch Native Application for prod
	@./build/native/nativeCompile/spring-native -Dspring.profiles.active=prod

build-native-image: ## Build the native image - The native image generated is highly dependent on the OS on which the command is run (GraalVM is installed on this OS)
	@./gradlew nativeCompile --build-args="-Ob"

build-native-image-for-prod: ## Build the native image fot prod - The native image generated is highly dependent on the OS on which the command is run (GraalVM is installed on this OS)
	@./gradlew nativeCompile --build-args="-Ob" -PaotProfiles=prod

build-native-image-static: ## Build the native image - In Static Link Mode
	@./gradlew nativeCompile --build-args="-Ob" -PnativeExecutableLinkedMode=static

build-native-image-static-ci: ## Build the native image - In Static Link Mode
	@./gradlew nativeCompile -PnativeExecutableLinkedMode=static

build-native-image-static-for-prod: ## Build the native image for prod - In Static Link Mode
	@./gradlew nativeCompile --build-args="-Ob" -PnativeExecutableLinkedMode=static -PaotProfiles=prod

load-jvm: ## Simulate a load of 1 million requests on the application in JVM mode
	@../scripts/load.sh $(JVM_COMMAND)

load-jvm-aot: ## Simulate a load of 1 million requests on the application in JVM AOT mode
	@../scripts/load.sh $(JVM_AOT_COMMAND)

load-native: ## Simulate a load of 1 million requests on the application in Native mode
	@../scripts/load.sh $(NATIVE_IMAGE_COMMAND)

start-jvm: ## Run application in JVM mode with performance output
	@../scripts/start.sh $(JVM_COMMAND)

start-jvm-aot: ## Run application in JVM AOT mode with performance output
	@../scripts/start.sh $(JVM_AOT_COMMAND)

start-native: ## Run application in Native Image mode with performance output
	@../scripts/start.sh $(NATIVE_IMAGE_COMMAND)

record-jvm: ##  Run application in JVM mode with performance & Plot output
	@../scripts/record.sh $(JVM_COMMAND)

record-jvm-aot: ##  Run application in JVM AOT mode with performance & Plot output
	@../scripts/record.sh $(JVM_AOT_COMMAND)

record-native: ##  Run application in Native Image mode with performance & Plot output
	@../scripts/record.sh $(NATIVE_IMAGE_COMMAND)

build-container-image-native: ## Build container image based on native image
	@./gradlew bootBuildImage

generate-sbom-native-with-syft: ## Extraction of the SBOM embedded in the native executable, using the syft tool
	@syft packages -o json=spring-native-sbom.json build/native/nativeCompile/spring-native

generate-sbom-native-with-native-inspect: ## Extraction of the SBOM embedded in the native executable, using native-image-inspect
	@native-image-inspect --sbom build/native/nativeCompile/spring-native > spring-native-sbom.json

generate-vulnerability-report: generate-sbom-native-with-syft ## Generation of the vulnerability report
	@grype sbom:./spring-native-sbom.json

build-docker-image-dynamic: ## Build Docker image with Dynamic Linked Mode of Native Executable
	@docker build -t spring-native:dynamic -f Dockerfile.dynamic .

build-docker-image-mostly: ## Build Docker image with Mostly Linked Mode of Native Executable
	@docker build -t spring-native:mostly -f Dockerfile.mostly .

build-docker-image-static: ## Build Docker image with Static Linked Mode of Native Executable
	@docker build -t spring-native:static -f Dockerfile.static .

build-docker-image-static-upx: ## Build Docker image with Static Linked Mode of Native Executable and compress it with UPX
	@docker build -t spring-native:static-upx -f Dockerfile.static-upx .

deploy-on-minikube: ## Deploy on minikube
	@kubectl apply -f k8s/spring-native.yml

get-minikube-service-url: ## Get minikube service URL
	@minikube service --url spring-native

undeploy-on-minikube: ## Undeploy on minikube
	@kubectl delete -f k8s/spring-native.yml
