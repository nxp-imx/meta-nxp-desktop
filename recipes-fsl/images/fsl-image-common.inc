# A desktop image with an Desktop rootfs
#
# and that we cannot just install arbitrary Yocto packages to avoid
# rootfs pollution or destruction.

IMAGE_LINGUAS = ""
IMAGE_INSTALL = ""
inherit core-image image nativeaptinstall features_check
export PACKAGE_INSTALL = "${IMAGE_INSTALL}"

APTGET_CHROOT_DIR = "${IMAGE_ROOTFS}"
APTGET_SKIP_UPGRADE = "1"

ROOTFS_POSTPROCESS_COMMAND:append = "do_fix_ldconfig; do_save_graphics; do_aptget_update; do_update_host; do_update_dns; do_config_netplan;"
IMAGE_PREPROCESS_COMMAND:append = " do_fix_connman_conflict; do_enable_graphics; do_cleanup_rootfs; "


# Without the kernel, modules, and firmware we can't really use the Linux
IMAGE_INSTALL += "\
	kernel-devicetree \
	kernel-image \
	${MACHINE_EXTRA_RRECOMMENDS} \
"

#####
IMAGE_FEATURES += " \
    tools-sdk \
    debug-tweaks \
    tools-profile \
    package-management \
    splash \
    nfs-server \
    tools-debug \
    ssh-server-dropbear \
    tools-testapps \
    hwcodecs \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '', \
       bb.utils.contains('DISTRO_FEATURES',     'x11', 'x11-base x11-sato', \
                                                       '', d), d)} \
"

#######

APTGET_EXTRA_PACKAGES_SERVICES_DISABLED += "\
	network-manager \
"
APTGET_EXTRA_PACKAGES += "\
	console-setup locales \
	apt vim \
	ethtool wget ftp iputils-ping \
	net-tools \
	nfs-common \
	openssh-server \
	python3.9 libtool autoconf pkg-config \
	bluez \
	python-is-python3 \
	netplan.io \
"
APTGET_EXTRA_SOURCE_PACKAGES += "\
"

# Add user with password user and default shell bash
USER_SHELL_BASH = "/bin/bash"
# Password: user
USER_PASSWD_USER = "zHipsIr46vtZk"
APTGET_ADD_USERS ?= "user:${USER_PASSWD_USER}:${USER_SHELL_BASH}"

HOST_NAME = "${MACHINE_ARCH}"

fakeroot do_update_host() {
	set -x

	echo >"${APTGET_CHROOT_DIR}/etc/hostname" "${HOST_NAME}"

	echo  >"${APTGET_CHROOT_DIR}/etc/hosts" "127.0.0.1 localhost"
	echo >>"${APTGET_CHROOT_DIR}/etc/hosts" "127.0.1.1 ${HOST_NAME}"
	echo >>"${APTGET_CHROOT_DIR}/etc/hosts" ""
	echo >>"${APTGET_CHROOT_DIR}/etc/hosts" "# The following lines are desirable for IPv6 capable hosts"
	echo >>"${APTGET_CHROOT_DIR}/etc/hosts" "::1 ip6-localhost ip6-loopback"
	echo >>"${APTGET_CHROOT_DIR}/etc/hosts" "fe00::0 ip6-localnet"
	echo >>"${APTGET_CHROOT_DIR}/etc/hosts" "ff00::0 ip6-mcastprefix"
	echo >>"${APTGET_CHROOT_DIR}/etc/hosts" "ff02::1 ip6-allnodes"
	echo >>"${APTGET_CHROOT_DIR}/etc/hosts" "ff02::2 ip6-allrouters"
	echo >>"${APTGET_CHROOT_DIR}/etc/hosts" "ff02::3 ip6-allhosts"

	set +x
}

fakeroot do_update_dns() {
	set -x

	if [ ! -L "${APTGET_CHROOT_DIR}/etc/resolv.conf" ]; then
		if [ -e "${APTGET_CHROOT_DIR}/etc/resolveconf" ]; then
			mkdir -p "/run/resolveconf"
			if [ -f "${APTGET_CHROOT_DIR}/etc/resolv.conf" ]; then
				mv -f "${APTGET_CHROOT_DIR}/etc/resolv.conf" "/run/resolveconf/resolv.conf"
			fi
			ln -sf  "/run/resolveconf/resolv.conf" "${APTGET_CHROOT_DIR}/etc/resolv.conf"
		elif [ -e "${APTGET_CHROOT_DIR}/etc/dhcp/dhclient-enter-hooks.d/resolved" ]; then
			mkdir -p "/run/systemd/resolve"
			if [ -f "${APTGET_CHROOT_DIR}/etc/resolv.conf" ]; then
				mv -f "${APTGET_CHROOT_DIR}/etc/resolv.conf" "/run/systemd/resolve/resolv.conf"
			fi
			ln -sf  "/run/systemd/resolve/resolv.conf" "${APTGET_CHROOT_DIR}/etc/resolv.conf"
		else
			touch "${APTGET_CHROOT_DIR}/etc/resolv.conf"
		fi
	fi

	set +x
}

do_config_netplan() {
	set -x

	echo >>"${APTGET_CHROOT_DIR}/etc/netplan/network-manage-all.yaml" "network:"
	echo >>"${APTGET_CHROOT_DIR}/etc/netplan/network-manage-all.yaml" "  version: 2"
	echo >>"${APTGET_CHROOT_DIR}/etc/netplan/network-manage-all.yaml" "  renderer: NetworkManager"

	set +x
}

do_fix_connman_conflict() {
	set -x

	#rm ${IMAGE_ROOTFS}/etc/systemd/system/network-online.target.wants/NetworkManager-wait-online.service
	#rm ${IMAGE_ROOTFS}/etc/systemd/system/dbus-org.freedesktop.nm-dispatcher.service
	#rm ${IMAGE_ROOTFS}/etc/systemd/system/multi-user.target.wants/NetworkManager.service
	rm ${IMAGE_ROOTFS}/etc/systemd/system/dbus-org.freedesktop.resolve1.service
	rm ${IMAGE_ROOTFS}/etc/systemd/system/multi-user.target.wants/systemd-resolved.service

	set +x
}

fakeroot do_save_graphics() {
	set -x

	if [ -e "${IMAGE_ROOTFS}/usr/bin/Xwayland" ]; then
		# backup graphics components
		mv ${IMAGE_ROOTFS}/usr/bin/Xwayland ${IMAGE_ROOTFS}/usr/bin/Xwayland_imx
	fi

	set +x
}

do_enable_graphics() {
	set -x

	if [ -e "${IMAGE_ROOTFS}/usr/bin/Xwayland" ]; then
	# set egl/gles2 for gnome/mutter compositor
#	echo >>"${IMAGE_ROOTFS}/etc/environment" "COGL_RENDERER=egl_wayland"
		echo >>"${IMAGE_ROOTFS}/etc/environment" "COGL_DRIVER=gles2"
#	echo >>"${IMAGE_ROOTFS}/etc/environment" "CLUTTER_BACKEND=wayland"
		echo >>"${IMAGE_ROOTFS}/etc/environment" "CLUTTER_DRIVER=gles2"
		# Warning: Ignoring XDG_SESSION_TYPE=wayland on Gnome. Use QT_QPA_PLATFORM=waylandd
		# to run on Wayland anyway.
		echo >>"${IMAGE_ROOTFS}/etc/environment" "QT_QPA_PLATFORM=wayland"

		# disable xsession startup
		rm -f ${IMAGE_ROOTFS}/usr/share/xsessions/*

		cp -f ${IMAGE_ROOTFS}/usr/bin/Xwayland_imx ${IMAGE_ROOTFS}/usr/bin/Xwayland

		rm -f ${IMAGE_ROOTFS}/usr/lib/systemd/system/default.target
		ln graphical.target -s ${IMAGE_ROOTFS}/usr/lib/systemd/system/default.target

		rm -f ${IMAGE_ROOTFS}/etc/systemd/system/default.target
		ln /usr/lib/systemd/system/graphical.target -s ${IMAGE_ROOTFS}/etc/systemd/system/default.target
	fi

	set +x
}

do_cleanup_rootfs() {
	set -x

	# remove apt-get source list, apt-get update can download them
	rm -rf ${IMAGE_ROOTFS}/var/lib/apt/lists/*

	# imx kernel didn't use udev to load firmware, remove this rules
	# to avoid login hang when load sdma firmware
	rm -f ${IMAGE_ROOTFS}/usr/lib/udev/rules.d/50-firmware.rules

	# remove vsidaemon to disable hantro v4l2 decoder/encoder
	if [ -e ${IMAGE_ROOTFS}/usr/bin/vsidaemon ]; then
		mv ${IMAGE_ROOTFS}/usr/bin/vsidaemon ${IMAGE_ROOTFS}/usr/bin/vsidaemon.bak
	fi

	set +x
}

#We need to add Yocto libraries to LD path and remove conflicting libraries
fakeroot do_fix_ldconfig() {
	#Ld config mises /usr/lib path
	set -x

	echo >>"${APTGET_CHROOT_DIR}/etc/ld.so.conf.d/01-yocto.conf" "/usr/lib"
#	chroot "${APTGET_CHROOT_DIR}" /sbin/ldconfig
#    rm ${IMAGE_ROOTFS}/usr/lib/libgudev*
#    rm ${IMAGE_ROOTFS}/usr/lib/libgdk*
#    rm -rf ${IMAGE_ROOTFS}/usr/lib/gdk-pixbuf*
#    rm ${IMAGE_ROOTFS}/usr/lib/libcairo*
#    rm ${IMAGE_ROOTFS}/usr/lib/libpango*
#    rm ${IMAGE_ROOTFS}/usr/lib/libpixman*
#    rm ${IMAGE_ROOTFS}/usr/lib/libpng*
#    rm ${IMAGE_ROOTFS}/usr/lib/libfontconfig*
#    rm ${IMAGE_ROOTFS}/etc/fonts/fonts.conf

	set +x
}

IMAGE_ROOTFS_SIZE ?= "8192"
IMAGE_ROOTFS_EXTRA_SPACE:append = "${@bb.utils.contains("DISTRO_FEATURES", "systemd", " + 4096", "" ,d)}"

COMPATIBLE_MACHINE ="(.*ubuntu)"

python do_rootfs:prepend() {
    d.setVar('IMAGE_LOG_CHECK_EXCLUDES', 'Failed')
}
