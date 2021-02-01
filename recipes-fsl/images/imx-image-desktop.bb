# A desktop image with an Desktop rootfs
#
# Note that we have a tight dependency to ubuntu-base
# and that we cannot just install arbitrary Yocto packages to avoid
# rootfs pollution or destruction.
PV = "${@d.getVar('PREFERRED_VERSION_ubuntu-base', True) or '1.0'}"

IMAGE_LINGUAS = ""
IMAGE_INSTALL = ""
inherit core-image image nativeaptinstall distro_features_check
export PACKAGE_INSTALL = "${IMAGE_INSTALL}"

APTGET_CHROOT_DIR = "${IMAGE_ROOTFS}"
APTGET_SKIP_UPGRADE = "1"

ROOTFS_POSTPROCESS_COMMAND_append = "do_fix_ldconfig; do_save_graphics;  do_aptget_update; do_update_host; do_update_dns;"
IMAGE_PREPROCESS_COMMAND_append = " do_fix_connman_conflict; do_enable_bluetooth; do_enable_graphics; do_cleanup_rootfs"

REQUIRED_DISTRO_FEATURES = "wayland"

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
	xserver-xorg-xwayland \
	tensorflow-lite \
	armnn \
"

IMAGE_INSTALL += "\
	patchelf \
"

# We want to have an itb to boot from in the /boot directory to be flexible
# about U-Boot behavior
#IMAGE_INSTALL += "\
#   linux-kernelitb-norootfs-image \
#"
#####
IMAGE_FEATURES += " \
    dev-pkgs \
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
	mc htop \
\
	apt git vim \
	ethtool wget ftp iputils-ping lrzsz \
	net-tools \
	openssh-server \
	python3-future libtool autoconf pkg-config \
	bluez connman \
	python-is-python3 \
	libcairo2 libpixman-1-0 libpango-1.0-0 libpangocairo-1.0-0 \
"
APTGET_EXTRA_SOURCE_PACKAGES += "\
	iproute2 \
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
    imx-gst1.0-plugin \
    gstreamer1.0 \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-base                      \
    gstreamer1.0-plugins-base-adder                \
    gstreamer1.0-plugins-base-alsa                 \
    gstreamer1.0-plugins-base-app                  \
    gstreamer1.0-plugins-base-apps                 \
    gstreamer1.0-plugins-base-audioconvert         \
    gstreamer1.0-plugins-base-audiomixer           \
    gstreamer1.0-plugins-base-audiorate            \
    gstreamer1.0-plugins-base-audioresample        \
    gstreamer1.0-plugins-base-audiotestsrc         \
    gstreamer1.0-plugins-base-compositor           \
    gstreamer1.0-plugins-base-dbg                  \
    gstreamer1.0-plugins-base-dev                  \
    gstreamer1.0-plugins-base-doc                  \
    gstreamer1.0-plugins-base-encoding             \
    gstreamer1.0-plugins-base-gio                  \
    gstreamer1.0-plugins-base-locale-af            \
    gstreamer1.0-plugins-base-locale-az            \
    gstreamer1.0-plugins-base-locale-bg            \
    gstreamer1.0-plugins-base-locale-ca            \
    gstreamer1.0-plugins-base-locale-cs            \
    gstreamer1.0-plugins-base-locale-da            \
    gstreamer1.0-plugins-base-locale-de            \
    gstreamer1.0-plugins-base-locale-el            \
    gstreamer1.0-plugins-base-locale-en-gb         \
    gstreamer1.0-plugins-base-locale-eo            \
    gstreamer1.0-plugins-base-locale-es            \
    gstreamer1.0-plugins-base-locale-eu            \
    gstreamer1.0-plugins-base-locale-fi            \
    gstreamer1.0-plugins-base-locale-fr            \
    gstreamer1.0-plugins-base-locale-fur           \
    gstreamer1.0-plugins-base-locale-gl            \
    gstreamer1.0-plugins-base-locale-hr            \
    gstreamer1.0-plugins-base-locale-hu            \
    gstreamer1.0-plugins-base-locale-id            \
    gstreamer1.0-plugins-base-locale-it            \
    gstreamer1.0-plugins-base-locale-ja            \
    gstreamer1.0-plugins-base-locale-lt            \
    gstreamer1.0-plugins-base-locale-lv            \
    gstreamer1.0-plugins-base-locale-nb            \
    gstreamer1.0-plugins-base-locale-nl            \
    gstreamer1.0-plugins-base-locale-or            \
    gstreamer1.0-plugins-base-locale-pl            \
    gstreamer1.0-plugins-base-locale-pt-br         \
    gstreamer1.0-plugins-base-locale-ro            \
    gstreamer1.0-plugins-base-locale-ru            \
    gstreamer1.0-plugins-base-locale-sk            \
    gstreamer1.0-plugins-base-locale-sl            \
    gstreamer1.0-plugins-base-locale-sq            \
    gstreamer1.0-plugins-base-locale-sr            \
    gstreamer1.0-plugins-base-locale-sv            \
    gstreamer1.0-plugins-base-locale-tr            \
    gstreamer1.0-plugins-base-locale-uk            \
    gstreamer1.0-plugins-base-locale-vi            \
    gstreamer1.0-plugins-base-locale-zh-cn         \
    gstreamer1.0-plugins-base-meta                 \
    gstreamer1.0-plugins-base-ogg                  \
    gstreamer1.0-plugins-base-opengl               \
    gstreamer1.0-plugins-base-overlaycomposition   \
    gstreamer1.0-plugins-base-pango                \
    gstreamer1.0-plugins-base-pbtypes              \
    gstreamer1.0-plugins-base-playback             \
    gstreamer1.0-plugins-base-rawparse             \
    gstreamer1.0-plugins-base-src                  \
    gstreamer1.0-plugins-base-staticdev            \
    gstreamer1.0-plugins-base-subparse             \
    gstreamer1.0-plugins-base-tcp                  \
    gstreamer1.0-plugins-base-theora               \
    gstreamer1.0-plugins-base-typefindfunctions    \
    gstreamer1.0-plugins-base-videoconvert         \
    gstreamer1.0-plugins-base-videorate            \
    gstreamer1.0-plugins-base-videoscale           \
    gstreamer1.0-plugins-base-videotestsrc         \
    gstreamer1.0-plugins-base-volume               \
    gstreamer1.0-plugins-base-vorbis               \
    gstreamer1.0-plugins-base-ximagesink           \
    gstreamer1.0-plugins-base-xvimagesink          \
    libgstallocators-1.0                           \
    libgstapp-1.0                                  \
    libgstaudio-1.0                                \
    libgstfft-1.0                                  \
    libgstgl-1.0                                   \
    libgstpbutils-1.0                              \
    libgstriff-1.0                                 \
    libgstrtp-1.0                                  \
    libgstrtsp-1.0                                 \
    libgstsdp-1.0                                  \
    libgsttag-1.0                                  \
    libgstvideo-1.0                                \
    gstreamer1.0-plugins-good \
    gstreamer1.0-plugins-good                \
    gstreamer1.0-plugins-good-alaw           \
    gstreamer1.0-plugins-good-alpha          \
    gstreamer1.0-plugins-good-alphacolor     \
    gstreamer1.0-plugins-good-apetag         \
    gstreamer1.0-plugins-good-audiofx        \
    gstreamer1.0-plugins-good-audioparsers   \
    gstreamer1.0-plugins-good-auparse        \
    gstreamer1.0-plugins-good-autodetect     \
    gstreamer1.0-plugins-good-avi            \
    gstreamer1.0-plugins-good-cairo          \
    gstreamer1.0-plugins-good-cutter         \
    gstreamer1.0-plugins-good-dbg            \
    gstreamer1.0-plugins-good-debug          \
    gstreamer1.0-plugins-good-deinterlace    \
    gstreamer1.0-plugins-good-dev            \
    gstreamer1.0-plugins-good-dtmf           \
    gstreamer1.0-plugins-good-effectv        \
    gstreamer1.0-plugins-good-equalizer      \
    gstreamer1.0-plugins-good-flac           \
    gstreamer1.0-plugins-good-flv            \
    gstreamer1.0-plugins-good-flxdec         \
    gstreamer1.0-plugins-good-gdkpixbuf      \
    gstreamer1.0-plugins-good-goom           \
    gstreamer1.0-plugins-good-goom2k1        \
    gstreamer1.0-plugins-good-icydemux       \
    gstreamer1.0-plugins-good-id3demux       \
    gstreamer1.0-plugins-good-imagefreeze    \
    gstreamer1.0-plugins-good-interleave     \
    gstreamer1.0-plugins-good-isomp4         \
    gstreamer1.0-plugins-good-jpeg           \
    gstreamer1.0-plugins-good-lame           \
    gstreamer1.0-plugins-good-level          \
    gstreamer1.0-plugins-good-locale-af      \
    gstreamer1.0-plugins-good-locale-az      \
    gstreamer1.0-plugins-good-locale-bg      \
    gstreamer1.0-plugins-good-locale-ca      \
    gstreamer1.0-plugins-good-locale-cs      \
    gstreamer1.0-plugins-good-locale-da      \
    gstreamer1.0-plugins-good-locale-de      \
    gstreamer1.0-plugins-good-locale-el      \
    gstreamer1.0-plugins-good-locale-en-gb   \
    gstreamer1.0-plugins-good-locale-eo      \
    gstreamer1.0-plugins-good-locale-es      \
    gstreamer1.0-plugins-good-locale-eu      \
    gstreamer1.0-plugins-good-locale-fi      \
    gstreamer1.0-plugins-good-locale-fr      \
    gstreamer1.0-plugins-good-locale-fur     \
    gstreamer1.0-plugins-good-locale-gl      \
    gstreamer1.0-plugins-good-locale-hr      \
    gstreamer1.0-plugins-good-locale-hu      \
    gstreamer1.0-plugins-good-locale-id      \
    gstreamer1.0-plugins-good-locale-it      \
    gstreamer1.0-plugins-good-locale-ja      \
    gstreamer1.0-plugins-good-locale-ky      \
    gstreamer1.0-plugins-good-locale-lt      \
    gstreamer1.0-plugins-good-locale-lv      \
    gstreamer1.0-plugins-good-locale-mt      \
    gstreamer1.0-plugins-good-locale-nb      \
    gstreamer1.0-plugins-good-locale-nl      \
    gstreamer1.0-plugins-good-locale-or      \
    gstreamer1.0-plugins-good-locale-pl      \
    gstreamer1.0-plugins-good-locale-pt-br   \
    gstreamer1.0-plugins-good-locale-ro      \
    gstreamer1.0-plugins-good-locale-ru      \
    gstreamer1.0-plugins-good-locale-sk      \
    gstreamer1.0-plugins-good-locale-sl      \
    gstreamer1.0-plugins-good-locale-sq      \
    gstreamer1.0-plugins-good-locale-sr      \
    gstreamer1.0-plugins-good-locale-sv      \
    gstreamer1.0-plugins-good-locale-tr      \
    gstreamer1.0-plugins-good-locale-uk      \
    gstreamer1.0-plugins-good-locale-vi      \
    gstreamer1.0-plugins-good-locale-zh-cn   \
    gstreamer1.0-plugins-good-locale-zh-hk   \
    gstreamer1.0-plugins-good-locale-zh-tw   \
    gstreamer1.0-plugins-good-matroska       \
    gstreamer1.0-plugins-good-meta           \
    gstreamer1.0-plugins-good-mpg123         \
    gstreamer1.0-plugins-good-mulaw          \
    gstreamer1.0-plugins-good-multifile      \
    gstreamer1.0-plugins-good-multipart      \
    gstreamer1.0-plugins-good-navigationtest \
    gstreamer1.0-plugins-good-ossaudio       \
    gstreamer1.0-plugins-good-png            \
    gstreamer1.0-plugins-good-pulseaudio     \
    gstreamer1.0-plugins-good-replaygain     \
    gstreamer1.0-plugins-good-rtp            \
    gstreamer1.0-plugins-good-rtpmanager     \
    gstreamer1.0-plugins-good-rtsp           \
    gstreamer1.0-plugins-good-shapewipe      \
    gstreamer1.0-plugins-good-smpte          \
    gstreamer1.0-plugins-good-soup           \
    gstreamer1.0-plugins-good-spectrum       \
    gstreamer1.0-plugins-good-speex          \
    gstreamer1.0-plugins-good-src            \
    gstreamer1.0-plugins-good-staticdev      \
    gstreamer1.0-plugins-good-taglib         \
    gstreamer1.0-plugins-good-udp            \
    gstreamer1.0-plugins-good-video4linux2   \
    gstreamer1.0-plugins-good-videobox       \
    gstreamer1.0-plugins-good-videocrop      \
    gstreamer1.0-plugins-good-videofilter    \
    gstreamer1.0-plugins-good-videomixer     \
    gstreamer1.0-plugins-good-wavenc         \
    gstreamer1.0-plugins-good-wavparse       \
    gstreamer1.0-plugins-good-ximagesrc      \
    gstreamer1.0-plugins-good-y4menc         \
    gstreamer1.0-plugins-bad-inter                    \
    gstreamer1.0-plugins-bad-locale-ro                \
    libgsturidownloader-1.0                           \
    gstreamer1.0-plugins-bad-locale-de                \
    gstreamer1.0-plugins-bad-locale-mt                \
    gstreamer1.0-plugins-bad-locale-fi                \
    gstreamer1.0-plugins-bad-gdp                      \
    gstreamer1.0-plugins-bad-asfmux                   \
    gstreamer1.0-plugins-bad-locale-da                \
    gstreamer1.0-plugins-bad-locale-af                \
    libgstwayland-1.0                                 \
    gstreamer1.0-plugins-bad-dvb                      \
    gstreamer1.0-plugins-bad-closedcaption            \
    gstreamer1.0-plugins-bad-netsim                   \
    gstreamer1.0-plugins-bad-dashdemux                \
    gstreamer1.0-plugins-bad-staticdev                \
    gstreamer1.0-plugins-bad-legacyrawparse           \
    gstreamer1.0-plugins-bad-adpcmenc                 \
    gstreamer1.0-plugins-bad-ivfparse                 \
    gstreamer1.0-plugins-bad-meta                     \
    libgstcodecparsers-1.0                            \
    gstreamer1.0-plugins-bad-locale-uk                \
    gstreamer1.0-plugins-bad-locale-sv                \
    gstreamer1.0-plugins-bad-videoframe-audiolevel    \
    gstreamer1.0-plugins-bad-locale-el                \
    gstreamer1.0-plugins-bad-hls                      \
    gstreamer1.0-plugins-bad-geometrictransform       \
    gstreamer1.0-plugins-bad-webp                     \
    gstreamer1.0-plugins-bad-yadif                    \
    gstreamer1.0-plugins-bad-sdpelem                  \
    gstreamer1.0-plugins-bad-pnm                      \
    gstreamer1.0-plugins-bad-bz2                      \
    gstreamer1.0-plugins-bad-locale-zh-cn             \
    gstreamer1.0-plugins-bad-segmentclip              \
    gstreamer1.0-plugins-bad-faceoverlay              \
    gstreamer1.0-plugins-bad-locale-nb                \
    gstreamer1.0-plugins-bad-mpegtsdemux              \
    gstreamer1.0-plugins-bad-dtls                     \
    gstreamer1.0-plugins-bad-dbg                      \
    gstreamer1.0-plugins-bad-gaudieffects             \
    gstreamer1.0-plugins-bad-ttmlsubs                 \
    gstreamer1.0-plugins-bad-smoothstreaming          \
    gstreamer1.0-plugins-bad-accurip                  \
    libgstmpegts-1.0                                  \
    gstreamer1.0-plugins-bad-locale-it                \
    libgstsctp-1.0                                    \
    gstreamer1.0-plugins-bad-audiofxbad               \
    gstreamer1.0-plugins-bad-kms                      \
    gstreamer1.0-plugins-bad-mpegpsmux                \
    gstreamer1.0-plugins-bad-mpegpsdemux              \
    gstreamer1.0-plugins-bad-locale-vi                \
    gstreamer1.0-plugins-bad-dvbsuboverlay            \
    gstreamer1.0-plugins-bad-audiovisualizers         \
    gstreamer1.0-plugins-bad-ipcpipeline              \
    gstreamer1.0-plugins-bad-curl                     \
    gstreamer1.0-plugins-bad-locale-es                \
    gstreamer1.0-plugins-bad-audiolatency             \
    gstreamer1.0-plugins-bad-videoparsersbad          \
    gstreamer1.0-plugins-bad                          \
    gstreamer1.0-plugins-bad-locale-ky                \
    gstreamer1.0-plugins-bad-locale-nl                \
    gstreamer1.0-plugins-bad-subenc                   \
    gstreamer1.0-plugins-bad-id3tag                   \
    gstreamer1.0-plugins-bad-locale-cs                \
    gstreamer1.0-plugins-bad-locale-fur               \
    gstreamer1.0-plugins-bad-locale-ja                \
    gstreamer1.0-plugins-bad-speed                    \
    gstreamer1.0-plugins-bad-src                      \
    gstreamer1.0-plugins-bad-freeverb                 \
    gstreamer1.0-plugins-bad-ivtc                     \
    gstreamer1.0-plugins-bad-rsvg                     \
    gstreamer1.0-plugins-bad-locale-sr                \
    gstreamer1.0-plugins-bad-shm                      \
    gstreamer1.0-plugins-bad-pcapparse                \
    gstreamer1.0-plugins-bad-locale-lt                \
    gstreamer1.0-plugins-bad-jpegformat               \
    gstreamer1.0-plugins-bad-fieldanalysis            \
    gstreamer1.0-plugins-bad-locale-pl                \
    gstreamer1.0-plugins-bad-fbdevsink                \
    gstreamer1.0-plugins-bad-locale-en-gb             \
    gstreamer1.0-plugins-bad-locale-fr                \
    gstreamer1.0-plugins-bad-debugutilsbad            \
    libgstbadaudio-1.0                                \
    gstreamer1.0-plugins-bad-locale-sl                \
    gstreamer1.0-plugins-bad-locale-hu                \
    gstreamer1.0-plugins-bad-sbc                      \
    gstreamer1.0-plugins-bad-vmnc                     \
    gstreamer1.0-plugins-bad-waylandsink              \
    libgstphotography-1.0                             \
    gstreamer1.0-plugins-bad-audiobuffersplit         \
    gstreamer1.0-plugins-bad-bayer                    \
    gstreamer1.0-plugins-bad-locale-gl                \
    libgstbasecamerabinsrc-1.0                        \
    gstreamer1.0-plugins-bad-locale-pt-br             \
    libgstplayer-1.0                                  \
    gstreamer1.0-plugins-bad-y4mdec                   \
    gstreamer1.0-plugins-bad-jp2kdecimator            \
    gstreamer1.0-plugins-bad-removesilence            \
    gstreamer1.0-plugins-bad-rfbsrc                   \
    gstreamer1.0-plugins-bad-locale-ru                \
    gstreamer1.0-plugins-bad-siren                    \
    gstreamer1.0-plugins-bad-locale-az                \
    gstreamer1.0-plugins-bad-adpcmdec                 \
    gstreamer1.0-plugins-bad-mpegtsmux                \
    gstreamer1.0-plugins-bad-locale-ca                \
    gstreamer1.0-plugins-bad-audiomixmatrix           \
    gstreamer1.0-plugins-bad-coloreffects             \
    gstreamer1.0-plugins-bad-sndfile                  \
    gstreamer1.0-plugins-bad-camerabin                \
    gstreamer1.0-plugins-bad-locale-sq                \
    libgstisoff-1.0                                   \
    libgstwebrtc-1.0                                  \
    gstreamer1.0-plugins-bad-festival                 \
    libgstadaptivedemux-1.0                           \
    gstreamer1.0-plugins-bad-dvdspu                   \
    gstreamer1.0-plugins-bad-decklink                 \
    gstreamer1.0-plugins-bad-timecode                 \
    gstreamer1.0-plugins-bad-uvch264                  \
    gstreamer1.0-plugins-bad-autoconvert              \
    gstreamer1.0-plugins-bad-locale-tr                \
    gstreamer1.0-plugins-bad-locale-hr                \
    gstreamer1.0-plugins-bad-locale-sk                \
    gstreamer1.0-plugins-bad-locale-eo                \
    libgstinsertbin-1.0                               \
    gstreamer1.0-plugins-bad-videosignal              \
    gstreamer1.0-plugins-bad-bluez                    \
    gstreamer1.0-plugins-bad-aiff                     \
    gstreamer1.0-plugins-bad-interlace                \
    gstreamer1.0-plugins-bad-videofiltersbad          \
    gstreamer1.0-plugins-bad-locale-ast               \
    gstreamer1.0-plugins-bad-locale-eu                \
    gstreamer1.0-plugins-bad-locale-lv                \
    gstreamer1.0-plugins-bad-midi                     \
    gstreamer1.0-plugins-bad-rtponvif                 \
    gstreamer1.0-plugins-bad-locale-bg                \
    gstreamer1.0-plugins-bad-locale-id                \
    gstreamer1.0-plugins-bad-smooth                   \
    gstreamer1.0-plugins-bad-dev                      \
    gstreamer1.0-plugins-bad-locale-or                \
    gstreamer1.0-plugins-bad-mxf                      \
    gstreamer1.0-plugins-bad-frei0r                   \
    gstreamer1.0-plugins-bad-proxy                    \
"

# GPU driver

IMAGE_INSTALL += " \
    imx-gpu-viv \
    libdrm-vivante \
"

# Minimum support for LS2 and S32V specific elements.
IMAGE_INSTALL_append_fsl-lsch3 += "\
    mc-utils-image \
    restool \
"

# We want easy installation of the BlueBox image to the target
DEPENDS_append_fsl-lsch3 = " \
    bbdeployscripts \
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
	echo >>"${IMAGE_ROOTFS}/etc/environment" "COGL_RENDERER=egl_wayland"
	echo >>"${IMAGE_ROOTFS}/etc/environment" "COGL_DRIVER=gles2"
	echo >>"${IMAGE_ROOTFS}/etc/environment" "CLUTTER_BACKEND=wayland"
	echo >>"${IMAGE_ROOTFS}/etc/environment" "CLUTTER_DRIVER=gles2"

	# disable GL/GLX for vivante GPU temporally
	rm -f ${IMAGE_ROOTFS}/usr/lib/libGL.so*

	# disable xsession startup
	rm -f ${IMAGE_ROOTFS}/usr/share/xsessions/*

	cp -f ${IMAGE_ROOTFS}/usr/bin/Xwayland_imx ${IMAGE_ROOTFS}/usr/bin/Xwayland

	rm -f ${IMAGE_ROOTFS}/usr/lib/systemd/system/default.target
	ln graphical.target -s ${IMAGE_ROOTFS}/usr/lib/systemd/system/default.target

	rm -f ${IMAGE_ROOTFS}/etc/systemd/system/default.target
	ln /usr/lib/systemd/system/graphical.target -s ${IMAGE_ROOTFS}/etc/systemd/system/default.target

	set +x
}

do_cleanup_rootfs() {
	set -x

	# remove apt-get source list, apt-get update can download them
	rm -rf ${IMAGE_ROOTFS}/var/lib/apt/lists/*

	set +x
}

#We need to add Yocto libraries to LD path and remove conflicting libraries
fakeroot do_fix_ldconfig() {
	#Ld config mises /usr/lib path
	set -x

	# Replace libGL.so.1 to libGLESv2.so.2 for libmutter
	chroot "${APTGET_CHROOT_DIR}" /usr/bin/patchelf --replace-needed libGL.so.1 libGLESv2.so.2 /lib/aarch64-linux-gnu/libmutter-6.so.0.0.0

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
