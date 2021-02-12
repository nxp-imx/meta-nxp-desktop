FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI += "\
	file://0001-qemu-Reinstated-syscall-emulation-through-libc-via-c.patch \
	file://0002-qemu-Fixed-utimensat-for-libc-based-calls.patch \
	file://0003-qemu-user-corrected-getsockopt-implementation.patch \
	file://0004-qemu-user-seccomp-handling-was-not-checked-properly-.patch \
	file://0005-qemu-user-SO_PEERGROUPS-was-not-emulated-breaking-tg.patch \
	file://0006-qemu-user-syscall.c-target_mmap-was-called-with-bad-.patch \
\
	file://0007-qemu-user-Subject-PATCH-qemu-user-Improved-diagnosti.patch \
	file://0008-qemu-user-builtin-strace-much-more-comprehensive.patch \
\
	file://0009-qemu-user-Loading-an-elf-broke-the-heap-for-that-elf.patch \
\
	file://0001-qemu-user-strace-didn-t-print-faccessat-correctly.patch \
"
