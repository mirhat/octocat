package ba.mirhat.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


/**
 * Data source for Orders SQLite table.
 */
public class KnjigaDataSource {

    private static final String TAG = KnjigaDataSource.class.getSimpleName();

    private SQLiteDatabase mDatabase;
    private BibliotekaDBHelper mRestaurantDataHelper;

    private static final String[] ALL_COLUMNS = {
            "id", "naziv", "autor", "ISBN", "slika", "objava", "stranica", "opis", "status"
    };

    public KnjigaDataSource(Context context) {
        Log.i(TAG,"OrderDataSource()");
        mRestaurantDataHelper = new BibliotekaDBHelper(context);
    }

    protected void open() {
        Log.i(TAG,"open()");
        mDatabase = mRestaurantDataHelper.getWritableDatabase();
    }

    protected void close() {
        Log.i(TAG,"close()");
        mRestaurantDataHelper.close();
    }

    public void addKnjiga(Knjiga knjiga) {

        Log.i(TAG, "Order: " + knjiga);

        ContentValues values = new ContentValues();
        values.put("naziv", knjiga.getNaziv());
        values.put("autor", knjiga.getAutor());
        values.put("ISBN", knjiga.getISBN());
        values.put("slika", knjiga.getSlika());
        values.put("objava", knjiga.getObjava());
        values.put("stranica", knjiga.getStranica());
        values.put("opis", knjiga.getOpis());
        values.put("status", knjiga.getStatus());

        long insertId = mDatabase.insert("knjiga", null, values);

    }

    public void deleteKnjiga(Knjiga knjiga) {
        Log.i(TAG, "deleteOrder():" + knjiga);
        long id = knjiga.getId();
    }

    public List<Knjiga> getAllKnjiga() {
        Log.i(TAG, "getAllOrders()");
        List<Knjiga> knjige = new ArrayList<>();
          
        Cursor cursor = mDatabase.query("knjiga", ALL_COLUMNS, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            knjige.add(cursorToKnjiga(cursor));
            cursor.moveToNext();
        }

        return knjige;
    }
    
    public void deleteAll() {
    	mDatabase.execSQL("delete from knjiga");
	}
    
    private static Knjiga cursorToKnjiga(Cursor cursor) {
        Log.i(TAG, "cursorToOrder()");
           
        return new Knjiga(cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4), 
        		cursor.getString(5), cursor.getInt(6), cursor.getString(7), cursor.getString(8));

    }

    public void checkout(){
        // delete all orders. Simulate that we finished out order
        mDatabase.delete("knjiga",null,null);
    }
}
