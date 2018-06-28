package identificadores;

import errores.ExcpIdent;
import java.util.*;

import items.declaraciones.*;
import items.expresiones.*;
import items.instrucciones.*;
import items.basic.*;
import items.conjuntos.Lista;
import java.util.Stack;
import java_cup.runtime.Symbol;

public class AnalizadorIdentificadores {
	Map<String, Identificador> mapa;
	private int nivel;
	private int dirEnNivel;
        private Stack<Integer> pilaDirsEnNivel;
	
	public AnalizadorIdentificadores(){
		mapa = new HashMap<String, Identificador>();
                pilaDirsEnNivel=new Stack<>();
		nivel = 0;
		dirEnNivel = 5;
	}
	
	private void entrarBloque(){
		this.nivel++;
                pilaDirsEnNivel.push(dirEnNivel);
                dirEnNivel=5;
	}
	
	private void salirBloque(){
                LinkedList<String> toDelete=new LinkedList();
                HashMap<String, Identificador> toEnter=new HashMap();
		for (String entry : mapa.keySet()) {
			if ((((mapa.get(entry).dec!=null)&&(mapa.get(entry).dec.nivelDef == this.nivel)) || ((mapa.get(entry).decf!=null)&&(mapa.get(entry).decf.nivelDefinicion==this.nivel))) && mapa.get(entry).sigDec == null) toDelete.add(entry);
			else if (((mapa.get(entry).dec!=null)&&(mapa.get(entry).dec.nivelDef == this.nivel)) || ((mapa.get(entry).decf!=null)&&(mapa.get(entry).decf.nivelDefinicion==this.nivel))) {
                            toDelete.add(entry);
                            toEnter.put(entry, mapa.get(entry).sigDec);
                        } // Restauramos el que estaba a un niel menor (o se pone a null)
		}
                for (String st : toDelete) mapa.remove(st);
                for (HashMap.Entry<String,Identificador> entry : toEnter.entrySet()){
                    mapa.put(entry.getKey(), entry.getValue());
                }
                dirEnNivel=pilaDirsEnNivel.pop();
		this.nivel--;
	}
	
	private int anadirId(String id, Declaration s) throws Exception{ // Añadir un nuevo identificador
		Identificador t;
		if (mapa.get(id) != null) {
			if (((mapa.get(id).dec!=null) && (mapa.get(id).dec.nivelDef == nivel))||((mapa.get(id).decf!=null)&&(mapa.get(id).decf.nivelDefinicion==nivel))) throw new Exception(); // Ya existe un identificador con el mismo id en el mismo nivel
			t = new Identificador(nivel, s, mapa.get(id)); // Remplazamos el anterior valor pero guardando un puntero para restaurarlo al bajar de nivel
		} else {
			t = new Identificador(nivel, s, null);
		}
                if (t.decf!=null){
                        t.decf.nivelDefinicion=nivel;
                        mapa.put(id, t);
                        return 0;
                }
                else if (t.dec.tipo instanceof Arreglo)
		{
			t.dec.pos = dirEnNivel;
                        t.dec.nivelDef=nivel;
			Iterator<Object> it = ((Arreglo)t.dec.tipo).d.pila.iterator();
			int k = 1;
			while(it.hasNext()) k = k*((NumConst)it.next()).intValue();
			dirEnNivel = dirEnNivel + k;
                        mapa.put(id, t);
                        return k;
		}
		else 
		{
			t.dec.pos = dirEnNivel;
			dirEnNivel++;
                        mapa.put(id, t);
                        return 1;
		}
	}
	
	private Declaration buscarId(String id){
            if (mapa.get(id).dec != null) return mapa.get(id).dec; 
            else return mapa.get(id).decf;
	}
	
	private void parsearListaDeclFunc(Lista a) throws Exception {
		Iterator<Object> it = a.pila.iterator();
		while(it.hasNext()){
			DeclF d = (DeclF)it.next();
			try {
				anadirId(d.nombre, d);
			} catch (Exception e) {
				throw new ExcpIdent("Identificador " + d.nombre + " ya declarado. "+ '\n' + "Tipo de la declaración inicial: " + mapa.get(d.nombre).dec.tipo.toString() + '\n' + "Tipo de la nueva declaración: " + d.tipo.toString(), d.fila);
                        }
                        entrarBloque();
                        Iterator<Object> ivar=((TipoFunc)d.tipo).listaVariables.pila.iterator();
                        Iterator<Object> itip=((TipoFunc)d.tipo).listaTipos.pila.iterator();
                        int leaux=0;
                        while(ivar.hasNext()){
                            String nom = (String)ivar.next();
                            if(!itip.hasNext()) throw new ExcpIdent("Identificador " + nom + " no tiene tipo, no se puede gestionar la declaración", d.fila);
                            TipoG tipo = (TipoG)itip.next();
                            try {
				leaux+=anadirId(nom, new Decl(nom, tipo, 0, d.fila));
                            } catch (Exception e) {
				throw new ExcpIdent("Identificador " + nom + " ya declarado. "+ '\n' + "Tipo de la declaración inicial: " + mapa.get(nom).dec.tipo.toString() + '\n' + "Tipo de la nueva declaración: " + d.tipo.toString(), d.fila);
                            }                            
                        }
                        if (itip.hasNext()) throw new ExcpIdent("Tipo "+ itip.next().toString() + " no tiene identificador asociado, no se puede gestionar la declaración", d.fila);
                        Iterator<Object> ii=((TipoFunc)d.tipo).operaciones.pila.iterator();
                        Lista auxFuncs=new Lista();
                        while(ii.hasNext()){
                            Object j=ii.next();
                            if(j instanceof DeclF) auxFuncs.add(j);
                        }
                        parsearListaDeclFunc(auxFuncs);
                        ii=((TipoFunc)d.tipo).operaciones.pila.iterator();
                        while(ii.hasNext()){
                            Object j=ii.next();
                            if(j instanceof Decl) leaux+=anadirId(((Decl) j).nombre,(Decl)j);;
                        }
                        ii=((TipoFunc)d.tipo).operaciones.pila.iterator();
                        while(ii.hasNext()){
                            Object j=ii.next();
                            if(!(j instanceof Declaration)) parsearInstr(j);
                        }
                        if(!((TipoFunc)d.tipo).tipoSalida.equals(Tipo.NULL)) parsearExpr(((TipoFunc)d.tipo).salida);;
                        d.le=leaux;
                        salirBloque();
		}
	}
        
	private void parsearVar(Variable v) throws Exception{
            try {
                Declaration daux;
		daux = buscarId(v.nombre);
                if (!(daux instanceof Decl)) throw new ExcpIdent("Intentando usar como variable el identificador "+v.nombre+" que representa una función.", v.fila);
                else v.d=(Decl) daux;
            } catch (NullPointerException e1){
                throw new ExcpIdent("Identificador " + v.nombre + " no declarado.", v.fila);
            }
            if (v.coord!=null){
		Iterator<Object> ie = v.coord.pila.iterator();
                while (ie.hasNext()){
                    Expr i = (Expr) ie.next();
                    parsearExpr(i);
                }
            }
        }
        
	private void parsearExpr(Expr e) throws Exception{
            switch(e.type){
                case 1:
                    parsearVar(e.v);
                    break;
                case 2:
                    parsearInstr(e.i);
                    break;
                case 3:
                    parsearExpr(e.eb1);
                    parsearExpr(e.eb2);
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    parsearExpr(e.eb1);
                    break;
            }
	}
	
	private void parsearInstr(Object i) throws Exception{
            if (i instanceof InstAsign) {
		parsearVar(((InstAsign) i).var);
		parsearExpr(((InstAsign) i).expr);
            } 
            else if (i instanceof InstIf) {
		parsearExpr(((InstIf) i).condicion);
		Iterator<Object> ii = ((InstIf) i).consecuencia.pila.iterator();
                while(ii.hasNext()){
                    Object j = ii.next();
                    if (!(j instanceof Declaration)) parsearInstr(j);
                    else if(j instanceof Decl) throw new ExcpIdent("Intentando definir identificador "+((Decl)j).nombre+" dentro de estructura If", ((Decl)j).fila);
                    else if(j instanceof DeclF) throw new ExcpIdent("Intentando definir identificador "+((DeclF)j).nombre+" dentro de estructura If", ((DeclF)j).fila);
                }
                if (((InstIf) i).aucontraire!=null) {
                    Iterator<Object> ie = ((InstIf) i).aucontraire.pila.iterator();
                    while(ie.hasNext()){
                        Object j = ie.next();
                        if (!(j instanceof Declaration)) parsearInstr(j);
                    else if(j instanceof Decl) throw new ExcpIdent(("Intentando definir identificador "+((Decl)j).nombre+" dentro de estructura If"), ((Decl)j).fila);
                    else if(j instanceof DeclF) throw new ExcpIdent(("Intentando definir identificador "+((DeclF)j).nombre+" dentro de estructura If"), ((DeclF)j).fila);
                    }
                }
            }
            else if (i instanceof InstWh){
		parsearExpr(((InstWh) i).cond);
		Iterator<Object> ii = ((InstWh) i).bloque.pila.iterator();
                while(ii.hasNext()){
                    Object j = ii.next();
                    if (!(j instanceof Declaration)) parsearInstr(j);
                    else if(j instanceof Decl) throw new ExcpIdent(("Intentando definir identificador "+((Decl)j).nombre+" dentro de estructura If"), ((Decl)j).fila);
                    else if(j instanceof DeclF) throw new ExcpIdent(("Intentando definir identificador "+((DeclF)j).nombre+" dentro de estructura If"), ((DeclF)j).fila);
                }	
            }
            else if (i instanceof InstDo){
                try{
                    if (!((buscarId(((InstDo)i).nombre)) instanceof DeclF)) throw new ExcpIdent ("Intentando llamar a función "+((InstDo)i).nombre+ " inexistente.", ((InstDo) i).fila);
                    ((InstDo)i).d=(DeclF)buscarId(((InstDo)i).nombre);
                }catch (NullPointerException e1){
                    throw new ExcpIdent("Identificador " + ((InstDo)i).nombre + " no declarado.", ((InstDo) i).fila);
                }
                Iterator<Object> ii = ((InstDo)i).atributos.pila.iterator();
                while(ii.hasNext()){
                    Object j = ii.next();
                    parsearExpr(((Expr)j));
                }
            }
	}
        
	public void parsear(Symbol s) throws Exception{
		parsearListaDeclFunc((Lista) s.value);
                if (((TipoFunc)(((DeclF)((Lista)s.value).pila.getLast()).tipo)).listaTipos.pila.size()>0) throw new ExcpIdent("La última función o procedimiento no debe tener parámetros", 0);
	}
}
