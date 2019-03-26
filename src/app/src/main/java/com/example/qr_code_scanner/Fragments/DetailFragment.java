package com.example.qr_code_scanner.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qr_code_scanner.R;
import com.example.qr_code_scanner.database.QRCodeData;
import com.example.qr_code_scanner.database.datatypes.QRCodeModel;
import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;


@SuppressLint("ValidFragment")
public class DetailFragment extends Fragment {
	int qrCodeId;
	QRCodeModel qrCode;
	TextView detailCodeName;
	TextView detailCodeDate;
	TextView detailCodeValue;
	Button detailDelete;
	QRCodeData qrcodeData;


	ImageView detailQRCode;
    @SuppressLint("ValidFragment")
    public DetailFragment(int qrCodeId) {
        this.qrCodeId = qrCodeId;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        qrcodeData = new QRCodeData(getContext());
        qrCode = qrcodeData.getQRCode(qrCodeId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		detailQRCode = view.findViewById(R.id.detail_fragment_qr_code);
		detailQRCode.setImageBitmap(setQRCode(qrCode.getValue()));
		detailCodeName = view.findViewById(R.id.detail_fragment_code_name);
		detailCodeDate= view.findViewById(R.id.detail_fragment_date);
		detailCodeValue =view.findViewById(R.id.detail_fragment_code_content);
		detailDelete=view.findViewById(R.id.detail_fragment_delete);
		detailCodeName.setText(qrCode.getName());
		detailCodeDate.setText(qrCode.getDate().toString());
		detailCodeValue.setText(qrCode.getValue());
		detailDelete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				qrcodeData.removeQRCode(qrCodeId);
			}
		});
	}

	@Override
    public void onAttach(Context context) {
        super.onAttach(context);
		qrcodeData = new QRCodeData(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private Bitmap setQRCode(String value) {
        Bitmap qrcode;
        QRGEncoder qrgEncoder = new QRGEncoder(value, null, QRGContents.Type.TEXT, 200);
        try {
            // Getting QR-Code as Bitmap
            qrcode = qrgEncoder.encodeAsBitmap();
        } catch (WriterException e) {
            throw new Error("---------------------------------------------Error by generation QRCode: " + e);
        }
        return qrcode;
    }
}
