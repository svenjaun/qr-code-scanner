package dev.jaun.qr_code_scanner.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


public class QRCodeDatabaseHelper extends SQLiteOpenHelper {
	@Nullable
    private static QRCodeDatabaseHelper instance = null;
	@NonNull
    private static final String DATABASE_NAME = "QRCodeModel.db";
	private static final int DATABASE_VERSION = 1;

	@NonNull static final String TABLE_NAME = "QRCode";

	@NonNull public static final String COL_ID = "ID";
	@NonNull public static final String COL_NAME = "Name";
	@NonNull public static final String COL_VALUE = "Value";
	@NonNull public static final String COL_DATE = "Date";

	@NonNull private static final String TABLE_CREATE =
			"CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
			+ COL_ID + " integer primary key autoincrement, "
			+ COL_NAME + " text, "
			+ COL_VALUE + " text, "
			+ COL_DATE + " integer)";

	private QRCodeDatabaseHelper(@NonNull Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    static QRCodeDatabaseHelper getInstance(@NonNull Context context) {
		if (instance == null) {
			instance = new QRCodeDatabaseHelper(context);
		}
		return instance;
	}
}