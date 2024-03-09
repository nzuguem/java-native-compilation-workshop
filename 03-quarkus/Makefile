default: help

JVM_COMMAND="java -Xmx256m -jar target/quarkus-native-1.0-runner.jar"
NATIVE_IMAGE_COMMAND="./target/quarkus-native-1.0-runner -Xmx256m"

# Substitution because CFN Mapping Keys do not support the "_" character
ARCH := $(shell uname -m)
ifeq ($(ARCH),x86_64)
    CPU_ARCH := $(shell echo $(ARCH) | sed 's/_/-/g')
else
    CPU_ARCH := $(ARCH)
endif

help: ## Display this help.
	@awk 'BEGIN {FS = ":.*##"; printf "\nUsage:\n  make \033[36m<target>\033[0m\n"} /^[a-zA-Z_0-9-]+:.*?##/ { printf "  \033[36m%-15s\033[0m %s\n", $$1, $$2 } /^##@/ { printf "\n\033[1m%s\033[0m\n", substr($$0, 5) } ' $(MAKEFILE_LIST)

build: ## Build the application
	@./mvnw package -Dquarkus.package.type=uber-jar

build-native-image: ## Build the native image - The native image generated is highly dependent on the OS on which the command is run (GraalVM is installed on this OS)
	@./mvnw package -Pnative -Dquarkus.native.additional-build-args="--enable-sbom=cyclonedx"

build-native-image-static: ## Build the native image - In Static Link Mode
	@./mvnw package -Pnative -Dquarkus.native.additional-build-args="--enable-sbom=cyclonedx","--static","--libc=musl"

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

build-QuarkusNative: ## Task executed by SAM CLI to generate the artifact to be deployed on AWS Lambda
	@./mvnw  package -Pnative  -Dquarkus.native.container-build=true
	@cp src/main/resources/bootstrap $(ARTIFACTS_DIR)
	@mv target/quarkus-native-1.0-runner $(ARTIFACTS_DIR)/hello
	@chmod 777 $(ARTIFACTS_DIR)/hello
	@chmod 777 $(ARTIFACTS_DIR)/bootstrap

sam-build: ## Build application for SAM (Serverless Application Model)
	@sam build

sam-deploy: ## Deploy application on AWS lambda
	@sam deploy --parameter-overrides ArchCPU=$(CPU_ARCH)

sam-logs: ## SAM Logs AWS Lambda Function (Tail)
	@sam logs -t -n QuarkusNative --stack-name quarkus-native

sam-delete: ## Delete Stack AWS
	@sam delete

generate-sbom-native-with-syft: ## Extraction of the SBOM embedded in the native executable, using the syft tool
	@syft packages -o json=quarkus-native-sbom.json target/quarkus-native-1.0-runner

generate-sbom-native-with-native-inspect: ## Extraction of the SBOM embedded in the native executable, using native-image-inspect
	@native-image-inspect --sbom target/quarkus-native-1.0-runner > quarkus-native-sbom.json

generate-vulnerability-report: generate-sbom-native-with-syft ## Generation of the vulnerability report
	@grype sbom:./quarkus-native-sbom.json

build-docker-image-dynamic: ## Build Docker image with Dynamic Linked Mode of Native Executable
	@docker build -t quarkus-native:dynamic -f Dockerfile.dynamic .

build-docker-image-mostly: ## Build Docker image with Mostly Linked Mode of Native Executable
	@docker build -t quarkus-native:mostly -f Dockerfile.mostly .

build-docker-image-static: ## Build Docker image with Static Linked Mode of Native Executable
	@docker build -t quarkus-native:static -f Dockerfile.static .

build-docker-image-static-upx: ## Build Docker image with Static Linked Mode of Native Executable and compress it with UPX
	@docker build -t quarkus-native:static-upx -f Dockerfile.static-upx .

