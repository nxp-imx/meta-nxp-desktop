FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://0001-request-for-2-more-buffer-for-better-performance.patch \
    file://0001-v4l2videoenc-Set-encoder-output-mode-to-DMABUF_IMPOR.patch \
"