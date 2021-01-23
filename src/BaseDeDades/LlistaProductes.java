package BaseDeDades;
import java.util.Arrays;

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

public class LlistaProductes {
	private int nProductes;
	private Producte[] llistap;
	 
	public LlistaProductes (int n) {
		nProductes = 0;
		llistap = new Producte[n];
	}
	
	public Producte[] getLlista() {
		return llistap;
	}

	public void setLlista(Producte[] llista) {
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
	public void nouProducte(Producte p) {       
		int i = 0;
		boolean trobat = false;

		while (i < nProductes && !trobat) {
			if (llistap[i].getCodi().equals(p.getCodi())) {
				llistap[i] = p;
				trobat = true;
			}
			i++;
		}

		if (!trobat || nProductes < llistap.length) {
			llistap[nProductes] = p;
			nProductes++;
		}
	}
	
	/**
	 * Elimina els productes que provenen d'un productor
	 * @param num, informacio del productor
	 */
	
	public void eliminaProducte (String num) {
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
		Producte[] llistaCeliacs;
		llistaCeliacs = new Producte [nProductes];
		int nCeliacs = 0;
		for (int i=0; i<nProductes; i++) {
			if (llistap[i] instanceof Producte_Granel && ((Producte_Granel) llistap[i]).getCeliac()){
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
		Producte[] llista_aux;
		llista_aux = new Producte [nProductes];
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

	public LlistaProductes productesGranel () {
		Producte[] llistaCeliacs;
		llistaCeliacs = new Producte [nProductes];
		int nCeliacs = 0;
		for (int i=0; i<nProductes; i++) {
			if (llistap[i] instanceof Producte_Granel ){
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
	
	public LlistaProductes productesUnitats () {
		Producte[] llistaUnitats;
		llistaUnitats = new Producte [nProductes];
		int nUnitat = 0;
		for (int i=0; i<nProductes; i++) {
			if (llistap[i] instanceof Producte_Unitat ){
				llistaUnitats[nUnitat]=llistap[i];
				nUnitat++;
			}
		}
		LlistaProductes aux = new LlistaProductes (nUnitat);
		for (int index = 0; index<nUnitat; index++) {
			aux.nouProducte(llistaUnitats[index]);
		}
		return aux;
	}
	
	public LlistaProductes productesStock () {
		Producte[] llista;
		llista = new Producte [nProductes];
		int nUnitat = 0;
		for (int i=0; i<nProductes; i++) {
			if (llistap[i].getStock() > 0.0 ){
				llista[nUnitat]=llistap[i];
				nUnitat++;
			}
		}
		LlistaProductes aux = new LlistaProductes (nUnitat);
		for (int index = 0; index<nUnitat; index++) {
			aux.nouProducte(llista[index]);
		}
		return aux;
	}
	
	public LlistaProductes productesNoStock () {
		Producte[] llista;
		llista = new Producte [nProductes];
		int nUnitat = 0;
		for (int i=0; i<nProductes; i++) {
			if (llistap[i].getStock() == 0.0 ){
				llista[nUnitat]=llistap[i];
				nUnitat++;
			}
		}
		LlistaProductes aux = new LlistaProductes (nUnitat);
		for (int index = 0; index<nUnitat; index++) {
			aux.nouProducte(llista[index]);
		}
		return aux;
	}
	
	
	
	

	
	
}
