FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = "file://0001-glamor-Fix-fbo-pixmap-format-with-GL_BGRA_EXT.patch \
"

EXTRA_OECONF += "--disable-glx \
"

OPENGL_PKGCONFIGS = "glamor dri3 xshmfence"

XSERVER_RRECOMMENDS = ""
