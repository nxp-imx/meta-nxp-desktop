DESCRIPTION = "A library to retrieve i.MX GPU performance data"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=5a0bf11f745e68024f37b4724a5364fe"

SRC_URI[aarch64-wayland.md5sum] = "ed3d3624580a3cdfe009ed3ebf3aae37"
SRC_URI[aarch64-wayland.sha256sum] = "9ec375d4669849a07c00c6e045b7f67a9b614fbee04729aca2c7240f72b53f1e"

inherit fsl-eula-unpack2 fsl-eula-graphics

FSLBIN_NAME = "${PN}-${PV}-aarch64-wayland"
SRC_URI = "${FSL_BIN_MIRROR}/${FSLBIN_NAME}.bin;name=aarch64-wayland;fsl-eula=true"

PACKAGE_ARCH = "${MACHINE_SOCARCH}"

RDEPENDS:${PN} = "libgal-imx"

# Compatible only with i.MX with GPU
COMPATIBLE_MACHINE        = "(^$)"
COMPATIBLE_MACHINE:imxgpu = "${MACHINE}"
