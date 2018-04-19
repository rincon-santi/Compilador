package items.instrucciones;

import items.conjuntos.Generic;
import items.expresiones.Expr;
import items.basic.Variable;

public class InstAsign implements Generic{
    private final Variable var;
    private final Expr expr;

    public InstAsign(Variable v, Expr e){
        var=v;
        expr=e;
    }

    @Override
    public String toString(){
        return "Asignacion a "+var.toString()+" de "+expr.toString();
    }
}
