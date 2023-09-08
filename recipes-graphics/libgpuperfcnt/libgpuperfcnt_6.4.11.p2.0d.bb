DESCRIPTION = "A library to retrieve i.MX GPU performance data"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=db4762b09b6bda63da103963e6e081de"

SRC_URI[aarch64-wayland.md5sum] = "1871a4daf953142664b9d419807e27ef"
SRC_URI[aarch64-wayland.sha256sum] = "fd699b83d211846063c174ad227bf272f72997a42df237529d873971cbed59d7"

inherit fsl-eula-unpack2 fsl-eula-graphics

FSLBIN_NAME = "${PN}-${PV}-aarch64-wayland"
SRC_URI = "${FSL_BIN_MIRROR}/${FSLBIN_NAME}.bin;name=aarch64-wayland;fsl-eula=true"

PACKAGE_ARCH = "${MACHINE_SOCARCH}"

RDEPENDS:${PN} = "libgal-imx"

# Compatible only with i.MX with GPU
COMPATIBLE_MACHINE        = "(^$)"
COMPATIBLE_MACHINE:imxgpu = "${MACHINE}"
