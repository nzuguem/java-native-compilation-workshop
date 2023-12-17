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
time java -XX:+UseJVMCICompiler CountUppercase.java Hello World

#1 (72 ms)
#2 (73 ms)
#3 (55 ms)
#4 (11 ms)
#5 (10 ms)
#6 (9 ms)
#7 (9 ms)
#8 (9 ms)
#9 (9 ms)
#total: 19999998 (266 ms)
#1,04s user 0,14s system 167% cpu 0,707 total 340096 kb rss
```

- `-XX:+UseJVMCICompiler` : It is optional, and lets you use the Graal JIT instead of C2.

3. With GraalVM, you can configure the runtime to ignore JVMCI and use HotSpot's C2 JIT.

```bash
time java -XX:-UseJVMCICompiler CountUppercase.java Hello World

#1 (120 ms)
#2 (72 ms)
#3 (47 ms)
#4 (44 ms)
#5 (39 ms)
#6 (38 ms)
#7 (47 ms)
#8 (43 ms)
#9 (38 ms)
#total: 19999998 (526 ms)
#1,21s user 0,11s system 140% cpu 0,942 total 360304 kb rss
```

## Performance measurement
- **Startup Time** *approximately equal* ***0,429 ms***
- **Memory** *approximately equal* ***350 MB*** 