package BaseDeDades;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Compres {
	private int codip;
	private int quantitat;
	private Date data;
	private int cost;
	
	public Compres (int c, int q, Date d, int coste) {
		this.codip = c;
		this.quantitat = q;
		this.data = d;
		this.cost = coste;
	}
	
	public int getCodip() {
		return codip;
	}
	
	public Compres copia() {
		Compres p = new Compres (this.codip, this.quantitat, this.data, this.cost);
		return p;
	}
}
