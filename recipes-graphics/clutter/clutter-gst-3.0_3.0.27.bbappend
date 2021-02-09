FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0001-cluttervideosink-try-to-import-dmabuf.patch \
"

EXTRA_OECONF += "--enable-introspection=no \
"
