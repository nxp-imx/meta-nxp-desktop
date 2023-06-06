FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

# Add support for extra syscalls and better chroot to support apt-get
# use inside pseudo for target rootfs setups
SRC_URI += " \
    file://0001-pseudo-Enabled-utimensat-ftimens.patch \
    file://0003-pseudo-The-stat-v-fs-functions-were-not-wrapped-prop.patch \
    file://0004-pseudo-Ensure-by-adding-the-right-headers-that-the-f.patch \
    file://0005-pseudo-Added-support-function-for-fnmatch.patch \
    file://0006-pseudo-Moved-chroot-path-check-into-subfunction-to-s.patch \
    file://0007-pseudo-Added-filename-translation-to-exec-path-handl.patch \
    file://0008-pseudo-path-search-was-not-shell-compliant.patch \
    file://0009-pseudo-Oops.-Forgot-to-add-variable-cleanup-to-suppo.patch \
    file://0010-pseudo-Unified-and-added-debug-messages-for-exec_pat.patch \
    file://0011-pseudo-Executable-check-is-now-cleaner-subfunction-f.patch \
    file://0012-pseudo-Implemented-means-to-force-chroot-usage-for-e.patch \
\
    file://0001-pseudo-To-support-chroot-exec-paths-properly-we-need.patch \
    file://0002-pseudo-Now-handles-chroot-scripts-and-executables.patch \
\
    file://0015-pseudo-We-need-to-treat-chroot-exceptions-properly-f.patch \
\
    file://0001-pseudo-Added-missing-support-for-faccessat.patch \
\
    file://0001-pseudo-pseudo_root_path-didn-t-work-properly-for-chr.patch \
    file://0001-pseudo-Fixing-an-incorrect-EAR-patch-port.patch \
    file://0001-pseudo-Cleaned-check-of-symlink-flag-handling.patch \
    file://0001-pseudo-Another-missing-EAR3-patch.patch \
    file://0001-pseudo-Diagnostic-cleanup-for-utimensat.patch \
\
    file://0001-attr-attributes.h-is-providing-the-ENOATTR-definitio.patch \
"

