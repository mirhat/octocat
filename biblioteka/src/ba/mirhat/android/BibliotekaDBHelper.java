package ba.mirhat.android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BibliotekaDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "biblioteka";

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "CREATE TABLE knjiga (id	INTEGER PRIMARY KEY AUTOINCREMENT,	"
    		+ "naziv	TEXT, autor	TEXT, ISBN	INTEGER, slika	BLOB, objava	TEXT, stranica	INTEGER, "
    		+ "opis	TEXT, status TEXT);";

    public BibliotekaDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    	db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS knjiga");
        onCreate(db);
    }

}
