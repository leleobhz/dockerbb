FROM ubuntu:20.04

LABEL maintainer="Leonardo Amaral <desenvolvimento@zenithtecnologia.com.br>"

ENV DEBIAN_FRONTEND=noninteractive
ENV GAS_DBD_FILE=/usr/local/etc/warsaw/gas.dbd

ADD https://cloud.gastecnologia.com.br/bb/downloads/ws/warsaw_setup64.deb /w.deb

RUN sed -i 's/archive.ubuntu.com/br.archive.ubuntu.com/' /etc/apt/sources.list \
    && apt update \
    && apt -y install bash \
    && dpkg -i /w.deb \
    ; echo -e '#!/bin/sh\n\nexit 0' > /var/lib/dpkg/info/warsaw.preinst \
    && echo -e '#!/bin/sh\n\nexit 0' > /var/lib/dpkg/info/warsaw.postinst \
    && echo -e '#!/bin/sh\n\nexit 0' > /var/lib/dpkg/info/warsaw.prerm \
    && echo -e '#!/bin/sh\n\nexit 0' > /var/lib/dpkg/info/warsaw.postrm \
    && apt -fy install \
    && apt -y autoclean \
    && apt -u clean \
    && rm -rf /var/lib/apt/lists/* /var/cache/apt/archives/*.deb

COPY init.sh /init.sh

ENTRYPOINT ["/init.sh"]
