package BaseDeDades;
import java.util.Arrays;

public class LlistaProductes {
	private int nProductes;
	private Productes[] llistap;
	 
	public LlistaProductes (int n) {
		nProductes = 0;
		llistap = new Productes[n];
	}
	
	public Productes[] getLlista() {
		return llistap;
	}

	public void setLlista(Productes[] llista) {
		this.llistap = llista;
	}

	public int getnProductes() {
		return nProductes;
	}
	
	public String toString() {
		return "LlistaProductes{" +
				"nProductes=" + nProductes +
				", llista=" + Arrays.toString(llistap) +
				'}';
	}
	
	/**
	 * Afegeix un producte a la llista
	 * @param p, producte que volem afegir
	 */
	public void nouProducte(Productes p) {       
		int i = 0;
		boolean trobat = false;

		while (i < nProductes && !trobat) {
			if (llistap[i].getCodi() == (p.getCodi())) {
				llistap[i] = p;
				trobat = true;
			}
			i++;
		}

		if (!trobat && nProductes < llistap.length) {
			llistap[nProductes] = p;
			nProductes++;
		}
	}
	
	/**
	 * Elimina els productes que provenen d'un productor
	 * @param num, informacio del productor
	 */
	
	public void eliminaProducte (String num) {
		int j = 0;
		for (int i=0; i<nProductes; i++) {
			Productor productor = llistap[i].getProductor();
			if (productor.getNif().equals(num)){
				for (int pos = i; pos<nProductes; pos++) {
					llistap[pos] = llistap [pos+1];
				}
				nProductes--;
				i--;
			}
		}

	}
	
	/**
	 * Retorna una llista amb els productes que son per a celiacs
	 * @return aux es tracta del llistat dels productes per a celiacs
	 */
	
	public LlistaProductes productesCeliacs () {
		Productes[] llistaCeliacs;
		llistaCeliacs = new Productes [nProductes];
		int nCeliacs = 0;
		for (int i=0; i<nProductes; i++) {
			if (llistap[i] instanceof Productes_Granel && ((Productes_Granel) llistap[i]).getCeliac()){
				llistaCeliacs[nCeliacs]=llistap[i];
				nCeliacs++;
			}
		}
		LlistaProductes aux = new LlistaProductes (nCeliacs);
		for (int index = 0; index<nCeliacs; index++) {
			aux.nouProducte(llistaCeliacs[index]);
		}
		return aux;
	}

	
	/**
	 * Retorna una llista amb els productes que provenen del productor introduit per parametre
	 * @param nif, informacio del productor
	 * @return aux es tracta de la llista amb les dades seleccionades
	 */
	
	public LlistaProductes productesMateix_productor (String nif) {
		Productes[] llista_aux;
		llista_aux = new Productes [nProductes];
		int nproductes = 0;
		for (int i=0; i<nProductes; i++) {
			Productor productor = llistap[i].getProductor();

			if (productor.getNif().equals(nif)){
				llista_aux[nproductes]=llistap[i];
				nproductes++;
			}
		}
		LlistaProductes aux = new LlistaProductes (nproductes);
		for (int index = 0; index<nproductes; index++) {
			aux.nouProducte(llista_aux[index]);
		}
		return aux;
	}
	

	
	
}
