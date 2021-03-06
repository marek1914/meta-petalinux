DESCRIPTION = "Video Codec Unit (VCU) for VCU in ZynqMP"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=03a7aef7e6f6a76a59fd9b8ba450b493"

BRANCH ?= "master"
REPO   ?= "git://github.com/Xilinx/vcu-ctrl-sw.git;protocol=https"
SRCREV = "88a5eaab24e7d435c10afcac78f757536b06a6ae"

BRANCHARG = "${@['nobranch=1', 'branch=${BRANCH}'][d.getVar('BRANCH', True) != '']}"
SRC_URI = "${REPO};${BRANCHARG}"

S  = "${WORKDIR}/git"

COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE_zynqmp = "zynqmp"

RDEPENDS_${PN} = "kernel-module-vcu"
LDFLAGS = "-lpthread"

EXTRA_OEMAKE = "CC='${CC}' CXX='${CXX} ${CXXFLAGS}' LDFLAGS='${LDFLAGS}'"

do_install() {
    install -d ${D}${bindir}
    install -d ${D}${libdir}
    install -d ${D}${includedir}/vcu-ctrl-sw/include

    cp -a --no-preserve=ownership ${S}/include/* ${D}${includedir}/vcu-ctrl-sw/include/

    install -m 0755 ${S}/bin/AL_Encoder.exe ${D}/${bindir}/
    install -m 0755 ${S}/bin/AL_Decoder.exe ${D}/${bindir}/
    oe_libinstall -C ${S}/bin/ -so liballegro_decode ${D}/${libdir}/
    oe_libinstall -C ${S}/bin/ -so liballegro_encode ${D}/${libdir}/
}

# These libraries shouldn't get installed in world builds unless something
# explicitly depends upon them.

EXCLUDE_FROM_WORLD = "1"
