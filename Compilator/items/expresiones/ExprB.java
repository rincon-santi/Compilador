package items.expresiones;

import items.basic.Operadores;
import items.basic.BoolConst;

public class ExprB extends Expr{
    private final Expr eb1;
    private final Expr eb2;
    private final Operadores op;
    private BoolConst n;

    public ExprB(ExprB ex, Operadores o){
        super.type=3;
        eb1=ex;
        eb2=null;
        op=o;
    }

    public ExprB(Expr ex1, Expr ex2, Operadores o){
        super.type=4;
        eb1=ex1;
        eb2=ex2;
        op=o;
    }
    
    public ExprB(BoolConst num){
        super.type=5;
        n=num;
        eb1=null;
        eb2=null;
        op=null;
    }

    @Override
    public String toString(){
        switch (type){
            case 1:
                return super.toString();
            case 2:
                return super.toString();
            case 3:
                return "("+op+eb1.toString()+")";
            case 4:
                return "("+eb1.toString()+op+eb2.toString()+")";
            case 5:
                return "("+n.toString()+")";
            default:
                return "EXPRESION ERRONEA";
        }
    }
}
