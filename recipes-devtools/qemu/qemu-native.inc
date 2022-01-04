inherit native

require qemu.inc

SRC_URI:append = " \
            file://0010-fix-libcap-header-issue-on-some-distro.patch \
            file://0011-cpus.c-Add-error-messages-when-qemi_cpu_kick_thread-.patch \
            "

EXTRA_OEMAKE:append = " LD='${LD}' AR='${AR}' OBJCOPY='${OBJCOPY}' LDFLAGS='${LDFLAGS}'"

LDFLAGS:append = " -fuse-ld=bfd"

do_install:append() {
     ${@bb.utils.contains('PACKAGECONFIG', 'gtk+', 'make_qemu_wrapper', '', d)}
}
