# Spring Boot Native

1. AOT Processing in Spring

> It all starts with the addition of the GraalVM Native Build Tools plugin (Gradle, Maven)

![JIT Compiler Log](../images/spring-aot-processing.png)

In addition to **GraalVM Native Build Tools** (Gradle or Maven), the Spring Boot plugin adds some AOT-related phases to the build process. \
One of these phases is the ***processAot***. The application will be started on the basis of compiled code, with the aim of inferring on the *ApplicationContext* and generating Java code (*AOT Code*) for the registration of `BeanDefinition`, as well as configuration files for the dynamic aspect of Java (*AOT Metadata*). \
During this step, we retrieve the GraalVM reachability metadata ([reachability metadata repo][graalvm-reachability-matadata-repo]) linked to third-party libraries.

> All these artifacts will then be passed to the GraalVM "native-image" component.

2. Create Native image

```bash
# Gradle
./gradlew nativeCompile

#Maven
./mvnw -Pnative native:compile
```

3. Generate a Docker image based on a native image (using BuildPacks)

```bash
#Gradle
./gradlew bootBuildImage

#Maven
./mvnw -Pnative spring-boot:build-image 
```

4. To run a Spring Boot application on the JVM and have it use AOT generated code

```bash
java -Dspring.aot.enabled=true -jar build/libs/spring-native-1.0.jar
```
> If your application starts with the spring.aot.enabled property set to true, then you have higher confidence that it will work when converted to a native image. \
> It can potentially be used for integration tests, instead of `./gradlew nativeTest` or `./mvnw -PnativeTest test`, as native image tests are quite time-consuming.

<!-- links -->
[graalvm-reachability-matadata-repo]:https://github.com/oracle/graalvm-reachability-metadata