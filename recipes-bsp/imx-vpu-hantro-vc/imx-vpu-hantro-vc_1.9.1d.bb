# Copyright 2019-2022 NXP

DESCRIPTION = "i.MX vc8000e encoder library for Kenrel 5.15"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=f9e5e25d5cd2c987e952e59552a344ac \
                    file://${BSPDIR}/sources/meta-imx/EULA.txt;md5=${FSL_EULA_FILE_MD5SUM}"

SRC_URI[md5sum] = "5856e1cc4debf9186f2110335d0458b9"
SRC_URI[sha256sum] = "ea79e2f99250c9e77012134e633c92d4022adafe36e18f42e51a2fbef949f162"

inherit fsl-eula2-unpack2

SRC_URI = "${FSL_BIN_MIRROR}/${BP}.bin;fsl-eula=true"

COMPATIBLE_MACHINE = "(mx8mp-nxp-bsp)"
