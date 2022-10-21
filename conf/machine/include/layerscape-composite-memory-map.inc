# The unified 64MiB memory layout of SD/NOR/QSPI/XSPI/NAND device on all NXP QorIQ Reference boards
# see meta-freescale/docs/qoriq_memory_layout.txt

# user can customize the composite firmware in ${MACHINE}.conf by overriding the default settings 

# QorIQ supports multiple boot type: sd boot, nand boot, qspi boot, flexspi-nor(xspi) boot, ifc-nor boot
MULTI_BOOTTYPE ?= "true"

# bootloader type: uboot or uefi, some boards support both
BOOTLOADER_TYPE ?= "uboot"

# Maximum number of separate images in the composite firmware
MAX_COMPOSITE_IMG_NUM               ?= "15"

# ATF BL2 with RCW/PBL
export COMPOSITE_IMG1_OFFSET        ?= "0x00000000"
export COMPOSITE_IMG1_FILE_sd       ?= "atf/bl2_sd.pbl"
export COMPOSITE_IMG1_FILE_sd_sec   ?= ""
export COMPOSITE_IMG1_FILE_nor      ?= ""
export COMPOSITE_IMG1_FILE_nor_sec  ?= ""
export COMPOSITE_IMG1_FILE_qspi     ?= ""
export COMPOSITE_IMG1_FILE_qspi_sec ?= ""
export COMPOSITE_IMG1_FILE_xspi     ?= ""
export COMPOSITE_IMG1_FILE_xspi_sec ?= ""

# ATF BL33 with bootloader (U-Boot or UEFI)
export COMPOSITE_IMG2_OFFSET        ?= "0x00100000"
export COMPOSITE_IMG2_FILE          ?= "atf/fip_uboot.bin"
export COMPOSITE_IMG2_FILE_sec      ?= "atf/fip_uboot_sec.bin"
export COMPOSITE_IMG2_FILE_uefi     ?= ""

# Bootloader environment
export COMPOSITE_IMG3_OFFSET        ?= "0x00500000"
export COMPOSITE_IMG3_FILE          ?= ""

# Secure boot header
export COMPOSITE_IMG4_OFFSET        ?= "0x00600000"
export COMPOSITE_IMG4_FILE_sd       ?= ""
export COMPOSITE_IMG4_FILE_nor      ?= ""
export COMPOSITE_IMG4_FILE_qspi     ?= ""
export COMPOSITE_IMG4_FILE_xspi     ?= ""

# DDR PHY FW or reserved
export COMPOSITE_IMG5_OFFSET        ?= "0x00800000"
export COMPOSITE_IMG5_FILE          ?= ""

# Fuse provisioning hdr or reserved
export COMPOSITE_IMG6_OFFSET        ?= "0x00880000"
export COMPOSITE_IMG6_FILE          ?= ""
export COMPOSITE_IMG6_FILE_sec      ?= ""

# DPAA FMAN ucode or reserved
export COMPOSITE_IMG7_OFFSET        ?= "0x00900000"
export COMPOSITE_IMG7_FILE          ?= ""

# QE ucode or DP firmware
export COMPOSITE_IMG8_OFFSET        ?= "0x00940000"
export COMPOSITE_IMG8_FILE          ?= ""

# Ethernet PHY firmware             
export COMPOSITE_IMG9_OFFSET        ?= "0x00980000"
export COMPOSITE_IMG9_FILE          ?= ""

# U-boot script or reserved
export COMPOSITE_IMG10_OFFSET       ?= "0x009C0000"
export COMPOSITE_IMG10_FILE         ?= ""

# DPAA2-MC or PFE firmware
export COMPOSITE_IMG11_OFFSET       ?= "0x00A00000"
export COMPOSITE_IMG11_FILE         ?= ""

# DPAA2 DPL or reserved
export COMPOSITE_IMG12_OFFSET       ?= "0x00D00000"
export COMPOSITE_IMG12_FILE         ?= ""

# DPAA2 DPC or reserved
export COMPOSITE_IMG13_OFFSET       ?= "0x00E00000"
export COMPOSITE_IMG13_FILE         ?= ""

# Device tree dtb or reserved
export COMPOSITE_IMG14_OFFSET       ?= "0x00F00000"
export COMPOSITE_IMG14_FILE         ?= ""

# Kernel itb with tiny initramfs    
export COMPOSITE_IMG15_OFFSET       ?= "0x01000000"
export COMPOSITE_IMG15_FILE         ?= "linux_layerscape_tiny_arm64.itb"