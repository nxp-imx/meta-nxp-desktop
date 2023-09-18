# Copyright 2019-2022 NXP

DESCRIPTION = "i.MX vc8000e encoder library for Kenrel 5.15"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=db4762b09b6bda63da103963e6e081de"

SRC_URI[md5sum] = "9037a6b9eeb3c8d0fc4afeb29740bee3"
SRC_URI[sha256sum] = "908cf08ffe741c07e2aa0a8bfd001117e4b9309c1c5158c92659150dd8bab3f1"

inherit fsl-eula2-unpack2

SRC_URI = "${FSL_BIN_MIRROR}/${BP}.bin;fsl-eula=true"

COMPATIBLE_MACHINE = "(mx8mp-nxp-bsp)"
