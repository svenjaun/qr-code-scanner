package dev.jaun.qr_code_scanner.database.datatypes;
import android.graphics.Bitmap;

import com.google.zxing.WriterException;

import java.util.Calendar;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidx.annotation.NonNull;

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

	public Bitmap getQRCode() {
		Bitmap qrCode;
		QRGEncoder qrgEncoder = new QRGEncoder(Value, null, QRGContents.Type.TEXT, 500);
		try {
			qrCode = qrgEncoder.encodeAsBitmap();
		} catch (WriterException e) {
			throw new Error("Error while generating QRCode: " + e);
		}
		return qrCode;
	}
}
