FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGECONFIG_CONFARGS:append = "-Dsecure-rpc=false"

SRC_URI:append:imxgpu = " \
    file://0003-glamor-Fix-fbo-pixmap-format-with-GL_BGRA_EXT.patch \
"

OPENGL_PKGCONFIGS:remove = "glx "
