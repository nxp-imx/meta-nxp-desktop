require recipes-core/images/core-image-minimal.bb

SUMMARY = "TinyLinux image to provide necessary linux environment to run installer or diagnose IP blocks"
DESCRIPTION = "Add extra oe-repo packages and NXP-specific tool packages."

LICENSE = "MIT"

IMAGE_INSTALL:append = " \
    dhcpcd \
    bash \
    coreutils \
    curl \
    parted  \
    mtd-utils  \
    net-tools \
    util-linux-fdisk \
    util-linux-lsblk \
    dosfstools \
    e2fsprogs \
    e2fsprogs-e2fsck \
    e2fsprogs-mke2fs \
    packagegroup-core-ssh-openssh \
    file \
    openssl \
    sudo \
    tar \
    inetutils-ping \
    keyutils \
    rng-tools \
"


IMAGE_INSTALL:append:fsl-lsch2 += " \
    fmc \
"


IMAGE_INSTALL:append:fsl-lsch3 += " \
    restool \
    tsntool \
    gpp-aioptool \
    mc-utils-image \
"


IMAGE_INSTALL:append:ls2084abbmini += " \
    kvaser \
"


IMAGE_INSTALL:append:ls1012a = " \
    kernel-modules \
    ppfe-firmware \
"

IMAGE_INSTALL:append:qoriq = " \
        flex-installer \
"

PACKAGE_ARCH = "${MACHINE_ARCH}"
IMAGE_FSTYPES = "tar.gz ext2.gz ext2.gz.u-boot cpio.gz squashfs"
