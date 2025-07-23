package EjerciciosClase2;

import java.util.List;

public class ListaUtilitaria {

    public static <T> void addOddElements(List<T> fuente, List<T> destino){
        for(int i = 0; i < fuente.size(); i += 2){
            destino.add(fuente.get(i));
        }
    }
}
