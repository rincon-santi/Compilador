package items.expresiones;

import items.basic.Operadores;
import items.basic.NumConst;

public class ExprA extends Expr{
    private final ExprA eb1;
    private final ExprA eb2;
    private final Operadores op;
    private NumConst n;

    public ExprA(ExprA ex1, ExprA ex2, Operadores o){
        super.type=3;
        eb1=ex1;
        eb2=ex2;
        op=o;
    }
    
    public ExprA(NumConst num){
        super.type=4;
        n=num;
        eb1=null;
        eb2=null;
        op=null;
    }

    @Override
    public String toString(){
        switch (super.type){
            case 1:
                return super.toString();
            case 2:
                return super.toString();
            case 3:
                return "("+eb1.toString()+op+eb2.toString()+")";
            case 4:
                return "("+n.toString()+")";
            default:
                return "EXPRESION ERRONEA";
        }
    }
}
