package uottawa.engineering.bda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public static class QrCode {
    private static String url = null;

    public static String read (AppCompatActivity activity, Bitmap code) {
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(activity).build();
        Frame myFrame = new Frame.Builder() .setBitmap(code) .build();
        SparseArray<Barcode> barcodes = barcodeDetector.detect(myFrame);
        return barcodes.valueAt(0).displayValue;
    }

    public static boolean sendRequest () {
        boolean sent = false;
        if (url != null) {
            sent = true;
            // faire le request ici
        }
        return sent;
    }
}