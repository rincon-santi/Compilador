package items.instrucciones;

import items.conjuntos.Generic;
import items.expresiones.Expr;
import items.conjuntos.Lista;

public class InstWh implements Generic{
    public Expr cond;
    public Lista bloque;

    public InstWh(Expr c, Lista b){
        cond=c;
        bloque=b;
    }

    @Override
    public String toString(){
        return "Mientras "+cond.toString()+" hacer "+bloque.toString();
    }
}
