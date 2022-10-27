SUMMARY = "support generic distro boot"
LICENSE = "MIT"

S = "${WORKDIR}"
do_deploy[nostamp] = "1"
do_patch[noexec] = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"

INHIBIT_DEFAULT_DEPS = "1"
DEPENDS = "u-boot-mkimage-native"

do_install () {
    if [ -n "${distro_boot_script}" ]; then
	mkdir -p ${D}/boot
	echo "${distro_boot_script}" > distroscr.tmp
	mkimage -A arm64 -O linux -T script -C none -a 0 -e 0 -n "boot.scr" \
		-d distroscr.tmp ${D}/boot/${MACHINE}_boot.scr
    fi
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
