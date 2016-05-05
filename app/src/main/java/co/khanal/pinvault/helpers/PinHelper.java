package co.khanal.pinvault.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import co.khanal.pinvault.contracts.PinContract;

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
}
