package com.example.qr_code_scanner;

import android.content.Context;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewHolder extends RecyclerView.ViewHolder {
	@NonNull private Context context;


	@BindView(R.id.qr_code_surfaceView) SurfaceView scanner;
	@BindView(R.id.qr_code_no_code_text) TextView qrCodeTextview;
	@BindView(R.id.qr_code_fab) FloatingActionButton floatingActionButton;
	@BindView(R.id.list_element_qr_code) ImageView listElementQrCode;
	@BindView(R.id.list_element_qr_code_name) TextView listElementNameTextView;
	@BindView(R.id.list_element_qr_code_date) TextView listEllementDateTextView;
	@BindView(R.id.detail_fragment_qr_code) ImageView detailQrCode;
	@BindView(R.id.detail_fragment_code_name) TextView detailCodeName;
	@BindView(R.id.detail_fragment_date) TextView detailDate;
	@BindView(R.id.detail_fragment_code_content) TextView detailCodeContent;
	@BindView(R.id.list_fragment_empty) LinearLayout listFragmentempty;

	public ViewHolder(@NonNull Context context, @NonNull View view) {
		super(view);
		ButterKnife.bind(this, view);
		this.context = context;
	}

	@NonNull
	public Context getContext() {
		return context;
	}

	public SurfaceView getScanner() {
		return scanner;
	}

	public TextView getQrCodeTextview() {
		return qrCodeTextview;
	}

	public FloatingActionButton getFloatingActionButton() {
		return floatingActionButton;
	}

	public ImageView getListElementQrCode() {
		return listElementQrCode;
	}

	public TextView getListElementNameTextView() {
		return listElementNameTextView;
	}

	public TextView getListEllementDateTextView() {
		return listEllementDateTextView;
	}

	public ImageView getDetailQrCode() {
		return detailQrCode;
	}

	public TextView getDetailCodeName() {
		return detailCodeName;
	}

	public TextView getDetailDate() {
		return detailDate;
	}

	public TextView getDetailCodeContent() {
		return detailCodeContent;
	}

}
