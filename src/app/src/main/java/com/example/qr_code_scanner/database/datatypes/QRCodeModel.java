package com.example.qr_code_scanner.database.datatypes;
import android.support.annotation.NonNull;

import java.util.Calendar;

@SuppressWarnings("unused")
public class QRCodeModel {
	@NonNull private final int ID;
	@NonNull private final String Name;
	@NonNull private final String Value;
	@NonNull private final long Date;

	public QRCodeModel(@NonNull int ID, @NonNull String Name,
                       @NonNull String Value, @NonNull long Date) {
		this.ID = ID;
		this.Name = Name;
		this.Value = Value;
		this.Date = Date;
	}

	@NonNull
	public int getID() {
		return ID;
	}

	@NonNull
	public String getName() {
		return Name;
	}

	@NonNull
	public String getValue() {
		return Value;
	}

	@NonNull
	public Calendar getDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(Date);
		return calendar;
	}
}
