# A desktop image without an Desktop rootfs
#
# Note that we have a tight dependency to ubuntu-main
# and that we cannot just install arbitrary Yocto packages to avoid
# rootfs pollution or destruction.
PV = "${@d.getVar('PREFERRED_VERSION_ubuntu-main', True) or '1.0'}"

require ls-image-common.inc
# This must be added first as it provides the foundation for
# subsequent modifications to the rootfs
IMAGE_INSTALL += "\
	ubuntu-main \
	ubuntu-main-dev \
	ubuntu-main-dbg \
	ubuntu-main-doc \
"
