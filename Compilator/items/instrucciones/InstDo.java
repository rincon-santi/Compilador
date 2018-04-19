package items.instrucciones;

import items.conjuntos.Generic;
import items.conjuntos.Lista;
import items.basic.Variable;

public class InstDo implements Generic{
    private final String nombre;
    private final Lista atributos;

    public InstDo(String n, Lista a){
        nombre=n;
        atributos=a;
    }

    @Override
    public String toString(){
        return "Llamada a la funcion "+nombre+" con atributos "+atributos.toString();
    }
}
