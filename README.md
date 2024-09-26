# Native compilation workshop in Java

[![Quarkus Application CI/CD Workflow](https://github.com/nzuguem/java-native-compilation-workshop/actions/workflows/quarkus.yml/badge.svg)](https://github.com/nzuguem/java-native-compilation-workshop/actions/workflows/quarkus.yml)
[![Spring Boot CI/CD Workflow](https://github.com/nzuguem/java-native-compilation-workshop/actions/workflows/spring-boot.yml/badge.svg)](https://github.com/nzuguem/java-native-compilation-workshop/actions/workflows/spring-boot.yml)
[![Quarkus Application CLI CI Workflow](https://github.com/nzuguem/java-native-compilation-workshop/actions/workflows/quarkus-cli.yml/badge.svg)](https://github.com/nzuguem/java-native-compilation-workshop/actions/workflows/quarkus-cli.yml)

Click the button below to start a new development environment: \
[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/nzuguem/java-native-compilation-workshop)

## Measurement considerations
> ⚠️ All measurements are highly dependent on the characteristics of my computer. So feel free to replay the commands to make up your own mind.

We use the `time` command to measure performance (execution time and memory consumption).

The use of this command differs depending on whether you're running MacOS or Linux :
- ***MacOS*** 
```
$ TIMEFMT+=' %M kb rss'
$ time <COMMAND>
#0,00s user 0,00s system 0% cpu 1,014 total 1168 kb rss
```
- ***Linux*** 
```
$ /usr/bin/time -f "%E real,%U user,%S sys, %M kb rss" <COMMAND>
#01.03 real,0.00 user,0.00 sys, 1648 kb rss
```
In the output of the `time` command, there are 2 elements of interest to us :

- **Program execution time** (`real` or `total`) -> `<VALUE> real` or `<VALUE> total`
> ***Startup Time*** = **Program Execution Time** - **Solution Time**
> - *Solution Time : Time taken for user code to be executed*
> - *Program Execution Time: Total program run time (runtime startup time + Solution Time)*

- **Memory consumption of the executed program** -> `<VALUE> kb rss`

## Content
1. [Basic compilation in Java](00-basic-compilation) 👉🏼 How the JIT Compiler works
2. [GraalVM JIT Compiler](01-graalvm-jit-compiler) 👉🏼 Installing and testing GraalVM JIT
3. [GraalVM AOT Compiler](02-graalvm-aot-compiler) 👉🏼 Discover the Ahead Of Time (AOT) compilation with GraalVM   
4. [Spring Boot Native - AOT](03-springboot) 👉🏼 Discover the Ahead Of Time (AOT) compilation with Spring Boot + GraalVM
5. [Quarkus Native - AOT](03-quarkus) 👉🏼 Discover the Ahead Of Time (AOT) compilation with Quarkus + GraalVM
   - [CLI with Quarkus Native - AOT](03-quarkus-cli) 👉🏼 Discover the Ahead Of Time (AOT) compilation with Quarkus + GraalVM + Picocli