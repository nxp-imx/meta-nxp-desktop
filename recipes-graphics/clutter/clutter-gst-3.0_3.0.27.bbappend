FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0001-cluttervideosink-try-to-import-dmabuf.patch \
            file://0001-cluttervideosink-add-YUY2-format-support.patch \
            file://0001-cluttervideosink-create-internal-buffer-pool-to-copy.patch \
            file://0001-MMFMWK-8957-cluttervideosink-import-video-crop-meta.patch \
            file://0001-clutter-video-sink-fix-totem-video-jitter-issue.patch \
"

EXTRA_OECONF += "--enable-introspection=no \
"
