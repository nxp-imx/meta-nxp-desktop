FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://0001-request-for-2-more-buffer-for-better-performance.patch \
    file://0001-v4l2videoenc-Set-encoder-output-mode-to-DMABUF_IMPOR.patch \
    file://0002-v4l2allocator-allow-to-import-single-dmabuf-with-mul.patch \
    file://0001-fix-gtkglsink-show-wrong-color-on-ubuntu.patch \
"

PACKAGECONFIG:append = " gtk"