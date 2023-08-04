# Override the EULA defined in meta-imx
cp sources/meta-nxp-desktop/EULA.txt sources/meta-imx/EULA.txt

source sources/meta-imx/tools/imx-setup-release.sh $@

if [ -f conf/local.conf ]; then
    sed -E "s/(MACHINE \?\?= ')(.*)'/\1\2desktop'/g" -i conf/local.conf
    echo ""                                                                       >> conf/local.conf
    echo "# Switch to rpm packaging to avoid rootfs build break"                  >> conf/local.conf
    echo "PACKAGE_CLASSES = \"package_rpm\""                                      >> conf/local.conf
    echo ""                                                                       >> conf/local.conf
    echo "# Save lots of disk space"                                              >> conf/local.conf
    echo "INHERIT += \"rm_work\""                                                 >> conf/local.conf
    echo ""                                                                       >> conf/local.conf
    echo "# Set your proxy if necessary"                                          >> conf/local.conf
    echo "#ENV_HOST_PROXIES = \"http_proxy=\""                                    >> conf/local.conf
    echo ""                                                                       >> conf/local.conf

    echo "BBLAYERS += \"\${BSPDIR}/sources/meta-nxp-desktop\""                    >> conf/bblayers.conf

    echo ""
    echo "IMX Desktop setup complete!"
    echo ""
    echo "You can now build the following Desktop images:"
    echo ""
    echo "$ bitbake imx-image-desktop"
    echo ""
fi
