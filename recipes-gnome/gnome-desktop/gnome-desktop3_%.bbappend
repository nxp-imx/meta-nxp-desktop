FILES_libgnome-desktop3 += "${datadir}/gnome/"

do_install_append() {
	rm -f ${D}${datadir}/gnome/gnome-version.xml
}