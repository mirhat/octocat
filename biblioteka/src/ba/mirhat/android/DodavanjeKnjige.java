package ba.mirhat.android;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.text.StaticLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class DodavanjeKnjige extends Activity {

	KnjigaDataSource knjigaDataSource;
	Button ucitajSliku;
	Button dodajKnjigu;
	Button skenirajButton;
	Button pretragaButton;
	EditText naziv;
	EditText autor;
	EditText ISBN;
	EditText datum;
	EditText stranice;
	EditText opis;
	EditText isbnPretraga;
	static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN"; 

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dodavanje_knjige);
		
		knjigaDataSource = new KnjigaDataSource(getApplicationContext());
		knjigaDataSource.open();
		
		naziv = (EditText) findViewById(R.id.opisDetaljno);
		autor = (EditText) findViewById(R.id.editText2);
		ISBN = (EditText) findViewById(R.id.editText3);
		datum = (EditText) findViewById(R.id.editText4);
		stranice = (EditText) findViewById(R.id.editText5);
		opis = (EditText) findViewById(R.id.editText6);
		isbnPretraga = (EditText) findViewById(R.id.isbnPretraga);
		
		dodajKnjigu = (Button) findViewById(R.id.dodajBtn);
		dodajKnjigu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	dodavanje(v);
            }
        });
		
		ucitajSliku = (Button) findViewById(R.id.ucitajBtn);
		ucitajSliku.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	ucitavanje(v);
            }
        });
		
		pretragaButton = (Button) findViewById(R.id.pretragaBtn);
		pretragaButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	pretraga(v);
            }
        });
		
		skenirajButton = (Button) findViewById(R.id.skenirajBtn);
		skenirajButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	skeniranje(v);
            }
        });
	}

	public void dodavanje(View v) {
		try{
			String n = naziv.getText().toString();
			String a = autor.getText().toString();
			int isbn = Integer.valueOf(ISBN.getText().toString());
			String d = datum.getText().toString();
			int s = 0;
			if (stranice.getText() != null && !stranice.getText().toString().equals("")) {
				s = Integer.valueOf(stranice.getText().toString());
			}
			String o = opis.getText().toString();
			
			knjigaDataSource.addKnjiga(new Knjiga(n, a, isbn, "nema", d, s, o, "neprocitana"));
			
			Toast.makeText(getApplicationContext(), "Dodali ste knjigu", Toast.LENGTH_SHORT).show();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void ucitavanje(View v) {
		
		
	}
	
	public void pretraga(View v) {
		final String isbn1 = isbnPretraga.getText().toString();
		
		if (isbn1.length() != 10 && isbn1.length() != 13) {
			return;
		}
		
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				String baza = "http://isbndb.com/api/v2/json/JQ2XI6PB/book/";
				final Knjiga k = SkiniJSON.dajKnjigu(baza + isbn1);
				
				runOnUiThread(new Runnable() {
				    public void run() {
				    	naziv.setText(k.getNaziv());
						autor.setText(k.getAutor());
						ISBN.setText(String.valueOf(k.getISBN()));
						datum.setText(k.getObjava());
						if (k.getStranica() != 0) {
							stranice.setText(String.valueOf(k.getStranica()));
						}
						opis.setText(k.getOpis());
				    }
				});				
			}
		});
		
		t.start();		
	}
	
	public void skeniranje(View v) {
		try {
			Intent intent = new Intent(ACTION_SCAN);
			intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
			startActivityForResult(intent, 0);
		} catch (ActivityNotFoundException anfe) {
			Toast.makeText(getApplicationContext(), "Nema skenera", Toast.LENGTH_SHORT).show();
			anfe.printStackTrace();
		}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String contents = intent.getStringExtra("SCAN_RESULT");
				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
				Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
				toast.show();
			}
		}
	}
}
