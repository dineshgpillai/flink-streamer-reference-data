FROM registry.redhat.io/redhat-openjdk-18/openjdk18-openshift

COPY .s2i/bin /usr/local/s2i
LABEL io.openshift.s2i.scripts-url=image:///usr/local/s2i

RUN chown -R 1001:0 /opt/apt-root
USER 1001

CMD["echo", "done builder image"]
