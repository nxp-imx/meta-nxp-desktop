#!/bin/bash
set -xe
Usage()
{
    echo "Usage: $0 -m MACHINE  -t BOOTTYPE -d TOPDIR -s DEPLOYDIR -e ENCAP -i IMA_EVM -o SECURE\

        -m        machine name
        -s        deploydir
"
    exit
}

# get command options
while getopts "m:d:s:" flag
do
        case $flag in
                m) MACHINE="$OPTARG";
                   echo "machine: $MACHINE";
                   ;;
                s) DEPLOY_DIR_IMAGE="$OPTARG";
                   echo "DEPLOY_DIR_IMAGE : $DEPLOY_DIR_IMAGE";
                   ;;
                ?) Usage;
                   exit 3
                   ;;
        esac
done

generate_qoriq_composite_firmware(){
    . $MACHINE.manifest
    . qoriq_memory_layout.cfg

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
                [ -z "COMPOSITE_IMG"$i"_OFFSET" ] && continue
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
                        echo -e "$i ${img_offset} <---> Writing ${img_file}"
                        if [ $i != 1 ]; then
                            if [ "${boottype}" = "sd" -o "${boottype}" = "emmc" ];then
                                img_offset=`printf "%d - %d\n" ${img_offset} ${MBRGPT_SIZE} | bc`
                            fi
                        fi
                        offset=`printf "%d / 1024\n" ${img_offset} | bc`
                        dd if=${DEPLOY_DIR_IMAGE}/${img_file} of=${fw_img} bs=1K seek=${offset} 2>/dev/null
                    else
                        echo -e "${DEPLOY_DIR_IMAGE}/${img_file} not found"
                    fi
                fi
            done
#            [ -f ${fw_img} ] && echo -e "${fw_img} [Done]"
        done
    done
    done
}

generate_boottgz(){
    img_dir=${DEPLOY_DIR_IMAGE}
    boot_dir=$img_dir/boot_LS_arm64_lts_5.15
    mkdir -p $boot_dir

    find $img_dir/*.dtb -type l | xargs -i cp {} $boot_dir

    find $img_dir/module* -type l | xargs -i tar -xvf {} -C $boot_dir
    mv $boot_dir/lib/* $boot_dir/
    rm -rf $boot_dir/lib

    find $img_dir/$kernel_img -type l | xargs -i cp {} $boot_dir/
    gzip -kc $boot_dir/$kernel_img > $boot_dir/$kernel_img.gz

    cp $img_dir/boot_scr/* $boot_dir

    tar -czvf $boot_dir.tgz -C $boot_dir/ .

    rm -rf $boot_dir
}

generate_qoriq_composite_firmware

generate_boottgz
