package main;


import EjerciciosClase2.ListaUtilitaria;
import EjerciciosClase2.NoNullMap;
import EjerciciosClase2.PilaCompleta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        PilaCompleta<String> listO = new PilaCompleta<String>();
        ArrayList<String> listDatos = new ArrayList<>();
        listDatos.add("Dato 1");
        listDatos.add("Dato 2");
        listDatos.add("Dato 3");
        listDatos.add("Dato 4");
        listDatos.add("Dato 5");

        listO.pushAll(listDatos);
        System.out.println("*EJERCICIO 1 CLASE 2 - PILACOMPETA");
        System.out.println(listO.popAll());
        System.out.println("");


        HashMap<Integer,String> mapDato = new NoNullMap<>();

        mapDato.put(1,"Esto no es null");
        //mapDato.put(2,null);

        System.out.println("*EJERCICIO 2 CLASE 2 - NO NULL");
        System.out.println(mapDato.get(1));
        System.out.println(" ");


        List<String> fuente = new ArrayList<>();
        fuente.add("Primero");
        fuente.add("Segundo");
        fuente.add("Tercero");
        fuente.add("Cuarto");
        fuente.add("Quinto");

        List<String> destino = new ArrayList<>();

        ListaUtilitaria.addOddElements(fuente, destino);

        System.out.println("*EJERCICIO 3 CLASE 2 - ADDODDELEMENST");
        for(String e:destino){
            System.out.println(e);
        }

    }
}