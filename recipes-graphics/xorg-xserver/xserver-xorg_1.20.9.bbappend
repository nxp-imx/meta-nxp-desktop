FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0001-disable-glx-when-build-xwayland-glamor.patch \
            file://0003-Remove-GL-library-and-dependency-from-xwayland.patch \
"

EXTRA_OECONF += "--disable-glx \
"

OPENGL_PKGCONFIGS = "glamor dri3 xshmfence"
