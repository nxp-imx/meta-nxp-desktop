Layerscape Yocto Project BSP for Linux distro PoC (Proof of Concept)
===============================================================

This README contains instructions for setting up a Yocto build
for the Linux distro PoC release image.

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
$ mkdir distro
$ cd distro
$ repo init -u https://source.codeaurora.org/external/qoriq/qoriq-components/yocto-sdk -b honister -m lf-5.15.5-1.0.0_distro.xml
$ repo sync
```

Start a build in distro folder
------------------------------

```
$ source  distro-setup-env -m <Machine>
```

Machine:

    lx2160ardb-rev2
    ls1028ardb
    ls1012ardb
    ls1012afrwy

Build the image
---------------

```
$ bitbake ls-image-main  # for all layerscape boards
```
Or

```
$ bitbake ls-image-desktop  # for ls1028a only
```

The default PoC build will create the account "user" with the password "user" for distro evaluation,
In order to change the account or password, uncomment and update APTGET_ADD_USERS in <build_dir>/conf/local.conf.
