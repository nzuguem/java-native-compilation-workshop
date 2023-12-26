# CLI with Quarkus Native
## Which language to use to write a CLI ?
A CLI can be coded in any programming language, the choice being motivated by :

- Its performance, with startup time as the main criterion. It's complicated to provide a CLI that takes a few seconds to start up in order to execute a process that takes a few milliseconds (which often puts Java in a bad position 😇).
- Ease of development: does the programming language provide a library of functions for managing options, commands and parameters?
- The skills of development and maintenance teams.

Several methods are available for distributing these programs, depending on the programming language used:

- Compiled programming languages (C, Go, etc.) offer an operating system-dependent binary
- Interpreted programming languages (Python, Java, etc.) offer a single deliverable independent of the operating system, but require a dedicated runtime environment to be installed beforehand (interpreter, jvm, etc.).

## A CLI written in Java ? 😬
The idea seems crazy, and a wall looms on the horizon when you think of the Java ecosystem and its complexity:

- A JVM is required to run the application
- Advanced functionalities require application servers
- Numerous libraries exist, but they require several JARs in addition to the application JAR
- Start-up times can be excessive for the functionality provided
- Memory impact can quickly escalate, depending on program dependencies

why use Java for short-lived scripts when it excels at managing long-lived applications ? \
Simply because it's now possible, viable and powerful! With the arrival of native compilation (AOT), a Java program can be extremely powerful right from the start, and consume less memory.

## Let's see it together !

1. Create native image - The native image generated is highly dependent on the OS on which the command is run (GraalVM is installed on this OS)
```bash
make build-native-image
```

2. Execute CLI

```bash
./hello --help

#Usage: hello [-hV] <name>
#      <name>      Your name.
#  -h, --help      Show this help message and exit.
#  -V, --version   Print version information and exit.
```

```bash
time ./hello World

# Hello World, go go commando!

# 0,01s user 0,01s system 78% cpu 0,023 total 22528 kb rss
```