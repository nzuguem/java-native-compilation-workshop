FROM gitpod/workspace-full

USER gitpod

# Make builder RUN use /bin/bash instead of /bin/sh
# https://github.com/moby/moby/issues/7281
SHELL ["/bin/bash", "-c"]

# Override time command
RUN echo "alias time='/usr/bin/time -f \"%E real,%U user,%S sys, %M kb rss\"'" >> /home/gitpod/.bashrc

# Install JDKs
RUN <<EOF
set -e

. /home/gitpod/.sdkman/bin/sdkman-init.sh

echo "sdkman_auto_answer=true" >> "$SDKMAN_DIR"/etc/config
echo "sdkman_auto_env=true" >> "$SDKMAN_DIR"/etc/config

sdk install java 21-graalce
sdk install java 21.0.1-tem
sdk default java 21-graalce
EOF

# Install monitoring and perf tools : perf, hey
RUN <<EOF
set -e

sudo apt-get update

sudo apt-get install -y linux-tools-generic \
                        hey
sudo rm -rf /var/lib/apt/lists/*
sudo cp /lib/linux-tools-*/perf /usr/bin/perf

pip install --no-cache-dir --upgrade pip
pip install --no-cache-dir psrecord
EOF