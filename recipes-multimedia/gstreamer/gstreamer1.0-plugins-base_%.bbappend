FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:imxdpu += " \
    file://0001-add-imxvideoconvert_g2d-into-playsink.patch \
"
