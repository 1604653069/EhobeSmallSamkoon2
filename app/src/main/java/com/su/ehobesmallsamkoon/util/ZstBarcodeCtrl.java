package com.su.ehobesmallsamkoon.util;

public class ZstBarcodeCtrl {

    private static ZstBarcodeCtrl mZstBarcodeCtrl = null;

    private ZstBarcodeCtrl() {
    }

    public static ZstBarcodeCtrl getInstance() {
        if (mZstBarcodeCtrl == null) {
            synchronized (ZstBarcodeCtrl.class) {
                if (mZstBarcodeCtrl == null)
                    mZstBarcodeCtrl = new ZstBarcodeCtrl();
            }
        }
        return mZstBarcodeCtrl;
    }

    public void closeZstBarcodeCtrl() {
        if (mZstBarcodeCtrl != null) {
            mZstBarcodeCtrl = null;
        }
    }

    public native void set_gpio(int state, int pin_num);

    static {
        System.loadLibrary("gpio");
    }

}