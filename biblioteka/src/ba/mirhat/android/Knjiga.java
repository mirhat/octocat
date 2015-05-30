package ba.mirhat.android;

import android.R.bool;
import android.R.integer;

public class Knjiga {
	private int id;
	private String naziv;
	private String autor;
	private long ISBN;
	private String slika;
	private String objava;
	private int stranica;
	private String opis;
	private String status;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Knjiga(String naziv, String autor, long iSBN, String slika,
			String objava, int stranica, String opis, String status) {
		super();
		this.naziv = naziv;
		this.autor = autor;
		ISBN = iSBN;
		this.slika = slika;
		this.objava = objava;
		this.stranica = stranica;
		this.opis = opis;
		this.status = status;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public long getISBN() {
		return ISBN;
	}
	public void setISBN(long iSBN) {
		ISBN = iSBN;
	}
	public String getSlika() {
		return slika;
	}
	public void setSlika(String slika) {
		this.slika = slika;
	}
	public String getObjava() {
		return objava;
	}
	public void setObjava(String objava) {
		this.objava = objava;
	}
	public int getStranica() {
		return stranica;
	}
	public void setStranica(int stranica) {
		this.stranica = stranica;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	
}
