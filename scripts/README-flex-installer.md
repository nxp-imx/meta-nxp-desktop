# Flex-installer Userguide
## Download and Install
```
    $ git clone https://github.com/nxp-imx/meta-nxp-desktop.git -b lf-6.1.1-1.0.0-langdale
    $ cp meta-nxp-desktop/scripts/flex-installer_1.14.2110.lf  /usr/bin/flex-installer
    $ sudo chmod a+x /usr/bin/flex-installer
    $ flex-installer -v
```
## Steps of  boot on boards (e.g. ls1043ardb)
### Yocto Tiny System Test
option1: SD boot by firmware image
```
$ flex-installer -i pf -d /dev/sdX
$ flex-installer -f firmware_ls1043ardb_sdboot.img
# under U-Boot prompt
=> run sd_bootcmd      # (if the board need to deploy DPAA2 network ,please use "run bootcmd")
```
Option2: Ram boot by kernel itb
```
# tftp a0000000 kernel-fsl-ls1043a-rdb.itb
# bootm a0000000#ls1043ardb
```
### Ubuntu system Test
option1: Enter ubuntu system directly
```
$ flex-installer -i pf -d /dev/sdX
$ flex-installer -f firmware_ls1043ardb_sdboot.img -r ls-image-main-ls1043ardb.tar.gz -b boot_ls1043ardb_lts_6.1.tgz -d /dev/sdX
```
option2: Enter Yocto Tiny system firstly and then get into ubuntu system
```
$ flex-installer -i pf -d /dev/sdX
$ flex-installer -f firmware_ls1043ardb_sdboot.img
# boot into tiny system by firmware image
# Deploy ethernet to use wget
=> boot
# back to "Download and Install" section to install flex-installer
# download rootfs and boottgz on board
$ flex-installer -f firmware_ls1043ardb_sdboot.img -r ls-image-main-ls1043ardb.tar.gz -b boot_ls1043ardb_lts_6.1.tgz -d /dev/mcnblk0
```

### Flex-installer with Non-default Partition Test
Note: if you want customized partition, and less than 4P
```
# To specify partition-3 for rootfs partition:
$ flex-installer -i pf -d /dev/sdX -p 3P=2G:2G:-1
$ flex-installer -f firmware_ls1043ardb_sdboot.img -r ls-image-main-ls1043ardb.tar.gz -b boot_ls1043ardb_lts_6.1.tgz -d /dev/sdX --rootpart=3
# under U-Boot prompt
=> setenv devpart_root 3
=> boot
```
```
# To specify partition-2 for rootfs partition:
$ flex-installer -i pf -p 2P=2G:-1 -d /dev/sdX
$ flex-installer -f firmware_ls1043ardb_sdboot.img -r ls-image-main-ls1043ardb.tar.gz -b boot_ls1043ardb_lts_6.1.tgz -d /dev/sdX --rootpart=2
# under U-Boot prompt
=> setenv devpart_root 2
=> boot
```
