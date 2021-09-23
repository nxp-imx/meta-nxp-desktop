FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_mx8qm += " \
    file://0001-add-imxvideoconvert_g2d-into-playsink.patch \
"