FILES:${PN}-gles = "${libdir}/libgdk-3.so* \
                    ${libdir}/libgtk-3.so*"

PROVIDES += "${PN}-gles"
PACKAGES =+ "${PN}-gles"

INSANE_SKIP:${PN}-gles += "dev-so"

RDEPENDS:${PN}-gles = ""
