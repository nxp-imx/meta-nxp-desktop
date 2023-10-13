FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://0001-request-for-2-more-buffer-for-better-performance.patch \
    file://0001-v4l2videoenc-Set-encoder-output-mode-to-DMABUF_IMPOR.patch \
    file://0001-LF-9558-gtkgstbasewidget-default-set-ignore-alpha-to.patch \
    file://0001-LF-10264-v4l2-drop-frame-for-frames-that-cannot-be-d.patch \
"

PACKAGECONFIG:append = " gtk"