# Copyright 2019-2022 NXP

DESCRIPTION = "i.MX vc8000e encoder library for Kenrel 5.15"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=5a0bf11f745e68024f37b4724a5364fe"

SRC_URI[md5sum] = "4bda81ac769d169d76cce5dffd73b1fc"
SRC_URI[sha256sum] = "aa4392a57c87d9c1af067d65b509c05d8438efe848bf874a09bf32d999974058"

inherit fsl-eula2-unpack2

SRC_URI = "${FSL_BIN_MIRROR}/${BP}.bin;fsl-eula=true"

COMPATIBLE_MACHINE = "(mx8mp-nxp-bsp)"
