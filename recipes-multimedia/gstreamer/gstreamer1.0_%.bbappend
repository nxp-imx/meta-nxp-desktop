FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://0001-don-t-remove-v4l2-plugin-it-s-the-only-decoder-on-8q.patch \
"
