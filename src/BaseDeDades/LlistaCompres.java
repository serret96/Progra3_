package BaseDeDades;
import java.util.Arrays;

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

	public class LlistaCompres {
		private int nCompres;
		private Compra[] llistac;
		 
		public LlistaCompres (int n) {
			nCompres = 0;
			llistac = new Compra[n];
		}
		
		public Compra[] getLlista() {
			return llistac;
		}

		public void setLlista(Compra[] llista) {
			this.llistac = llista;
		}

		public int getnCompres() {
			return nCompres;
		}
		
		public String toString() {
			return "LlistaCompres{" +
					"nCompres=" + nCompres +
					", llista=" + Arrays.toString(llistac) +
					'}';
		}
		
		
		/**
		 * Afegeix una nova compra a la llista
		 * @param c, compra que volem afegir
		 */
		
		public void nouCompra(Compra c) {       
			int i = 0;
			boolean trobat = false;

			while (i < nCompres && !trobat) {
				if (llistac[i].getCodip() == (c.getCodip())) {
					llistac[i] = c.copia();
					trobat = true;
				}
				i++;
			}

			if (!trobat && nCompres < llistac.length) {
				llistac[nCompres] = c.copia();
				nCompres++;
			}
		}
		
		/**
		 * Acci� que ordenar la llista segons la data
		 */
		
		public void ordenarLlista() {
			int i, j;
			Compra aux;
	         for(i=0;i<llistac.length-1;i++)
	              for(j=0;j<llistac.length-i-1;j++)
	                   if(llistac[j+1].getData().after(llistac[j].getData())){
	                      aux=llistac[j+1];
	                      llistac[j+1]=llistac[j];
	                      llistac[j]=aux;
	                   }
			
			
			}
		
		}
		

