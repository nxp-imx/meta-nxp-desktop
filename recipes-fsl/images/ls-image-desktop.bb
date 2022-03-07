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
    libdrm-vivante \
    imx-gpu-viv-tools \
    apitrace \
"

APTGET_NETWORK_TOOLS = " \
	iozone3 \
	iperf \
	lmbench \
	netperf \
	cpufrequtils \
	makedev \
	mmc-utils \
	netdata \
	i2c-tools \
	usbutils \
	lm-sensors \
	linuxptp \
	rt-tests \
	can-utils \
	blktrace \
	sysfsutils \
	watchdog \
	fio \
"

#######

APTGET_EXTRA_PACKAGES += "\
       ${APTGET_NETWORK_TOOLS} \
       ntpdate connman patchelf \
       weston \
"

COMPATIBLE_MACHINE = "(ls1028a)"
