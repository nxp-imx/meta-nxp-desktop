# Layerscape lite image without an Desktop rootfs
#
# Note that we have a tight dependency to ubuntu-main
# and that we cannot just install arbitrary Yocto packages to avoid
# rootfs pollution or destruction.
PV = "${@d.getVar('PREFERRED_VERSION_ubuntu-base', True) or '1.0'}"

REQUIRED_DISTRO_FEATURES = ""

require ls-image-common.inc

APTGET_EXTRA_PACKAGES_MAIN = "1"

# This must be added first as it provides the foundation for
# subsequent modifications to the rootfs
IMAGE_INSTALL += "\
	ubuntu-main \
"

IMAGE_INSTALL:append = " \
	${LAYERSCAPE_NETWORK_TOOLS} \
	makedevs lmsensors-sensors \
"

SOC_DEFAULT_IMAGE_FSTYPES:append = " tar.gz ext2.gz ext2.gz.u-boot"
SOC_DEFAULT_IMAGE_FSTYPES:remove = " tar.bz2"

IMAGE_ROOTFS_SIZE ?= "1572864"
