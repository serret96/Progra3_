import BaseDeDades.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.time.format.DateTimeFormatter;
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

public class Main {
    private static LlistaProductors llistaProductors = new LlistaProductors(20);

    /**
	 * Metode que calcula la distancia entre dos grups de coordenades
	 * @param lat1, lng1: dades referents al primer grup. lat2, lng2: segon grup de dades de coordenades
	 * @return distancia entre els dos grups
	 */
	public static double distanciaCoord(double lat1, double lng1, double lat2, double lng2) {  
        //double radioTierra = 3958.75;//en millas  
        double radioTierra = 6371;//en kilómetros  
        double dLat = Math.toRadians(lat2 - lat1);  
        double dLng = Math.toRadians(lng2 - lng1);  
        double sindLat = Math.sin(dLat / 2);  
        double sindLng = Math.sin(dLng / 2);  
        double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)  
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));  
        double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));  
        double distancia = radioTierra * va2;  
   
        return distancia;  
    }
	
	
	public static LlistaCompres llegirFitxersCompres() throws FileNotFoundException, ParseException {
		LlistaCompres llistaCompres = new LlistaCompres(10);
		String [] linea = new String[8];
		String codi;
		int  quant, cost;
		Date date;
		Scanner sc =new Scanner(new File("compres3.txt"));
		while(sc.hasNextLine())
		{
			linea =sc.nextLine().split(";");
			codi = linea[0];
			quant = Integer.parseInt(linea[1]);
			date = new SimpleDateFormat("dd/MM/yyyy").parse(linea[2]);
			cost = Integer.parseInt(linea[3]);
			Producte producte;
			Compra c = null;
			String nomp = linea[4];
			String nif = linea[5];
			String nomv = linea[6];
			double lat = Double.parseDouble(linea[7]);
			double lon = Double.parseDouble(linea[8]);
			Productor productor = new Productor(nif, nomv);
			String [] linea2 = new String[3];
			linea2 = linea[10].split("_");
			if (linea2[0].equals("UT"))			//Cas que sigui un producte_unitat
			{
				int preu = Integer.parseInt(linea[9]);
				int pes = Integer.parseInt(linea[11]);
				int stock = Integer.parseInt(linea[12]);
				Producte_Unitat productes_unitat = new Producte_Unitat(nomp, productor, lat, lon, linea[10], preu, pes, stock);
				producte = productes_unitat;
			}
			else								//Cas que sigui un producte_granel
			{
				int preu = Integer.parseInt(linea[9]);
				Boolean celiac = Boolean.parseBoolean(linea[11]);
				float stock = Float.parseFloat(linea[12]);
				Producte_Granel productes_granel = new Producte_Granel(nomp, productor, lat, lon, linea[10], preu, celiac, stock);
				producte = productes_granel;
			}
			c = new Compra(codi, quant, date, cost, producte);

			llistaCompres.nouCompra(c);

	}
		sc.close();
		return llistaCompres;
	}

    

    public static  void EscriureFitxers(LlistaCompres llistaCompres, LlistaProductes llistaProductes) throws FileNotFoundException {

        PrintWriter pw = new PrintWriter(new File("compres3.txt"));

        Compra []c = new Compra[llistaCompres.getnCompres()];
        c = llistaCompres.getLlista();
        Compra c1;
        int day;
        String month;
        int months;
        int year;
        String [] linea;
        for (int i = 0; i < llistaCompres.getnCompres(); i++)
        {
            c1 = c[i];

                String aux = c1.getData().toString();
                linea = aux.split(" ");

                day = Integer.parseInt(linea[2]);
                month = linea[1];
                year = Integer.parseInt(linea[5]);
                months = retornaMonth(month);
                if (c1.getProducte() instanceof Producte_Unitat)
                {
                    pw.write(c1.getCodip()+";"+c1.getQuantitat()+";"+day+"/"+months+"/"+year+";"+c1.getCost()+";"+c1.getProducte().getNom_producte()+
                            ";"+c1.getProducte().getProductor().getNif()+";"+c1.getProducte().getProductor().getNomproductor()+";"+c1.getProducte().getLat()+
                            ";"+c1.getProducte().getLon()+";"+((Producte_Unitat) c1.getProducte()).getPreu()+";"+c1.getProducte().getCodi()+";"+c1.getProducte().getStock()+"\n");
                }
                else
                {
                    pw.write(c1.getCodip()+";"+c1.getQuantitat()+";"+day+"/"+months+"/"+year+";"+c1.getCost()+";"+c1.getProducte().getNom_producte()+
                            ";"+c1.getProducte().getProductor().getNif()+";"+c1.getProducte().getProductor().getNomproductor()+";"+c1.getProducte().getLat()+
                            ";"+c1.getProducte().getLon()+";"+((Producte_Granel) c1.getProducte()).getPreu()+
                            ";"+c1.getProducte().getCodi()+";"+((Producte_Granel) c1.getProducte()).getCeliac()+";"+c1.getProducte().getStock()+"\n");
                }

        }
        pw.close();

        PrintWriter pw2 = new PrintWriter(new File("productes2.txt"));

        Producte []p = new Producte[llistaProductes.getnProductes()];
        p = llistaProductes.getLlista();
        Producte p1;

        for (int i = 0; i < llistaProductes.getnProductes(); i++)
        {
            p1 = p[i];

            linea = p1.getCodi().split("_");
            if (p1 instanceof Producte_Unitat)
            {
                Producte_Unitat pu = (Producte_Unitat) p1;
                Productor productor = pu.getProductor();

                pw2.write(pu.getNom_producte()+";"+productor.getNif()+";"+productor.getNomproductor()+
                        ";"+pu.getLat()+";"+pu.getLon()+";"+pu.getPreu()+";"+pu.getCodi()+";"+pu.getPreukg()+";"+p1.getStock()+"\n");

            }
            else
            {
                Producte_Granel pu = (Producte_Granel) p1;
                Productor productor = pu.getProductor();

                pw2.write(pu.getNom_producte()+";"+productor.getNif()+";"+productor.getNomproductor()+";"+
                        pu.getLat()+";"+pu.getLon()+";"+pu.getPreu()+";"+pu.getCodi()+";"+pu.getCeliac()+";"+pu.getStock()+"\n");
            }


        }
        pw2.close();
    }

    private static int retornaMonth(String month) {
    int aux = -1;
	    switch (month)
        {
            case "Jan":
                aux = 1;
                break;
            case "Feb":
                aux = 2;
                break;
            case "Mar":
                aux = 3;
                break;
            case "Apr":
                aux = 4;
                break;
            case "May":
                aux = 5;
                break;
            case "Jun":
                aux = 6;
                break;
            case "Jul":
                aux = 7;
                break;
            case "Aug":
                aux = 8;
                break;
            case "Sep":
                aux = 9;
                break;
            case "Oct":
                aux = 10;
                break;
            case "Nov":
                aux = 11;
                break;
            case "Dec":
                aux = 12;
                break;
        }
        return aux;
    }

    public static LlistaProductes llegirFitxersProductes() throws FileNotFoundException {
		LlistaProductes llistaProductes = new LlistaProductes(10);
		String [] linea = new String[8];
		Scanner sc2 =new Scanner(new File("productes2.txt"));

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
			Productor [] llistaPro =llistaProductors.getLlistap();
            boolean control = false;
			if (llistaPro == null)
            {
                llistaProductors.nouElement(productor);
            }
			else
            {
                control =llistaProductors.Existeix(productor);
                if (!control)
                {
                    llistaProductors.nouElement(productor);
                }
            }
			String [] linea2 = new String[3];
			linea2 = linea[6].split("_");
			if (linea2[0].equals("UT"))
			{
				int preu = Integer.parseInt(linea[5]);
				int pes = Integer.parseInt(linea[7]);
				float stock = Float.parseFloat(linea[8]);

				Producte_Unitat productes_unitat = new Producte_Unitat(nomp, productor, lat, lon, linea[6], preu, pes, stock);
				llistaProductes.nouProducte(productes_unitat);
			}
			else
			{
				int preu = Integer.parseInt(linea[5]);
				Boolean celiac = Boolean.parseBoolean(linea[7]);
				float stock = Float.parseFloat(linea[8]);
				Producte_Granel productes_granel = new Producte_Granel(nomp, productor, lat, lon, linea[6], preu, celiac, stock);
				llistaProductes.nouProducte(productes_granel);

			}

		}
		sc2.close();

		return llistaProductes;

	}

    public static void imprimirLlistaProductes(LlistaProductes llistaProductes)
    {
        Producte p;
        Producte [] llista = llistaProductes.getLlista();
        for (int i = 0; i < llistaProductes.getnProductes(); i++) {
            p = llista[i];
            Productor productor = p.getProductor();
            System.out.println("Producte "+(i+1)+"\n Nom producte: "+ p.getNom_producte());
            System.out.println(" Codi: "+ p.getCodi());
            System.out.println(" Venedor: "+ productor.getNomproductor());
            System.out.println(" NIF: "+ productor.getNif()+ "\n\n");
        }
    }
    public static  void imprimirLlistaCeliacs(LlistaProductes llistaProductes)
    {

        llistaProductes = llistaProductes.productesCeliacs();
        Producte [] llista = llistaProductes.getLlista();
        Producte p;
        for (int i = 0; i < llistaProductes.getnProductes(); i++) {
            p = llista[i];
            Productor productor = p.getProductor();

            System.out.println("Producte "+(i+1)+"\n Nom producte: "+ p.getNom_producte());
            System.out.println(" Codi: "+ p.getCodi());
            System.out.println(" Venedor: "+ productor.getNomproductor());
            System.out.println(" NIF: "+ productor.getNif()+ "\n\n");
        }
    }
    public static void imprimirProductesMateix(LlistaProductes llistaProductes)
    {
        llistaProductes = llistaProductes.productesMateix_productor("11111111A");
        Producte [] llista = llistaProductes.getLlista();
        Producte p;
        for (int i = 0; i < llistaProductes.getnProductes(); i++) {
            p = llista[i];
            Productor productor = p.getProductor();

            System.out.println("Producte "+(i+1)+"\n Nom producte: "+ p.getNom_producte());
            System.out.println(" Codi: "+ p.getCodi());
            System.out.println(" Venedor: "+ productor.getNomproductor());
            System.out.println(" NIF: "+ productor.getNif()+ "\n\n");
        }
    }

    public static Producte nouPro()
    {
        Producte productes;
        Scanner sc = new Scanner(System.in);
        System.out.println("Inserim un nou producte");
        System.out.println("Nom producte: ");
        String nom = sc.nextLine();

        System.out.println("selecciona un productor amb el seun numero: ");
        Productor [] llistaProductors2 = llistaProductors.getLlistap();

        for (int i = 0; i < llistaProductors.getnProductors(); i++) {
            System.out.println(i+1+" "+ llistaProductors2[i].getNomproductor());
        }
        System.out.println(llistaProductors.getnProductors()+1 + " genera un uno productor y seleccional per al nou producte");

        int valor = Integer.parseInt(sc.nextLine());

        Productor productor1;
        if (valor == llistaProductors.getnProductors()+1)
        {
            System.out.println("\nNIF productor:");
            String nif = sc.nextLine();
            System.out.println("Nom productor:");
            String productor = sc.nextLine();
            productor1 = new Productor(nif, productor);
            llistaProductors.nouElement(productor1);
        }
        else
        {
            productor1 = llistaProductors2[valor-1];
        }

        /*System.out.println("NIF productor:");
        String nif = sc.nextLine();
        System.out.println("Nom productor:");
        String productor = sc.nextLine();
        */

        System.out.println("latitud:");
        double lat = sc.nextDouble();
        System.out.println("long:");
        double longi = sc.nextDouble();

        System.out.println("Es tracta de granel o unitat? (1 = granel| 0 = unitat)");
        if (sc.nextInt() == 1)
        {
            System.out.println("codi");
            int codi_v = sc.nextInt();
            String codi = "GR_"+codi_v;
            System.out.println("Preu del producte: ");
            int preu = sc.nextInt();
            System.out.println("Producte valid per a celiacs s/n");
            String c = sc.next();
            boolean control = false;
            if (c.equals("s")) {
                control = true;
            }

            System.out.println("Stock del producte");
            int stock = sc.nextInt();

            Producte_Granel gr =  new Producte_Granel(nom, productor1, lat, longi,codi, preu, control, stock);
            productes = gr;
        }
        else
        {
            System.out.println("codi");
            int codi_v = sc.nextInt();
            String codi = "UT_"+codi_v;
            System.out.println("Preu del producte: ");
            int preu = sc.nextInt();
            System.out.println("Preu del producte per kg: ");
            int preukg = sc.nextInt();
            System.out.println("Stock del producte: ");
            int stock = sc.nextInt();

            Producte_Unitat un =  new Producte_Unitat(nom, productor1, lat, longi,codi, preu, preukg, stock);
            productes = un;
        }
        return productes;
    }

    public static String llegirNif()
    {
        String nif;
        System.out.println("Introdueix el NIF del productor: ");
        Scanner sc = new Scanner(System.in);
        nif = sc.nextLine();
        return nif;
    }

    public static void main(String[] args) throws FileNotFoundException, ParseException {

        System.out.println("Les coordenades del client seran per defecte");
        LlistaCompres llistaCompres = new LlistaCompres(20);
        LlistaProductes llistaProductes = new LlistaProductes(20);
        llistaProductes = llegirFitxersProductes();
        llistaCompres = llegirFitxersCompres();


        Client client = new Client(0, 0);

        Scanner sn = new Scanner(System.in);
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario

        while(!salir){

            System.out.println("1. Llistar tots els productes");
            System.out.println("2. Llistar productes per celiacs");
            System.out.println("3. Llistar ofertes d'un productor");
            System.out.println("4. Afegir nou producte");
            System.out.println("5. Esborrar productes d'un productor");
            System.out.println("6. Modificar dades d'un producte (ESTOC/PREU)");
            System.out.println("7. Llistar compres realitzades");
            System.out.println("8. Modificar ubicacio usuari");
            System.out.println("9. Productes/Productors proxims");
            System.out.println("10. Nova Compra");
            System.out.println("11. Salir");
            System.out.println("Escribe una de las opciones");
            opcion = sn.nextInt();




            switch(opcion){
                case 1:
                    System.out.println("Has seleccionado la opcion 1");
                    imprimirLlistaProductes(llistaProductes);

                    break;
                case 2:
                    System.out.println("Has seleccionado la opcion 2");
                    imprimirLlistaCeliacs(llistaProductes);
                    break;
                case 3:
                    System.out.println("Has seleccionado la opcion 3");
                    imprimirProductesMateix(llistaProductes);
                    break;
                case 4:
                    System.out.println("Has seleccionado la opcion 4");
                    Producte productes;
                    productes =nouPro();
                    llistaProductes.nouProducte(productes);
                    break;
                case 5:
                    System.out.println("Has seleccionado la opcion 5");
                    String nif;
                    nif = llegirNif();
                    llistaProductes.eliminaProducte(nif);
                    break;
                case 6:
                    System.out.println("Has seleccionado la opcion 6");
                    System.out.println("Modificar estoc o preu d'un producte");
                    modificarProducte(llistaProductes);
                    break;
                case 7:
                    System.out.println("Has seleccionado la opcion 7");
                    visualitzarCompres(llistaCompres);
                    break;
                case 8:
                    System.out.println("Has seleccionado la opcion 8");
                    System.out.println("Modifiquem la ubicacio del client: ");
                    modificarUbicacio(client);
                    break;
                case 9:
                    System.out.println("Has seleccionado la opcion 9");
                    productesProxims(llistaProductes);
                    break;
                case 10:
                    System.out.println("Has seleccionado la opcion 10");
                    novaCompra(llistaCompres, llistaProductes);
                    break;
                case 11:
                    EscriureFitxers(llistaCompres, llistaProductes);
                    salir=true;
                    break;
                default:
                    System.out.println("Solo números entre 1 y 4");
            }

        }

    }

    private static void productesProxims(LlistaProductes llistaProductes) {

	    Scanner scanner = new Scanner(System.in);

        System.out.println("introdueix les dues coordenades: ");
        double lat = scanner.nextDouble();
        double longi = scanner.nextDouble();
        int producte = 0;

        System.out.println("introdueix el producte: ");
        String codi = scanner.nextLine();
        Producte[] llista = llistaProductes.getLlista();
        for (int i = 0; i < llistaProductes.getnProductes(); i++) {

            if (codi.equals(llista[i].getCodi()))
            {
                producte = i;
            }
        }
        double distancia = distanciaCoord(lat, longi, llista[producte].getLat(), llista[producte].getLon());
        System.out.println("la distancia entre els dos elemetns es: "+ distancia);
    }

    private static void novaCompra(LlistaCompres llistaCompres, LlistaProductes llistaProductes) {

	    Scanner scanner = new Scanner(System.in);

        System.out.println("introdueix el codi del producte");
	    String codi = scanner.nextLine();
        System.out.println("introdueix la cantitad de element que vols del producte");
	    int quantitat = scanner.nextInt();
	    Date date = new Date();

        int cost = 0;
        Producte [] llista = llistaProductes.getLlista();
        int producte = 0;
        for (int i = 0; i < llistaProductes.getnProductes(); i++) {
            if (codi.equals(llista[i].getCodi()))
            {
                producte = i;
            }
        }

        String [] linea = codi.split("_");
        if (linea[0].equals("UT"))
        {
            cost = quantitat *  ((Producte_Unitat) llista[producte]).getPreukg();
        }
        else
        {
            cost = quantitat *  ((Producte_Granel) llista[producte]).getPreu();
        }
        Compra compres = new Compra(codi, quantitat, date, cost, llista[producte]);
        llistaCompres.nouCompra(compres);
    }

    private static void modificarProducte(LlistaProductes llistaProductes) {
	    Scanner scanner = new Scanner(System.in);

        System.out.println("Quin producte vols modificar (Insereix el codi)");
        String codi = scanner.nextLine();
        Producte [] llista = llistaProductes.getLlista();
        int preu, estoc;
        int producte = 0;
        for (int i = 0; i < llistaProductes.getnProductes(); i++) {
            if (llista[i].getCodi().equals(codi))
            {
                producte = i;
            }
        }
        codi = llista[producte].getCodi();
        String [] linea = codi.split("_");
        if (linea[0].equals("UT"))
        {
            Producte_Unitat ut = (Producte_Unitat) llista[producte];
            System.out.println("Preu kg actual: "+ ut.getPreukg());
            System.out.println("Preu estoc: "+ llista[producte].getStock()+ "en Unitats\n");

            System.out.println("Introdueix nou preu:");
            preu = scanner.nextInt();
            System.out.println("Introdueix nou estoc:");
            estoc = scanner.nextInt();
            ut.setPreu(preu);
            llista[producte].setStock(estoc);
            llista[producte] = ut;
        }
        else
        {
            Producte_Granel gr = (Producte_Granel) llista[producte];
            System.out.println("Preu acutal: "+ gr.getPreu());
            System.out.println("Estoc acutal: "+ gr.getStockg()+ "en kg \n");
            System.out.println("Introdueix nou preu:");
            preu = scanner.nextInt();
            System.out.println("Introdueix nou estoc:");
            estoc = scanner.nextInt();
            gr.setPreuG(preu);
            gr.setStock(estoc);
            llista[producte] = gr;
        }

        llistaProductes.setLlista(llista);
    }

    private static void visualitzarCompres(LlistaCompres llistaCompres) {
        Compra [] llista;
        llista = llistaCompres.getLlista();
        int i =0;
        while(llista[i] != null)
        {
            System.out.println("Compra "+ i);
            System.out.println("Codi: "+ llista[i].getCodip());
            System.out.println("cost: "+ llista[i].getCost());
            System.out.println("Codi: "+ llista[i].getQuantitat());
            System.out.println("Codi: "+ llista[i].getData()+ "\n");
            i++;
        }

    }

    private static void modificarUbicacio(Client client) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Latitud:");
        double lat = scanner.nextDouble();
        System.out.println("Longitud:");
        double longi = scanner.nextDouble();

        client.setLatc(lat);
        client.setLonc(longi);
    }

}
