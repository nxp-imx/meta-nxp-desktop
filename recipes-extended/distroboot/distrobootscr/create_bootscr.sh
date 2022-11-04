#!/bin/bash
set -xe

Usage()
{
    echo "Usage: $0 -m MACHINE   -d WORKDIR\

        -m        machine name
        -d        topdir
"
    exit
}

# get command options
while getopts "m:d:" flag
do
        case $flag in
                m) MACHINE="$OPTARG";
                   echo "machine: $MACHINE";
                   ;;
                d) D="$OPTARG";
                   echo "WORKDIR : $D";
                   ;;
                ?) Usage;
                   exit 3
                   ;;
        esac
done

generate_distro_bootscr(){
    . $MACHINE.manifest
    if [ -n "${distro_boot_script}" ]; then
        mkdir -p ${D}/boot
        echo "${distro_boot_script}" > distroscr.tmp
        mkimage -A arm64 -O linux -T script -C none -a 0 -e 0 -n "boot.scr" \
            -d distroscr.tmp ${D}/boot/${MACHINE}_boot.scr
    fi
}

generate_distro_bootscr
