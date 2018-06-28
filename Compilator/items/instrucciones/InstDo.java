package items.instrucciones;

import items.conjuntos.Generic;
import items.conjuntos.Lista;
import items.basic.Variable;
import items.declaraciones.DeclF;

public class InstDo implements Generic{
    public final String nombre;
    public final Lista atributos;
    public int fila;
    public DeclF d;

    public InstDo(String n, Lista a, int f){
        nombre=n;
        atributos=a;
        fila=f;
    }

    @Override
    public String toString(){
        return "Llamada a la funcion "+nombre+" con atributos "+atributos.toString();
    }
}
