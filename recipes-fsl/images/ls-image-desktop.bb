# A desktop image with an Desktop rootfs
#
# Note that we have a tight dependency to ubuntu-base
# and that we cannot just install arbitrary Yocto packages to avoid
# rootfs pollution or destruction.
PV = "${@d.getVar('PREFERRED_VERSION_ubuntu-base', True) or '1.0'}"

require ls-image-common.inc

REQUIRED_DISTRO_FEATURES = " wayland x11"

# This must be added first as it provides the foundation for
# subsequent modifications to the rootfs
IMAGE_INSTALL += "\
	ubuntu-base \
	ubuntu-base-dev \
	ubuntu-base-dbg \
	ubuntu-base-doc \
"

IMAGE_INSTALL += "\
	systemd-gpuconfig \
	udev-rules-imx \
	xwayland \
"

##############################################################################
# NOTE: We cannot install arbitrary Yocto packages as they will
# conflict with the content of the prebuilt Desktop rootfs and pull
# in dependencies that may break the rootfs.
# Any package addition needs to be carefully evaluated with respect
# to the final image that we build.
##############################################################################


# GPU driver
IMAGE_INSTALL += " \
    wayland-protocols \
    libclc-imx libclc-imx-dev \
    libgles1-imx libgles1-imx-dev \
    libgles2-imx libgles2-imx-dev \
    libgles3-imx-dev \
    libglslc-imx \
    libopencl-imx \
    libegl-imx libegl-imx-dev \
    libgal-imx libgal-imx-dev \
    libvsc-imx \
    libgbm-imx libgbm-imx-dev \
    libvulkan-imx \
    libopenvx-imx libopenvx-imx-dev \
    libnn-imx \
    imx-gpu-viv-tools \
    apitrace \
"

IMAGE_INSTALL += " \
    ${LAYERSCAPE_DEMO_SAMPLES} \
"

#######

APTGET_EXTRA_PACKAGES += "\
       ${LAYERSCAPE_NETWORK_TOOLS} \
       iperf \
       libvirt-daemon-system \
       makedev lm-sensors \
       ntpdate patchelf \
       weston \
"

SOC_DEFAULT_IMAGE_FSTYPES:append = " wic.bmap wic.bz2 tar.gz"
SOC_DEFAULT_IMAGE_FSTYPES:remove = " tar.bz2"

IMAGE_ROOTFS_SIZE ?= "6291456"

COMPATIBLE_MACHINE = "(ls1028a)"
