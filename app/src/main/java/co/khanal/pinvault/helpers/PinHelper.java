package co.khanal.pinvault.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

import co.khanal.pinvault.contracts.PinContract;
import co.khanal.pinvault.pojos.Pin;

/**
 * Created by abhi on 5/5/16.
 */
public class PinHelper extends SQLiteOpenHelper {
    public PinHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PinContract.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(PinContract.DROP_TABLE);
        db.execSQL(PinContract.CREATE_TABLE);
    }

    public void insertPin(Pin pin) throws SQLException {
        ContentValues values = new ContentValues();
        values.put(PinContract.LABEL_COLUMN, pin.getLabel());
        values.put(PinContract.PIN_COLUMN, pin.getPin());
        values.put(PinContract.NOTES_COLUMN, pin.getNotes());

        SQLiteDatabase db = getWritableDatabase();
        db.insertOrThrow(PinContract.TABLE_NAME,
                null,
                values);
    }

    public void removePin(int _id) throws SQLException{
        SQLiteDatabase db = getWritableDatabase();
        Integer[] boundArgs = {_id};
        db.execSQL("DELETE * FROM " + PinContract.TABLE_NAME + " WHERE " + PinContract.ID_COLUMN + "=?;", boundArgs);
    }
}
