package EjerciciosClase2;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PilaCompleta<E> extends Stack<E> {

    public void pushAll(List<? extends E> list){
        for(E e:list){
            super.push(e);
        }

    }

    public List<? super E> popAll(){
        List<E> outPut = new ArrayList<>();
        while (this.empty()){
            E e = super.pop();
            outPut.add(e);
        }
        return outPut;
    }
}
