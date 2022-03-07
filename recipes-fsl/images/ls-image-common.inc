# we cannot just install arbitrary Yocto packages to avoid
# rootfs pollution or destruction.

require fsl-image-common.inc


IMAGE_INSTALL:append = " \
	packagegroup-fsl-tools-core \
"

IMAGE_INSTALL:append:ls1012a = " \
	dpdk \
"
