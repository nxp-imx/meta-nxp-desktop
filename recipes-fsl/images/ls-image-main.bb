# A desktop image without an Desktop rootfs
#
# Note that we have a tight dependency to ubuntu-main
# and that we cannot just install arbitrary Yocto packages to avoid
# rootfs pollution or destruction.
PV = "${@d.getVar('PREFERRED_VERSION_ubuntu-main', True) or '1.0'}"

REQUIRED_DISTRO_FEATURES = ""

require ls-image-common.inc

# This must be added first as it provides the foundation for
# subsequent modifications to the rootfs
IMAGE_INSTALL += "\
	ubuntu-main \
	ubuntu-main-dev \
	ubuntu-main-dbg \
	ubuntu-main-doc \
"

YOCTO_NETWORK_TOOLS = " \
	iozone3 \
	iperf3 \
	lmbench \
	netperf \
	cpufrequtils \
	makedevs \
	mmc-utils \
	netdata \
	i2c-tools \
	usbutils \
	lmsensors-sensors \
	linuxptp \
	rt-tests \
	can-utils \
	blktrace \
	sysfsutils \
	watchdog \
	fio \
	dpdk-examples \
	ovs-dpdk \
	libpkcs11 \
	pktgen-dpdk \
	secure-obj \
	secure-obj-module \
"

IMAGE_INSTALL:append = " \
	${YOCTO_NETWORK_TOOLS} \
"
