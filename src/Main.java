import BaseDeDades.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        int codi, quant, cost;
        Date date;
        Compres[] llistac;

        int i = 0;

        while(sc.hasNextLine())
        {
            linea =sc.nextLine().split(";");

            codi = Integer.parseInt(linea[0]);
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

    public static void main(String[] args) throws FileNotFoundException, ParseException {


        System.out.println("Les coordenades del client seran per defecte");
        LlistaCompres llistaCompres = new LlistaCompres(10);
        LlistaProductes llistaProductes = new LlistaProductes(10);
        llegirFitxers(llistaCompres, llistaProductes);
        Productes [] llistaa = llistaProductes.getLlista();
        Productes u =  llistaa[0];
        Productes_Unitat uni;
        System.out.println("prova");


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
                    break;
                case 6:
                    System.out.println("Has seleccionado la opcion 6");
                    break;
                case 7:
                    System.out.println("Has seleccionado la opcion 7");
                    break;
                case 8:
                    System.out.println("Has seleccionado la opcion 8");
                    break;
                case 9:
                    System.out.println("Has seleccionado la opcion 9");
                    break;
                case 10:
                    System.out.println("Has seleccionado la opcion 10");
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

}
