FILES:libgnome-desktop += "${datadir}/gnome/"

do_install:append() {
	rm -f ${D}${datadir}/gnome/gnome-version.xml
}

GNOME_MIRROR = "https://ftp.acc.umu.se/pub/GNOME/sources/"
