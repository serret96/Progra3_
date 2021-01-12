package BaseDeDades;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

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
		

