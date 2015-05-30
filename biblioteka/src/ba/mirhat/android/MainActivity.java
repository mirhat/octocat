package ba.mirhat.android;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	ListView listview;
	List<Knjiga> listaKnjiga;
	KnjigaDataSource knjigaDataSource;
	ImageButton novaKnjiga;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		
		listview = (ListView) findViewById(R.id.listaKnjiga);
		knjigaDataSource = new KnjigaDataSource(getApplicationContext());
		knjigaDataSource.open();
		
	;
		
		popuniListu();		
		novaKnjiga = (ImageButton) findViewById(R.id.novaKnjiga);
		novaKnjiga.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(getApplicationContext(), DodavanjeKnjige.class);
            	startActivity(intent);
            }
        });
		
		listview.setOnItemClickListener(new OnItemClickListener() {
		      public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
		    	  Knjiga k = (Knjiga) (listview.getItemAtPosition(myItemInt));
		    	  
		    	  Intent i = new Intent(getBaseContext(), Detaljno.class);
		    	  i.putExtra("naziv", k.getNaziv());
		    	  i.putExtra("autor", k.getAutor());
		    	  i.putExtra("datum", k.getObjava());
		    	  i.putExtra("status", k.getStatus());
		    	  i.putExtra("opis", k.getOpis());
		    	  i.putExtra("stranice", String.valueOf(k.getStranica()));
		    	  startActivity(i);
		      }                 
		});
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		popuniListu();
	}



	private void popuniListu(){
		listaKnjiga = knjigaDataSource.getAllKnjiga();		
		listview.setAdapter(new knjigeAdapter(this, listaKnjiga));
	}
	
}
