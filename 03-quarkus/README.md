# Quarkus Native
## What is quarkus ?
Quarkus is an open source project, initiated in 2018 by Red Hat. \
It's a Cloud Native Java stack tailored for OpenJDK HotSpot and GraalVM, crafted from the best of breed Java libraries and standards.

Their mantra is ***Supersonic Subatomic Java*** :
- ***Supersonic*** -> *Startup Speed* : Quarkus does all its best to boot your application as fast as possible and to respond to the first request.
- ***Subatomic*** -> *Memory Consumption & Packaging Size* : The size of the memory used to run an application, but also the size of the executable once the application is packaged. Quarkus is optimized to reduce the amount of memory used by a Java application.

Quarkus was specifically designed with GraalVM in mind. It stands out as a framework that facilitates the adaptation of other frameworks to the challenges inherent in GraalVM, such as Dynamic Proxies, Reflection, etc... The key element of this approach is to displace, during the "build time" phase, the operations usually performed at runtime by conventional frameworks.

In this way, Quarkus' role in relation to GraalVM is to elaborate the metadata (resources, reflections, proxies, etc.) associated with the frameworks and transmit them to GraalVM for the creation of a native executable. By making this transition right from the construction phase, Quarkus bypasses the obstacles associated with GraalVM and offers a more efficient solution for generating native applications.

> The magic of quarkus lies in its extensions, which are all GraalVM-compatible by design.

![Traditional Application Startup Steps](../images/traditional-application-startup-steps.png)
![Quarkus Application Startup Steps](../images/quarkus-application-startup-steps.png)

> The consequences of moving these steps to build time are **less memory consumption** (*many classes have been eliminated because they are not useful in the runtime, so there are fewer classes to scan and load at startup*) and **reduced startup time** (*fewer things to do at startup*).
