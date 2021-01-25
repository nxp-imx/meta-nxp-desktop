FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0001-disable-glx-when-build-xwayland-glamor.patch \
            file://0002-prefer-to-use-GLES2-for-glamor-EGL-config.patch \
"

EXTRA_OECONF += "--disable-glx \
"
