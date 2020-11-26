import BaseDeDades.*;

import java.io.File;
import java.io.FileNotFoundException;
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






            //llistaProductes.nouProducte();

        }
        sc2.close();


    }

    public static void main(String[] args) throws FileNotFoundException, ParseException {


        System.out.println("Les coordenades del client seran per defecte");
        LlistaCompres llistaCompres = new LlistaCompres(10);
        LlistaProductes llistaProductes = new LlistaProductes(10);
        llegirFitxers(llistaCompres, llistaProductes);



        //Compres c = new Compres();


    }

}
