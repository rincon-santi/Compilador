
package items.basic;

import items.conjuntos.Lista;
import items.expresiones.Expr;

public class TipoFunc extends TipoG {
    public final Lista listaTipos;
    public final Tipo tipoSalida;
    public final Lista listaVariables;
    public final Lista operaciones;
    public final Expr salida;
    
    public TipoFunc(Lista lt, Tipo ts, Lista lv, Lista ops, Expr s){
        super.t=null;
        listaTipos=lt;
        tipoSalida=ts;
        listaVariables=lv;
        operaciones=ops;
        salida=s;
    }
    
    @Override
    public String toString(){
        return " que parte de "+listaTipos.toString()+" y devuelve "+tipoSalida.toString()+"."+'\n'+"Desde "+listaVariables.toString()+" efectúa "+operaciones.toString()+" y retorna "+salida.toString();
    }
}
