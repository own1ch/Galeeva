package com.eremin.galeeva;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private ZXingScannerView mScannerView;
    private static final String TAG = "result";
    //Database database;
    int countOfTouches = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mScannerView = new ZXingScannerView(this);
        checkPermissionCamera();
        checkPermissionData();
        LinearLayout lr = findViewById(R.id.camera);
        lr.addView(mScannerView);
        //database = new Database(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                countOfTouches++;
                if(countOfTouches >= 3) {
                    countOfTouches = 0;
                    Intent intent = new Intent(this, AdminActivity.class);
                    startActivity(intent);
                }
        }
        return super.onTouchEvent(event);
    }



    private void checkPermissionCamera() {
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if(permission == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    private void checkPermissionData() {
        int permissionData = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissionData == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        Database database = new Database(this);
        database.updateLastInto(Integer.parseInt(rawResult.getText()));
        mScannerView.resumeCameraPreview(this);
        Toast.makeText(this, database.getName() + " пожалуйста проходите!", Toast.LENGTH_LONG).show();
    }
}
