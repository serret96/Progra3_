package BaseDeDades;

public class Productes_Unitat extends Productes  {
	
	private int preu;
	private int stocku;
	private String codi_car;
	private int preukg;
	
	public Productes_Unitat  (String nomp, String nifv, String nomv, double latitud, double longitud, String codit, int p, int pkg, int stock) {
		super(nomp, nifv, nomv, latitud, longitud, codit);
		this.preu = p;
		this.codi_car = "UT_";
		this.preukg = pkg;
		this.stocku = stock;
	}

	public int getPreu() {
		return preu;
	}

	public void setPreu(int preu) {
		this.preu = preu;
	}

	public int getStocku() {
		return stocku;
	}

	public void setStocku(int stocku) {
		this.stocku = stocku;
	}

	public int getPreukg() {
		return preukg;
	}

	public void setPreukg(int preukg) {
		this.preukg = preukg;
	}

	public void setPreuU (int p) {
		this.preu = p;
	}
	
	public void setStockU (int s) {
		this.stocku = s;
	}
}
