FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

# Add support for extra syscalls and better chroot to support apt-get
# use inside pseudo for target rootfs setups
SRC_URI += " \
    file://0001-pseudo-Enabled-utimensat-ftimens.patch \
    file://0002-pseudo-The-stat-v-fs-functions-were-not-wrapped-prop.patch \
    file://0003-pseudo-proper-compile-of-f-stat-v-fs-wrappers.patch \
    file://0004-pseudo-Added-support-function-for-fnmatch.patch \
    file://0005-Moved-chroot-path-check-into-subfunction.patch \
    file://0006-pseudo-Filename-translation-for-exec-path-handling.patch \
    file://0007-pseudo-path-search-was-not-shell-compliant.patch \
    file://0008-pseudo-Oops.-Forgot-to-add-variable-cleanup.patch \
    file://0009-pseudo-Unified-and-added-debug-messages-for-exec_pat.patch \
    file://0010-pseudo-Clean-up-executable-check.patch \
    file://0011-pseudo-Implemented-means-to-force-chroot-usage.patch \
    file://0012-pseudo-Change-the-usage-of-pseudo_exec_path.patch \
    file://0013-seudo-Now-handles-chroot-scripts-and-executables.patch \
    file://0014-pseudo-Treat-chroot-exceptions-properly-for-std-path.patch \
    file://0015-pseudo-Added-missing-support-for-faccessat.patch \
    file://0016-pseudo-pseudo_root_path-didn-t-work-properly-for-chr.patch \
    file://0017-pseudo-Fixing-an-incorrect-prior-patch-port.patch \
    file://0018-pseudo-Diagnostic-cleanup-for-utimensat.patch \
    file://0019-pseudo-realpath-wrapper-did-not-do-chroot-translatio.patch \
    file://0020-pseudo-did-not-treat-softlinks-correctly-in-chroot.patch \
\
    file://0001-pseudo-Critical-chroot-readlink-fix.patch \
\
    file://0001-pseudo-Added-dup3-support.patch \
    file://0002-pseudo-Fix-fxstatat-calls-for-non-fs-file-handles.patch \
"
