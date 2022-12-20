# Flex-installer Userguide

Step1: Install flex-installer shell srcipt
```
    $ git clone https://github.com/nxp-imx/meta-nxp-desktop.git -b lf-5.15.71-2.2.0-kirkstone
    $ cp meta-nxp-desktop/scripts/flex-installer /usr/bin/
    $ sudo chmod a+x /usr/bin/flex-installer
    $ which flex-installer
```
Step2: Copy firmware and rootfs (e.g:ls1043radb)
```
    $ mkdir -p ~/Layerscape/ls1043ardb
    $ cp <build-dir>/tmp/deploy/images/ls1043ardb/firmware_ls1043ardb_sdboot.img ~/Layerscape/ls1043ardb/
    $ cp <build-dir>/tmp/deploy/images/ls1043ardb/ls-image-main-ls1043ardb.tar.gz ~/Layerscape/ls1043ardb/
    $ cp <build-dir>/tmp/deploy/images/ls1043ardb/boot_LS_arm64_lts_5.15.tgz ~/Layerscape/ls1043ardb/
```
Note: For ls1028ardb board, rootfs used ls-image-desktop-<board>.tar.gz ,which contain display.

Step3: Burn to SD card(e.g:MACHINE=ls1043ardb)
```
    # default partition 4 partitions as 4P=2G:128M:5G:-1
    $ flex-installer -i pf -d /dev/sdX;
    # specify custom partitions as 4P=2G:3G:6G:-1
    $ flex-installer -i pf -d /dev/sdx -p 4P=2G:3G:6G:-1
```
option1: Enter Yocto Tiny system.
```
    $ flex-installer -f firmware_ls1043ardb_sdboot.img -d /dev/sdX
    # under U-Boot prompt
    => run sd_bootcmd
```
option2: Kernel Image,dtb, boot_scr also in boot_LS_arm64_lts_5.15.tgz,you can burn them into the first bootpartition.
```
    $ flex-installer -f firmware_ls1043ardb_sdboot.img -r ls-image-main-ls1043ardb.tar.gz -b boot_LS_arm64_lts_5.15.tgz -d /dev/sdX
```
option3: Yocto rootfs contains Kernel Image,dtb, boot_scr already.
```
    $ flex-installer -f firmware_ls1043ardb_sdboot.img -r ls-image-main-ls1043ardb.tar.gz -m ls1043ardb -d /dev/sdX
```

Note: if you want customized partition, and less than 4P
```
    # To specify partition-3 for rootfs partition:
    $ flex-installer -i pf -d /dev/sdX -p 3P=2G:4G:-1
    $ flex-installer -f firmware_ls1043ardb_sdboot.img -r ls-image-main-ls1043ardb.tar.gz -d /dev/sdX --rootpart=3
    # under U-Boot prompt
    => setenv devpart_root 3
    => boot
```
```
    # To specify partition-2 for rootfs partition:
    $ flex-installer -i pf -p 2P=2G:-1 -d /dev/sdX
    $ flex-installer -f firmware_ls1043ardb_sdboot.img -r ls-image-main-ls1043ardb.tar.gz -d /dev/sdX --rootpart=2
    # under U-Boot prompt
    => setenv devpart_root 2
    => boot
```