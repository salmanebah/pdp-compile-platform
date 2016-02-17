# build with : docker build -t  pdp-compile-platform-img .
#VERSION 1.0
FROM ubuntu:14.04
MAINTAINER Salmane bah, salmane.bah@u-bordeaux1.fr
RUN echo "deb http://cn.archive.ubuntu.com/ubuntu/ trusty main universe multiverse restricted" >> /etc/apt/sources.list
RUN echo "deb http://cn.archive.ubuntu.com/ubuntu/ trusty-updates main universe multiverse restricted" >> /etc/apt/sources.list
RUN apt-get update
RUN apt-get install -y g++
RUN apt-get install -y python
RUN apt-get install -y clisp
RUN apt-get install -y software-properties-common
RUN add-apt-repository ppa:webupd8team/java
RUN apt-get update 

# automatically accept oracle license
RUN echo oracle-java7-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections
# and install java 7 oracle jdk
RUN apt-get -y install oracle-java7-installer && apt-get clean
RUN update-alternatives --display java 
ENV JAVA_HOME /usr/lib/jvm/java-7-oracle

