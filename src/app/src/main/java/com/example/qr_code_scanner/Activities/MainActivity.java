package com.example.qr_code_scanner.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

	SurfaceView surfaceView;
	TextView txtBarcodeValue;
	private BarcodeDetector barcodeDetector;
	private CameraSource cameraSource;
	private static final int REQUEST_CAMERA_PERMISSION = 201;
	EditFragment editFragment;
	FloatingActionButton floatingActionButton;
	Activity thisActyvity = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initViews();
	}

	private void initViews() {
		txtBarcodeValue = findViewById(R.id.qr_code_no_code_text);
		surfaceView = findViewById(R.id.qr_code_surfaceView);
		floatingActionButton = findViewById(R.id.qr_code_fab);

		//TODO use for opening link in detail view
                /*if (intentData.length() > 0) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(intentData)));
                }*/

		floatingActionButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(thisActyvity, HistoryActivity.class);
				startActivity(intent);
			}
		});
	}

	private void initialiseDetectorsAndSources() {

		Toast.makeText(getApplicationContext(), "Barcode scanner started",
				Toast.LENGTH_SHORT).show();

		barcodeDetector = new BarcodeDetector.Builder(this)
				.setBarcodeFormats(Barcode.ALL_FORMATS)
				.build();

		cameraSource = new CameraSource.Builder(this, barcodeDetector)
				.setRequestedPreviewSize(1920, 1080)
				.setAutoFocusEnabled(true) //you should add this feature
				.build();

		surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
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

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				cameraSource.stop();
			}
		});


		barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
			@Override
			public void release() {
				Toast.makeText(getApplicationContext(),
						"To prevent memory leaks barcode scanner has been stopped",
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void receiveDetections(Detector.Detections<Barcode> detections) {
				final SparseArray<Barcode> barcodes = detections.getDetectedItems();
				if (barcodes.size() != 0) {
					editFragment = new EditFragment(barcodes.valueAt(0).displayValue);
					getSupportFragmentManager().beginTransaction().replace(
							R.id.fragment_holder, editFragment).commit();
				}
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		cameraSource.release();
	}

	@Override
	protected void onResume() {
		super.onResume();
		initialiseDetectorsAndSources();
	}
}
