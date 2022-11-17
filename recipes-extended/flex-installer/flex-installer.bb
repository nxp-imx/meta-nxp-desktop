SUMMARY = "A flexible distro installer"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

SRC_URI = "file://flex-installer.sh"

inherit deploy

do_install[noexec] = "1"

RDEPENDS:${PN} += "bash"

do_deploy () {
    mkdir -p ${DEPLOY_DIR_IMAGE}
    install -m 644 ${WORKDIR}/flex-installer.sh ${DEPLOY_DIR_IMAGE}/
}
addtask deploy after do_install before do_build

COMPATIBLE_MACHINE = "(qoriq)"
