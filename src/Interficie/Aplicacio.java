package Interficie;

import BaseDeDades.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.table.*;

public class Aplicacio extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel nord = new JPanel ();
	private JCheckBox cb1 = new JCheckBox("Tipus");
	private JCheckBox cb2 = new JCheckBox("Disponibilitat");
	private JButton b1 = new JButton ("Cercar");


	public Aplicacio(String titol) throws FileNotFoundException {
		//popup();
		super(titol);

		Container meuCont=getContentPane();
		meuCont.setLayout(new BorderLayout());


		nord.setLayout(new FlowLayout());
		nord.add(cb1);
		nord.add(cb2);
		nord.add(b1);
		meuCont.add(nord, BorderLayout.NORTH);


		String[] columns = new String[] {
				"Nom Producte", "Nif", "Nom Venedor", "Latitud", "Longitud","Codi"};

		LlistaProductes llistaProductes = llegirFitxers();;

		Productes [] productes1 = llistaProductes.getLlista();
		Object [][] data = new Object[10][6];
		for (int i = 0; i < llistaProductes.getnProductes(); i++) {
			Productes productes2 = productes1[i];
			Productor productor = productes2.getProductor();
				data[i][0] = productes2.getNom_producte();
				data[i][1] = productor.getNif();
				data[i][2] = productor.getNomproductor();
				data[i][3] = productes2.getLat();
				data[i][4] = productes2.getLon();
				data[i][5] = productes2.getCodi();

		}

		JTable table = new JTable(data, columns);

		meuCont.add(new JScrollPane(table), BorderLayout.CENTER);

		JButton bottonImprimir =new JButton("Imprimir tabla");

		bottonImprimir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					table.print();
				}catch(Exception e1)
				{

				}
			}
		});





		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(900,300);
		setVisible(true);



	}

	public static void main(String[] args) throws FileNotFoundException {
		new Aplicacio("Productes");
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
		Compres[] llistac;

		Scanner sc2 =new Scanner(new File("C:\\Users\\serret96\\Desktop\\Progra\\practica3\\Progra3\\Progra3\\src\\productes.txt"));

		while(sc2.hasNextLine())
		{
			linea =sc2.nextLine().split(";");
			String nomp = linea[0];
			String nif = linea[1];
			String nomv = linea[2];
			double lat = Double.parseDouble(linea[3]);
			double lon = Double.parseDouble(linea[4]);
			//codi = linea[5];
			float stock = Float.parseFloat(linea[8]);
			Productor productor = new Productor(nif, nomv);

			Productes p = new Productes(nomp, productor, lat, lon, linea[6],stock);
			String [] linea2 = new String[3];
			linea2 = linea[6].split("_");
			if (linea2[0].equals("UT"))
			{
				int preu = Integer.parseInt(linea[5]);
				int pes = Integer.parseInt(linea[7]);


				Productes_Unitat productes_unitat = new Productes_Unitat(nomp, productor, lat, lon, linea[6], preu, pes, stock);
				llistaProductes.nouProducte(productes_unitat);
			}
			else
			{
				int preu = Integer.parseInt(linea[5]);
				Boolean celiac = Boolean.parseBoolean(linea[7]);
				Productes_Granel productes_granel = new Productes_Granel(nomp, productor, lat, lon, linea[6], preu, celiac, stock);
				llistaProductes.nouProducte(productes_granel);

			}

		}
		sc2.close();

		return llistaProductes;

	}
}