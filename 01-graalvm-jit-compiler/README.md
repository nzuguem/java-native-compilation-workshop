# GraalVM JIT Compiler

1. Install GraalVM

```bash
curl -s "https://get.sdkman.io" | bash

sdk  install java 21-graalce

sdk  default java 21-graalce
```

2. One of GraalVM's best features is its Graal JIT Compiler. It is written in Java and includes a multitude of algorithms for optimizing the native code generated (it is more powerful than C2).
   Migrating from OpenJDK to GraalVM allows you to benefit natively from the performance provided by the Graal JIT, without changing your application code.

```bash
java -XX:+UseJVMCICompiler CountUppercase.java Hello World

#1 (77 ms)
#2 (80 ms)
#3 (55 ms)
#4 (47 ms)
#5 (42 ms)
#6 (43 ms)
#7 (53 ms)
#8 (54 ms)
#9 (43 ms)
#total: 19999998 (538 ms)
```

- `-XX:+UseJVMCICompiler` : It is optional, and lets you use the Graal JIT instead of C2.

3. With GraalVM, you can configure the runtime to ignore JVMCI and use HotSpot's C2 JIT.

```bash
java -XX:-UseJVMCICompiler CountUppercase.java Hello World

#1 (173 ms)
#2 (94 ms)
#3 (41 ms)
#4 (56 ms)
#5 (43 ms)
#6 (40 ms)
#7 (39 ms)
#8 (45 ms)
#9 (43 ms)
#total: 19999998 (612 ms)
```

