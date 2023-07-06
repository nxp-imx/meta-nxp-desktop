FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
FILES:libgnome-desktop += "${datadir}/gnome/"

SRC_URI += " \
    file://0001-meson.build-Disable-libseccomp-for-all-archs.patch \
"

do_install:append() {
	rm -f ${D}${datadir}/gnome/gnome-version.xml
}

GNOME_MIRROR = "https://ftp.acc.umu.se/pub/GNOME/sources/"
