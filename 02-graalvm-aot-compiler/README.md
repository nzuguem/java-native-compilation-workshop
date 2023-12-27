# GraalVM AOT Compiler

1. AOT compilation is another GraalVM feature. It allows you to build a native executable (optimized for a specific platform) and an automatic executable (no JVM required). It is done at build time, which guarantees low memory consumption and rapid start-up.
   The downside is that it takes a long time 

```bash
javac CountUppercase.java

native-image --enable-sbom=cyclonedx CountUppercase

time ./countuppercase Hello World

#1 (125 ms)
#2 (107 ms)
#3 (107 ms)
#4 (104 ms)
#5 (105 ms)
#6 (105 ms)
#7 (105 ms)
#8 (108 ms)
#9 (104 ms)
#total: 19999998 (1075 ms)
#1,05s user 0,02s system 97% cpu 1,099 total 67728 kb rss
```

With 10 million loop turns, the execution time with C2/Graal is less than with the native image. The reason is that C2/Graal performs frequent optimizations, whereas with the native image, optimization has been performed up to a certain level.

Below are the native compilation logs. For more information on this output, see this [doc][native-image-compilation-output]
![JIT Compiler Log](../images/native-image-build-output.png)

## Performance measurement
- **Startup Time** *approximately equal* ***0,024 s***
- **Memory** *approximately equal* ***67 MB***

Native-image performance (AOT) is far superior to that of [JIT](../01-graalvm-jit-compiler/README.md#performance-measurement)

2. The native-image utility provides partial support for reflection. It uses static analysis to detect the elements of your application that are accessed using the Java Reflection API. However, because the analysis is static, it cannot always completely predict all usages of the API when the program runs : It's the CWA (Closed World Assumption)
- Let's try generating and executing a native image with reflection
```bash
javac Task.java

native-image --enable-sbom=cyclonedx --no-fallback Task

time ./task CountUppercase "Hello World"
#Exception in thread "main" java.lang.ClassNotFoundException: CountUppercase
#	at org.graalvm.nativeimage.builder/com.oracle.svm.core.hub.ClassForNameSupport.forName(ClassForNameSupport.java:122)
#	at org.graalvm.nativeimage.builder/com.oracle.svm.core.hub.ClassForNameSupport.forName(ClassForNameSupport.java:86)
#	at java.base@21.0.1/java.lang.Class.forName(DynamicHub.java:1346)
#	at java.base@21.0.1/java.lang.Class.forName(DynamicHub.java:1309)
#	at java.base@21.0.1/java.lang.Class.forName(DynamicHub.java:1302)
#	at Task.main(Task.java:33)
#	at java.base@21.0.1/java.lang.invoke.LambdaForm$DMH/sa346b79c.invokeStaticInit(LambdaForm$DMH)
```
This shows that, from its static analysis, the native-image tool was unable to determine that class ***CountUppercase*** is used by the application and therefore did not include it in the native executable.

- To solve this problem, we need to explicitly provide a configuration file to the native image utility to specify the program elements that use the API. These configuration files can be edited manually, or we can use the native image agent to generate them.
```bash
# Destination of configuration files. Automatically read by native image utility
mkdir -p META-INF/native-image

java -agentlib:native-image-agent=config-output-dir=META-INF/native-image Task CountUppercase "Hello World"

native-image --enable-sbom=cyclonedx --no-fallback Task

time ./task CountUppercase "Hello World"
#1 (108 ms)
#2 (81 ms)
#3 (79 ms)
#4 (80 ms)
#5 (78 ms)
#6 (79 ms)
#7 (83 ms)
#8 (79 ms)
#9 (82 ms)
#total: 19999998 (832 ms)
#0,80s user 0,02s system 95% cpu 0,856 total 68464 kb rss
```
The configuration file (*reflect-config.json*) generated:
```json
[
  {
    "name":"CountUppercase",
    "methods":[
       {
          "name":"process",
          "parameterTypes":["java.lang.String"] 
       }
    ]
  }
]
```

Java's dynamism is not limited to reflection. There are many other elements (JNI, Proxy, etc.). For more details, consult this [doc][native-image-dynamic-java]
<!-- links -->
[native-image-compilation-output]: https://www.graalvm.org/latest/reference-manual/native-image/overview/BuildOutput/
[native-image-dynamic-java]: https://www.graalvm.org/latest/reference-manual/native-image/dynamic-features/