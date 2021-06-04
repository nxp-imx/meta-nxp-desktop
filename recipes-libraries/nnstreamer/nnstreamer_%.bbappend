FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI += "\
        file://0001-enable-compilation-cache-for-NNAPI-delegate.patch \
        "
EXTRA_OEMESON += "\
        -Dtflite2-nnapi-delegate-support=true \
        "
