package uottawa.engineering.bda;

import android.*;
import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.widget.TextView;
import android.view.SurfaceView;
import android.graphics.Camera;




import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;




public class Scan extends AppCompatActivity {

    SurfaceView cameraPreview;
    TextView txtResult;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);


        cameraPreview = (SurfaceView)findViewById(R.id.cameraPreview);
        txtResult = (TextView)findViewById(R.id.msgTxt);

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        cameraSource = new CameraSource
                .Builder(this,barcodeDetector)
                .setRequestedPreviewSize(640,480)
                .build();

        //Add Event
        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder surfaceholder){
                
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2){

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder){

            }




        });



        /*
        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceholder){
                if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)){
                    //Request permission
                    ActivityCompat.requestPermissions(Scan.this,
                            new String[]{Manifest.permission.CAMERA},RequestCameraPermissionID);
                    return;
                }
                cameraSource.start(cameraPreview.getHolder());
            }


            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, inti2){

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder){

            }
        });
        */




    }
}
