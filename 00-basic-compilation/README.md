# Basic compilation in Java

1. Calculation a million times fibonacci of 17 and observation of JIT activity (C1, C2)

```bash
time java -XX:+PrintCompilation Fibonacci.java 17 1000000 > jit-compiler.log

#  4.87s user 0.10s system 104% cpu 4.771 total
```
![JIT Compiler Log](../images/jit-compiler-log.png)

We can observe the activity of C1 (*2, 3*), via ***Tiried Compilation***. All the profiling data collected, together with the JIT compilation thresholds, triggers C2 (*4*).
The runtime proceeds to the de-optimization phases when the compilation hypotheses are wrong (*made not entering*).

For more information on compilation logs, see [here][explain-compilation-log]

<!-- Links -->
[explain-compilation-log]: https://www.baeldung.com/jvm-tiered-compilation#1-compilation-logs

2. Execution in pure interpretation mode (**JIT-less***)

```bash
time java -Xint Fibonacci.java 17 1000000

# 😴😪🥱💤🛌🏼
```
Very, very serious performance degradation, as we no longer benefit from JIT. The runtime's interpreter mode is very costly.


