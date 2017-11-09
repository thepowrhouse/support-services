#!/usr/bin/env bash

function note() {
    local GREEN NC
    GREEN='\033[0;32m'
    NC='\033[0m' # No Color
    printf "\n${GREEN}$@  ${NC}\n" >&2
}

set -e

note "Building config...";          gradle :config-server:buildDocker;
note "Building discovery...";       gradle :discovery-server:buildDocker;
note "Building edge...";            gradle :edge-server:buildDocker;
note "Building hystrix...";         gradle :hystrix-dashboard:buildDocker;
note "Building turbine...";         gradle :turbine-server:buildDocker;
note "Building auth...";            gradle :auth-server:buildDocker;