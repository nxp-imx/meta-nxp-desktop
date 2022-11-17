IMAGE_INSTALL:append = " \
    dhcpcd \
    coreutils \
    net-tools \
    util-linux-fdisk \
    util-linux-lsblk \
    e2fsprogs \
    e2fsprogs-e2fsck \
    openssl \
    tar \
    inetutils-ping \
    keyutils \
    rng-tools \
"

IMAGE_INSTALL:append:fsl-lsch2 = " \
    fmc \
"

IMAGE_INSTALL:append:fsl-lsch3 = " \
    restool \
    tsntool \
"
IMAGE_INSTALL:append:ls1088a = " \
    gpp-aioptool \
    mc-utils-image \
"
IMAGE_INSTALL:append:ls2088a = " \
    gpp-aioptool \
    mc-utils-image \
"
IMAGE_INSTALL:append:lx2160a = " \
    mc-utils-image \
"
IMAGE_INSTALL:append:lx2162a = " \
    mc-utils-image \
"
IMAGE_INSTALL:append:ls2084abbmini = " \
    kvaser \
"

IMAGE_INSTALL:append:ls1012a = " \
    kernel-modules \
    ppfe-firmware \
"

IMAGE_FSTYPES += "cpio.gz"
