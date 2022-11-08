# Flex-installer Userguide

Step1: Install flex-installer shell srcipt
```
    $ git clone https://github.com/nxp-imx/meta-nxp-desktop.git -b lf-5.15.52-2.1.0-kirkstone
    $ cp meta-nxp-desktop/scripts/flex-installer /usr/bin/
    $ sudo chmod a+x /usr/bin/flex-installer
    $ which flex-installer
```
Step2: Copy firmware and rootfs (e.g:ls1028radb)
```
    $ mkdir -p ~/Layerscape/ls1028ardb
    $ cp <build-dir>/tmp/deploy/images/ls1028ardb/firmware_ls1028ardb_sdboot.img ~/Layerscape/ls1028ardb/
    $ cp <build-dir>/tmp/deploy/images/ls1028ardb/ls-image-desktop-ls1028ardb.tar.gz ~/Layerscape/ls1028ardb/
    $ cp <build-dir>/tmp/deploy/images/ls1028ardb/boot_LS_arm64_lts_5.15.tgz ~/Layerscape/ls1028ardb/
```
Note: For other board, rootfs used ls-image-main-<board>.tar.gz

Step3: Burn to SD card(e.g:MACHINE=1028ardb)
```
    $ flex-installer -i pf -d /dev/sdX;
```
option1: Enter Yocto Tiny system.
```
    $ flex-installer -f firmware_ls1028ardb_sdboot.img -d /dev/sdX
    # under U-Boot prompt
    => run sd_bootcmd
```
option2: Kernel Image,dtb, boot_scr also in boot_LS_arm64_lts_5.15.tgz,you can burn them into the first bootpartition.
```
    $ flex-installer -f firmware_ls1028ardb_sdboot.img -r ls-image-main-ls1028ardb.tar.gz -b boot_LS_arm64_lts_5.15.tgz -d /dev/sdX
```
option3: Yocto rootfs contains Kernel Image,dtb, boot_scr already.
```
    $ flex-installer -f firmware_ls1028ardb_sdboot.img -r ls-image-main-ls1028ardb.tar.gz -m ls1028ardb -d /dev/sdX
```
