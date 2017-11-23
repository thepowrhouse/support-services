#!/usr/bin/env bash

kubectl create -f config.yml

sleep 30s

kubectl create -f discovery.yml

sleep 30s

kubectl create -f edge.yml

sleep 30s

kubectl create -f auth.yml