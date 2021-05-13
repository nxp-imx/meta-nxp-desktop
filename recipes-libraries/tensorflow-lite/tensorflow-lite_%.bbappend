FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI += " file://tensorflow2-lite.pc \
"

DEPENDS += " flatbuffers"

do_install_append () {
    install -d ${D}${libdir}/pkgconfig
    install -m 0644 ${WORKDIR}/tensorflow2-lite.pc ${D}${libdir}/pkgconfig/tensorflow2-lite.pc
}
