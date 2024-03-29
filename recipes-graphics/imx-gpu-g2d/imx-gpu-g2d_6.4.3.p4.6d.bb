# Copyright (C) 2016 Freescale Semiconductor
# Copyright 2017-2021 NXP
# Copyright 2018 (C) O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "GPU G2D library and apps for i.MX with 2D GPU and no DPU"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=5a0bf11f745e68024f37b4724a5364fe"
DEPENDS += "libgal-imx"
PROVIDES += "virtual/libg2d"

FSLBIN_NAME     = "${PN}-${PV}-${TARGET_ARCH}"

SRC_URI = "${FSL_BIN_MIRROR}/${FSLBIN_NAME}.bin;name=${TARGET_ARCH};fsl-eula=true"
SRC_URI[aarch64.md5sum] = "f5ce5a1fc450584f41d3441664938a94"
SRC_URI[aarch64.sha256sum] = "005e60ac7bd0fa1ce3349d4b76c98519d58e1a2e80ac6e25e0105d2fb5dd5d70"

S = "${WORKDIR}/${FSLBIN_NAME}"

inherit fsl-eula-unpack

do_install () {
    install -d ${D}${libdir}
    install -d ${D}${includedir}
    cp ${S}/g2d/usr/lib/*.so* ${D}${libdir}
    cp -Pr ${S}/g2d/usr/include/* ${D}${includedir}
}

FILES:${PN} = "${libdir}/libg2d* /opt"
FILES:${PN}-dev = "${includedir}"
INSANE_SKIP:${PN} = "ldflags"

RDEPENDS:${PN} = "libgal-imx"

COMPATIBLE_MACHINE = "(imxgpu2d)"
