IMAGE_INSTALL:append:qoriq = " \
        flex-installer \
"
IMAGE_INSTALL:append = " \
    dhcpcd \
"
IMAGE_FSTYPES += "cpio.gz"
