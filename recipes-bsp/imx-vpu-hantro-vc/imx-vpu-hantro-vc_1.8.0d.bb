# Copyright 2019-2022 NXP

DESCRIPTION = "i.MX vc8000e encoder library for Kenrel 5.15"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=d3c315c6eaa43e07d8c130dc3a04a011"

SRC_URI[md5sum] = "0233ccfdef5aba70cd977bc2ccd1980a"
SRC_URI[sha256sum] = "3b29a9b0199106ee4cad91e8fca44ce1d6c64c395bf0841c7cef53f87a5f15af"

inherit fsl-eula2-unpack2

SRC_URI = "${FSL_BIN_MIRROR}/${BP}.bin;fsl-eula=true"

COMPATIBLE_MACHINE = "(mx8mp-nxp-bsp)"
