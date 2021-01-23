package BaseDeDades;
import java.util.Date;

/**
 * Pràctica 3 Programació
 *
 * Ruben Gomez
 * ruben.gomez@estudiants.urv.cat
 *
 * Ruben Serret Montserrat
 * ruben.serret@estudiants.urv.cat
 *
 */

public class Compra {
	private String codip;
	private int quantitat;
	private Date data;
	private int cost;
	private Producte productes;
	
	public Compra (String c, int q, Date d, int coste, Producte  productes) {
		this.codip = c;
		this.quantitat = q;
		this.data = d;
		this.cost = coste;
		this.productes = productes;
	}
	
	public String getCodip() {
		return codip;
	}

	public int getQuantitat() {
		return quantitat;
	}

	public Date getData() {
		return data;
	}

	public int getCost() {
		return cost;
	}
	
	public Producte getProducte() {
		return productes;
	}

	public Compra copia() {
		Compra p = new Compra (this.codip, this.quantitat, this.data, this.cost, this.productes);
		return p;
	}
}
