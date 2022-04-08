require conf/machine/include/utilities.inc

# short-description: Create eMMC/SD card image with a boot partition
#
# Offsets:
#   * RCW+PBI+BL2 at block 8
#   * PFE firmware at block 0x100
#   * FIP (BL31+BL32+BL33) at 0x800
#   * Env variables at 0x2800
#   * Secureboot headers at 0x3000
#   * DDR PHY FIP at 0x4000
#   * DPAA1 FMAN ucode at 0x4800
#   * DPAA2-MC at 0x5000
#   * DPAA2 DPL at 0x6800
#   * DPAA2 DPC at 0x7000
#   * Device tree (UEFI) at 0x7800
#   * Kernel at 0x8000
#   * Ramdisk at 0x10000
#
# The disk layout (in blocks) used is:
#  blocks                           KiB
#     0x0 +-----------------------+    0x0
#         | MBR / GPT ...         |
#     0x8 +-----------------------+    0x4
#         | RCW + PBI + BL2       |
#   0x100 +-----------------------+   0x80
#         | PFE firmware          |
#   0x800 +-----------------------+  0x400
#         | FIP (BL31+BL32+BL33)  |
#  0x2800 +-----------------------+ 0x1400
#         | U-Boot Env variables  |
#  0x3000 +-----------------------+ 0x1800
#         | Secureboot headers    |
#  0x4000 +-----------------------+ 0x2000
#         | DDR PHY FIP           |
#  0x4800 +-----------------------+ 0x2400
#         | DPAA1 FMAN ucode      |
#  0x5000 +-----------------------+ 0x2800
#         | DPAA2-MC              |
#  0x6800 +-----------------------+ 0x3400
#         | DPAA2 DPL             |
#  0x7000 +-----------------------+ 0x3800
#         | DPAA2 DPC             |
#  0x7800 +-----------------------+ 0x3c00
#         | Device tree (UEFI)    |
#  0x8000 +-----------------------+ 0x4000
#         | Kernel                |
# 0x10000 +-----------------------+ 0x8000
#         | Ramdisk               |
#         +-----------------------+
#

# offset(1K) number in beginning 64MB space of SD/eMMC card on all QorIQ platforms

SD_PBL_OFFSET  ?= "4"
SD_FIP_OFFSET  ?= "1024"
SD_UBOOT_ENV_OFFSET  ?= "5120"
SD_SECUREBOOT_HEADERS_OFFSET  ?= "6144"
SD_DDR_PHY_FW_OFFSET  ?= "8192"
SD_FUSE_HEADERS_OFFSET  ?= "8704"
SD_FMAN_UCODE_OFFSET  ?= "9216"
SD_QE_DP_FW_OFFSET  ?= "9472"
SD_ETH_PHY_FW_OFFSET  ?= "9728"
SD_UBOOT_SCR_OFFSET  ?= "9984"
SD_DPAA2_MC_FW_OFFSET  ?= "10240"
SD_DPAA2_MC_DPL_OFFSET  ?= "13312"
SD_DPAA2_MC_DPC_OFFSET  ?= "14336"
SD_DEVICE_TREE_OFFSET  ?= "15360"
SD_KERNEL_OFFSET  ?= "16384"
SD_KERNELITB_OFFSET  ?= "16384"
SD_RAMDISKRFS_OFFSET  ?= "17408"

# offset(1K) number in beginning 64MB space of QSPI/XSPI/NAND flash on all QorIQ platforms

FLASH_PBL_OFFSET  ?= "0"
FLASH_FIP_OFFSET  ?= "1024"
FLASH_UBOOT_ENV_OFFSET  ?= "5120"
FLASH_SECUREBOOT_HEADERS_OFFSET  ?= "6144"
FLASH_DDR_PHY_FW_OFFSET  ?= "8192"
FLASH_FUSE_HEADERS_OFFSET  ?= "8704"
FLASH_FMAN_UCODE_OFFSET  ?= "9216"
FLASH_QE_DP_FW_OFFSET  ?= "9472"
FLASH_ETH_PHY_FW_OFFSET  ?= "9728"
FLASH_UBOOT_SCR_OFFSET  ?= "9984"
FLASH_PFE_OFFSET ?= "10240"
FLASH_DPAA2_MC_FW_OFFSET  ?= "10240"
FLASH_DPAA2_MC_DPL_OFFSET  ?= "13312"
FLASH_DPAA2_MC_DPC_OFFSET  ?= "14336"
FLASH_DEVICE_TREE_OFFSET  ?= "15360"
FLASH_KERNEL_OFFSET  ?= "16384"
FLASH_KERNELITB_OFFSET  ?= "16384"
FLASH_RAMDISKRFS_OFFSET  ?= "17408"

# Defaualt Firmware setting
PBL_FILE ?= "atf/bl2_sd.pbl"
FIP_FILE ?= "atf/fip_uboot.bin"
DDR_PHY_FILE ?= ""
QE_DP_FILE ?= ""
FMAN_UCODE_FILE ?= ""
ETH_PHY_FILE ?= ""
PFE_FILE ?= ""
MCFW_FILE ?= ""
DPL_FILE ?= ""
DPC_FILE ?= ""
ITB_FILE ?= "fitImage"

WKS_FILE ?= ""

# lx2160a
DDR_PHY_FILE:lx2160a ?= "ddr-phy/fip_ddr_all.bin"
ETH_PHY_FILE:lx2160a ?= "inphi/in112525-phy-ucode.txt"
MCFW_FILE:lx2160a ?= "mc_app/mc.itb"
DPL_FILE:lx2160a ?= "mc-utils/dpl-eth.19.dtb"
DPC_FILE:lx2160a ?= "mc-utils/dpc-usxgmii.dtb"

WKS_FILE:lx2160a ?= "qoriq-lx2160a.wks.in"

# ls1043a
FMAN_UCODE_FILE:ls1043a ?= "fsl_fman_ucode_ls1043_r1.1_108_4_9.bin"
ETH_PHY_FILE:ls1043a ?= "ls2-phy/cs4315-cs4340-PHY-ucode.txt"

WKS_FILE:ls1043a ?= "qoriq-ls104xa.wks.in"


# ls1046a
FMAN_UCODE_FILE:ls1046a ?= "fsl_fman_ucode_ls1046_r1.0_106_4_18.bin"
ETH_PHY_FILE:ls1046a ?= "ls2-phy/cs4315-cs4340-PHY-ucode.txt"

WKS_FILE:ls1046a ?= "qoriq-ls104xa.wks.in"

# ls1028a
QE_DP_FILE:ls1028a ?= "dp/ls1028a-dp-fw.bin"

WKS_FILE:ls1028a ?= "qoriq-ls1028a.wks.in"

# ls102xa
QE_DP_FILE:ls102xa ?= "boot/iram_Type_A_LS1021a_r1.0.bin"
WKS_FILE:ls102xa ?= "qoriq-ls102xa.wks.in"

# ls1021atwr
RCW_FILE:ls1021atwr = "rcw/ls1021atwr/SSR_PNS_30/rcw_1200_sdboot.bin"
UBOOT_FILE:ls1021atwr = "u-boot.bin-sdcard-qspi"

# ls1021atsn
RCW_FILE:ls1021atsn = "rcw/ls1021atsn/SSR_PNS_30/rcw_1200_sdboot.bin"
UBOOT_FILE:ls1021atsn = "u-boot.bin-sdcard"

# ls1021aiot
UBOOT_FILE:ls1021aiot ?= "${@bb.utils.contains('DISTRO_FEATURES', \
                          'baremetal', \
                          'u-boot.bin', \
                          'u-boot.bin-sdcard', d)}"
WKS_FILE:ls1021aiot ?= "qoriq-ls1021aiot.wks.in"

# ls1012a
FLASH_PFE_OFFSET:ls1012afrwy ?= "128"
FLASH_FIP_OFFSET:ls1012afrwy ?= "384"

PBL_FILE:ls1012a ?= "atf/bl2_qspi.pbl"
PFE_FILE:ls1012a ?= "engine-pfe-bin/pfe_fw_sbl.itb"

WKS_FILE:ls1012a ?= "qoriq-ls1012a.wks.in"

# Do not update fstab file when using wic images
# WIC_CREATE_EXTRA_ARGS ?= "--no-fstab-update"

IMAGE_BOOT_FILES ?= " \
    ${KERNEL_IMAGETYPE} \
    ${@make_dtb_boot_files(d)} \
"

DISTRO_BOOT_SCR_FILE ?= "${MACHINE}_boot.scr"
DISTRO_BOOT_SCR_FILE:lx2160ardb-rev2 ?= "lx2160ardb_boot.scr"

IMAGE_BOOT_FILES:append = " ${DISTRO_BOOT_SCR_FILE}"
WKS_FILE_DEPENDS:append = " u-boot-script-distroboot"

IMAGE_BOOT_FILES:remove:ls1012a = " ${DISTRO_BOOT_SCR_FILE}"
WKS_FILE_DEPENDS:remove:ls1012a = " u-boot-script-distroboot"
