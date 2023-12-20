FROM gitpod/workspace-full

USER gitpod

# Install custom tools, runtime, etc. using apt-get
RUN bash -c ". /home/gitpod/.sdkman/bin/sdkman-init.sh && \
    sdk install java 21-graalce && \
    sdk install java 21.0.1-tem &&\
    sdk default java 21-graalce"
