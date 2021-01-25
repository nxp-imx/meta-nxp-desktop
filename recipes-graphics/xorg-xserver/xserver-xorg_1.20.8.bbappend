FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0001-disable-glx-when-build-xwayland-glamor.patch \
"

EXTRA_OECONF += "--disable-glx \
"
