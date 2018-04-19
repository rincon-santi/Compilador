package items.expresiones;

import items.basic.Variable;
import items.instrucciones.InstDo;

public class Expr{
    protected int type;
    private Variable v;
    private InstDo i;

    public Expr(){}

    public Expr(Variable var){
        type=1;
        v=var;
    }

    public Expr(InstDo ins){
        type=2;
        i=ins;
    }

    public String toString(){
        switch (type){
            case 1:
                return "("+v.toString()+")";
            case 2:
                return "("+i.toString()+")";
            default:
                return "EXPRESION ERRONEA";
        }
    }
}
