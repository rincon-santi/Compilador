package items.declaraciones;

import items.conjuntos.Generic;
import items.basic.TipoG;

public class Decl implements Generic, Declaration{
    public String nombre;
    public TipoG tipo;
    public int pos;
    public int nivelDef, fila;
    
    public Decl(String n, TipoG t, int nivel, int f){
        nivelDef=nivel;
        nombre=n;
        tipo=t;
        fila=f;
    }

    @Override
    public String toString(){
        return "Variable "+nombre+" definida por "+tipo;
    }
}
