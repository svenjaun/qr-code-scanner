package com.example.qr_code_scanner.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.qr_code_scanner.database.datatypes.QRCodeModel;
import java.util.ArrayList;

public class QRCodeData {
	@NonNull final String tableName = QRCodeDatabaseHelper.TABLE_NAME;
	private static QRCodeDatabaseHelper dbHelper;
	@NonNull final String[] columns = new String[]{QRCodeDatabaseHelper.COL_ID,
			QRCodeDatabaseHelper.COL_NAME,
			QRCodeDatabaseHelper.COL_VALUE,
			QRCodeDatabaseHelper.COL_DATE};

	public QRCodeData(@NonNull Context context) {
		dbHelper = QRCodeDatabaseHelper.getInstance(context);
	}

	@NonNull
	SQLiteDatabase getDb() {
		return dbHelper.getWritableDatabase();
	}

	public long createQRCode(@NonNull QRCodeModel qrcode) {
		long id = -1;
		ContentValues values = new ContentValues();
		values.put(QRCodeDatabaseHelper.COL_ID, qrcode.getID());
		values.put(QRCodeDatabaseHelper.COL_NAME, qrcode.getName());
		values.put(QRCodeDatabaseHelper.COL_VALUE, qrcode.getValue());
		values.put(QRCodeDatabaseHelper.COL_DATE,
				qrcode.getDate().getTimeInMillis());
		try {
			id = getDb().insert(tableName, null, values);
		} catch (SQLException e) {
			System.out.println("Database error while inserting a new QRCode: " + e);
		}
		return id;
	}

	@Nullable
	public QRCodeModel getCertificate(long id) {
		QRCodeModel result = null;
		try {
			Cursor cursor = getDb().query(tableName, columns,
					QRCodeDatabaseHelper.COL_ID + " = " + id,
					null, null, null, null, null);
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				result = cursorToCertificate(cursor);
			}
			cursor.close();
		} catch (SQLException e) {
			System.out.println("Database error while querying for certificate with id " + id + ": " + e);
		}
		return result;
	}

	@Nullable
	public QRCodeModel getQRCode(@NonNull String qrcodeId) {
		QRCodeModel result = null;
		try {
			Cursor cursor = getDb().query(tableName, columns,
					QRCodeDatabaseHelper.COL_ID +
							" = " + qrcodeId, null, null, null, null, null);
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				result = cursorToCertificate(cursor);
			}
			cursor.close();
		} catch (SQLException e) {
			System.out.println(	"Database error while querying for certificate with id '" + qrcodeId + "': s" + e);
		}
		return result;
	}

	@NonNull
	public ArrayList<QRCodeModel> getAllQRCodes() {
		ArrayList<QRCodeModel> result = new ArrayList<>();
		try {
			Cursor cursor = getDb().query(tableName, columns, null, null, null, null, null);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				result.add(cursorToCertificate(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		} catch (SQLException e) {
			System.out.println("Database error while querying all certificates: " + e);
		}
		return result;
	}

	private QRCodeModel cursorToCertificate(@NonNull Cursor cursor) {
		return new QRCodeModel(
				cursor.getInt(cursor.getColumnIndex(QRCodeDatabaseHelper.COL_ID)),
				cursor.getString(cursor.getColumnIndex(
						QRCodeDatabaseHelper.COL_NAME)),
				cursor.getString(cursor.getColumnIndex(
						QRCodeDatabaseHelper.COL_VALUE)),
				cursor.getLong(cursor.getColumnIndex(
						QRCodeDatabaseHelper.COL_DATE)));
	}

	public boolean removeQRCode(long id) {
		boolean success = false;
		try {
			QRCodeModel certificate = getCertificate(id);
			if (certificate == null) {
				return false;
			}
			success = getDb().delete(tableName, QRCodeDatabaseHelper.COL_ID + "=?",
					new String[]{String.valueOf(id)}) > 0;
		} catch (SQLException e) {
			System.out.println("Database error while deleting certificate with id '" + id + "': " + e);
		}
		return success;
	}
}