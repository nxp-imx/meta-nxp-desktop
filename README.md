i.MX Linux Yocto Project BSP for Desktop
===========================================

This README contains instructions for setting up a Yocto build
for the Desktop release image.

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
$ repo init -u https://source.codeaurora.org/external/imx/imx-manifest -b imx-linux-hardknott -m imx-5.10.35-2.0.0_desktop.xml
$ repo sync
```

Create a _new_ build folder
---------------------------

If you want to create a _new_ build folder:

```
$ DISTRO=imx-desktop-xwayland MACHINE=imx8mpevkdesktop source imx-setup-desktop.sh -b build-desktop
```

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
