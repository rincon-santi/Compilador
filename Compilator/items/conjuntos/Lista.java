package items.conjuntos;

import java.util.LinkedList;

public class Lista{  
    private final LinkedList<Object> pila;
    
    public Lista(String kind, Object inicial){
        pila = new LinkedList<>();
        pila.push(inicial);    
    }
    
    public void add(Object unomas){
        pila.push(unomas);
    }

    @Override
    public String toString(){
        String buffer="(";
        LinkedList<Object> aux=new LinkedList<>();
        while(pila.getFirst()!=null){        
            aux.add(pila.getFirst());
            buffer+=pila.getFirst().toString();
            buffer+=",";
            pila.pop();
        }
        while(aux.getFirst()!=null){
            pila.add(aux.getFirst());
            aux.pop();
        }
        buffer+=")";
        return buffer;
    }
}
