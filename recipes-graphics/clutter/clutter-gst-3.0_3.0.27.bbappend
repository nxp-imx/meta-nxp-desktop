FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0001-cluttervideosink-try-to-import-dmabuf.patch \
            file://0001-cluttervideosink-add-YUY2-format-support.patch \
            file://0001-cluttervideosink-create-internal-buffer-pool-to-copy.patch \
"

EXTRA_OECONF += "--enable-introspection=no \
"
