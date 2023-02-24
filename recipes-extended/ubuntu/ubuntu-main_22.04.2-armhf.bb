SUMMARY = "A prebuilt main image as baseline for custom work"
require ubuntu-base-image.inc
SECTION = "devel"

APTGET_EXTRA_PACKAGES_MAIN = "1"

# Desktop 22.04.2 baseline
SRC_URI[md5sum] = "2b45cd48b94d3fe89624e4a4e238ef0f"
SRC_URI[sha256sum] = "244e2d2a95d8fa1481c67138012b6ffedd684fc37a53b890f8c29e6abf02e14d"

COMPATIBLE_MACHINE = "(ls1021atwr)"
