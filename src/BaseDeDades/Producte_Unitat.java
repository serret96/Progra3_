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

public class Producte_Unitat extends Producte {

	private int preu;
	private String codi_car;
	private int preukg;

	public Producte_Unitat(String nomp, Productor productor, double latitud, double longitud, String codit, int p, int pkg, float stock) {
		super(nomp, productor, latitud, longitud, codit, stock);
		this.preu = p;
		this.codi_car = "UT_";
		this.preukg = pkg;
	}

	public int getPreu() {
		return preu;
	}

	public void setPreu(int preu) {
		this.preu = preu;
	}


	public int getPreukg() {
		return preukg;
	}
	
	public String getCodi_car() {
		return codi_car;
	}

	public void setPreukg(int preukg) {
		this.preukg = preukg;
	}

	public void setPreuU(int p) {
		this.preu = p;
	}
}
	

