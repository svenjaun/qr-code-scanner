package com.example.qr_code_scanner.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qr_code_scanner.QrCodeListAdapter;
import com.example.qr_code_scanner.R;
import com.example.qr_code_scanner.database.QRCodeData;
import com.example.qr_code_scanner.database.datatypes.QRCodeModel;

import java.util.ArrayList;
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
    @Nullable private QrCodeListAdapter listAdapter;
    @BindView(R.id.list_fragment_recycler_view) RecyclerView recyclerView;


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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_edit, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
    public void onCreateViewHook(@NonNull FragmentActivity activity,
                                 @NonNull Context context, @NonNull View v,
                                 @Nullable Bundle savedInstanceState) {
        setListAdapter(activity, v);
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

    @NonNull
    private ArrayList<QRCodeModel> getQrCodes(@NonNull Context context) {
        QRCodeData certificateData = new QRCodeData(context);
        return certificateData.getAllQRCodes();
    }

    private void createQRCode(String name) {
        QRCodeData qrcodeData = new QRCodeData(getContext());
        qrcodeData.createQRCode(new QRCodeModel(qrcodeData.getLatestID(), name, value, new Date().getTime()));
    }

    private void setListAdapter(@NonNull FragmentActivity activity, @NonNull View view) {
        listAdapter = new QrCodeListAdapter(activity, getQrCodes(activity));
        listAdapter.registerAdapterDataObserver(
                new ListDataObserver(view, listAdapter));
        recyclerView.setAdapter(listAdapter);
    }
}

