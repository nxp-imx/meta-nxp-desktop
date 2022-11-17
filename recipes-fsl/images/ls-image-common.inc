# we cannot just install arbitrary Yocto packages to avoid
# rootfs pollution or destruction.

require fsl-image-common.inc

IMAGE_PREPROCESS_COMMAND:append = " do_enable_enetc_ports; do_update_rootfs; "

DPDK_SECURE_OBJ = " \
	ovs-dpdk \
	libpkcs11 \
	pktgen-dpdk \
	secure-obj \
	secure-obj-module \
"

IMAGE_INSTALL:append = " \
	packagegroup-fsl-tools-core \
	packagegroup-fsl-tools-extended \
	${DPDK_SECURE_OBJ} \
	openssl-engines \
	ipsec-tools \
"
IMAGE_INSTALL:append:qoriq = " \
	distrobootscr \
"
IMAGE_INSTALL:remove:ls1021atwr = " \
	${DPDK_SECURE_OBJ} \
"
IMAGE_INSTALL:remove:ls1012afrwy = " \
	${DPDK_SECURE_OBJ} \
"

LAYERSCAPE_NETWORK_TOOLS = " \
	iozone3 \
	iperf3 \
	lmbench \
	netperf \
	cpufrequtils \
	mmc-utils \
	netdata \
	i2c-tools \
	usbutils \
	linuxptp \
	rt-tests \
	can-utils \
	blktrace \
	sysfsutils \
	watchdog \
	fio \
"

LAYERSCAPE_DEMO_SAMPLES = " \
	dpdk-examples \
	ipsec-demo \
"

LAYERSCAPE_DEMO_SAMPLES:remove:ls1021atwr = " \
	dpdk-examples \
"

do_enable_enetc_ports() {
	set -x

#	if [ -e ${IMAGE_ROOTFS}/usr/lib/systemd/system/NetworkManager.service ]; then
#		rm ${IMAGE_ROOTFS}/usr/lib/systemd/system/NetworkManager.service
#		rm ${IMAGE_ROOTFS}/usr/lib/systemd/system/NetworkManager-dispatcher.service
#		rm ${IMAGE_ROOTFS}/usr/lib/systemd/system/NetworkManager-wait-online.service

#		rm ${IMAGE_ROOTFS}/etc/systemd/system/multi-user.target.wants/NetworkManager.service
#		rm ${IMAGE_ROOTFS}/etc/systemd/system/network-online.target.wants/NetworkManager-wait-online.service
#	fi

	echo  >"${APTGET_CHROOT_DIR}/etc/systemd/network/dsa.network" "[Match]"
	echo >>"${APTGET_CHROOT_DIR}/etc/systemd/network/dsa.network" "Name=swp0 swp1 swp2 swp3"
	echo >>"${APTGET_CHROOT_DIR}/etc/systemd/network/dsa.network" ""
	echo >>"${APTGET_CHROOT_DIR}/etc/systemd/network/dsa.network" "[Network]"
	echo >>"${APTGET_CHROOT_DIR}/etc/systemd/network/dsa.network" "BindCarrier=eno2"
	echo >>"${APTGET_CHROOT_DIR}/etc/systemd/network/dsa.network" "DHCP=yes"

	echo  >"${APTGET_CHROOT_DIR}/etc/systemd/network/eno0.network" "[Match]"
	echo >>"${APTGET_CHROOT_DIR}/etc/systemd/network/eno0.network" "Name=eno0"
	echo >>"${APTGET_CHROOT_DIR}/etc/systemd/network/eno0.network" ""
	echo >>"${APTGET_CHROOT_DIR}/etc/systemd/network/eno0.network" "[Network]"
	echo >>"${APTGET_CHROOT_DIR}/etc/systemd/network/eno0.network" "DHCP=yes"

	echo  >"${APTGET_CHROOT_DIR}/etc/systemd/network/eno2.network" "[Match]"
	echo >>"${APTGET_CHROOT_DIR}/etc/systemd/network/eno2.network" "Name=eno2"
	echo >>"${APTGET_CHROOT_DIR}/etc/systemd/network/eno2.network" ""
	echo >>"${APTGET_CHROOT_DIR}/etc/systemd/network/eno2.network" "[Network]"
	echo >>"${APTGET_CHROOT_DIR}/etc/systemd/network/eno2.network" "LinkLocalAddressing=yes"

	set +x
}

do_update_rootfs() {
	set -x

	if [ -e ${IMAGE_ROOTFS}/usr/lib/engines-3/devcrypto.so ]; then
		if [ -e ${IMAGE_ROOTFS}/usr/lib/aarch64-linux-gnu/engines-3 ]; then
			mv ${IMAGE_ROOTFS}/usr/lib/engines-3/devcrypto.so ${IMAGE_ROOTFS}/usr/lib/aarch64-linux-gnu/engines-3/
			rm -rf ${IMAGE_ROOTFS}/usr/lib/engines-3
		fi
	fi

	set +x
}
