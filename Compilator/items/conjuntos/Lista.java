package items.conjuntos;

import java.util.LinkedList;

public class Lista{  
    public final LinkedList<Object> pila;
    
    public Lista(){
        pila=new LinkedList<>();
    }
    
    public Lista(Object inicial){
        pila = new LinkedList<>();
        pila.add(inicial);    
    }
    
    public void add(Object unomas){
        pila.add(unomas);
    }

    @Override
    public String toString(){
        String buffer="(";
        LinkedList<Object> aux=new LinkedList<>();
        while(!pila.isEmpty()){        
            aux.add(pila.getFirst());
            buffer+=pila.getFirst().toString();
            pila.pop();
            if (!pila.isEmpty())             buffer+=",";
        }
        while(!aux.isEmpty()){
            pila.add(aux.getFirst());
            aux.pop();
        }
        buffer+=")";
        return buffer;
    }
}
