# Flex-installer Userguide

Step1: Install flex-installer shell srcipt
```
    $ wget http://shlinux12.ap.freescale.net/temp/flex-installer/flex-installer
    $ chmod a+x flex-installer
    $ mv flex-installer /bin/
    $ flex-install --help
```
Step2: Download firmware and rootfs (e.g:ls1028radb)
```
    $ wget http://shlinux22.ap.freescale.net/internal-only/Linux_IMX_5.15_Desktop/144/layerscape/ls1028ardb/firmware_ls1028ardb_sdboot.img
    $ wget http://shlinux22.ap.freescale.net/internal-only/Linux_IMX_5.15_Desktop/144/layerscape/ls1028ardb/ls-image-desktop-ls1028ardb.tar.gz
    $ wget http://shlinux22.ap.freescale.net/internal-only/Linux_IMX_5.15_Desktop/144/layerscape/ls1028ardb/boot_LS_arm64_lts_5.15.tgz
```
If not have boot_LS_arm64_lts_5.15.tgz, you should tarball manually.
```
    $ mkdir -p boot_tgz
    $ cd boot_tgz
    $ wget http://shlinux22.ap.freescale.net/internal-only/Linux_IMX_5.15_Desktop/144/layerscape/ls1028ardb/Image
    $ tar -cvf Image.gz Image
    $ wget http://shlinux22.ap.freescale.net/internal-only/Linux_IMX_5.15_Desktop/144/layerscape/ls1028ardb/boot_scr/*
    $ wget https://shlinux22.ap.freescale.net/internal-only/Linux_IMX_5.15_Desktop/144/layerscape/ls1028ardb/modules-ls1028ardb.tgz
    $ tar -cxvf modules-ls1028ardb.tgz
    $ mv lib/* ./;rm -rf lib
    $ tar -czf boot_LS_arm64_lts_5.15.tgz ./* ;mv boot_LS_arm64_lts_5.15.tgz ../
    $ rm -rf boot_tgz
```
Step3: Burn to SD card(e.g:MACHINE=1028ardb)
```
    $ flex-installer -i pf -d /dev/sdX;
```
option1: Yocto rootfs contains Kernel Image,dtb, boot_scr already.
```
    $ flex-installer -f firmware_ls1028ardb_sdboot.img -r ls-image-main-ls1028ardb.tar.gz -m ls1028ardb -d /dev/sdX
```
option2: Kernel Image,dtb, boot_scr also in boot_LS_arm64_lts_5.15.tgz,you can burn them into the first bootpartition.
```
    $ flex-installer -f firmware_ls1028ardb_sdboot.img -r ls-image-main-ls1028ardb.tar.gz -b boot_LS_arm64_lts_5.15.tgz -d /dev/sdX
```

More info: http://shlinux12.ap.freescale.net/temp/flex-installer/README.md 
