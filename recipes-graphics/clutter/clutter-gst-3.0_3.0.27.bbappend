FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0001-cluttervideosink-try-to-import-dmabuf.patch \
            file://0001-cluttervideosink-add-YUY2-format-support.patch \
"

EXTRA_OECONF += "--enable-introspection=no \
"
