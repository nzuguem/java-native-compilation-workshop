# Basic compilation in Java

1. Counts the number of uppercase characters in a body of text. To simulate a large load, the same sentence is processed 10 million times and observation of JIT activity (C1, C2)

```bash
java -XX:+PrintCompilation CountUppercase.java Hello World > jit-compiler.log

#1 (144 ms)
#2 (101 ms)
#3 (51 ms)
#4 (52 ms)
#5 (66 ms)
#6 (53 ms)
#7 (49 ms)
#8 (51 ms)
#9 (61 ms)
#total: 19999998 (700 ms)
```
![JIT Compiler Log](../images/jit-compiler-log.png)

We can observe the activity of C1 (*3*), via ***Tiried Compilation***. All the profiling data collected, together with the JIT compilation thresholds, triggers C2 (*4*).
The runtime proceeds to the de-optimization phases when the compilation hypotheses are wrong (*made not entering*).

For more information on compilation logs, see [here][explain-compilation-log]

<!-- Links -->
[explain-compilation-log]: https://www.baeldung.com/jvm-tiered-compilation#1-compilation-logs

2. Execution in pure interpretation mode (**JIT-less***)

```bash
java -Xint CountUppercase.java Hello World

# 😴😪🥱💤🛌🏼
#1 (3282 ms)
#2 (3312 ms)
#3 (3393 ms)
#4 (3400 ms)
#5 (3268 ms)
#6 (3353 ms)
#7 (3328 ms)
#8 (3309 ms)
#9 (3334 ms)
#total: 19999998 (33284 ms)
```
Very, very serious performance degradation, as we no longer benefit from JIT. The runtime's interpreter mode is very costly.


