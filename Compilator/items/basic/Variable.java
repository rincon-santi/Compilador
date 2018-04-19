package items.basic;

import items.conjuntos.Lista;

public class Variable{
    private final String nombre;
    private final Lista coord;
    
    public Variable(String n, Lista l){
        nombre=n;
        coord=l;
    }

    @Override
    public String toString (){
        if (coord==null) return "Variable "+nombre;
        else return "Variable en las coordenadas "+coord.toString()+" del identificador "+nombre;
    }
}
