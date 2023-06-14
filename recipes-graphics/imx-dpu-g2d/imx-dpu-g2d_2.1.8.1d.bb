# Copyright (C) 2016 Freescale Semiconductor
# Copyright 2017-2021,2023 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "GPU G2D library and apps for i.MX with 2D GPU and DPU"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=63a38e9f392d8813d6f1f4d0d6fbe657"
DEPENDS = "libgal-imx libdrm"
PROVIDES += "virtual/libg2d"

SRC_URI = "${FSL_BIN_MIRROR}/${BPN}-${PV}.bin;fsl-eula=true"
SRC_URI[md5sum] = "baa04f43336f5324acb857c48470dae9"
SRC_URI[sha256sum] = "0eadd20cca38bc88b9056e1387e83bab5ab05ad010301f62610296f11b163fb0"

inherit fsl-eula-unpack

do_install () {
    install -d ${D}${libdir}
    install -d ${D}${includedir}
    cp -r ${S}/g2d/usr/lib/*.so* ${D}${libdir}
    cp -Pr ${S}/g2d/usr/include/* ${D}${includedir}
}

FILES:${PN} = "${libdir}/libg2d* /opt"
FILES:${PN}-dev = "${libdir}/libg2d${SOLIBSDEV} ${includedir}"
INSANE_SKIP:${PN} += "ldflags"

RDEPENDS:${PN} = "libgal-imx libdrm libopencl-imx"

# This is required to provide support for VPU Amphion HEVC tile format
# From NXP [MGS-5547] (commit e175d6b4f78deab24d319b852998bef55cdecc99):
# VPU Amphion HEVC tile support was added using OpenCL, so add a dependency on libopencl-imx.
RDEPENDS:${PN} += "libopencl-imx"

COMPATIBLE_MACHINE = "(imxdpu)"
