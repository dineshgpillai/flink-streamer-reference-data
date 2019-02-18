FROM openshift/base-centos7



USER root
RUN wget https://repos.fedorapeople.org/repos/dchen/apache-maven/epel-apache-maven.repo -O /etc/yum.repos.d/epel-apache-maven.repo
RUN sed -i s/\$releasever/6/g /etc/yum.repos.d/epel-apache-maven.repo
RUN yum install -y java-1.8.0-openjdk-devel

RUN yum install -y apache-maven
RUN mvn -version
#RUN alternatives --config java
#RUN yum -y install maven && yum -y clean all

COPY .s2i/bin /usr/local/s2i

LABEL io.openshift.s2i.scripts-url=image:///usr/local/s2i
