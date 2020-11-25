package BaseDeDades;
import java.util.Arrays;

	public class LlistaCompres {
		private int nCompres;
		private Compres[] llistac;
		 
		public LlistaCompres (int n) {
			nCompres = 0;
			llistac = new Compres[n];
		}
		
		public Compres[] getLlista() {
			return llistac;
		}

		public void setLlista(Compres[] llista) {
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
		public void nouCompra(Compres c) {       
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
		
	}

