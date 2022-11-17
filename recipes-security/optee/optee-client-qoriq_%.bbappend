# Patch to fix bach-specific command line issue
# Over-write original function do_install
#
do_install () {
        oe_runmake install

        install -d ${D}${libdir}/
        install -D -p -m0644 ${B}/export/usr/lib/libteec.so.1.0.0 ${D}${libdir}/libteec.so.1.0.0
        ln -sf libteec.so.1.0.0 ${D}${libdir}/libteec.so.1
        ln -sf libteec.so.1.0.0 ${D}${libdir}/libteec.so

        install -D -p -m0644 ${B}/export/usr/lib/libckteec.so.0.1.0 ${D}${libdir}/libckteec.so.0.1.0
        ln -sf libckteec.so.0.1.0 ${D}${libdir}/libckteec.so.0
        ln -sf libckteec.so.0.1.0 ${D}${libdir}/libckteec.so

        install -D -p -m0755 ${B}/export/usr/sbin/tee-supplicant ${D}${bindir}/tee-supplicant

        cp -a ${B}/export/usr/include ${D}${includedir}

        if [ "${VIRTUAL-RUNTIME_init_manager}" = "systemd" ]; then
                install -d ${D}${systemd_system_unitdir}/
                install -m0644 ${WORKDIR}/tee-supplicant.service ${D}${systemd_system_unitdir}/
                sed -i -e s:/etc:${sysconfdir}:g -e s:/usr/bin:${bindir}:g ${D}${systemd_system_unitdir}/tee-supplicant.service
        else
                install -d ${D}${sysconfdir}/init.d/
                install -m 0755 ${WORKDIR}/tee-supplicant.init ${D}${sysconfdir}/init.d/
        fi
}
