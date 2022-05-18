SUMMARY = "A prebuilt main image as baseline for custom work"
require ubuntu-base-image.inc
SECTION = "devel"

APTGET_EXTRA_PACKAGES_MAIN = "1"

# Desktop 22.04 baseline
SRC_URI[md5sum] = "ed1b3b69400df92f8c35a06fe8fa0d8b"
SRC_URI[sha256sum] = "fdf4dba0bfa22c80453cc6f40185195ad7dff765f598b14cce9604b9c7cb8c83"

COMPATIBLE_MACHINE = "(ls1021atwr)"
