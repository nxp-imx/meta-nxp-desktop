DESCRIPTION = "A library to retrieve i.MX GPU performance data"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=be5ff43682ed6c57dfcbeb97651c2829"

SRC_URI[aarch64-wayland.md5sum] = "cb62051f8dac5f6e4ff7b5be66213d79"
SRC_URI[aarch64-wayland.sha256sum] = "590838c2fb45598e770f5a5a7e2087cd3aaf7153c91b29c8350911c943d4bf9e"

inherit fsl-eula-unpack2 fsl-eula-graphics

FSLBIN_NAME = "${PN}-${PV}-aarch64-wayland"
SRC_URI = "${FSL_BIN_MIRROR}/${FSLBIN_NAME}.bin;name=aarch64-wayland;fsl-eula=true"

PACKAGE_ARCH = "${MACHINE_SOCARCH}"

RDEPENDS:${PN} = "libgal-imx"

# Compatible only with i.MX with GPU
COMPATIBLE_MACHINE        = "(^$)"
COMPATIBLE_MACHINE:imxgpu = "${MACHINE}"
