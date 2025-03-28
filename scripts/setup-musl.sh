#!/usr/bin/env bash
set -e

ZLIB_VERSION=1.3.1
TOOLCHAIN_DIR=`pwd`/x86_64-linux-musl-native

# Download musl
wget -q http://more.musl.cc/10/x86_64-linux-musl/x86_64-linux-musl-native.tgz
tar -xzf x86_64-linux-musl-native.tgz
rm x86_64-linux-musl-native.tgz

# Download, build, install zlib into TOOLCHAIN_DIR
echo "zlib version=${ZLIB_VERSION}"
wget -q https://zlib.net/fossils/zlib-${ZLIB_VERSION}.tar.gz
tar -xzf zlib-${ZLIB_VERSION}.tar.gz
rm zlib-${ZLIB_VERSION}.tar.gz
pushd zlib-${ZLIB_VERSION}
./configure --prefix=${TOOLCHAIN_DIR} --static
make && make install
popd

echo "export PATH=$TOOLCHAIN_DIR/bin:\$PATH" >> $HOME/.bashrc