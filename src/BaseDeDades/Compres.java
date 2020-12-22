package BaseDeDades;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Compres {
	private String codip;
	private int quantitat;
	private Date data;
	private int cost;
	private Productes productes;
	
	public Compres (String c, int q, Date d, int coste, Productes  productes) {
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

	public Compres copia() {
		Compres p = new Compres (this.codip, this.quantitat, this.data, this.cost, this.productes);
		return p;
	}
}
