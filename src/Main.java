import BaseDeDades.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Scanner;

public class Main {

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

    public static void llegirFitxers(LlistaCompres llistaCompres, LlistaProductes llistaProductes) throws FileNotFoundException, ParseException {

        Scanner sc =new Scanner(new File("C:\\Users\\serret96\\Desktop\\Progra\\practica3\\Progra3\\Progra3\\src\\compres.txt"));
        String [] linea = new String[8];
        String codi;
        int  quant, cost;
        Date date;
        Compres[] llistac;

        int i = 0;

        while(sc.hasNextLine())
        {
            linea =sc.nextLine().split(";");

            codi = linea[0];
            quant = Integer.parseInt(linea[1]);
            date = new SimpleDateFormat("dd/MM/yyyy").parse(linea[2]);
            cost = Integer.parseInt(linea[3]);

            Compres c = new Compres(codi, quant, date, cost);

            llistaCompres.nouCompra(c);

            i++;
        }
        sc.close();

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


            Productes p = new Productes(nomp, nif, nomv, lat, lon, linea[6]);
            String [] linea2 = new String[3];
            linea2 = linea[6].split("_");
            if (linea2[0].equals("UT"))
            {
                int preu = Integer.parseInt(linea[5]);
                int pes = Integer.parseInt(linea[7]);
                int stock = Integer.parseInt(linea[8]);

                Productes_Unitat productes_unitat = new Productes_Unitat(nomp, nif, nomv, lat, lon, linea[6], preu, pes, stock);
                llistaProductes.nouProducte(productes_unitat);
            }
            else
            {
                int preu = Integer.parseInt(linea[5]);
                Boolean celiac = Boolean.parseBoolean(linea[7]);
                int stock = Integer.parseInt(linea[8]);
                Productes_Granel productes_granel = new Productes_Granel(nomp, nif, nomv, lat, lon, linea[6], preu, celiac, stock);
                llistaProductes.nouProducte(productes_granel);

            }

        }
        sc2.close();


    }

    public static  void EscriureFitxers(LlistaCompres llistaCompres, LlistaProductes llistaProductes) throws FileNotFoundException {

        PrintWriter pw = new PrintWriter(new File("compres.txt"));

        Compres []c = new Compres[llistaCompres.getnCompres()];
        c = llistaCompres.getLlista();

        Compres c1;
        for (int i = 0; i < llistaCompres.getnCompres(); i++)
        {
            c1 = c[i];
            pw.write(c1.getCodip()+";"+c1.getData()+";"+c1.getQuantitat()+";"+c1.getCost()+"\n");
        }
        pw.close();

        PrintWriter pw2 = new PrintWriter(new File("compres2.txt"));

        Productes []p = new Productes[llistaProductes.getnProductes()];
        p = llistaProductes.getLlista();
        Productes p1;

        for (int i = 0; i < llistaProductes.getnProductes(); i++)
        {
            p1 = p[i];
            String [] linea;
            linea = p1.getCodi().split("_");
            if (linea[0].equals("UT"))
            {
                Productes_Unitat pu = (Productes_Unitat) p1;
                pw2.write(pu.getCodi()+";"+pu.getNif()+";"+pu.getNom_producte()+";"+pu.getNom_venedor()+
                        ";"+pu.getLat()+";"+pu.getLon()+";"+pu.getPreu()+";"+pu.getPreukg()+";"+pu.getStocku()+"\n");

            }
            else
            {
                Productes_Granel pu = (Productes_Granel) p1;
                pw2.write(pu.getCodi()+";"+pu.getNif()+";"+pu.getNom_producte()+";"+pu.getNom_venedor()+";"+
                        pu.getLat()+";"+pu.getLon()+";"+pu.getPreu()+";"+pu.getCeliac()+";"+pu.getStockg()+"\n");
            }


        }
        pw2.close();
    }

    public static void imprimirLlistaProductes(LlistaProductes llistaProductes)
    {
        Productes p;
        Productes [] llista = llistaProductes.getLlista();
        for (int i = 0; i < llistaProductes.getnProductes(); i++) {
            p = llista[i];
            System.out.println("Producte "+(i+1)+"\n Nom producte: "+ p.getNom_producte());
            System.out.println(" Codi: "+ p.getCodi());
            System.out.println(" Venedor: "+ p.getNom_venedor());
            System.out.println(" NIF: "+ p.getNif()+ "\n\n");
        }
    }
    public static  void imprimirLlistaCeliacs(LlistaProductes llistaProductes)
    {

        llistaProductes = llistaProductes.productesCeliacs();
        Productes [] llista = llistaProductes.getLlista();
        Productes p;
        for (int i = 0; i < llistaProductes.getnProductes(); i++) {
            p = llista[i];
            System.out.println("Producte "+(i+1)+"\n Nom producte: "+ p.getNom_producte());
            System.out.println(" Codi: "+ p.getCodi());
            System.out.println(" Venedor: "+ p.getNom_venedor());
            System.out.println(" NIF: "+ p.getNif()+ "\n\n");
        }
    }
    public static void imprimirProductesMateix(LlistaProductes llistaProductes)
    {
        llistaProductes = llistaProductes.productesMateix_productor("11111111A");
        Productes [] llista = llistaProductes.getLlista();
        Productes p;
        for (int i = 0; i < llistaProductes.getnProductes(); i++) {
            p = llista[i];
            System.out.println("Producte "+(i+1)+"\n Nom producte: "+ p.getNom_producte());
            System.out.println(" Codi: "+ p.getCodi());
            System.out.println(" Venedor: "+ p.getNom_venedor());
            System.out.println(" NIF: "+ p.getNif()+ "\n\n");
        }
    }

    public static Productes nouPro()
    {
        Productes productes;
        Scanner sc = new Scanner(System.in);
        System.out.println("Inserim un nou producte");
        System.out.println("Nom producte: ");
        String nom = sc.nextLine();
        System.out.println("NIF productor:");
        String nif = sc.nextLine();
        System.out.println("Nom productor:");
        String productor = sc.nextLine();
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

            Productes_Granel gr =  new Productes_Granel(nom, nif, productor, lat, longi,codi, preu, control, stock);
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

            Productes_Unitat un =  new Productes_Unitat(nom, nif, productor, lat, longi,codi, preu, preukg, stock);
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
        LlistaCompres llistaCompres = new LlistaCompres(10);
        LlistaProductes llistaProductes = new LlistaProductes(10);
        llegirFitxers(llistaCompres, llistaProductes);
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
                    Productes productes;
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
        Productes[] llista = llistaProductes.getLlista();
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
        Productes [] llista = llistaProductes.getLlista();
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
            cost = quantitat *  ((Productes_Unitat) llista[producte]).getPreukg();
        }
        else
        {
            cost = quantitat *  ((Productes_Granel) llista[producte]).getPreu();
        }
        Compres compres = new Compres(codi, quantitat, date, cost);
        llistaCompres.nouCompra(compres);
    }

    private static void modificarProducte(LlistaProductes llistaProductes) {
	    Scanner scanner = new Scanner(System.in);

        System.out.println("Quin producte vols modificar (Insereix el codi)");
        String codi = scanner.nextLine();
        Productes [] llista = llistaProductes.getLlista();
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
            Productes_Unitat ut = (Productes_Unitat) llista[producte];
            System.out.println("Preu kg actual: "+ ut.getPreukg());
            System.out.println("Preu estoc: "+ ut.getStocku()+ "en Unitats\n");

            System.out.println("Introdueix nou preu:");
            preu = scanner.nextInt();
            System.out.println("Introdueix nou estoc:");
            estoc = scanner.nextInt();
            ut.setPreu(preu);
            ut.setStockU(estoc);
            llista[producte] = ut;
        }
        else
        {
            Productes_Granel gr = (Productes_Granel) llista[producte];
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
        Compres [] llista;
        llista = llistaCompres.getLlista();

        for (int i = 0; i < llista.length; i++) {
            System.out.println("Compra "+ i);
            System.out.println("Codi: "+ llista[i].getCodip());
            System.out.println("cost: "+ llista[i].getCost());
            System.out.println("Codi: "+ llista[i].getQuantitat());
            System.out.println("Codi: "+ llista[i].getData()+ "\n");
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
