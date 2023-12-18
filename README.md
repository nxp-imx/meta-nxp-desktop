NXP Linux Yocto Project BSP for Desktop PoC (Proof of Concept)
===============================================================

This README contains instructions for setting up a Yocto build
for the Desktop PoC release image.

Install the `repo` utility
--------------------------

To get the BSP you need to have `repo` installed.

```
$ mkdir ~/bin
$ curl https://storage.googleapis.com/git-repo-downloads/repo  > ~/bin/repo
$ chmod a+x ~/bin/repo
$ PATH=${PATH}:~/bin
```

Download the Yocto Project BSP for i.MX
------------------------------

```
$ mkdir desktop
$ cd desktop
$ repo init -u https://github.com/nxp-imx/imx-manifest.git -b imx-linux-mickledore -m imx-6.1.55-2.2.0_desktop.xml
$ repo sync
```

Create a _new_ build folder
---------------------------

If you want to create a _new_ build folder:

```
$ DISTRO=imx-desktop-xwayland MACHINE=imx8mpevk source imx-setup-desktop.sh -b build-desktop
```

Note: The available build MACHINEs for below boards:

    imx8mpevk -  i.MX8MP-EVK
    imx8mqevk -  i.MX8MQ-EVK
    imx8mmevk -  i.MX8MM-EVK
    imx8mnevk -  i.MX8MN-EVK
    imx8qmmek -  i.MX8QM-MEK
    imx8ulpevk -  i.MX8ULP-EVK
    imx8qxpc0mek -  i.MX8QXP-MEK with silicon revision C0 chip

Use an _existing_ build folder
------------------------------

If you want to build in an _existing_ build folder:

```
$ cd desktop
$ source setup-environment build-desktop
```

Build the image for i.MX
---------------

```
$ bitbake imx-image-desktop
```


Download the Yocto Project BSP for Layerscape
------------------------------

```
$ mkdir distro
$ cd distro
$ repo init -u https://github.com/nxp-qoriq/yocto-sdk.git -b mickledore -m ls-6.1.55-2.2.0_distro.xml
$ repo sync
```

Start a layerscape build in distro folder
------------------------------

```
$ source  distro-setup-env -m <Machine>
```

Machine:

    ls1012afrwy
    ls1012ardb
    ls1021atwr
    ls1028ardb
    ls1043ardb
    ls1046afrwy
    ls1046ardb
    ls1088ardb
    ls2088ardb
    lx2160ardb-rev2
    lx2162aqds

Build the layerscape image
---------------

```
$ bitbake ls-image-main  # build main image for networking feature
```

```
$ bitbake ls-image-desktop  # build desktop image for ls1028a only
```

```
$ bitbake ls-image-lite # build lite image with the optimized config
```

```
# generate composite firmware for  Layerscape machines
$ bitbake qoriq-composite-firmware
$ bitbake generate-boottgz
```


Notice
---------------
The default PoC build will create the account "user" with the password "user" for desktop evaluation.<br />

If you want to add multiple account, you can set as below in <build_dir>/conf/local.conf. (every user setting should be splitted by a space).

```
APTGET_ADD_USERS:append = " user1:<password hash>:${USER_SHELL_BASH} user2:<password hash>:${USER_SHELL_BASH}"
```

How to generate password hash value?
LDP system passwrod generated by openssl algorithm，the default algorithm is MD5, such as:
```
$ openssl passwd -1 ab123
```
Then return value is "$1$FP6PEJWC$e7l2p4w5ek4XDRRrdAwIo/" from terminal, write this value in local.conf
```
APTGET_ADD_USERS:append = " user1:\$1\$FP6PEJWC\$e7l2p4w5ek4XDRRrdAwIo/:${USER_SHELL_BASH}"
```

**Important: if the value contain "$", it should escape with "\\$".**

After build image, you can log in with "user1:ab123" or "user:user"
