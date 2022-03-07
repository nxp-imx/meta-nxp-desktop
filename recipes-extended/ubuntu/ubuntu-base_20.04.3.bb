SUMMARY = "A prebuilt Desktop Base image as baseline for custom work"
require ubuntu-base-image.inc
SECTION = "devel"

APTGET_EXTRA_PACKAGES += " \
    libtinfo5 \
    udhcpc \
    python \
"

APTGET_EXTRA_PACKAGES_DESKTOP = " \
    gnome-terminal \
    gnome-shell \
    gdm3 \
    adwaita-icon-theme \
    adwaita-icon-theme-full \
    gnome-accessibility-themes \
    gnome-backgrounds \
    gnome-calculator gnome-calendar \
    gnome-clocks gnome-color-manager \
    gnome-contacts \
    gnome-control-center \
    gnome-disk-utility \
    gnome-screenshot gnome-session \
    gnome-themes-extra gnome-tweaks \
    gnome-sound-recorder \
    gnome-startup-applications \
    gnome-video-effects \
    media-player-info \
    ubuntu-wallpapers \
    ubuntu-wallpapers-focal \
    dmz-cursor-theme \
    totem libtotem0 \
    alsa-base alsa-utils \
    rhythmbox \
    nautilus \
    eog \
"

APTGET_EXTRA_PACKAGES_REMOVE += " \
    xwayland \
"
