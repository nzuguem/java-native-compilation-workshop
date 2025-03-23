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
sdk install java 24-graal
sdk install java 24-tem
sdk default java 24-graal

## Install Quarkus CLI
sdk install quarkus 3.19.4

EOF

# Install monitoring and perf tools : hey, upx
RUN <<EOF
set -e

sudo apt-get update

## Install Hey
sudo apt-get install -y hey
## Install UPX
sudo apt-get install -y upx


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

# Install Scan Vulnerabilities Tools
RUN <<EOF

set -e

## Install syft and grype
curl -sSfL https://raw.githubusercontent.com/anchore/grype/main/install.sh | sudo sh -s -- -b /usr/local/bin
curl -sSfL https://raw.githubusercontent.com/anchore/syft/main/install.sh | sudo sh -s -- -b /usr/local/bin
EOF

# Install Docker Cloud Driver Buildx
RUN <<EOF
set -e
mkdir -p ~/.docker/cli-plugins
wget -q -O ~/.docker/cli-plugins/docker-buildx https://github.com/docker/buildx-desktop/releases/download/v0.12.0-desktop.2/buildx-v0.12.0-desktop.2.linux-amd64
chmod a+x ~/.docker/cli-plugins/docker-buildx
EOF
