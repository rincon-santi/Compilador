
package tipado;

import errores.ExcpTipos;
import items.conjuntos.*;
import items.declaraciones.*;
import items.basic.*;
import items.expresiones.*;
import items.instrucciones.*;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java_cup.runtime.Symbol;

public class AnalizadorTipos {
    private TablasErrores t = new TablasErrores();

	
	
	private void parsearListaFunciones(Lista a) throws ExcpTipos {
		Iterator<Object> id = a.pila.iterator();
		while (id.hasNext()){
			DeclF i = (DeclF)id.next();
			if (((TipoFunc)i.tipo).tipoSalida != parsearExpr(((TipoFunc)i.tipo).salida, true))
				throw new ExcpTipos("Intento de declarar una función asignandole un tipo de retorno que no corresponde en "+i.toString(), i.fila);
			Iterator<Object> ii = ((TipoFunc)i.tipo).operaciones.pila.iterator();
                        while(ii.hasNext()){
                            parsearInstr(ii.next());
                        }
		}
		
	}

        private Tipo parsearVar(Variable v, boolean strict) throws ExcpTipos{
                    if (v.coord==null){
                        if((v.d.tipo instanceof Arreglo)&&strict) throw new ExcpTipos("Intento de utilización de una colección sin acceder a un elemento", v.fila);
                        if(v.d.tipo instanceof TipoFunc) throw new ExcpTipos("Intento de utilización de una función como variable", v.fila);
                        else return v.d.tipo.t;
                    }
                    else{
                        if (!(v.d.tipo instanceof Arreglo)) throw new ExcpTipos("Intento de acceder a un índice de un elemento que no es una colección", v.fila);
                        Iterator<Object> it =v.coord.pila.iterator();
                        while (it.hasNext()) {
                            Expr e=(Expr)it.next();
                            try {
                            t.Acceso(parsearExpr(e, true));
                            } catch (Exception ex) {
                                if (ex instanceof ExcpTipos) throw (ExcpTipos)ex;
                                throw new ExcpTipos(ex.getMessage(),e.fila);
                            }
                        }
                        if (v.coord.pila.size() != ((Arreglo)v.d.tipo).d.pila.size()) throw new ExcpTipos("Número de índices de acceso no consistente con las dimensiones de la declaración", v.fila);
                        return ((Arreglo)v.d.tipo).t;
                    }
        }
        
	private Tipo parsearExpr(Expr e, boolean strict) throws ExcpTipos{
            try{
                switch (e.type){
                case 1:
                    return parsearVar(e.v, strict);
                case 2:
                    return parsearInstr(e.i);
                case 3:
                    switch(e.op){
                        case POR:
                            return t.Multiplicacion(parsearExpr(e.eb1, true), parsearExpr(e.eb2, true));
                        case CON:
                            return t.And(parsearExpr(e.eb1, true), parsearExpr(e.eb2, true));
                        case MAS:
                            return t.Suma(parsearExpr(e.eb1, true), parsearExpr(e.eb2, true));
                        case MENOS:
                            return t.Resta(parsearExpr(e.eb1, true), parsearExpr(e.eb2, true));
                        case DIS:
                            return t.Or(parsearExpr(e.eb1, true), parsearExpr(e.eb2, true));
                        case MENOR:
                        case MAYOR:
                        case MENOROIGUAL:
                        case MAYOROIGUAL:
                            return t.Comparacion(parsearExpr(e.eb1, true), parsearExpr(e.eb2, true));
                        case IGUAL:
                        case DIFF:
                            return t.Igual(parsearExpr(e.eb1, true), parsearExpr(e.eb2, true));
                        default:
                            throw new ExcpTipos("Operador desconocido", e.fila);
                    }
                case 4:
                    return Tipo.ENT;
                case 5:
                    return Tipo.BOOL;
                case 6:
                    return t.Negar(parsearExpr(e.eb1, true));
                }
            }catch (Exception ex){
                if (ex instanceof ExcpTipos) throw (ExcpTipos)ex;
                throw new ExcpTipos(ex.getMessage()+" en la expresion "+e.toString(), e.fila);
            }
            return Tipo.NULL;
	}

	private Tipo parsearInstr(Object i) throws ExcpTipos{
            try{
		if (i instanceof InstAsign) {
			t.Asignacion(parsearVar(((InstAsign) i).var, true), parsearExpr(((InstAsign) i).expr, true));
                        return Tipo.NULL;
		} else if (i instanceof InstIf) {
			t.Condicion(parsearExpr(((InstIf) i).condicion, true), "si");
			Iterator<Object> ii = ((InstIf) i).consecuencia.pila.iterator();
			while(ii.hasNext()){
				parsearInstr(ii.next());
			}
                        if (((InstIf)i).aucontraire!=null){
                            Iterator<Object> ie=((InstIf)i).aucontraire.pila.iterator();                        
                            while(ie.hasNext()){
				parsearInstr(ie.next());
                            }
                        }
                        return Tipo.NULL;
		} else if (i instanceof InstWh){
			t.Condicion(parsearExpr(((InstWh) i).cond, true), "mientras");
			Iterator<Object> ii = ((InstWh) i).bloque.pila.iterator();
			while(ii.hasNext()){
				parsearInstr(ii.next());
			}
                        return Tipo.NULL;
		} else if (i instanceof InstDo){
                    InstDo iform = ((InstDo)i);
                    if (((TipoFunc)iform.d.tipo).listaTipos.pila.size()!=iform.atributos.pila.size()) throw new ExcpTipos("Núero de atributos incorrectos en la llamada "+iform.toString(), iform.fila);
                    Iterator<Object> ii=((TipoFunc)iform.d.tipo).listaTipos.pila.iterator();
                    Iterator<Object> ie=iform.atributos.pila.iterator();
                    while(ie.hasNext()){
                        Expr val=(Expr) ie.next();
                        try{
                        t.Asignacion(parsearExpr(val, false), ((TipoG)ii.next()).t);
                        }catch (Exception exc){
                            if (exc instanceof ExcpTipos) throw (ExcpTipos)exc;
                            throw new ExcpTipos(exc.getMessage()+" en el valor "+val.toString()+" de la llamada "+iform.toString(), iform.fila);
                        }
                    }
                    return ((TipoFunc)iform.d.tipo).tipoSalida;
                } else if (i instanceof DeclF){
                    DeclF iform = (DeclF)i;
                    if (((TipoFunc)iform.tipo).tipoSalida != parsearExpr(((TipoFunc)iform.tipo).salida, true))
			throw new ExcpTipos("Intento de declarar una función asignandole un tipo de retorno que no corresponde en "+i.toString(), ((DeclF) i).fila);
                    Iterator<Object> ii = ((TipoFunc)iform.tipo).operaciones.pila.iterator();
                    while(ii.hasNext()){
                        parsearInstr(ii.next());
                    }
                    return Tipo.NULL;
                }
                return Tipo.NULL;
            }
            catch(Exception e){
                if (e instanceof ExcpTipos) throw (ExcpTipos)e;
                throw new ExcpTipos(e.getMessage(), 0);
            }
	}

	public void parsear(Symbol s) throws Exception{
		parsearListaFunciones((Lista)s.value);
}
}
