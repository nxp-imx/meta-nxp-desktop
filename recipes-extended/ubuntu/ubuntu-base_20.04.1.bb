SUMMARY = "A prebuilt Desktop Base image as baseline for custom work"
require ubuntu-license.inc
SECTION = "devel"

# Desktop 20.04.1 baseline
SRC_URI[md5sum] = "6c4cf15a389d6ae485d92322c97e875a"
SRC_URI[sha256sum] = "269709ecd5c506f229f10983f990c394278b202ca29f089844e2e5c2b80ad8b4"

require ubuntu-base.inc

# There are some basic differences between different Desktop versions.
# We try not to address them in the generic recipe
APTGET_EXTRA_PACKAGES += ""

# Desktop 20 unifies things and turns some things into symlinks. We
# solve this with Yocto "usrmerge" but that isn't quite enough.
# We still need to ship the symlinks.
# We also need to remove the udev reference as apparently bitbake.conf
# isn't quite adapted to usrmerge there.
FILES_${PN}_remove = "/lib/udev"
FILES_${PN} += "/bin"
FILES_${PN} += "/sbin"

# The downside of not having the symlink destination content is that we
# are missing a few basic files that are must have for dependencies.
RPROVIDES_${PN}_ubuntu += " \
    /bin/sh \
    /bin/bash \
    /bin/dash \
"

# We should not have a single PROVIDES entry as this package
# does not provide anything for build time of any other package!
# PROVIDES += ""

# This is the installed package list as found in log_do_install.
# Minor edits have been done to remove an architecture suffix. 
APTGET_RPROVIDES += " \
adduser apt apt-transport-https apt-utils \
base-files base-passwd bash bc bison bsdutils busybox bzip2 \
ca-certificates coreutils dash db5.3-doc db5.3-sql-util db5.3-util dbus \
dbus-user-session dconf-gsettings-backend dconf-service debconf \
debianutils device-tree-compiler diffutils dirmngr distro-info-data \
dmsetup dpkg e2fsprogs fdisk file findutils gcc-10-base gir1.2-glib-2.0 \
gir1.2-packagekitglib-1.0 glib-networking glib-networking-common \
glib-networking-services gnupg gnupg-l10n gnupg-utils gpg gpg-agent \
gpg-wks-client gpg-wks-server gpgconf gpgsm gpgv grep \
gsettings-desktop-schemas gzip hostname htop init-system-helpers \
iproute2 iso-codes kmod krb5-locales libacl1 libapparmor1 libappstream4 \
libapt-pkg6.0 libargon2-1 libasn1-8-heimdal libassuan0 libatm1 libattr1 \
libaudit-common libaudit1 libblkid1 libbrotli1 libbsd0 libbz2-1.0 \
libc-bin libc6 libcap-ng0 libcap2 libcap2-bin libcom-err2 libcrypt1 \
libcryptsetup12 libdb5.3 libdb5.3++ libdb5.3++-dev libdb5.3-dbg \
libdb5.3-dev libdb5.3-java libdb5.3-java-dev libdb5.3-java-jni \
libdb5.3-sql libdb5.3-sql-dev libdb5.3-stl libdb5.3-stl-dev \
libdb5.3-tcl libdbus-1-3 libdconf1 libdebconfclient0 libdevmapper1.02.1 \
libelf1 libexpat1 libext2fs2 libfdisk1 libfdt1 libffi7 libfribidi0 \
libgcc-s1 libgcrypt20 libgirepository-1.0-1 libglib2.0-0 libglib2.0-bin \
libglib2.0-data libgmp10 libgnutls30 libgpg-error0 libgssapi-krb5-2 \
libgssapi3-heimdal libgstreamer1.0-0 libhcrypto4-heimdal \
libheimbase1-heimdal libheimntlm0-heimdal libhogweed5 \
libhx509-5-heimdal libicu66 libidn2-0 libip4tc2 libjson-c4 libk5crypto3 \
libkeyutils1 libkmod2 libkrb5-26-heimdal libkrb5-3 libkrb5support0 \
libksba8 libldap-2.4-2 libldap-common liblmdb0 liblz4-1 liblzma5 \
libmagic-mgc libmagic1 libmnl0 libmount1 libmpdec2 libncurses6 \
libncursesw6 libnettle7 libnewt0.52 libnpth0 libnss-db libnss-systemd \
libp11-kit0 libpackagekit-glib2-18 libpam-cap libpam-modules \
libpam-modules-bin libpam-runtime libpam-systemd libpam0g libpcre2-8-0 \
libpcre3 libpolkit-agent-1-0 libpolkit-gobject-1-0 libpopt0 libprocps8 \
libproxy1v5 libpsl5 libpython2-stdlib libpython2.7-minimal \
libpython2.7-stdlib libpython3-stdlib libpython3.8-minimal \
libpython3.8-stdlib libreadline8 libroken18-heimdal libsasl2-2 \
libsasl2-modules libsasl2-modules-db libseccomp2 libselinux1 \
libsemanage-common libsemanage1 libsepol1 libsigsegv2 libslang2 \
libsmartcols1 libsoup2.4-1 libsqlite3-0 libss2 libssl-dev libssl1.1 \
libstdc++6 libstemmer0d libsystemd0 libtasn1-6 libtcl8.6 libtinfo6 \
libudev1 libunistring2 libuuid1 libwind0-heimdal libxml2 libxtables12 \
libyaml-0-2 libzstd1 login logsave lsb-base lsb-release m4 mawk \
mime-support mount ncurses-base ncurses-bin net-tools netbase \
networkd-dispatcher openssl packagekit packagekit-tools passwd \
perl-base pinentry-curses policykit-1 procps publicsuffix \
python-apt-common python-is-python2 python2 python2-minimal python2.7 \
python2.7-minimal python3 python3-apt python3-certifi python3-chardet \
python3-dbus python3-distro-info python3-gi python3-idna \
python3-minimal python3-pkg-resources python3-requests \
python3-requests-unixsocket python3-six python3-software-properties \
python3-urllib3 python3.8 python3.8-minimal readline-common sed \
sensible-utils shared-mime-info software-properties-common sudo systemd \
systemd-sysv systemd-timesyncd sysvinit-utils tar tcl tcl8.6 tzdata \
ubuntu-keyring ucf udev udhcpc unattended-upgrades util-linux whiptail \
xdg-user-dirs xz-utils zlib1g \
"

# Extra packages that Desktop will replace and thus enables the use of 
# Yocto packages such as gstreamer to be used in Desktop without package conflitcs

RCONFLICTS_${PN} += " glib-2.0 libglib-2.0-0 libglib-2.0-utils python3-core python3-dev \
                      python3-distutils python3-pickle python3-xml \
                      update-alternatives-opkg pam-plugin-unix \
                      libpam-runtime shadow-base shadow dbus polkit \
                      systemd systemd-dev iso-codes-dev \ 
                      shared-mime-info-dev bluez5 \ 
                      python3-stringold python3-numbers python3-numbers \
                      python3-shell python3-pprint python3-logging \
                      python3-datetime python3-difflib python3-typing \
                      python3-debugger python3-audio python3-codecs \
                      python3-mime python3-mmap python3-threading \
                      python3-ctypes python3-math python3-crypt \
                      python3-email python3-io python3-netclient \
                      python3-asyncio python3-unittest python3-pydoc \
                      python3-misc python3-doctest python3-multiprocessing \
                      python3-compression python3-html python3-netserver \
                      libtirpc3 python3-compile python3-json python3-unixadmin \
                      python3-plistlib python3-xmlrpc"

RREPLACES_${PN} += " glib-2.0 libglib-2.0-0 libglib-2.0-utils python3-core python3-dev \
                     python3-distutils python3-pickle python3-xml \
                     update-alternatives-opkg pam-plugin-unix \
                     libpam-runtime shadow-base shadow dbus polkit \
                     systemd systemd-dev iso-codes-dev \
                     shared-mime-info-dev bluez5 \
                     python3-stringold python3-numbers python3-numbers \
                     python3-shell python3-pprint python3-logging \
                     python3-datetime python3-difflib python3-typing \
                     python3-debugger python3-audio python3-codecs \
                     python3-mime python3-mmap python3-threading \
                     python3-ctypes python3-math python3-crypt \
                     python3-email python3-io python3-netclient \
                     python3-asyncio python3-unittest python3-pydoc \
                     python3-misc python3-doctest python3-multiprocessing \
                     python3-compression python3-html python3-netserver \ 
                     libtirpc3 python3-compile python3-json python3-unixadmin \
                     python3-plistlib python3-xmlrpc"
RPROVIDES_${PN}_ubuntu += " libglib-2.0 "
