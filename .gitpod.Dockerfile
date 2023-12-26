FROM gitpod/workspace-full

USER gitpod

# Make builder RUN use /bin/bash instead of /bin/sh
# https://github.com/moby/moby/issues/7281
SHELL ["/bin/bash", "-c"]

# Override time command
RUN echo "alias time='/usr/bin/time -f \"%E real,%U user,%S sys, %M kb rss\"'" >> /home/gitpod/.bashrc

# Install SDKs
RUN <<EOF
set -e

. /home/gitpod/.sdkman/bin/sdkman-init.sh

echo "sdkman_auto_answer=true" >> "$SDKMAN_DIR"/etc/config
echo "sdkman_auto_env=true" >> "$SDKMAN_DIR"/etc/config

## Install JDKs
sdk install java 21-graalce
sdk install java 21.0.1-tem
sdk default java 21-graalce

## Install Quarkus CLI
sdk install quarkus
EOF

# Install monitoring and perf tools : hey
RUN <<EOF
set -e

sudo apt-get update

## Install Hey
sudo apt-get install -y hey

sudo rm -rf /var/lib/apt/lists/*

# Install psrecord
pip install --no-cache-dir --upgrade pip
pip install --no-cache-dir psrecord
pip install --no-cache-dir matplotlib
EOF

# Install AWS Tools
RUN <<EOF
set -e

## Install AWS CLI
pip install --no-cache-dir awscli --upgrade

## Install SAM CLI
pip install --no-cache-dir aws-sam-cli --upgrade
EOF
