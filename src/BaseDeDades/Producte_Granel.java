package BaseDeDades;

/**
 * Pr�ctica 3 Programaci�
 *
 * Ruben Gomez
 * ruben.gomez@estudiants.urv.cat
 *
 * Ruben Serret Montserrat
 * ruben.serret@estudiants.urv.cat
 *
 */

public class Producte_Granel extends Producte {

	private int preu;
	private String codi_car;
	private Boolean celiac;
	private int stockg;
	
	public Producte_Granel  (String nomp, Productor productor, double latitud, double longitud, String codit, int p, Boolean c, float stock) {
		super(nomp, productor, latitud, longitud, codit, stock);
		this.preu = p;
		this.codi_car = "GR_";
		this.celiac = c;
	}

	public int getPreu() {
		return preu;
	}

	public float getStockg() {
		return stockg;
	}
	
	public String getCodi_car() {
		return codi_car;
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
