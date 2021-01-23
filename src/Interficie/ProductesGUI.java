package Interficie;
import BaseDeDades.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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

public class ProductesGUI extends JFrame {
	//Declaració de varibles
	private static final long serialVersionUID = 1L;
	private JPanel nord = new JPanel ();									//Panel on es guarda els checkboxs
	private JCheckBox granel = new JCheckBox("Granel", false);				
	private JCheckBox unitat = new JCheckBox("Unitat", false);				
	private JCheckBox disponibilitat = new JCheckBox("En Stock", false);	
	private JCheckBox nodisponibilitat = new JCheckBox("Sense Disponibilitat", false);
	private ButtonGroup filtre1 = new ButtonGroup();						//Conjunt de checkbox filtre per tipus
	private ButtonGroup filtre2 = new ButtonGroup();						//Conjunt de checkbox filtre per disponibilitat
	private String[] columns = new String[] {"Nom Producte", "Nif", "Nom Venedor", "Stock", "Codi"};
	DefaultTableModel model = new DefaultTableModel(columns,0); 
	JTable table = new JTable(model);
	

	private  LlistaProductes llistaProductes, llistaProductesaux;


	public ProductesGUI(String titol) throws FileNotFoundException {
		
		super(titol);
		popup();

		Container meuCont=getContentPane();
		meuCont.setLayout(new BorderLayout());

		 llistaProductes = llegirFitxers();
		Producte [] productes1 = llistaProductes.getLlista();
		//Afegir els checkbox al panel
		nord.add(unitat);
		nord.add(granel);
		nord.add(nodisponibilitat);
		nord.add(disponibilitat);
		//Afegir els checkbox als grups
		filtre1.add(unitat);
		filtre1.add(granel);
		filtre2.add(disponibilitat);
		filtre2.add(nodisponibilitat);
		//Afegir el panel al Container
		meuCont.add(nord, BorderLayout.NORTH);
		nord.setLayout(new FlowLayout());
		//Listeners dels checkboxs
		ActionListener actionListener = new ActionHandler();
		unitat.addActionListener(actionListener);
		granel.addActionListener(actionListener);
		disponibilitat.addActionListener(actionListener);
		nodisponibilitat.addActionListener(actionListener);
		//Replenar la taula mostrada amb les dades
		for (int i = 0; i < llistaProductes.getnProductes(); i++) {
			Producte productes2 = productes1[i];
			Productor productor = productes2.getProductor();
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.addRow(new Object[]{productes2.getNom_producte(),productor.getNif(),productor.getNomproductor(),productes2.getStock(),
					productes2.getCodi()});
		}
		meuCont.add(new JScrollPane(table), BorderLayout.CENTER);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(900,300);
		setVisible(true);


	}
	/**
	 * Acció per buidar la taula
	 */
	
	public void refresh  () {
		model.setRowCount(0);
	}

	/**
	 * ActionListener dels CheckBox, existeixen dos blocs, el de tipus de productes i el de disponibilitat
	 */
	
	class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			
			llistaProductesaux = llistaProductes.productesGranel();
			Producte [] productes1 = llistaProductesaux.getLlista();
			if (unitat.isSelected()) {
				refresh();
				llistaProductesaux = llistaProductes.productesUnitats();
				if (nodisponibilitat.isSelected()) {
				for (int i = 0; i < llistaProductesaux.getnProductes(); i++) {
					productes1 = llistaProductesaux.getLlista();
					Producte productes2 = productes1[i];
					if (productes2.getStock()==0) {
					Productor productor = productes2.getProductor();
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					model.addRow(new Object[]{productes2.getNom_producte(),productor.getNif(),productor.getNomproductor(),productes2.getStock(),
							((Producte_Unitat) productes2).getCodi_car()});}}
				}
				if (disponibilitat.isSelected()) {
					for (int i = 0; i < llistaProductesaux.getnProductes(); i++) {
						productes1 = llistaProductesaux.getLlista();
						Producte productes2 = productes1[i];
						if (productes2 instanceof Producte_Unitat && productes2.getStock() != 0) {
						Productor productor = productes2.getProductor();
						DefaultTableModel model = (DefaultTableModel) table.getModel();
						model.addRow(new Object[]{productes2.getNom_producte(),productor.getNif(),productor.getNomproductor(),productes2.getStock(),
								((Producte_Unitat) productes2).getCodi_car()});}}
				}
				
			}
			else if (granel.isSelected()) {
				refresh();
				llistaProductesaux = llistaProductes.productesGranel();
				if (nodisponibilitat.isSelected()) {
					for (int i = 0; i < llistaProductesaux.getnProductes(); i++) {
						Producte productes2 = productes1[i];
						if (productes2.getStock()==0) {
						Productor productor = productes2.getProductor();
						DefaultTableModel model = (DefaultTableModel) table.getModel();
						model.addRow(new Object[]{productes2.getNom_producte(),productor.getNif(),productor.getNomproductor(),productes2.getStock(),
								((Producte_Granel) productes2).getCodi_car()});}}
					
					
				}
				if (disponibilitat.isSelected()) {

					for (int i = 0; i < llistaProductesaux.getnProductes(); i++) {
						Producte productes2 = productes1[i];
						if (productes2.getStock() != 0 ) {
						Productor productor = productes2.getProductor();
						DefaultTableModel model = (DefaultTableModel) table.getModel();
						model.addRow(new Object[]{productes2.getNom_producte(),productor.getNif(),productor.getNomproductor(),productes2.getStock(),
								((Producte_Granel) productes2).getCodi_car()});}}
				}
			}
		}
	}

	/**
	 * 
	 * Main del programa
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		new ProductesGUI("Productes");
	}



	/**
	 * Acció que mostra la pestanya popup per demanar el nif
	 * fins que no s'introdueix un correcte, el torna a demanar 
	 */


	public void popup() {
		String dni = JOptionPane.showInputDialog("Introdueix el NIF", JOptionPane.QUESTION_MESSAGE);
			
		while (dni.equals("") || !(validar (dni))) {
			// Missatge d'error.
			JOptionPane.showMessageDialog(null, "Cal un nif!", "ERROR", JOptionPane.ERROR_MESSAGE);
			dni = JOptionPane.showInputDialog("Introdueix el NIF ");
			}
		}

	/**
		* Funció que valida el dni introduit
		* @param dni
		* @return esValido, boolea que diu si el dni introduit és vàlid
		*/
		
	public static boolean validar(String dni) {

	       boolean esValido = false;
	       int i = 0;
	       int caracterASCII = 0;
	       char letra = ' ';
	       int miDNI = 0;
	       int resto = 0;
	       char[] asignacionLetra = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X','B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};


	       if(dni.length() == 9 && Character.isLetter(dni.charAt(8))) {

	          do {
	               caracterASCII = dni.codePointAt(i);
	               esValido = (caracterASCII > 47 && caracterASCII < 58);
	               i++;
	            }
	          while(i < dni.length() - 1 && esValido);
	        }

	        if(esValido) {
	            letra = Character.toUpperCase(dni.charAt(8));
	            miDNI = Integer.parseInt(dni.substring(0,8));
	            resto = miDNI % 23;
	            esValido = (letra == asignacionLetra[resto]);
	        }

	        return esValido;
	    }

		/**
		 * Funció que llegeix el fitxer dels productes i els emmagatzema
		 * @return llistaproductes, llista amb els elements llegits
		 * @throws FileNotFoundException
		 */
	public static LlistaProductes llegirFitxers() throws FileNotFoundException {
		LlistaProductes llistaProductes = new LlistaProductes(10);
		String [] linea = new String[8];
		Scanner sc2 =new Scanner(new File("src\\productes.txt"));

		while(sc2.hasNextLine())
		{
			linea =sc2.nextLine().split(";");
			String nomp = linea[0];
			String nif = linea[1];
			String nomv = linea[2];
			double lat = Double.parseDouble(linea[3]);
			double lon = Double.parseDouble(linea[4]);
			//codi = linea[5];

			Productor productor = new Productor(nif, nomv);


			String [] linea2 = new String[3];
			linea2 = linea[6].split("_");
			if (linea2[0].equals("UT"))
			{
				int preu = Integer.parseInt(linea[5]);
				int pes = Integer.parseInt(linea[7]);
				int stock = Integer.parseInt(linea[8]);

				Producte_Unitat productes_unitat = new Producte_Unitat(nomp, productor, lat, lon, linea[6], preu, pes, stock);
				llistaProductes.nouProducte(productes_unitat);
			}
			else
			{
				int preu = Integer.parseInt(linea[5]);
				Boolean celiac = Boolean.parseBoolean(linea[7]);
				int stock = Integer.parseInt(linea[8]);
				Producte_Granel productes_granel = new Producte_Granel(nomp, productor, lat, lon, linea[6], preu, celiac, stock);
				llistaProductes.nouProducte(productes_granel);

			}

		}
		sc2.close();

		return llistaProductes;

	}
	
}