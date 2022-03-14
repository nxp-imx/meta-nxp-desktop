do_install () {
    install -d ${D}/usr/lib/firmware
    install -d ${D}/boot/engine-pfe-bin
    install -m 644 ${S}/NXP-Binary-EULA.txt ${D}/usr/lib/firmware
    install -m 755 ${S}/ls1012a/slow_path/*.elf ${D}/usr/lib/firmware
    install -m 755 ${S}/ls1012a/u-boot/* ${D}/boot/engine-pfe-bin
}

FILES:${PN} += "/usr/lib/firmware"
