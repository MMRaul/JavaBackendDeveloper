package main;


import EjerciciosClase2.NoNullMap;
import EjerciciosClase2.PilaCompleta;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        HashMap<Integer,String> mapDato = new NoNullMap<>();

        mapDato.put(1,"Esto no es null");
        //mapDato.put(2,null);
        System.out.println(mapDato.get(1));
        //System.out.println(mapDato.get(2));


        PilaCompleta<String> listO = new PilaCompleta<String>();
        ArrayList<String> listDatos = new ArrayList<>();
        listDatos.add("Dato 1");
        listDatos.add("Dato 2");
        listDatos.add("Dato 3");
        listDatos.add("Dato 4");
        listDatos.add("Dato 5");

        listO.pushAll(listDatos);

        System.out.println(listO);

    }
}