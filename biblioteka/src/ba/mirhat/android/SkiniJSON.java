package ba.mirhat.android;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.util.Log;

public class SkiniJSON {


	public static Knjiga dajKnjigu(String url){
		String result = skidanje(url);
		Log.d("JSON", result);

		try {
			JSONObject jObject = new JSONObject(result);
			JSONArray data = jObject.getJSONArray("data");
			jObject = data.getJSONObject(0);

			String naziv = jObject.getString("title");
			data = jObject.getJSONArray("author_data");
			
			StringBuilder authorBuild = new StringBuilder("");
			String autor = "";
			try{
			    for(int a=0; a<data.length(); a++){
			        if(a>0) authorBuild.append(", ");
			        authorBuild.append(data.getJSONObject(a).getString("name"));
			    }
			    autor = authorBuild.toString();
			}
			catch(JSONException jse){ 
			    
			}
			

			long ISBN = jObject.getLong("isbn10");

			String edition = jObject.getString("edition_info");
			String datum = edition.split(";")[1].trim();

			String[] fizicki = jObject.getString("physical_description_text").split(",");
			String str = "";
			int strane = 0;
			if (fizicki.length > 1) {
				str = fizicki[1].replaceAll("\\D+","");
				strane = Integer.valueOf(str);
			} 

			String opis = jObject.getString("summary");

			return new Knjiga(naziv, autor, ISBN, "NEMA", datum, strane, opis, "neprocitana");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private static String skidanje(String url) {
		InputStream inputStream = null;
		String result = null;
		DefaultHttpClient   httpclient = new DefaultHttpClient(new BasicHttpParams());
		try {

			HttpGet httppost = new HttpGet(url);
			// Depends on your web service
			httppost.setHeader("Content-type", "application/json");

			HttpResponse response = httpclient.execute(httppost);           
			HttpEntity entity = response.getEntity();

			inputStream = entity.getContent();
			// json is UTF-8 by default
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();

			String line = null;
			while ((line = reader.readLine()) != null)
			{
				sb.append(line + "\n");
			}
			result = sb.toString();
		} catch (Exception e) { 
			e.printStackTrace();
		}
		finally {
			try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
		}

		return result;
	}
}
