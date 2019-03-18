package com.example.qr_code_scanner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

public class ListAdapter extends ArrayAdapter<QRCode> {
    public ListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
}
