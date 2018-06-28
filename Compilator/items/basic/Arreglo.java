package items.basic;

import items.conjuntos.Dimension;

public class Arreglo extends TipoG{
    public final Dimension d;
    public final int fila;

    public Arreglo(Dimension dim, Tipo tip, int f){
        super.t=tip;
        d=dim;
        fila=f;
    }

    @Override
    public String toString(){
        return "Array "+super.t+" de "+d.toString();
    }
}
