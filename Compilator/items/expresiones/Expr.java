package items.expresiones;

import items.basic.BoolConst;
import items.basic.NumConst;
import items.basic.Operadores;
import items.basic.Variable;
import items.instrucciones.InstDo;

public class Expr{
    public int type, fila;
    public Variable v;
    public InstDo i;
    public final Expr eb1;
    public final Expr eb2;
    public final Operadores op;
    public NumConst n;
    public BoolConst b;


    public Expr(Variable var, int f){
        type=1;
        v=var;
        eb1=null;
        eb2=null;
        op=null;
        fila=f;
    }

    public Expr(InstDo ins, int f){
        type=2;
        i=ins;
        eb1=null;
        eb2=null;
        op=null;
        fila=f;
    }
    
    public Expr(Expr ex1, Expr ex2, Operadores o, int f){
        type=3;
        eb1=ex1;
        eb2=ex2;
        op=o;
        fila=f;
    }
    
    public Expr(NumConst num, int f){
        type=4;
        n=num;
        eb1=null;
        eb2=null;
        op=null;
        fila=f;
    }
    public Expr(BoolConst num, int f){
        type=5;
        b=num;
        eb1=null;
        eb2=null;
        op=null;
        fila=f;
    }
    
    public Expr(Expr ex, Operadores o, int f){
        type=6;
        eb1=ex;
        eb2=null;
        op=o;
        fila=f;
    }


    public String toString(){
        switch (type){
            case 1:
                return "("+v.toString()+")";
            case 2:
                return "("+i.toString()+")";
            case 3:
                return "("+eb1.toString()+op+eb2.toString()+")";
            case 4:
                return "("+n.toString()+")";
            case 5:
                return "("+n.toString()+")";
            case 6:
                return "("+op+eb1.toString()+")";
            default:
                return "EXPRESION ERRONEA";
        }
    }
}
