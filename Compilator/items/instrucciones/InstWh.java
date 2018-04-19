package items.instrucciones;

import items.conjuntos.Generic;
import items.expresiones.ExprB;
import items.conjuntos.Lista;

public class InstWh implements Generic{
    private ExprB cond;
    private Lista bloque;

    public InstWh(ExprB c, Lista b){
        cond=c;
        bloque=b;
    }

    @Override
    public String toString(){
        return "Mientras "+cond.toString()+" hacer "+bloque.toString();
    }
}
