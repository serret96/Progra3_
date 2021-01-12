package BaseDeDades;
import java.util.Date;
import java.text.SimpleDateFormat;

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

	public Compra copia() {
		Compra p = new Compra (this.codip, this.quantitat, this.data, this.cost, this.productes);
		return p;
	}
}
