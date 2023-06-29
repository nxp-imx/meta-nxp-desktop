# NXP i.MX SOC extra configuration udev rules
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

do_install:append () {
    # LDP release, we only need install NXP specific blacklist as blacklist-nxp.conf
    rm -rf ${D}${sysconfdir}/*
    if [ -e "${WORKDIR}/blacklist.conf" ]; then
        install -d ${D}${sysconfdir}/modprobe.d
        install -m 0644 ${WORKDIR}/blacklist.conf ${D}${sysconfdir}/modprobe.d/blacklist-nxp.conf
    fi
}
