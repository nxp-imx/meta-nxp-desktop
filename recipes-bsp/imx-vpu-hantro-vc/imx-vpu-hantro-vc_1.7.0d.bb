# Copyright 2019-2021 NXP

DESCRIPTION = "i.MX vc8000e encoder library for Kenrel 5.15"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=d3c315c6eaa43e07d8c130dc3a04a011"

SRC_URI[md5sum] = "e4ceaa23b1f26cc0da7745cfffae28f7"
SRC_URI[sha256sum] = "327c59d16b47f5440c43686628142cee235f835bc2d46a887f3588ea51b2b466"

inherit fsl-eula2-unpack2

COMPATIBLE_MACHINE = "(imx8mpevk)"
