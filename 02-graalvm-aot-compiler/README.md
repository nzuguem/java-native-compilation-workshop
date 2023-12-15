# GraalVM AOT Compiler

1. AOT compilation is another GraalVM feature. It allows you to build a native executable (optimized for a specific platform) and an automatic executable (no JVM required). It is done at build time, which guarantees low memory consumption and rapid start-up.
   The downside is that it takes a long time 

```bash
javac CountUppercase.java

native-image CountUppercase

./countuppercase Hello World
#1 (139 ms)
#2 (106 ms)
#3 (105 ms)
#4 (104 ms)
#5 (117 ms)
#6 (126 ms)
#7 (112 ms)
#8 (108 ms)
#9 (106 ms)
#total: 19999998 (1132 ms)
```

With 10 million loop turns, the execution time with C2/Graal is less than with the native image. The reason is that C2/Graal performs frequent optimizations, whereas with the native image, optimization has been performed up to a certain level.

Below are the native compilation logs. For more information on this output, see this [doc][native-image-compilation-output]
![JIT Compiler Log](../images/native-image-build-output.png)


<!-- links -->
[native-image-compilation-output]: https://www.graalvm.org/latest/reference-manual/native-image/overview/BuildOutput/