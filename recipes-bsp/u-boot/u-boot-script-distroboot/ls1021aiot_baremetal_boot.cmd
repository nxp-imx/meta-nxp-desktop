setenv loadaddr       0x81000000;
setenv baremetal_addr 0x84000000;
setenv kernel_addr_r  0x86000000;
setenv fdt_addr_r     0x8f000000;
env exists dtb || setenv dtb ls1021a-iot-bm.dtb;
env exists kernel_image || setenv kernel_image uImage;
env exists devpart_boot || setenv devpart_boot 1;
env exists devpart_root || setenv devpart_root 2;
env exists baremetal_image || setenv baremetal_image bm-u-boot.bin;
part uuid $devtype $devnum:$devpart_root partuuidr;
setenv bootargs "console=ttyS0,115200 cma=64M@0x0-0xb0000000 root=PARTUUID=$partuuidr rw rootwait $othbootargs";
load $devtype $devnum:$devpart_boot $baremetal_addr $baremetal_image;
cpu start ${baremetal_addr};
load $devtype $devnum:$devpart_boot $kernel_addr_r $kernel_image;
load $devtype $devnum:$devpart_boot $fdt_addr_r $dtb;
bootm $kernel_addr_r - $fdt_addr_r