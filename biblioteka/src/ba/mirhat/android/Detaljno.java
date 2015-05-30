package ba.mirhat.android;

import android.app.Activity;
import android.net.wifi.WifiConfiguration.Status;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

public class Detaljno extends Activity {

	TextView naziv;
	TextView autor;
	TextView datum;
	EditText opis;
	Spinner statusSpin;
	TextView stranice;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detaljno);
		
		naziv = (TextView) findViewById(R.id.naslovDetaljno);
		autor = (TextView) findViewById(R.id.autorDetaljno);
		datum = (TextView) findViewById(R.id.datumDetaljno);
		opis = (EditText) findViewById(R.id.opisDetaljno);
		stranice = (TextView) findViewById(R.id.straniceDetaljno);
		
		statusSpin = (Spinner) findViewById(R.id.statusSpin);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.statusi_niz, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		statusSpin.setAdapter(adapter);
		
		Bundle b = getIntent().getExtras();
		
		naziv.setText(b.getString("naziv"));
		autor.setText(b.getString("autor"));
		datum.setText(b.getString("datum"));
		stranice.setText(b.getString("stranice"));
		
		Toast.makeText(this, b.getString("stranice"), Toast.LENGTH_SHORT).show();
		
		if (b.getString("status").contains("neprocitana")) {
			statusSpin.setSelection(0);
		} else if(b.getString("status").contains("na citanju")){
			statusSpin.setSelection(1);
		} else {
			statusSpin.setSelection(2);
		}
		
		statusSpin.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, 
		            int pos, long id) {
		        
		    }

		    public void onNothingSelected(AdapterView<?> parent) {
		        // Another interface callback
		    }
			
		});
		
		opis.setText(b.getString("opis"));
	}
}
