package com.example.qr_code_scanner;

import android.app.Activity;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qr_code_scanner.database.datatypes.QRCodeModel;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class QrCodeListAdapter extends RecyclerView.Adapter<ViewHolder> {
	@NonNull private Activity activity;
	@NonNull private ArrayList<QRCodeModel> qrCodes;

	public QrCodeListAdapter(@NonNull Activity activity, @NonNull ArrayList<QRCodeModel> qrCodes) {
		this.activity = activity;
		this.qrCodes = qrCodes;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		View v = LayoutInflater.from(activity).inflate(R.layout.list_element, viewGroup, false);
		return new ViewHolder(activity,v);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
		QRCodeModel qrCode = qrCodes.get(i);
		setViews(viewHolder,qrCode);
	}

	@Override
	public int getItemCount() {
		return qrCodes.size();
	}

	private void setViews(@NonNull ViewHolder viewHolder,@NonNull QRCodeModel qrCode) {
		viewHolder.getListElementNameTextView()
				.setText(qrCode.getName());
		viewHolder.getListEllementDateTextView()
				.setText(qrCode.getDate().toString());
	}

	@NonNull
	public ArrayList<QRCodeModel> getQrCodes() {
		return qrCodes;
	}
}
