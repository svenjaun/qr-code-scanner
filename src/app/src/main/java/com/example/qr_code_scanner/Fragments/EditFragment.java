package com.example.qr_code_scanner.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.qr_code_scanner.QrCodeListAdapter;
import com.example.qr_code_scanner.R;
import com.example.qr_code_scanner.database.QRCodeData;
import com.example.qr_code_scanner.database.datatypes.QRCodeModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class EditFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

	@BindView(R.id.edit_fragment_code_name) EditText editCodeName;
	@BindView(R.id.edit_fragment_date) TextView editDate;
	@BindView(R.id.edit_fragment_code_content) TextView editCodeContent;
	@BindView(R.id.edit_fragment_save) Button editSave;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String value;

    private OnFragmentInteractionListener mListener;

    public EditFragment() {

    }

    @SuppressLint("ValidFragment")
    public EditFragment(String value) {
        this.value = value;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
    }

	@NonNull
	private int getQrCodesId(@NonNull Context context) {
		QRCodeData qrCodeData = new QRCodeData(context);
		return qrCodeData.getLatestID();
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

    	Date date = Calendar.getInstance().getTime();
    	editCodeName.setText("QR-Code"+getQrCodesId(getContext()));
    	editCodeContent.setText(value);
    	editDate.setText(date.toString());
    	editSave.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    createQRCode(editCodeName.getText().toString());
		    }
	    });


        return inflater.inflate(R.layout.fragment_edit, container, false);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

	private void createQRCode(String name) {
        QRCodeData qrcodeData = new QRCodeData(getContext());
        qrcodeData.createQRCode(new QRCodeModel(qrcodeData.getLatestID(), name, value, new Date().getTime()));
    }

}

