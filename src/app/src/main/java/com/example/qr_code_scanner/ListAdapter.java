package com.example.qr_code_scanner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.example.qr_code_scanner.database.datatypes.QRCodeModel;

public class ListAdapter extends ArrayAdapter<QRCodeModel> {
    public ListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
}
