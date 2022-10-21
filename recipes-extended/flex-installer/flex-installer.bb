SUMMARY = "A flexible distro installer"
LICENSE = "BSD"

S = "${WORKDIR}"
do_deploy[nostamp] = "1"
do_patch[noexec] = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"

INHIBIT_DEFAULT_DEPS = "1"
RDEPENDS:${PN} += "bash"

do_install () {
    install -d ${D}/${bindir}
    install ${TOPDIR}/../sources/meta-nxp-desktop/scripts/flex-installer ${D}/${bindir}
}

FILES_${PN} += "${bindir}"
INSANE_SKIP_${PN} += "arch already-stripped"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"

COMPATIBLE_MACHINE = "(qoriq)"
