env exists dtb || setenv dtb ls1021a-iot.dtb;
env exists kernel_image || setenv kernel_image uImage;
env exists devpart_boot || setenv devpart_boot 1;
env exists devpart_root || setenv devpart_root 2;
part uuid $devtype $devnum:$devpart_root partuuidr;
setenv bootargs "console=ttyS0,115200 cma=64M@0x0-0xb0000000 root=PARTUUID=$partuuidr rw rootwait $othbootargs";
load $devtype $devnum:$devpart_boot $kernel_addr_r $kernel_image;
load $devtype $devnum:$devpart_boot $fdt_addr_r $dtb;
bootm $kernel_addr_r - $fdt_addr_r