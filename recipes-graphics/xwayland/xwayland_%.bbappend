FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGECONFIG_CONFARGS:append = "-Dsecure-rpc=false"

OPENGL_PKGCONFIGS:remove = "glx "
