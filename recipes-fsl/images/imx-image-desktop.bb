# A desktop image with an Desktop rootfs
#
# Note that we have a tight dependency to ubuntu-base
# and that we cannot just install arbitrary Yocto packages to avoid
# rootfs pollution or destruction.
PV = "${@d.getVar('PREFERRED_VERSION_ubuntu-base', True) or '1.0'}"

require fsl-image-common.inc

ROOTFS_POSTPROCESS_COMMAND:append = "do_save_cheese; do_rm_opencv_test_and_sample;"
IMAGE_PREPROCESS_COMMAND:append = "do_enable_cheese;"

REQUIRED_DISTRO_FEATURES = "wayland"

ML_NNSTREAMER_PKGS = " \
    nnstreamer \
    nnstreamer-tensorflow-lite \
    nnstreamer-python3 \
    nnstreamer-protobuf \
"

# This must be added first as it provides the foundation for
# subsequent modifications to the rootfs
IMAGE_INSTALL += "\
	ubuntu-base \
	ubuntu-base-dev \
	ubuntu-base-dbg \
	ubuntu-base-doc \
"

IMAGE_INSTALL += "\
	firmwared \
	systemd-gpuconfig \
	alsa-state \
	libcogl \
	clutter-gst-3.0 \
	cheese \
	xwayland \
	chromium-ozone-wayland \
	gtk+3-gles \
	tensorflow-lite \
	tensorflow-lite-vx-delegate \
	${ML_NNSTREAMER_PKGS} \
	opencv \
"
APTGET_EXTRA_PACKAGES += "\
	ntpdate patchelf \
	libcairo2 libpixman-1-0 libpango-1.0-0 libpangocairo-1.0-0 \
	squashfs-tools golang-github-snapcore-snapd-dev golang-github-ubuntu-core-snappy-dev \
	snap-confine snapd-xdg-open snapd ubuntu-core-launcher ubuntu-core-snapd-units ubuntu-snappy-cli ubuntu-snappy \
"

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
G2D_SAMPLES                 = ""
G2D_SAMPLES:imxgpu2d        = "imx-g2d-samples"
G2D_SAMPLES:imxdpu          = "imx-g2d-samples"

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
    ${G2D_SAMPLES} \
    apitrace \
    gputop \
"
# isp
IMAGE_INSTALL:append:mx8mp-nxp-bsp = " \
    isp-imx \
    basler-camera \
    kernel-module-isp-vvcam \
"

IMAGE_INSTALL:remove:mx8mm-nxp-bsp = " \
    libgles3-imx-dev \
    libclc-imx libclc-imx-dev \
    libopencl-imx \
    libvulkan-imx \
    libopenvx-imx libopenvx-imx-dev \
    libnn-imx \
    tensorflow-lite \
    tensorflow-lite-vx-delegate \
    ${ML_NNSTREAMER_PKGS} \
"

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

do_rm_opencv_test_and_sample() {
	set -x

	rm -r ${IMAGE_ROOTFS}/usr/share/opencv4
	rm -r ${IMAGE_ROOTFS}/usr/share/OpenCV

	set +x
}
