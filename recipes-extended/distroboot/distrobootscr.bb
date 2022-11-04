SUMMARY = "support generic distro boot"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://create_bootscr.sh \
"
S = "${WORKDIR}"
do_deploy[nostamp] = "1"
do_patch[noexec] = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"

INHIBIT_DEFAULT_DEPS = "1"
DEPENDS = "u-boot-mkimage-native"


do_install () {
    cp ${TOPDIR}/../sources/meta-nxp-desktop/recipes-bsp/composite-firmware/qoriq-composite-firmware/${MACHINE}.manifest ./
    ./create_bootscr.sh -m ${MACHINE} -d ./image/
}

do_deploy () {
    if [ -f "${D}/boot/${MACHINE}_boot.scr" ]; then
        mkdir -p ${DEPLOY_DIR_IMAGE}/boot_scr/
        install -m 644 ${D}/boot/${MACHINE}_boot.scr ${DEPLOY_DIR_IMAGE}/boot_scr/
    fi
}

addtask deploy after do_install before do_build

FILES:${PN} += "/boot/"
INSANE_SKIP:${PN} += "arch already-stripped"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"


COMPATIBLE_MACHINE = "(qoriq)"
