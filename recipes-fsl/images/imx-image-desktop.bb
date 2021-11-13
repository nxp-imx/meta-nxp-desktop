# A desktop image with an Desktop rootfs
#
# Note that we have a tight dependency to ubuntu-base
# and that we cannot just install arbitrary Yocto packages to avoid
# rootfs pollution or destruction.
PV = "${@d.getVar('PREFERRED_VERSION_ubuntu-base', True) or '1.0'}"

IMAGE_LINGUAS = ""
IMAGE_INSTALL = ""
inherit core-image image nativeaptinstall features_check
export PACKAGE_INSTALL = "${IMAGE_INSTALL}"

APTGET_CHROOT_DIR = "${IMAGE_ROOTFS}"
APTGET_SKIP_UPGRADE = "1"

ROOTFS_POSTPROCESS_COMMAND_append = "do_fix_ldconfig; do_save_graphics; do_save_cheese;  do_aptget_update; do_update_host; do_update_dns;"
IMAGE_PREPROCESS_COMMAND_append = " do_fix_connman_conflict; do_enable_graphics; do_enable_cheese; do_cleanup_rootfs"

REQUIRED_DISTRO_FEATURES = "wayland"

ML_NNSTREAMER_PKGS = " \
    nnstreamer \
    nnstreamer-tensorflow-lite \
    nnstreamer-python3 \
    nnstreamer-protobuf \
    nnshark \
"

# This must be added first as it provides the foundation for
# subsequent modifications to the rootfs
IMAGE_INSTALL += "\
	ubuntu-base \
	ubuntu-base-dev \
	ubuntu-base-dbg \
	ubuntu-base-doc \
"

# Without the kernel, modules, and firmware we can't really use the Linux
IMAGE_INSTALL += "\
	kernel-devicetree \
	kernel-image \
	${MACHINE_EXTRA_RRECOMMENDS} \
"

IMAGE_INSTALL += "\
	perf \
	firmwared \
	systemd-gpuconfig \
	alsa-state \
	libcogl \
	clutter-gst-3.0 \
	cheese \
	xserver-xorg-xwayland \
	chromium-ozone-wayland \
	tensorflow-lite \
	${ML_NNSTREAMER_PKGS} \
	armnn \
"
# We want to have an itb to boot from in the /boot directory to be flexible
# about U-Boot behavior
#IMAGE_INSTALL += "\
#   linux-kernelitb-norootfs-image \
#"
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
	patchelf \
	apt vim \
	ethtool wget ftp iputils-ping lrzsz \
	net-tools \
	ntpdate \
	nfs-common \
	openssh-server \
	python3.9 libtool autoconf pkg-config \
	bluez connman \
	python-is-python3 \
	libcairo2 libpixman-1-0 libpango-1.0-0 libpangocairo-1.0-0 \
"
APTGET_EXTRA_SOURCE_PACKAGES += "\
"

# Add user with password user and default shell bash
USER_SHELL_BASH = "/bin/bash"
# Password: user
USER_PASSWD_USER = "zHipsIr46vtZk"
APTGET_ADD_USERS ?= "user:${USER_PASSWD_USER}:${USER_SHELL_BASH}"

HOST_NAME = "${MACHINE_ARCH}"

##############################################################################
# NOTE: We cannot install arbitrary Yocto packages as they will
# conflict with the content of the prebuilt Desktop rootfs and pull
# in dependencies that may break the rootfs.
# Any package addition needs to be carefully evaluated with respect
# to the final image that we build.
##############################################################################

IMAGE_INSTALL += " \
    packagegroup-fsl-gstreamer1.0 \
    packagegroup-fsl-gstreamer1.0-full \
"

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
    libgpuperfcnt \
    ${@bb.utils.contains('COMPATIBLE_MACHINE', 'imxgpu2d', 'imx-gpu-g2d imx-g2d-samples', '', d)} \
    ${@bb.utils.contains('COMPATIBLE_MACHINE', 'imxdpu', 'imx-dpu-g2d', '', d)} \
    apitrace \
    gputop \
"
# isp
IMAGE_INSTALL_append_mx8mp = " \
    isp-imx \
    basler-camera \
    kernel-module-isp-vvcam \
"

IMAGE_INSTALL_remove_mx8mm = " \
    libgles3-imx-dev \
    libclc-imx libclc-imx-dev \
    libopencl-imx \
    libvulkan-imx \
    libopenvx-imx libopenvx-imx-dev \
    libnn-imx \
    tensorflow-lite \
    ${ML_NNSTREAMER_PKGS} \
    armnn \
"

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

	# backup graphics components
	mv ${IMAGE_ROOTFS}/usr/bin/Xwayland ${IMAGE_ROOTFS}/usr/bin/Xwayland_imx

	set +x
}

do_enable_graphics() {
	set -x

	# set egl/gles2 for gnome/mutter compositor
#	echo >>"${IMAGE_ROOTFS}/etc/environment" "LD_PRELOAD=/usr/lib/libGLESv2.so.2"
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

	set +x
}

fakeroot do_save_cheese() {
	set -x

	# backup cheese exe
	mv ${IMAGE_ROOTFS}/usr/bin/cheese ${IMAGE_ROOTFS}/usr/bin/cheese_imx

	set +x
}

fakeroot do_enable_cheese() {
	set -x

	cp -f ${IMAGE_ROOTFS}/usr/bin/cheese_imx ${IMAGE_ROOTFS}/usr/bin/cheese

	set +x
}

do_cleanup_rootfs() {
	set -x

	# remove apt-get source list, apt-get update can download them
	rm -rf ${IMAGE_ROOTFS}/var/lib/apt/lists/*

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


fakeroot do_enable_bluetooth() {
    set -x
    
    echo >> "${APTGET_CHROOT_DIR}/lib/systemd/system/hciattach.service" "[Unit] \n
Description=Configure Bluetooth Modems connected by UART \n
Before=bluetooth.service \n
After=dev-serial1.device \n
\n
[Service] \n
Type=forking \n
ExecStart=-/usr/bin/hciattach /dev/ttymxc0 qualcomm -t120 115200 flow \n
 \n
[Install] \n
WantedBy=multi-user.target \n
" 

    rm -f ${IMAGE_ROOTFS}/etc/systemd/system/multi-user.target.wants/hciattach.service
    ln -s /lib/systemd/system/hciattach.service ${IMAGE_ROOTFS}/etc/systemd/system/multi-user.target.wants/hciattach.service
    
    set +x
}

IMAGE_ROOTFS_SIZE ?= "8192"
IMAGE_ROOTFS_EXTRA_SPACE_append = "${@bb.utils.contains("DISTRO_FEATURES", "systemd", " + 4096", "" ,d)}"

COMPATIBLE_MACHINE ="(.*ubuntu)"

python do_rootfs_prepend() {
    d.setVar('IMAGE_LOG_CHECK_EXCLUDES', 'Failed')
}
