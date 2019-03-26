package com.example.qr_code_scanner.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qr_code_scanner.Fragments.EditFragment;
import com.example.qr_code_scanner.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    SurfaceView surfaceView;
    TextView txtBarcodeValue;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    EditFragment editFragment;
    FloatingActionButton floatingActionButton;
    Activity thisActivity = this;
    MainActivity thisMainActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        surfaceView = findViewById(R.id.qr_code_surfaceView);
        floatingActionButton = findViewById(R.id.qr_code_fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thisActivity, HistoryActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    //Configurations for QRCode-scanner
    private void initialiseDetectorsAndSources() {
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true)
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            // Ask for Camera rights
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(MainActivity.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            // Camera stops before the surface is being destroyed.
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            // If QRCode is scanned, open the edit_fragment with the value of the Code but if edit_fragment is already in the backstack: do nothing
            @SuppressLint("RestrictedApi")
            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                        editFragment = new EditFragment(barcodes.valueAt(0).displayValue, thisMainActivity);
                        getSupportFragmentManager().beginTransaction().addToBackStack("edit_fragment").setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left).replace(
                                R.id.fragment_holder, editFragment).commit();
                        floatingActionButton.setVisibility(View.INVISIBLE); // TODO: non-UI thread-- Future Android will throw an exception.
                        floatingActionButton.setEnabled(false);
                    }

                }
            }
        });
    }

    // Stops the camera and releases all Resources
    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    // Reloads the Camera
    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        floatingActionButton.setVisibility(View.VISIBLE);
        floatingActionButton.setEnabled(true);
    }
}
