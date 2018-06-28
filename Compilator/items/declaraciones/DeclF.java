package items.declaraciones;

import items.conjuntos.Generic;
import items.basic.TipoG;
import items.basic.Variable;
import items.basic.Tipo;
import items.basic.TipoFunc;
import items.expresiones.Expr;
import items.conjuntos.Lista;

public class DeclF  implements Generic, Declaration{
    public String nombre;
    public TipoG tipo;
    public int pos;
    public int nivelDefinicion;
    public int direccion;
    public int le, fila;

    public DeclF(String n, Lista lt, Tipo ts, Lista lv, Lista ops, Expr s, int f){
        nombre=n;
        tipo=new TipoFunc(lt, ts, lv, ops, s);
        fila=f;
    }

    @Override
    public String toString(){
        return "Función "+nombre+tipo.toString();
    }
}
