FROM gitpod/workspace-full

USER gitpod

# Install custom tools, runtime, etc. using apt-get
RUN bash -c ". /home/gitpod/.sdkman/bin/sdkman-init.sh && \
    sdk install java 21-graalce && \
    sdk install java 21.0.1-tem &&\
    sdk default java 21-graalce"

RUN echo 'alias time="/usr/bin/time -f "%E real,%U user,%S sys, %M kb rss"' >> /home/gitpod/.bashrc