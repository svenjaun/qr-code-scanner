package dev.jaun.qr_code_scanner.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import dev.jaun.qr_code_scanner.Activities.MainActivity;
import dev.jaun.qr_code_scanner.Activities.HistoryActivity;
import dev.jaun.qr_code_scanner.R;
import dev.jaun.qr_code_scanner.database.QRCodeData;
import dev.jaun.qr_code_scanner.database.datatypes.QRCodeModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class EditFragment extends Fragment {
    RecyclerView recyclerView;
    private MainActivity mainActivity;
    private EditText editCodeName;
    private String value;

    public EditFragment() {

    }

    @SuppressLint("ValidFragment")
    public EditFragment(String value, MainActivity mainActivity) {
        this.value = value;
        this.mainActivity = mainActivity;
    }

    private QRCodeData qrcodeData;
    private long qrCodeID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        recyclerView = getActivity().findViewById(R.id.list_fragment_recycler_view);
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        editCodeName = view.findViewById(R.id.edit_fragment_code_name);
        TextView editDate = view.findViewById(R.id.edit_fragment_date);
        TextView editCodeContent = view.findViewById(R.id.edit_fragment_code_content);
        Button editSave = view.findViewById(R.id.edit_fragment_save);
        Button editCancel = view.findViewById(R.id.edit_fragment_cancel);
        Date date = Calendar.getInstance().getTime();
        editCodeName.setText("QR-Code" + qrcodeData.getLatestID());
        editCodeContent.setText(value);
        editDate.setText(date.toString());
        editCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
                mainActivity.findViewById(R.id.qr_code_fab).setVisibility(View.VISIBLE);
                mainActivity.findViewById(R.id.qr_code_fab).setEnabled(true);
            }
        });
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        SimpleDateFormat format = new SimpleDateFormat("EEEE dd.MMMM yyyy", Locale.GERMAN);
        editDate.setText(format.format(cal.getTime()));
        editSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editCodeName.getText().toString();
                if (name.trim().isEmpty()) {
                    name = "QR-Code" + qrcodeData.getLatestID();
                }
                qrCodeID = createQRCode(name);
                Intent intent = new Intent(getActivity(), HistoryActivity.class);
                getFragmentManager().popBackStack();
                mainActivity.findViewById(R.id.qr_code_fab).setVisibility(View.VISIBLE);
                mainActivity.findViewById(R.id.qr_code_fab).setEnabled(true);
                int i = (int) qrCodeID;
                if ((long) i != qrCodeID) {
                    throw new IllegalArgumentException(qrCodeID + " cannot be cast to int without changing its value.");
                }
                intent.putExtra("QRCodeID", i);
                startActivity(intent);
            }
        });
        super.onViewCreated(view, savedInstanceState);
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

    private long createQRCode(String name) {
        return qrcodeData.createQRCode(
                new QRCodeModel(qrcodeData.getLatestID(), name, value, new Date().getTime()));
    }
}

