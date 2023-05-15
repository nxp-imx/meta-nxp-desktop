SUMMARY = "A prebuilt Desktop Base image as baseline for custom work"
require ubuntu-license.inc
SECTION = "devel"

# Desktop 22.04 baseline
SRC_URI[md5sum] = "ad795d8ef6da675ad7571e1b0d1c090a"
SRC_URI[sha256sum] = "4180f82e41fc42d24fa6cf43f7ea592b63833520e43687b3012cbc60550d579c"

require ubuntu-base.inc

# There are some basic differences between different Desktop versions.
# We try not to address them in the generic recipe
APTGET_EXTRA_PACKAGES += ""

# Desktop 20 unifies things and turns some things into symlinks. We
# solve this with Yocto "usrmerge" but that isn't quite enough.
# We still need to ship the symlinks.
# We also need to remove the udev reference as apparently bitbake.conf
# isn't quite adapted to usrmerge there.
FILES:${PN}:remove = "/lib/udev"
FILES:${PN} += "/bin"
FILES:${PN} += "/sbin"

# The downside of not having the symlink destination content is that we
# are missing a few basic files that are must have for dependencies.
RPROVIDES:${PN}:ubuntu += " \
    /bin/sh \
    /bin/bash \
    /bin/dash \
"

APTGET_EXTRA_PACKAGES += " \
    libdebuginfod1 \
"

# Extra packages that Desktop will replace and thus enables the use of 
# Yocto packages such as gstreamer to be used in Desktop without package conflitcs
YOCTO-DEPENDS-LIST = " glib-2.0 libglib-2.0-0 libglib-2.0-utils python3-core python3-dev \
                      python3-distutils python3-pickle python3-xml \
                      update-alternatives-opkg \
                      libpam-runtime shadow-base shadow dbus polkit \
                      systemd systemd-dev iso-codes-dev file gptfdisk \
                      shared-mime-info-dev bluez5 dtc e2fsprogs kmod \
                      e2fsprogs-e2fsck e2fsprogs-tune2fs e2fsprogs-badblocks \
                      procps procps-sysctl pstree fuser killall elfutils \
                      fio i2c-tools ifenslave keyutils libkmod2 libelf1 \
                      libasm1 libpci3 libsysfs2 udev \
                      udev-hwdb iproute2-ip iproute2 glibc-utils \
                      memtester minicom mmc-utils pciutils psmisc ptpd \
                      coreutils openssl-conf libcap merge-files upower \
                      openssl libssl1.1 libcrypto1.1 libssl3 libcrypto3 \
                      python3-pyelftools slang zstd libdrm-dev pkgconfig \
                      python3-resource sysfsutils sysklogd sysstat lrzsz \
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
                      python3-compile python3-json python3-unixadmin \
                      python3-plistlib python3-xmlrpc python2 python3 bash \
                     attr avahi-daemon base-files base-passwd bash-completion consolekit \
                     cracklib cryptodev-linux curl flex-libfl gobject-introspection icu \
                     lame libatomic-ops libattr1 libavahi-client3 libavahi-common3 \
                     libavahi-core7 libavahi-glib1 libavahi-gobject0 libblkid1 \
                     libcairo-gobject2 libcairo-script-interpreter2 libcairo2 libcap-ng0 \
                     libcap2 libdaemon0 libdbus-1-3 libdbus-glib-1-2 \
                     libdrm-radeon libdrm-nouveau libdrm-omap \
                     libdrm-intel libdrm-exynos libdrm-freedreno libdrm-amdgpu libdrm-etnaviv \
                     libexpat1 libfdisk1 libffi7 libfontconfig1 \
                     libfreetype6 libfribidi0 libgcc1 libgdk-pixbuf-2.0-0 libgdk-pixbuf-xlib-2.0-0 \
                     libgmp10 libgmpxx4 libgnutls-openssl27 libgnutls30 libgnutlsxx28 libgudev-1.0-0 \
                     libharfbuzz0 libical libice6 libicudata64 libicui18n64 libicuio64 libicutu64 \
                     libicuuc64 libidn2-0 libltdl7 liblzma5 libmount1 libmp3lame0 \
                     libogg0 liborc-0.4-0 liborc-test-0.4-0 libpam libpciaccess0 \
                     libpcrecpp0 libpcreposix0 libpixman-1-0 libpng16-16 libpsl5 \
                     libreadline8 libsbc1 libsm6 libsmartcols1 libsoup-2.4 libspeex1 \
                     libspeexdsp1 libsqlite3-0 libstdc++6 libsystemd0 libtag-c0 libtag1 \
                     libtheora libticw5 libtool libturbojpeg0 libudev1 \
                     libunistring2 libuuid1 libvorbis libpulse0 \
                     libpulse-mainloop-glib0 libpulse-simple0 pulseaudio-dev \
                     libx11-6 libx11-xcb1 libxau6 libxcb-composite0 libxcb-damage0 \
                     libxcb-dpms0 libxcb-dri2-0 libxcb-dri3-0 libxcb-glx0 libxcb-present0 \
                     libxcb-randr0 libxcb-record0 libxcb-render0 libxcb-res0 libxcb-screensaver0 \
                     libxcb-shape0 libxcb-shm0 libxcb-sync1 libxcb-xf86dri0 libxcb-xfixes0 \
                     libxcb-xinerama0 libxcb-xinput0 libxcb-xkb1 libxcb-xtest0 libxcb-xv0 \
                     libxcb-xvmc0 libxcb1 libxdamage1 libxdmcp6 libxext6 libxfixes3 libxft2 \
                     libxi6 libxml2 libxrender1 libxtst6 libxv1 libz1 \
                     mozjs mpg123 nspr orc linux-libc-headers-dev pango pulseaudio \
                     python3-pycairo python3-pkgutil python3-dbus python3-pygobject \
                     valgrind wayland attr-dev avahi-dev \
                     base-files-dev base-passwd-dev bash-completion-dev \
                     bluez5-dev bzip2-dev ca-certificates consolekit-dev \
                     cracklib-dev cryptodev-linux-dev curl-devflac-dev \
                     flex-dev glib-networking gobject-introspection-dev \
                      icu-dev lame-dev libatomic-dev libatomic-ops-dev \
                      libc6-dbg libcap-dbg libcap-dev libcap-ng-dev \
                      perl perl-dev libcrypt libcrypt-dbg libcrypt-dev \
                      libdaemon-dev libdbus-glib-1-dev libexpat-dbg libexpat-dev \
                      libffi-dbg libffi-dev libfontconfig-dbg libfontconfig-dev \
                      libfreetype-dbg libfreetype-dev libfribidi-dbg libfribidi-dev \
                      libgcc-s-dbg cpp libgdk-pixbuf-2.0-dbg libgdk-pixbuf-2.0-dev \
                      libglib-2.0-dbg libglib-2.0-dev libgmp-dbg libgmp-dev libgnutls-dev \
                      libgudev-1.0-dbg libgudev-1.0-dev libical-dev libice-dev libidn2-dbg libidn2-dev \
                      libjpeg-dbg libjpeg-dev libmp3lame-dev libnss-mdns libogg-dbg libogg-dev libpam-dev \
                      libpciaccess-dev libpcre-dbg libpcre-dev libpixman-1-dbg libpixman-1-dev \
                      libpng16-dbg libpng16-dev libpsl-dbg libpsl-dev libpthread-stubs-dev libreadline-dev \
                      libsbc-dbg libsbc-dev libsm-dev libsoup-2.4-dbg libsoup-2.4-dev \
                      libspeex-dbg libspeex-dev libspeexdsp-dev libsqlite3-dev libstdc++-dev \
                      libtag-dbg libtag-dev libtheora-dbg libtheora-dev libtool-dev \
                      libunistring-dbg libunistring-dev libusb-1.0-dbg libusb-1.0-dev \
                      libvorbis-dbg libvorbis-dev libx11-dbg libx11-dev \
                      libxau-dbg libxau-dev libxcb-dbg libxcb-dev libxdamage-dbg libxdamage-dev \
                      libxdmcp-dbg libxdmcp-dev libxext-dbg libxext-dev libxfixes-dbg libxfixes-dev \
                      libxft-dbg libxft-dev libxi-dev libxml2-dbg libxml2-dev libxrender-dbg libxrender-dev \
                      libxtst-dev libxv-dbg libxv-dev libz-dbg libz-dev mozjs-dev mpg123-dbg mpg123-dev \
                      ncurses-dev nettle-dbg nettle-dev nspr-dev openssl-dev orc-dev polkit-dev \
                      python3-dbus-dev python3-pycairo-dev python3-pygobject-dev shadow-dev \
                      util-linux-dev util-macros-dev valgrind-dev wayland-dbg wayland-dev \
                      wireless-tools xcb-proto-dev xorgproto-dev xtrans-dev \
                     rgb xkeyboard-config xf86-input-libinput libinput10 \
                     xkbcomp xserver-xf86-config mkfontdir mkfontscale font-util \
                     mkfontdir-dev mkfontscale-dev xkeyboard-config-dev encodings-dev \
                     font-util-dev libxkbfile-dev libfontenc-dev libxfont2-dev \
                     libxkbfile1 libcairo-dev alsa-utils-alsactl libasound2 \
                     libx11-locale libclutter-1.0-0 libatk-1.0-0 \
                      alsa-topology-conf alsa-ucm-conf libatopology2 libepoxy0 \
                      libicudata67 libicui18n67 libicuuc67 librsvg-2-2 libsndfile1 \
                      libxshmfence1 python3-nose python3-numpy libcairo-dbg \
                      libharfbuzz-dev libharfbuzz-dbg libpciaccess-dbg pango-dbg pango-dev \
                      librsvg-2-dbg librsvg-2-dev libsndfile-dbg libsndfile-dev \
                      xrandr xrandr-dev libxrandr-dev libxrandr2 libxxf86vm1 \
                      libxxf86vm-dev ibxshmfence-dev mesa-dev \
                     libgdk-pixbuf-2.0-loader-xpm libgdk-pixbuf-2.0-loader-png libgdk-pixbuf-2.0-loader-jpeg libgdk-pixbuf-2.0-loader-gif libgtk-2.0 \
                     libclutter-1.0-dev libclutter-gtk-1.0-0 libclutter-gtk-1.0-dev libcogl-gles2-20 \
                     libcogl-pango20 libcogl-path20 libcanberra libcanberra-dev libcanberra-gtk2 \
                     libcanberra-gtk3 gtk+-dev libepoxy-dev \
                     gsettings-desktop-schemas-dev libxkbcommon \
                     libxcb-util-dev libxcb-util1 libxcomposite-dev libxcomposite1 \
                     libxcursor-dev libxcursor1 libxkbcommon-dev libxshmfence-dev \
                     libjson-glib-1.0-0 libjson-glib-1.0-dev \
                      gsettings-desktop-schemas adwaita-icon-theme-symbolic \
                      hicolor-icon-theme libatk-bridge-2.0-0 libxslt nss \
                      libatspi0 libgtk-3.0 gtk+3 gtk+3-dev gdk-pixbuf \
                      libxfont2-2 libfontenc1 desktop-file-utils \
                      libunwind libwebp libxcvt libzstd1 \
                      usbutils libusb-1.0-0 libdw1 \
                      libgcc libxkbcommon0 libdebuginfod1 \
                      libssl libcrypto wpa-supplicant \
                      libpython3.10 bash-dev \
                      libgtk-4.0  util-linux-lsblk util-linux-blkid \
                      update-rc.d \
                      init-system-helpers-service \
"

RCONFLICTS:${PN} += " ${YOCTO-DEPENDS-LIST} "
RREPLACES:${PN} += " ${YOCTO-DEPENDS-LIST} "
RPROVIDES:${PN} += " ${YOCTO-DEPENDS-LIST} "

RPROVIDES:${PN}:ubuntu += " libglib-2.0 "

do_install[network] = "1"

QA_EMPTY_DIRS = ""

python do_package:append() {
    os.environ['PSEUDO_DISABLED'] = '1'
    d.setVar('ERROR_QA', '')
}
