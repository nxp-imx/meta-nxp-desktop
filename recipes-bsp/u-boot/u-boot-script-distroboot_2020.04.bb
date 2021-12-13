DESCRIPTION = "Boot script for launching images with U-Boot distro boot"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

INHIBIT_DEFAULT_DEPS = "1"
DEPENDS = "u-boot-mkimage-native"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI_append_ls1021aiot = "file://ls1021aiot_boot.cmd \
                             file://ls1021aiot_baremetal_boot.cmd"
SRC_URI_append_ls1021atsn = "file://ls1021atsn_boot.cmd"
SRC_URI_append_ls1021atwr = "file://ls1021atwr_boot.cmd"
SRC_URI_append_ls1028ardb = "file://ls1028ardb_boot.cmd \
                             file://ls1028ardb_baremetal_boot.cmd"
SRC_URI_append_ls1043ardb = "file://ls1043ardb_boot.cmd \
                             file://ls1043ardb_baremetal_boot.cmd"
SRC_URI_append_ls1046ardb = "file://ls1046ardb_boot.cmd \
                             file://ls1046ardb_baremetal_boot.cmd"
SRC_URI_append_ls1046afrwy = "file://ls1046afrwy_boot.cmd"
SRC_URI_append_lx2160ardb = "file://lx2160ardb_boot.cmd \
                             file://lx2160ardb_baremetal_boot.cmd"
SRC_URI_append_lx2160ardb-rev2 = "file://lx2160ardb-rev2_boot.cmd \
                             file://lx2160ardb-rev2_baremetal_boot.cmd"

inherit deploy

B = "${WORKDIR}"

do_configure[noexec] = "1"

DISTRO_BOOT_CMD_FILE ?= "${@bb.utils.contains('DISTRO_FEATURES', 'baremetal',  \
    '${MACHINE}_baremetal_boot.cmd', '${MACHINE}_boot.cmd', d)}"
DISTRO_BOOT_SCR_FILE ?= "${MACHINE}_boot.scr"

do_compile() {
    mkimage -A arm64 -T script -C none -n "Distro boot script" -d ${B}/${DISTRO_BOOT_CMD_FILE} ${B}/${DISTRO_BOOT_SCR_FILE}
}

do_install() {
    install -Dm 0644 ${B}/${DISTRO_BOOT_SCR_FILE} ${D}/boot/${DISTRO_BOOT_SCR_FILE}
}

do_deploy() {
    install -Dm 0644 ${D}/boot/${DISTRO_BOOT_SCR_FILE} ${DEPLOYDIR}/${DISTRO_BOOT_SCR_FILE}
}

addtask deploy after do_install before do_build

PROVIDES += "u-boot-script-distroboot"

PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "(qoriq)"

FILES_${PN} = "/boot"
