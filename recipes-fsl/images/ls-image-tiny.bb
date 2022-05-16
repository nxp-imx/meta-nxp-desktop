# Layerscape tiny image without an Desktop rootfs
#
# Note that we have a tight dependency to fsl-image-networking
PV = "${@d.getVar('PREFERRED_VERSION_yocto-tiny', True) or '1.0'}"

require recipes-fsl/images/fsl-image-networking.bb

IMAGE_ROOTFS_SIZE ?= "524288"
