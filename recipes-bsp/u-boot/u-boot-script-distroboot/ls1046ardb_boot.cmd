env exists dtb || setenv dtb fsl-ls1046a-rdb-sdk.dtb;
env exists kernel_image || setenv kernel_image Image;
env exists devpart_boot || setenv devpart_boot 1;
env exists devpart_root || setenv devpart_root 2;
part uuid $devtype $devnum:$devpart_root partuuidr;
setenv bootargs "console=ttyS0,115200 earlycon=uart8250,mmio,0x21c0500 root=PARTUUID=$partuuidr rw rootwait $othbootargs";
load $devtype $devnum:$devpart_boot $kernel_addr_r $kernel_image;
load $devtype $devnum:$devpart_boot $fdt_addr_r $dtb;
booti $kernel_addr_r - $fdt_addr_r