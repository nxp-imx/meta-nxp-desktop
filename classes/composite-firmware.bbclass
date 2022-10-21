# A generic function to create a NOR/QSPI/XSPI/NAND flash or SD/eMMC card composite firmware
# which consists of separately customizable images

# ${BOOTLOADER_TYPE} can be uboot, uefi
# ${BOOT_TYPE} can be sd, emmc, qspi, xspi, nor, nand, defined in ${MACHINE}.config
# In case of SD boot, the max size of MBR/GPT is 4k for QorIQ SoC or 1k/32k/33k for i.MX SoC
#
# MAINTAINER: Shengzhou Liu <shengzhou.liu@nxp.com>
#



create_composite_firmware() {
    [ -z "${BOOTLOADER_TYPE}" ] && BOOTLOADER_TYPE=uboot
    [ -z "${BOOT_TYPE}" ] && BOOT_TYPE=sd
    [ -z "${MBRGPT_SIZE}" ] && MBRGPT_SIZE=4096

    for bootloader in ${BOOTLOADER_TYPE}; do
	for boottype in ${BOOT_TYPE}; do
	    if [ "${MULTI_BOOTTYPE}" = "true" -a "${bootloader}" = "uefi" ]; then
	        [ $boottype = sd -o $boottype = emmc -o $boottype = nand ] && continue
	        blopt=_uefi
	    else
		blopt=""
	    fi
            for securetype in normal secure; do
		if [ "${securetype}" = "secure" ]; then
		    secopt=_sec
		    [ "$bootloader" = "uefi" ] && break
		else
		    secopt=""
		fi
                fw_img=${DEPLOY_DIR_IMAGE}/firmware_${MACHINE}_${boottype}boot${secopt}${blopt}.img
                rm -f ${fw_img} && img_offset="" && img_file=""

		for i in $(seq 1 ${MAX_COMPOSITE_IMG_NUM}); do
		    img_offset=$(eval echo '$'COMPOSITE_IMG"$i"_OFFSET)

		    if [ "${MULTI_BOOTTYPE}" = "true" ]; then
			[ "${securetype}" != "secure" -a $i = 4 ] && continue
		        if [ $i = 1 -o $i = 4 ]; then
		            img_file=$(eval echo '$'COMPOSITE_IMG"$i"_FILE_"$boottype""$secopt")
			elif [ "${bootloader}" = "uefi" ] && [ $i = 2 -o $i = 3 ]; then
			    img_file=$(eval echo '$'COMPOSITE_IMG"$i"_FILE_uefi)
		        else
			    img_file=$(eval echo '$'COMPOSITE_IMG"$i"_FILE_"$secopt")
		        fi
		        [ $i = 1 -a -z "${img_file}" ] && rm -f ${fw_img} && break
			if [ -z "${img_file}" -a "${securetype}" = "secure" ] && [ $i = 1 -o $i = 2 -o $i = 5 ]; then
			    img_file=$(eval echo '$'COMPOSITE_IMG"$i"_FILE_sec)
			fi
		    fi

		    [ -z "${img_file}" ] && img_file=$(eval echo '$'COMPOSITE_IMG"$i"_FILE)

                    if [ -n "${img_file}" ]; then
			if [ -e "${DEPLOY_DIR_IMAGE}/${img_file}" ]; then
			    bbnote "$i ${img_offset} <---> Writing ${img_file}"
			    if [ $i != 1 -a "${boottype}" = "sd" -o "${boottype}" = "emmc" ]; then
				img_offset=`printf "%d - %d\n" ${img_offset} ${MBRGPT_SIZE} | bc`
			    fi
			    offset=`printf "%d / 1024\n" ${img_offset} | bc`
                            dd if=${DEPLOY_DIR_IMAGE}/${img_file} of=${fw_img} bs=1K seek=${offset} 2>/dev/null
			else
			    bbnote "${DEPLOY_DIR_IMAGE}/${img_file} not found"
			fi
                    fi
		done
                [ -f ${fw_img} ] && bbnote "${fw_img} [Done]"
	    done
        done
    done
}
