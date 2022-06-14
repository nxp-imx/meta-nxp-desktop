SUMMARY = "Take photos and videos with your webcam, with fun graphical effects"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=a17cb0a873d252440acfdf9b3d0e7fbf"

def gnome_verdir(v):
    return oe.utils.trim_version(v, 2)

DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base libcanberra gtk4 clutter-1.0 clutter-gst-3.0 clutter-gtk-1.0 vala-native gnome-desktop libxml2-native gdk-pixbuf-native itstool-native"

PR = "r0"

GNOMEBASEBUILDCLASS = "meson"

inherit gnomebase pkgconfig gobject-introspection vala features_check

REQUIRED_DISTRO_FEATURES = "opengl x11"

GNOME_COMPRESS_TYPE = "xz"
GNOME_MIRROR = "https://ftp.acc.umu.se/pub/GNOME/sources/"
SRC_URI = "${GNOME_MIRROR}/${BPN}/${@gnome_verdir("${PV}")}/${BPN}-${PV}.tar.${GNOME_COMPRESS_TYPE};name=archive \
           file://0001-change-encoding-profile-hide-video-recording-timesta.patch \
           file://0002-don-t-build-help-folder-to-avoid-build-break.patch \
           file://0001-cheese-camera-support-max-framerate-to-60.patch \
           file://0002-disable-viewfinder-sink-a-v-sync-to-get-better-perfo.patch \
           file://0001-disable-global-preset-usage-for-encoder-auto-plugin.patch \
           file://0001-cheese-limitation-the-max-resolution-to-3840x2160.patch \
"

SRC_URI:append:imxdpu += " \
           file://0001-fixate-caps-to-NV12-for-8qm-8qm-encoder-only-support.patch \
"

FILES:${PN} += "${datadir}/dbus-1 ${datadir}/metainfo"

SRC_URI[archive.md5sum] = "f847793aaf08fb349f5d182e76d4c83d"
SRC_URI[archive.sha256sum] = "522960ca28730ff66439070b04418c9e0a4bb55560d75bfe08b218da2c67e547"

EXTRA_OEMESON += "-Dintrospection=false -Dgtk_doc=false -Dman=false"

do_install:append() {
    rm -f ${D}${datadir}/icons/hicolor/scalable/apps/org.gnome.Cheese.svg
    rm -f ${D}${datadir}/icons/hicolor/symbolic/apps/org.gnome.Cheese-symbolic.svg
}
