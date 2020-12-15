package BaseDeDades;

public class Productes_Granel extends Productes {

	private int preu;
	private String codi_car;
	private Boolean celiac;
	private int stockg;
	
	public Productes_Granel  (String nomp, String nifv, String nomv, double latitud, double longitud, String codit, int p, Boolean c, int stock) {
		super(nomp, nifv, nomv, latitud, longitud, codit);
		this.preu = p;
		this.codi_car = "GR_";
		this.celiac = c;
		this.stockg = stock;
	}

	public int getPreu() {
		return preu;
	}

	public int getStockg() {
		return stockg;
	}

	public void setPreuG (int p) {
			this.preu = p;
		}
		
		public void setStock (int s) {
			this.stockg = s;
		}
		
		public Boolean getCeliac() {
			return celiac;
		}

}
