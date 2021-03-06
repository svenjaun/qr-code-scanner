package dev.jaun.qr_code_scanner.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import dev.jaun.qr_code_scanner.database.datatypes.QRCodeModel;

public class QRCodeData {
    @NonNull
    private final String tableName = QRCodeDatabaseHelper.TABLE_NAME;
    private static QRCodeDatabaseHelper dbHelper;
    @NonNull
    private final String[] columns = new String[]{QRCodeDatabaseHelper.COL_ID,
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
    public QRCodeModel getQRCodeById(@NonNull int qrcodeId) {
        QRCodeModel result = null;
        try {
            Cursor cursor = getDb().query(tableName, columns,
                    QRCodeDatabaseHelper.COL_ID + " = " + qrcodeId,
                    null, null, null, null, null);
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                result = cursorToQRCode(cursor);
            }
            cursor.close();
        } catch (SQLException e) {
            System.out.println("Database error while querying for QRCodes with id '" + qrcodeId + "': s" + e);
        }
        return result;
    }

    public QRCodeModel getQRCodeByName(@NonNull String qrcodeName) {
        QRCodeModel result = null;
        try {
            Cursor cursor = getDb().query(tableName, columns,
                    QRCodeDatabaseHelper.COL_NAME +
                            " = \"" + qrcodeName + "\"", null, null, null, null, null);
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                result = cursorToQRCode(cursor);
            }
            cursor.close();
        } catch (SQLException e) {
            System.out.println("Database error while querying for QRCode with name '" + qrcodeName + "': s" + e);
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
                result.add(cursorToQRCode(cursor));
                cursor.moveToNext();
            }
            cursor.close();
        } catch (SQLException e) {
            System.out.println("Database error while querying all QRCodes: " + e);
        }
        return result;
    }

    private QRCodeModel cursorToQRCode(@NonNull Cursor cursor) {
        return new QRCodeModel(
                cursor.getInt(cursor.getColumnIndex(QRCodeDatabaseHelper.COL_ID)),
                cursor.getString(cursor.getColumnIndex(
                        QRCodeDatabaseHelper.COL_NAME)),
                cursor.getString(cursor.getColumnIndex(
                        QRCodeDatabaseHelper.COL_VALUE)),
                cursor.getLong(cursor.getColumnIndex(
                        QRCodeDatabaseHelper.COL_DATE)));
    }

    public boolean removeQRCode(int id) {
        boolean success = false;
        try {
            QRCodeModel qrCode = getQRCodeById(id);
            if (qrCode == null) {
                return false;
            }
            success = getDb().delete(tableName, QRCodeDatabaseHelper.COL_ID + "=?",
                    new String[]{String.valueOf(id)}) > 0;
        } catch (SQLException e) {
            System.out.println("Database error while deleting qrCode with id '" + id + "': " + e);
        }
        return success;
    }

    public int getLatestID() {
        int id;
        try {
            Cursor cursor = getDb().rawQuery("select * from " + tableName, null);
            cursor.moveToLast();
            if (cursor.getCount() <= 0) {
                return 1;
            }

            id = cursorToQRCode(cursor).getID() + 1;
            cursor.close();
        } catch (Exception e) {
            System.out.println("Database error while get id or no entry in db: " + e);
            id = 0;
        }
        return id;
    }
}