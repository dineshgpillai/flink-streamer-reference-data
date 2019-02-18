FROM openshift/base-centos7


USER root
RUN yum -y install maven && yum -y clean all

COPY .s2i/bin /usr/local/s2i

LABEL io.openshift.s2i.scripts-url=image:///usr/local/s2i
