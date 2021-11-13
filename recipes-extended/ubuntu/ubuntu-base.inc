#
# Generically build a ubuntu-base package set.
#

inherit nativeaptinstall

APTGET_CHROOT_DIR = "${D}"

S = "${WORKDIR}/rootfs"
B = "${S}"

ROOTFS="ubuntu-base-${UBUNTU_TARGET_VERSION}-base-${UBUNTU_TARGET_ARCH}.tar.gz"
SRC_URI = " \
	http://cdimage.ubuntu.com/ubuntu-base/releases/${UBUNTU_TARGET_BASEVERSION}/release/${ROOTFS};unpack=0;subdir=rootfs \
	file://fstab \
"

# Note that we still populate_sysroot because otherwise manifest
# updates through staging would not happen.
do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_populate_sdk[noexec] = "1"

# As this package will never be built/compiled, we do not really need
# to look at any build- or runtime-dependencies or debug symbols!
# We have quite a number of deficiencies to ignore. The Desktop packages
# and rootfs do not really conform to Yocto rules.
INSANE_SKIP_${PN} += "already-stripped build-deps file-rdeps dev-so staticdev"
INHIBIT_DEFAULT_DEPS = "1"

# We need to keep all the other recipes happy that will rely on us
# during runtime through default dependencies!
RCONFLICTS_${PN} += "glibc eglibc"
RREPLACES_${PN} += "glibc eglibc"

# The basic ubuntu-base rootfs does not provide enough functionality to
# enable ethernet or interfaces automatically in general.
# We support ifconfig, route, busybox, udhcpc
# which in conjunction should be enough to start up the Ethernet and
# do a local apt-get install of major packages.
APTGET_EXTRA_PACKAGES += "\
	udev \
	sudo udhcpc htop iproute2 \
	whiptail \
	kmod gnupg net-tools netbase \
"
# We *MUST* provide all Desktop packages in ubuntu-base that we need
# to install Yocto packages! Packages installed in images via
# IMAGE_INSTALL will not help to resolve the dependency chain!
# The following is an incomplete hint at dependency needs to accomodate
# Yocto packages:
# - dtc, xz-utils: LS2 restool references libs
# - python*, bc, db*: kernel-devsrc has scripts
# Note how we translate Desktop names into Yocto names for proper
# handling.
APTGET_EXTRA_PACKAGES += "\
    bison \
    device-tree-compiler \
    libnss-db \
    libssl-dev \
    openssl \
	python bc db5.3 \
	xz-utils bzip2 \
"
# Some packages are implicitly present in Desktop due to the kernel
# and rootfs configuration. We pretend to have the respective Yocto
# names therefore. Stuff is grouped for easier updates
RPROVIDES_${PN}_ubuntu += " \
/bin/sh \
flex \
"
RPROVIDES_${PN}_ubuntu += " \
shared-mime-info \
"
RPROVIDES_${PN}_ubuntu += " \
rtld(GNU_HASH) \
libc6 \
libc6-utils \
libc6-extra-nss \
libc6-thread-db \
libc6-pcprofile \
libcrypto1.1 \
libgcc1-dev \
libssl1.1 \
update-alternatives \
"
RPROVIDES_${PN}_ubuntu += " \
pam-plugin-access \
pam-plugin-debug \
pam-plugin-deny \
pam-plugin-echo \
pam-plugin-env \
pam-plugin-exec \
pam-plugin-faildelay \
pam-plugin-filter \
pam-plugin-ftp \
pam-plugin-group \
pam-plugin-issue \
pam-plugin-keyinit \
pam-plugin-limits \
pam-plugin-lastlog \
pam-plugin-listfile \
pam-plugin-localuser \
pam-plugin-loginuid \
pam-plugin-mkhomedir \
pam-plugin-motd \
pam-plugin-namespace \
pam-plugin-nologin \
pam-plugin-permit \
pam-plugin-pwhistory \
pam-plugin-rhosts \
pam-plugin-rootok \
pam-plugin-securetty \
pam-plugin-shells \
pam-plugin-stress \
pam-plugin-succeed-if \
pam-plugin-time \
pam-plugin-timestamp \
pam-plugin-tally \
pam-plugin-tally2 \
pam-plugin-umask \
pam-plugin-unix \
pam-plugin-warn \
pam-plugin-wheel \
pam-plugin-xauth \
"
RPROVIDES_${PN}_ubuntu += " \
libform5 \
libformw5 \
libmenu5 \
libmenuw5 \
libncurses5 \
libncursesw5 \
libpanel5 \
libpanelw5 \
libtic5 \
libtinfo5 \
ncurses-terminfo-base \
"

# A few things are provided where we don't have a Yocto equivalent
# currently/possibly. We add them preemptively just in case
RPROVIDES_${PN}_ubuntu += " \
pam-plugin-tty-audit \
pam-plugin-userdb \
pam-plugin-extrausers \
pam-plugin-sepermit \
pam-plugin-selinux \
"

# Ensure that all files of the tgz end up in our packages
FILES_${PN} += "${base_libdir}/${TRANSLATED_TARGET_ARCH}-linux-gnu/lib*${SOLIBS}"
FILES_${PN} += "${base_libdir}/${TRANSLATED_TARGET_ARCH}-linux-gnu/ld*${SOLIBS}"
FILES_${PN}-dev = ""
#FILES_${PN}-dev += "${base_libdir}/${TRANSLATED_TARGET_ARCH}-linux-gnu/lib*${SOLIBSDEV}"
#FILES_${PN}-dev += "${base_libdir}/${TRANSLATED_TARGET_ARCH}-linux-gnu/ld*${SOLIBSDEV}"
#FILES_${PN}-dev += "${libdir}/sudo/lib*${SOLIBSDEV}"
FILES_${PN}-dbg += "${base_libdir}/${TRANSLATED_TARGET_ARCH}-linux-gnu/.debug/lib*${SOLIBS}"
FILES_${PN}-dbg += "${base_libdir}/${TRANSLATED_TARGET_ARCH}-linux-gnu/.debug/lib*${SOLIBSDEV}"

FILES_${PN} += "/usr/include"
FILES_${PN} += "/usr/share /usr/lib /usr/local /usr/games /usr/src"
FILES_${PN} += "/dev"
FILES_${PN} += "/lib"
FILES_${PN} += "/run /dev /lib /media /boot /tmp /proc /opt /mnt /home /srv /sys /run /root"
FILES_${PN}-dbg_append = " ${localstatedir}/lib/dpkg/info/.debug"
FILES_${PN}-dbg_${TRANSLATED_TARGET_ARCH} += "${base_libdir}/${TRANSLATED_TARGET_ARCH}-linux-gnu/.debug"

fakeroot do_shell_prepare() {
	cd "${D}"
	rm -rf "*"
	tar -C "${D}" -xzf "${S}/${ROOTFS}"
}

fakeroot do_aptget_user_update_append() {

	set -x

	# Without an /etc/fstab, the rootfs remains read only
	install -m 0644 ${WORKDIR}/fstab ${D}${sysconfdir}/fstab

	# After we are done installing our extra packages, we
	# optionally kill the log directory content, so that we
	# don't clutter the target
	#rm -rf ${APTGET_CHROOT_DIR}/var/log/*

	# The info dir file causes a Yocto complaint.
	# Per Yocto docs it should never be packaged
	rm -f "${APTGET_CHROOT_DIR}${infodir}/dir"

	# There is an unfulfilled libldap dependency.
	# Fixing the dependency seems a bit tricky to do,
	# so we eliminate the single tool that causes
	# the dependency problem. UNDERSTAND AND FIX!
	#rm -f "${APTGET_CHROOT_DIR}/usr/lib/gnupg/gpgkeys_ldap"

	# The default ubuntu-base rootfs does not do filesystem
	# fixes on boot. Given the nature of the BlueBox, we want
	# to enable that by default
	if [ -f "${APTGET_CHROOT_DIR}/lib/init/vars.sh" ]; then
		sed -i "s/^#*FSCKFIX\s*=.*/FSCKFIX=yes/g" "${APTGET_CHROOT_DIR}/lib/init/vars.sh"
	fi

	set +x
}

python do_install() {
    bb.build.exec_func("do_shell_prepare", d)
    bb.build.exec_func("do_aptget_update", d)
}

PKGDEST = "${WORKDIR}/packages-split"
python prep_xattr_postinst() {
    # Tricky hack. We go through the dirs and files of the split
    # package to determine if there is any non-root uid/gid to preserve.
    # if so, we generate a postinst script that should do just that.
    pkgdest = d.getVar('PKGDEST', True)
    packages = d.getVar('PACKAGES', True).split()
    for pkg in packages:
        lines = []
        pkgpath = os.path.join(pkgdest, pkg)
        for basedir, dirnames, filenames in os.walk(pkgpath):
            basedirchroot = basedir[ len(pkgpath) : ]
            for f in dirnames + filenames:
                ff = os.path.join(basedir, f)
                s = os.lstat(ff)
                if s.st_uid != 0 or s.st_gid != 0:
                    lines.append(str(s.st_uid) + ":" + str(s.st_gid) + ' "' + os.path.join(basedirchroot, f) + '"')

        if lines:
            prefix = '#!/bin/sh -e\n' \
                 'if [ x"$D" = "x" ]; then\n' \ 
                 '    # Do nothing on target!\n' \
                 '    exit 1\n' \
                 'else\n'
            body = ''
            for l in lines:
                body += 'chroot "$D" chown ' + l + '\n'
            suffix = 'fi\n'

            d.setVar('pkg_postinst_' + pkg, prefix + body + suffix)
}
PACKAGEFUNCS =+ "prep_xattr_postinst"

COMPATIBLE_MACHINE = "ubuntu"
