package BaseDeDades;

public class Productes {
	private String nom_producte;
	private String nif;
	private String nom_venedor;
	private double lat;
	private double lon;
	private String codi;
	
	public Productes (String nomp, String nifv, String nomv, double latitud, double longitud, String codit) {
		this.nom_producte = nomp;
		this.nif = nifv;
		this.nom_venedor = nomv;
		this.lat = latitud;
		this.lon = longitud;
		this.codi = codit;
	}
	
	// Getters dels diferents atributs/**
	public String getNom_producte() {
		return nom_producte;
	}
	
	public String getNif() {
		return nif;
	}
	
	public String getNom_venedor() {
		return nom_venedor;
	}
	
	public double getLat() {
		return lat;
	}
	
	public double getLon() {
		return lon;
	}
	
	public String getCodi() {
		return codi;
	}
	
	public Productes copia() {
		Productes c = new Productes (this.nom_producte, this.nif, this.nom_venedor, this.lat, this.lon, this.codi);
		return c;
	}
	
}
