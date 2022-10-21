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


FILES:${PN} += "/boot/"
INSANE_SKIP:${PN} += "arch already-stripped"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"


COMPATIBLE_MACHINE = "(qoriq)"
