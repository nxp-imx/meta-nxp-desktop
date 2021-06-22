DESCRIPTION = "A library to retrieve i.MX GPU performance data"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=b3b0bab6ff55420d043cf1652c52d788"

SRC_URI[aarch64-wayland.md5sum] = "496ee4a54b5c16f974db456fd9b9feb3"
SRC_URI[aarch64-wayland.sha256sum] = "dc89b58fcceda12d70aaea6e2b8317aefa92b239b884fd5ada0eab353b11d889"

inherit fsl-eula-unpack2 fsl-eula-graphics

FSLBIN_NAME = "${PN}-${PV}-aarch64-wayland"
SRC_URI = "${FSL_GPU_MIRROR}/${FSLBIN_NAME}.bin;name=aarch64-wayland;fsl-eula=true"

PACKAGE_ARCH = "${MACHINE_SOCARCH}"

RDEPENDS_${PN} = "libgal-imx"

# Compatible only with i.MX with GPU
COMPATIBLE_MACHINE        = "(^$)"
COMPATIBLE_MACHINE_imxgpu = "${MACHINE}"
