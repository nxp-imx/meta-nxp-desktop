FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
DEPENDS:append:imxgpu2d = " virtual/egl"



SRC_URI += " \
            file://0001-gdk-wayland-need-bind-to-gles-when-use_es-is-enable.patch \
            "
FILES:${PN}-gles = "${libdir}/libgdk-3.so* \
                    ${libdir}/libgtk-3.so*"

PROVIDES += "${PN}-gles"
PACKAGES =+ "${PN}-gles"

INSANE_SKIP:${PN}-gles += "dev-so"

RDEPENDS:${PN}-gles = ""
