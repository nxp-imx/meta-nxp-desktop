FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

EXTRA_OECONF += "--disable-glx \
"

OPENGL_PKGCONFIGS = "glamor dri3 xshmfence"

XSERVER_RRECOMMENDS = ""
