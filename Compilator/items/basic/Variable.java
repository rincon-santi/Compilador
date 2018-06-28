package items.basic;

import items.conjuntos.Lista;
import items.declaraciones.Decl;

public class Variable{
    public final String nombre;
    public final Lista coord;
    public Decl d;
    public int fila;
    
    public Variable(String n, Lista l, int f){
        nombre=n;
        coord=l;
        fila=f;
    }

    @Override
    public String toString (){
        if (coord==null) return "Variable "+nombre;
        else return "Variable en las coordenadas "+coord.toString()+" del identificador "+nombre;
    }
}
