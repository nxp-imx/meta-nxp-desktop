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
$ repo init -u https://github.com/nxp-imx/imx-manifest.git -b imx-linux-kirkstone -m imx-5.15.52-2.1.0_desktop.xml
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
$ repo init -u https://github.com/nxp-qoriq/yocto-sdk.git -b kirkstone -m ls-5.15.52-2.1.0_distro.xml
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
Or

```
$ bitbake ls-image-desktop  # build desktop image for ls1028a only
```

```
$ bitbake ls-image-lite # build lite image with the optimized config
```

```
$ bitbake ls-image-tiny # build yocto tiny image with limited tools
```


The default PoC build will create the account "user" with the password "user" for desktop evaluation,
In order to change the account or password, uncomment and update APTGET_ADD_USERS in <build_dir>/conf/local.conf.
