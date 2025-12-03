#!/bin/bash
# Install Java 21
apt-get update
apt-get install -y openjdk-21-jdk

# Set JAVA_HOME
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH

# Build the project
chmod +x mvnw
./mvnw clean package -DskipTests
