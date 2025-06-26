package EjerciciosClase2;

import java.util.HashMap;

public class NoNullMap<K, V> extends HashMap<K, V> {

    @Override
    public V put(K key, V value){
        if(value == null)
            throw new RuntimeException("SOLO VALORES DIFERENTES A NULL");

        return super.put(key, value);
    }
}
