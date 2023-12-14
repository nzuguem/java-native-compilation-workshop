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
time java -XX:+UseJVMCICompiler Fibonacci.java 17 1000000

# 4.99s user 0.11s system 101% cpu 5.047 total
```

- `-XX:+UseJVMCICompiler` : It is optional, and lets you use the Graal JIT instead of C2.

3. With GraalVM, you can configure the runtime to ignore JVMCI and use HotSpot's C2 JIT.

```bash
time java -XX:-UseJVMCICompiler Fibonacci.java 17 1000000

# 8.23s user 0.16s system 99% cpu 8.396 total
```

