#!/bin/sh
set -eu

protoc=$(protoc --version)

mumblerepo=../mumble
protof=src/Mumble.proto

cwd=$(pwd)
cd "$mumblerepo"
describe=$(git describe --tags --dirty --always)
branch=$(git rev-parse --symbolic-full-name --abbrev-ref HEAD)
cd "$cwd"

cat <<EOF >"$protof"
// This is $protof from the Mumble repository at $describe (branch $branch).
// Going to compile to java classes using protoc from ${protoc}.
// NOTE: java compile options added at the bottom of this file.
//
EOF

cat >>"$protof" "$mumblerepo/$protof"

cat <<EOF >>"$protof"
option java_package = "se.lublin.humla.protobuf";
option java_outer_classname = "Mumble";
option java_multiple_files = false;
EOF

protoc --java_out=src/main/java "$protof"

git diff --stat src/main/java/ "$protof"
