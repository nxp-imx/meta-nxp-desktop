setenv loadaddr       0x81000000;
setenv baremetal_addr 0x84000000;
setenv kernel_addr_r  0x86000000;
setenv fdt_addr_r     0x90000000;
env exists dtb || setenv dtb fsl-lx2160a-rdb.dtb;
env exists kernel_image || setenv kernel_image Image;
env exists devpart_boot || setenv devpart_boot 1;
env exists devpart_root || setenv devpart_root 2;
env exists baremetal_image || setenv baremetal_image bm-u-boot.bin;
part uuid $devtype $devnum:$devpart_root partuuidr;
setenv bootargs "console=ttyAMA0,115200 earlycon=pl011,mmio32,0x21c0000 root=PARTUUID=$partuuidr rw rootwait pci=pcie_bus_perf maxcpus=1 memmap=1152M$0xb0000000 $othbootargs";
load $devtype $devnum:$devpart_boot $baremetal_addr $baremetal_image;
cpu start ${baremetal_addr};
load $devtype $devnum:$devpart_boot $kernel_addr_r $kernel_image;
load $devtype $devnum:$devpart_boot $fdt_addr_r $dtb;
booti $kernel_addr_r - $fdt_addr_r