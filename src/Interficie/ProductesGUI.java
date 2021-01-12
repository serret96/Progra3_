package Interficie;

import BaseDeDades.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.table.*;

public class ProductesGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel nord = new JPanel ();
	private JCheckBox cb1 = new JCheckBox("Granel", false);
	private JCheckBox cb2 = new JCheckBox("Disponibilitat", false);
	private String[] columns = new String[] {"Nom Producte", "Nif", "Nom Venedor", "Stock", "Codi"};
	DefaultTableModel model = new DefaultTableModel(columns,0); 
	JTable table = new JTable(model); 

	private  LlistaProductes llistaProductes, llistaProductesaux;


	public ProductesGUI(String titol) throws FileNotFoundException {
		//popup();
		super(titol);

		Container meuCont=getContentPane();
		meuCont.setLayout(new BorderLayout());

		 llistaProductes = llegirFitxers();
		Producte [] productes1 = llistaProductes.getLlista();

		nord.add(cb1);
		nord.add(cb2);
		meuCont.add(nord, BorderLayout.NORTH);
		nord.setLayout(new FlowLayout());
		// add action listener for the check boxes
		ActionListener actionListener = new ActionHandler();
		cb1.addActionListener(actionListener);
		cb2.addActionListener(actionListener);

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
	
	public void refresh  () {
		model.setRowCount(0);
	}

	class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			JCheckBox checkbox = (JCheckBox) event.getSource();
			if (checkbox.isSelected()) {
				if (checkbox == cb1) {
					refresh();
					System.out.println("Granel is selected");
					llistaProductesaux = llistaProductes.productesGranel();
					Producte [] productes1 = llistaProductesaux.getLlista();

					for (int i = 0; i < llistaProductesaux.getnProductes(); i++) {
						Producte productes2 = productes1[i];
						Productor productor = productes2.getProductor();
						DefaultTableModel model = (DefaultTableModel) table.getModel();
						model.addRow(new Object[]{productes2.getNom_producte(),productor.getNif(),productor.getNomproductor(),productes2.getStock(),
								productes2.getCodi()});
					}
					
				} else if (checkbox == cb2) {
					refresh();
					System.out.println("Disponibilitat is selected");
					llistaProductesaux =llistaProductes.productesStock();
					Producte [] productes1 = llistaProductesaux.getLlista();

					for (int i = 0; i < llistaProductesaux.getnProductes(); i++) {
						Producte productes2 = productes1[i];
						Productor productor = productes2.getProductor();
						DefaultTableModel model = (DefaultTableModel) table.getModel();
						model.addRow(new Object[]{productes2.getNom_producte(),productor.getNif(),productor.getNomproductor(),productes2.getStock(),
								productes2.getCodi()});
					}
			
					
				}
			} else {
				if (checkbox == cb1) {
					refresh();
					System.out.println("Granel is not selected");
					llistaProductesaux =llistaProductes.productesUnitats();
					Producte [] productes1 = llistaProductesaux.getLlista();

					for (int i = 0; i < llistaProductesaux.getnProductes(); i++) {
						Producte productes2 = productes1[i];
						Productor productor = productes2.getProductor();
						DefaultTableModel model = (DefaultTableModel) table.getModel();
						model.addRow(new Object[]{productes2.getNom_producte(),productor.getNif(),productor.getNomproductor(),productes2.getStock(),
								productes2.getCodi()});
					}


				} else if (checkbox == cb2) {
					refresh();
					System.out.println("Disponibilitat is not selected");
					llistaProductesaux =llistaProductes.productesStock();
					Producte [] productes1 = llistaProductesaux.getLlista();

					for (int i = 0; i < llistaProductesaux.getnProductes(); i++) {
						Producte productes2 = productes1[i];
						Productor productor = productes2.getProductor();
						DefaultTableModel model = (DefaultTableModel) table.getModel();
						model.addRow(new Object[]{productes2.getNom_producte(),productor.getNif(),productor.getNomproductor(),productes2.getStock(),
								productes2.getCodi()});
					}

				}

			}

		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		new ProductesGUI("Productes");
	}






		/*public void popup() {
			String dni = JOptionPane.showInputDialog("Introdueix el NIF", JOptionPane.QUESTION_MESSAGE);
			while (dni == null || dni.equals("") || !(validar (dni))) {
				// Missatge d'error.
				JOptionPane.showMessageDialog(null, "Cal un nif!", "ERROR", JOptionPane.ERROR_MESSAGE);
				dni = JOptionPane.showInputDialog("Introdueix el NIF ");
				}
		}


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
}*/

	public static LlistaProductes llegirFitxers() throws FileNotFoundException {
		LlistaProductes llistaProductes = new LlistaProductes(10);
		String [] linea = new String[8];
		String codi;
		int  quant, cost;
		Date date;
		Compra[] llistac;

		Scanner sc2 =new Scanner(new File("C:\\Users\\ruben\\git\\Progra3_Definitiu\\src\\productes.txt"));

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