package items.conjuntos;

import items.basic.NumConst;
import items.conjuntos.Lista;

public class Dimension extends Lista{
    public int fila;

    public Dimension(NumConst c, int f){
        super(c);
        fila=f;
    }
}
