package BaseDeDades;

public class Productes {
	private String nom_producte;
	private Productor productor;
	private double lat;
	private double lon;
	private String codi;
	private float stock;

	public Productes(String nomp, Productor productor, double latitud, double longitud, String codit, float stock) {
		super();
		this.nom_producte = nomp;
		this.productor = productor;
		this.lat = latitud;
		this.lon = longitud;
		this.codi = codit;
		this.stock = stock;
	}

	public Productes(String nom, Productor productor, double lat, double longi, String codi, int preu, int preukg, float stock) {
		super();
		this.nom_producte = nom;
		this.productor = productor;
		this.lat = lat;
		this.lon = longi;
		this.codi = codi;
		this.stock = stock;
	}

	public Productes(String nom, Productor productor, double lat, double longi, String codi, int preu, boolean control, float stock) {
		super();
		this.nom_producte = nom;
		this.productor = productor;
		this.lat = lat;
		this.lon = longi;
		this.codi = codi;
		this.stock = stock;
	}

	public String getNom_producte() {
		return nom_producte;
	}

	public Productor getProductor() {
		return productor;
	}

	public void setProductor(Productor productor) {
		this.productor = productor;
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

	public float getStock() {
		return stock;
	}

	public void setStock(float stock) {
		this.stock = stock;
	}

	public Productes copia() {
		Productes c = new Productes (this.nom_producte,this.productor, this.lat, this.lon, this.codi, this.stock);
		return c;
	}
	
}
