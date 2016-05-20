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

    public long insertPin(Pin pin) throws SQLException {
        ContentValues values = new ContentValues();
        values.put(PinContract.LABEL_COLUMN, pin.getLabel());
        values.put(PinContract.PIN_COLUMN, pin.getPin());
        values.put(PinContract.NOTES_COLUMN, pin.getNotes());

        SQLiteDatabase db = getWritableDatabase();
        return db.insertOrThrow(PinContract.TABLE_NAME,
                null,
                values);

    }

    public boolean removePin(long _id) throws SQLException{
        SQLiteDatabase db = getWritableDatabase();
        String[] args = {
                String.valueOf(_id)
        };
        int res = db.delete(
                PinContract.TABLE_NAME,
                PinContract.ID_COLUMN + "=?",
                args
        );

        if(res != 0){
            return true;
        } else {
            return false;
        }
    }

    public Pin getPin(long _id) throws SQLException{
        SQLiteDatabase db = getReadableDatabase();
        String[] args = {
          String.valueOf(_id)
        };
        Cursor cursor = db.query(
                PinContract.TABLE_NAME,
                PinContract.selection,
                PinContract.ID_COLUMN + "=?",
                args,
                null,
                null,
                null
        );

        if(cursor.moveToFirst()){
            Pin returnPin = new Pin();
            returnPin.set_id(cursor.getLong(0));
            returnPin.setLabel(cursor.getString(1));
            returnPin.setPin(cursor.getString(2));
            returnPin.setNotes(cursor.getString(3));

            return returnPin;
        }
        return null;
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
        List<Pin> pins = new ArrayList<Pin>();

        if(cursor.moveToFirst()){

            do {
                Pin pin = new Pin(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                );
                pins.add(pin);
            } while (cursor.moveToNext());
        }
        return pins;
    }

    public boolean removeAll() {
        SQLiteDatabase db = getWritableDatabase();
        int res = db.delete(
                PinContract.TABLE_NAME,
                "1",
                null
        );

        if(res != 0){
            return true;
        } else {
            return false;
        }
    }

    public long updatePin(Pin pin) throws Exception {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("label", pin.getLabel());
        values.put("pin", pin.getPin());
        values.put("notes", pin.getNotes());

        String[] args = {
          String.valueOf(pin.get_id())
        };

        int res = db.update(
                PinContract.TABLE_NAME,
                values,
                PinContract.ID_COLUMN + "=?",
                args
        );

        if(res > 0)
            return pin.get_id();
        else return res;
    }
}
