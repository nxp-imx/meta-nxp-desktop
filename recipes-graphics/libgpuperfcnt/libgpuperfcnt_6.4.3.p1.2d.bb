DESCRIPTION = "A library to retrieve i.MX GPU performance data"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=a632fefd1c359980434f9389833cab3a"

SRC_URI[aarch64-wayland.md5sum] = "50c1f0d5de3d4eab27f867e2e5652148"
SRC_URI[aarch64-wayland.sha256sum] = "6a28829e1b06b05ebf3f6c76379b9fab1e697c36aecf479c7eb8c9b3e88ca8f8"

inherit fsl-eula-unpack2 fsl-eula-graphics

FSLBIN_NAME = "${PN}-${PV}-aarch64-wayland"
SRC_URI = "${FSL_GPU_MIRROR}/${FSLBIN_NAME}.bin;name=aarch64-wayland;fsl-eula=true"

PACKAGE_ARCH = "${MACHINE_SOCARCH}"

RDEPENDS_${PN} = "libgal-imx"

# Compatible only with i.MX with GPU
COMPATIBLE_MACHINE        = "(^$)"
COMPATIBLE_MACHINE_imxgpu = "${MACHINE}"
