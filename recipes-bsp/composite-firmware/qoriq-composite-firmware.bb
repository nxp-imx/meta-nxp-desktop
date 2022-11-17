DESCRIPTION = "NXP composite firmware for QorIQ platforms"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://${MACHINE}.manifest \
           file://create_composite_firmware.sh \
           file://qoriq_memory_layout.cfg \
           "

inherit deploy

ITB_IMAGE = "fsl-image-kernelitb"
DEPENDS = "u-boot-mkimage-native qoriq-cst-native qoriq-atf"
DEPENDS:ls1021atwr = "u-boot-mkimage-native qoriq-cst-native u-boot"
do_deploy[depends] += "virtual/kernel:do_deploy ${ITB_IMAGE}:do_build distrobootscr:do_deploy flex-installer:do_deploy"

do_deploy[nostamp] = "1"
do_patch[noexec] = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"

S = "${WORKDIR}"

do_deploy() {
    cd ${RECIPE_SYSROOT_NATIVE}/usr/bin/cst
    cp ${S}/*.sh ./
    cp ${S}/${MACHINE}.manifest ./
    cp ${S}/qoriq_memory_layout.cfg ./
    ./create_composite_firmware.sh -m ${MACHINE} -s ${DEPLOY_DIR_IMAGE}
}

addtask deploy before do_build after do_compile

PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "(qoriq-arm|qoriq-arm64)"
