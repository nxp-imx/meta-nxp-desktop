# we cannot just install arbitrary Yocto packages to avoid
# rootfs pollution or destruction.

require fsl-image-common.inc

IMAGE_PREPROCESS_COMMAND:append = " do_enable_enetc_ports; "

IMAGE_INSTALL:append = " \
	packagegroup-fsl-tools-core \
	ipsec-tools \
	ipsec-demo \
"

IMAGE_INSTALL:append:ls1012a = " \
	ceetm \
	dce \
	dpdk \
	dpdk-module \
	dpdk-tools \
	keyctl-caam \
	optee-os-qoriq \
	optee-client-qoriq \
	optee-test-qoriq \
	spc \
	tsntool \
"

IMAGE_INSTALL:append:ls1046a = "\
	kernel-module-nxp89xx \
	linux-firmware-nxp89xx \
	nxp-wlan-sdk \
"

do_enable_enetc_ports() {
	set -x

	if [ -e ${IMAGE_ROOTFS}/usr/lib/systemd/system/connman.service ]; then
		rm ${IMAGE_ROOTFS}/usr/lib/systemd/system/connman.service
		rm ${IMAGE_ROOTFS}/usr/lib/systemd/system/connman-wait-online.service

		rm ${IMAGE_ROOTFS}/etc/systemd/system/multi-user.target.wants/connman.service
		rm ${IMAGE_ROOTFS}/etc/systemd/system/network-online.target.wants/connman-wait-online.service
	fi

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
