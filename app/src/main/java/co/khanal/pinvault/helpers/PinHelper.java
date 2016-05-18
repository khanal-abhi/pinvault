package co.khanal.pinvault.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public Pin getPin(int _id) throws SQLException{
        SQLiteDatabase db = getReadableDatabase();
        String[] args = {
          PinContract.ID_COLUMN + "+" + _id
        };
        Cursor cursor = db.query(
                PinContract.TABLE_NAME,
                PinContract.selection,
                null,
                args,
                null,
                null,
                null
        );

        if(cursor.moveToFirst()){
            Pin returnPin = new Pin();
            returnPin.set_id(cursor.getInt(0));
            returnPin.setLabel(cursor.getString(1));
            returnPin.setPin(cursor.getString(2));
            returnPin.setNotes(cursor.getString(3));
        }
        throw new SQLException("Could not find any pin with id= " + _id);
    }

    public List<Pin> getPins() throws SQLException{
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                PinContract.TABLE_NAME,
                PinContract.selection,
                null,
                null,
                null,
                null,
                null
        );

        if(cursor.moveToFirst()){
            List<Pin> pins = new ArrayList<Pin>();
            do {
                Pin pin = new Pin(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                );
                pins.add(pin);
            } while (cursor.moveToNext());

            return pins;
        }
        return null;
    }
}
