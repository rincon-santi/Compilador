package items.declaraciones;

import items.conjuntos.Generic;
import items.basic.TipoG;
import items.basic.Variable;
import items.basic.Tipo;
import items.expresiones.Expr;
import items.conjuntos.Lista;

public class DeclF implements Generic{
    private final String nombre;
    private final Lista listaTipos;
    private final Tipo tipoSalida;
    private final Lista listaVariables;
    private final Lista operaciones;
    private final Expr salida;

    public DeclF(String n, Lista lt, Tipo ts, Lista lv, Lista ops, Expr s){
        nombre=n;
        listaTipos=lt;
        tipoSalida=ts;
        listaVariables=lv;
        operaciones=ops;
        salida=s;
    }

    @Override
    public String toString(){
        return "Función "+nombre+" que parte de "+listaTipos.toString()+" y devuelve "+tipoSalida.toString()+"."+'\n'+"Desde "+listaVariables.toString()+" efectúa "+operaciones.toString()+" y retorna "+salida.toString();
    }
}
