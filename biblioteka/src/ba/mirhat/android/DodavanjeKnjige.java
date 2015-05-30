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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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
				pretragaPom();
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
			long isbn = Long.valueOf(ISBN.getText().toString());
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
		pretragaPom();
	}

	private void pretragaPom() {
		final String isbn1 = isbnPretraga.getText().toString();

		if (isbn1.length() != 10 && isbn1.length() != 13) {
			return;
		}

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				String baza = "http://isbndb.com/api/v2/json/JQ2XI6PB/book/";
				final Knjiga k = SkiniJSON.dajKnjigu(baza + isbn1);
				if (k == null) {
					return;
				}

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
		IntentIntegrator scanIntegrator = new IntentIntegrator(this);
		scanIntegrator.initiateScan();
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanningResult != null) {
			String scanContent = scanningResult.getContents();

			isbnPretraga.setText(scanContent);
			pretraga(null);

		}
		else{
			Toast toast = Toast.makeText(getApplicationContext(), 
					"No scan data received!", Toast.LENGTH_SHORT);
			toast.show();
		}
	}
}
