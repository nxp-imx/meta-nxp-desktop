# Copyright 2020-2022 NXP

DESCRIPTION = "i.MX Verisilicon Software ISP"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=5a0bf11f745e68024f37b4724a5364fe"

DEPENDS = "boost libdrm virtual/libg2d libtinyxml2"

SRC_URI = "${FSL_BIN_MIRROR}/${BP}.bin;fsl-eula=true"
SRC_URI[md5sum] = "7fb898bc8ab3797b275f5d5c8781b51d"
SRC_URI[sha256sum] = "d23c03c6a6a757a15a3fc44df1fb5604067ecd6c6cf2ddf5cb1c677b52c5c36a"

inherit fsl-eula-unpack cmake systemd use-imx-headers

# Build the sub-folder appshell
OECMAKE_SOURCEPATH = "${S}/appshell"

# Use make instead of ninja
OECMAKE_GENERATOR = "Unix Makefiles"

SYSTEMD_SERVICE:${PN} = "imx8-isp.service"

EXTRA_OECMAKE += " \
    -DSDKTARGETSYSROOT=${STAGING_DIR_HOST} \
    -DCMAKE_BUILD_TYPE=release \
    -DISP_VERSION=ISP8000NANO_V1802 \
    -DPLATFORM=ARM64 \
    -DAPPMODE=V4L2 \
    -DQTLESS=1 \
    -DFULL_SRC_COMPILE=1 \
    -DWITH_DRM=1 \
    -DWITH_DWE=1 \
    -DSERVER_LESS=1 \
    -DSUBDEV_V4L2=1 \
    -DENABLE_IRQ=1 \
    -DPARTITION_BUILD=0 \
    -D3A_SRC_BUILD=0 \
    -DIMX_G2D=ON \
    -Wno-dev \
"

do_install() {
    install -d ${D}/${libdir}
    install -d ${D}/${includedir}
    install -d ${D}/opt/imx8-isp/bin
    install -d ${D}/opt/imx8-isp/bin/dewarp_config

    cp -r ${WORKDIR}/build/generated/release/bin/*_test ${D}/opt/imx8-isp/bin
    #cp -r ${WORKDIR}/build/generated/release/bin/*2775* ${D}/opt/imx8-isp/bin
    cp -r ${WORKDIR}/build/generated/release/bin/*.xml ${D}/opt/imx8-isp/bin
    cp -r ${WORKDIR}/build/generated/release/bin/*.drv ${D}/opt/imx8-isp/bin
    cp -r ${WORKDIR}/${BP}/dewarp/dewarp_config/ ${D}/opt/imx8-isp/bin
    cp -r ${WORKDIR}/build/generated/release/bin/isp_media_server ${D}/opt/imx8-isp/bin
    cp -r ${WORKDIR}/build/generated/release/bin/vvext ${D}/opt/imx8-isp/bin
    cp -r ${WORKDIR}/build/generated/release/lib/*.so* ${D}/${libdir}
    cp -r ${WORKDIR}/build/generated/release/include/* ${D}/${includedir}

    cp ${WORKDIR}/${BP}/imx/run.sh ${D}/opt/imx8-isp/bin
    cp ${WORKDIR}/${BP}/imx/start_isp.sh ${D}/opt/imx8-isp/bin

    chmod +x ${D}/opt/imx8-isp/bin/run.sh
    chmod +x ${D}/opt/imx8-isp/bin/start_isp.sh

    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
      install -d ${D}${systemd_system_unitdir}
      install -m 0644 ${WORKDIR}/${BP}/imx/imx8-isp.service ${D}${systemd_system_unitdir}
    fi
}

# The build contains a mix of versioned and unversioned libraries, so
# the default packaging configuration needs some modification so that
# unversioned .so libraries go to the main package and versioned .so
# symlinks go to -dev.
FILES_SOLIBSDEV = ""
FILES_SOLIBS_VERSIONED = " \
    ${libdir}/libar1335.so \
    ${libdir}/libcppnetlib-client-connections.so \
    ${libdir}/libcppnetlib-server-parsers.so \
    ${libdir}/libcppnetlib-uri.so \
    ${libdir}/libjsoncpp.so \
    ${libdir}/libos08a20.so \
    ${libdir}/libov2775.so \
"
FILES:${PN} += "/opt ${libdir}/lib*${SOLIBSDEV}"
FILES:${PN}-dev += "${FILES_SOLIBS_VERSIONED}"

INSANE_SKIP:${PN} = "rpaths"

RDEPENDS:${PN} = "libdrm bash"

COMPATIBLE_MACHINE = "(mx8mp-nxp-bsp)"
