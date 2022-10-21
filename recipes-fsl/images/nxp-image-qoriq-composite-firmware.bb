DESCRIPTION = "NXP composite firmware for QorIQ platforms"
LICENSE = "MIT"


inherit deploy

DESTARCH ?= "arm64"
DESTARCH_armv7a = "arm32"
DESTARCH_aarch64 = "arm64"

# KERNEL_ITS   ?= "${TOPDIR}/../sources/meta-qoriq/recipes-kernel/linux/linux-qoriq-all-${DESTARCH}.its"
KERNEL_ITS   ?= "${TOPDIR}/../sources/meta-qoriq/recipes-fsl/images/fsl-image-kernelitb/kernel-arm64.its"
# KERNEL_ITS   ?= "${TOPDIR}/../sources/meta-qoriq/recipes-bsp/verified-boot/files/kernel.its"
KERNEL_IMAGE ?= "${KERNEL_IMAGETYPE}"
ROOTFS_IMAGE ?= "nxp-image-poky-tiny"


DEPENDS = "u-boot-mkimage-native qoriq-cst-native qoriq-atf"
DEPENDS_ls1021atwr = "u-boot-mkimage-native qoriq-cst-native u-boot"
do_deploy[depends] += "u-boot-mkimage-native:do_populate_sysroot virtual/kernel:do_deploy ${ROOTFS_IMAGE}:do_build"

BOOT_TYPE ??= ""
BOOT_TYPE:ls1012ardb ?= "qspi"
BOOT_TYPE:ls1012afrwy ?= "qspi"
BOOT_TYPE:ls1021atwr ?= "nor sd"
BOOT_TYPE:ls1028ardb ?= "xspi sd"
BOOT_TYPE:ls1043ardb ?= "nor sd nand"
BOOT_TYPE:ls1046ardb ?= "qspi sd"
BOOT_TYPE:ls1046afrwy ?= "qspi sd"
BOOT_TYPE:ls1088a ?= "qspi sd"
BOOT_TYPE:ls2088ardb ?= "nor"
BOOT_TYPE:lx2160a ?= "xspi sd"

S = "${WORKDIR}"
do_deploy[nostamp] = "1"
do_patch[noexec] = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"


inherit composite-firmware

do_deploy() {
    cp -f ${KERNEL_ITS} kernel.its
    sed -i -e "s,\$deploydir,${DEPLOY_DIR_IMAGE}," kernel.its
    tinyrfs=$(basename ${DEPLOY_DIR_IMAGE}/${ROOTFS_IMAGE}*.cpio.gz)
    sed -i -e "s,rootfs.cpio.gz,${tinyrfs}," kernel.its
#    mkimage -f kernel.its linux_layerscape_tiny_${DESTARCH}.itb
#    install -m 644 linux_layerscape_tiny_${DESTARCH}.itb ${DEPLOY_DIR_IMAGE}
    create_composite_firmware
}


addtask deploy before do_build after do_compile


do_image_complete[depends] += "nxp-image-poky-tiny:do_image_complete"


PACKAGE_ARCH = "${MACHINE_SOCARCH}"
COMPATIBLE_MACHINE = "(qoriq-arm|qoriq-arm64)"