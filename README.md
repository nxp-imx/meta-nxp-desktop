i.MX Linux Yocto Project BSP for Desktop PoC (Proof of Concept)
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

Download the Yocto Project BSP
------------------------------

```
$ mkdir desktop
$ cd desktop
$ repo init -u https://source.codeaurora.org/external/imx/imx-manifest -b imx-linux-hardknott -m imx-5.10.52-2.1.0_desktop.xml
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

Use an _existing_ build folder
------------------------------

If you want to build in an _existing_ build folder:

```
$ cd desktop
$ source setup-environment build-desktop
```

Build the image
---------------

```
$ bitbake imx-image-desktop
```

The default PoC build will create the account "user" with the password "user" for desktop evaluation,
In order to change the account or password, uncomment and update APTGET_ADD_USER in <build_dir>/conf/local.conf.
