package items.instrucciones;

import items.conjuntos.Generic;
import items.conjuntos.Lista;
import items.expresiones.Expr;

public class InstIf implements Generic{
    public final Expr condicion;
    public final Lista consecuencia;
    public final Lista aucontraire;
    
    public InstIf(Expr cond, Lista con, Lista au){
        condicion=cond;
        consecuencia=con;
        aucontraire=au;
    }

    @Override
    public String toString(){
        if (aucontraire==null){
            return "Si "+condicion.toString()+" entonces realiza "+consecuencia.toString();
        }else{
            return "Si "+condicion.toString()+" entonces realiza "+consecuencia.toString()+", en caso contrario ejecuta "+aucontraire.toString();
        }
    }
}
