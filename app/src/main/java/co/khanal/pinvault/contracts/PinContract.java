package co.khanal.pinvault.contracts;

/**
 * Created by abhi on 5/5/16.
 */
public class PinContract {

    public static String DATABASE_NAME = "pinvault";
    public static String TABLE_NAME = "pinvault";
    public static String ID_COLUMN = "_id";
    public static String LABEL_COLUMN = "label";
    public static String PIN_COLUMN = "pin";
    public static String NOTES_COLUMN = "notes";

    public static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " +
            ID_COLUMN + " INTEGER PRIMARY AUTOINCREMENT," +
            LABEL_COLUMN + " TEXT," +
            PIN_COLUMN + " TEXT," +
            NOTES_COLUMN + " TEXT " +
            ");";

    public static String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

}
