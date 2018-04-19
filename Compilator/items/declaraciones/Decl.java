package items.declaraciones;

import items.conjuntos.Generic;
import items.basic.TipoG;

public class Decl implements Generic{
    private final String nombre;
    private final TipoG tipo;

    public Decl(String n, TipoG t){
        nombre=n;
        tipo=t;
    }

    @Override
    public String toString(){
        return "Variable "+nombre+" definida por "+tipo;
    }
}
