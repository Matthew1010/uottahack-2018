package uottawa.engineering.bda;

import android.*;
import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.vision.barcode.Barcode;
import org.json.JSONObject;

import uottawa.engineering.bda.objects.AuthToken;
import uottawa.engineering.bda.objects.WaitTime;
import uottawa.engineering.bda.tasks.GetTokenTask;
import uottawa.engineering.bda.tasks.GetWaitTimeTask;
import uottawa.engineering.bda.utils.Config;


public class MainActivity extends AppCompatActivity implements GetTokenTask.GetTokenWatcher, GetWaitTimeTask.TaskWatcher {

    Button scanbtn;
    TextView result;
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Config.getConfig(this);

        scanbtn = (Button) findViewById(R.id.scanbtn);
        result = (TextView) findViewById(R.id.result);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }
        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Scan.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            if(data != null){
                final Barcode barcode = data.getParcelableExtra("barcode");
                result.post(new Runnable() {
                    @Override
                    public void run() {
                        result.setText(barcode.displayValue);
                    }
                });
            }
        }
    }
}

        /*
        View btn1 = (View) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(), Scan.class);
                startActivity(i);
            }
        });
        */










    private void testJSON() {
        GetTokenTask tokenTask = new GetTokenTask(this);
        tokenTask.execute();
    }

    @Override
    public void setTokenResponse() {
        Log.i("MainActivity", "Token saved");
        GetWaitTimeTask timeTask = new GetWaitTimeTask(this);
        timeTask.execute(AuthToken.current());
    }

    @Override
    public void onWaitTimeReceive(WaitTime time) {
        Log.i("MainActivity", "Time: " + time);
    }
}
