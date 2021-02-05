FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0001-cogl-texture-2d-support-import-dmabuf-using-egl-imag.patch \
            file://0001-fix-build-break-cased-by-viv-gbm-version-number-only.patch \
"

PACKAGECONFIG_append = " glx wayland-server egl-kms\
"
