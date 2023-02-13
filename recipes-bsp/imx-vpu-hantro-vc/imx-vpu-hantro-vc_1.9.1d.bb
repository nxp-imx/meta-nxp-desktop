# Copyright 2019-2022 NXP

DESCRIPTION = "i.MX vc8000e encoder library for Kenrel 5.15"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=5a0bf11f745e68024f37b4724a5364fe"

SRC_URI[md5sum] = "82a85f6172768cfc8e6e8c804d915795"
SRC_URI[sha256sum] = "6dd387fb8c5e3a0678e4e3ebf501bd302df515516256243930f6d5339ecac368"

inherit fsl-eula2-unpack2

SRC_URI = "${FSL_BIN_MIRROR}/${BP}.bin;fsl-eula=true"

COMPATIBLE_MACHINE = "(mx8mp-nxp-bsp)"
