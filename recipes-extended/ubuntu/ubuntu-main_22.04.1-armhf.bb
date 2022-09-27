SUMMARY = "A prebuilt main image as baseline for custom work"
require ubuntu-base-image.inc
SECTION = "devel"

APTGET_EXTRA_PACKAGES_MAIN = "1"

# Desktop 22.04.1 baseline
SRC_URI[md5sum] = "6e63b79b2e3ae7cc67ef4bab7ad2a037"
SRC_URI[sha256sum] = "e5503aa925f5d4fd432bb3a89cf266807dabee66877b15ef825efd2dd9c60227"

COMPATIBLE_MACHINE = "(ls1021atwr)"
