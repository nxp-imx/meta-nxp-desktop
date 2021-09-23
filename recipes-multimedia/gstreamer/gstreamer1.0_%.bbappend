FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_mx8qm += " \
    file://0001-don-t-remove-v4l2-plugin-it-s-the-only-decoder-on-8q.patch \
"