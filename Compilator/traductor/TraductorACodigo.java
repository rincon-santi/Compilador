
package traductor;

import items.basic.Arreglo;
import items.basic.BoolConst;
import items.basic.NumConst;
import items.basic.Tipo;
import items.basic.TipoFunc;
import items.basic.TipoG;
import items.basic.Variable;
import items.conjuntos.Lista;
import items.declaraciones.Decl;
import items.declaraciones.DeclF;
import items.declaraciones.Declaration;
import items.expresiones.Expr;
import items.instrucciones.InstAsign;
import items.instrucciones.InstDo;
import items.instrucciones.InstIf;
import items.instrucciones.InstWh;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;
import java_cup.runtime.Symbol;

public class TraductorACodigo {
    private PrintWriter out;
	private List<String> programa;
	int desfase;
        int pila;
        int maxPila;
        Stack<Integer> antiguasPilas, antiguasMaxPila;
	
	public TraductorACodigo(PrintWriter o) {
		out = o;
		programa = new ArrayList<String>();
		desfase = 0;
                pila=0;
                maxPila=0;
                antiguasPilas=new Stack<>();
                antiguasMaxPila=new Stack<>();
	}

	private void traducirListaDeclF(Lista a){
		Iterator<Object> id = a.pila.iterator();
		while(id.hasNext()){
			traducirDecF((DeclF)id.next());
		}
	}
        
        private void traducirDecF(DeclF f){
            f.direccion=programa.size()+desfase;
            programa.add("ssp "+(f.le+5)+";");
            int placeSep=programa.size();
            desfase++;
            int placeUjp=programa.size();
            desfase++;
            Iterator<Object> ii=((TipoFunc)f.tipo).operaciones.pila.iterator();
            while (ii.hasNext()){
                Object j=ii.next();
                if (j instanceof DeclF) traducirDecF((DeclF)j);
            }
            programa.add(placeUjp, "ujp "+(programa.size()+desfase)+";");
            desfase--;
            ii=((TipoFunc)f.tipo).operaciones.pila.iterator();
            antiguasPilas.push(pila);
            antiguasMaxPila.push(maxPila);
            pila=0;
            maxPila=0;
            ii=((TipoFunc)f.tipo).operaciones.pila.iterator();
            while (ii.hasNext()){
                Object j= ii.next();
                if (!(j instanceof Declaration)) traducirInstr(j, f.nivelDefinicion+1);
            }
            if (!((TipoFunc)f.tipo).tipoSalida.equals(Tipo.NULL)){
                programa.add("lda 0 0;");
                pila++;
                traducirExpr(((TipoFunc)f.tipo).salida, f.nivelDefinicion+1);
                programa.add("sto;");
                if (pila>maxPila) maxPila=pila;
                pila--;
            }
            if (pila>maxPila) maxPila=pila;
            if(((TipoFunc)f.tipo).tipoSalida.equals(Tipo.NULL)) programa.add("retp;");
            else programa.add("retf;");
            programa.add(placeSep, "sep "+maxPila+";");
            maxPila=antiguasMaxPila.pop();
            pila=antiguasPilas.pop();
            desfase--;
        }
        
	private void traducirExpr(Expr e, int lvl){
			if (e.type==1){
                            if((e.v.coord==null)&&(!(e.v.d.tipo instanceof Arreglo))){
				programa.add("lda "+(lvl-e.v.d.nivelDef)+" "+e.v.d.pos+";");
                                pila++;
				programa.add("ind;");
                            } else if(e.v.coord==null){
                                int dim=1;
                                for(int i=0; i<((Arreglo)e.v.d.tipo).d.pila.size(); i++){
                                    dim=dim*((NumConst)((Arreglo)e.v.d.tipo).d.pila.get(i)).intValue();
                                }
                                for (int i=0; i<dim; i++){
                                    programa.add("lda "+(lvl-e.v.d.nivelDef)+" "+(e.v.d.pos+i)+";");
                                    pila++;
                                    programa.add("ind;");
                                }
                            } 
                            else {
				Variable a = e.v;
				ListIterator<Object> ie = a.coord.pila.listIterator(a.coord.pila.size());
				ListIterator<Object> ii = ((Arreglo)a.d.tipo).d.pila.listIterator(((Arreglo)a.d.tipo).d.pila.size()); 
				int mAcum = 1;
				List<Integer> lista = new ArrayList<Integer>();
				while (ii.hasPrevious()){
					int aux = ((NumConst)ii.previous()).intValue();
					lista.add(mAcum);
					mAcum *= aux;
				}
				ListIterator<Integer> iii = lista.listIterator();
				ListIterator<Object> ic = ((Arreglo)a.d.tipo).d.pila.listIterator(((Arreglo)a.d.tipo).d.pila.size());
				int cnt = 0;
				while(ie.hasPrevious()){
					traducirExpr((Expr)ie.previous(), lvl);
					programa.add("chk 0 "+(((NumConst)ic.previous()).intValue()-1)+";");
                                        pila++;
					programa.add("ldc "+iii.next()+";");
                                        pila++;
					programa.add("mul;");
                                        if (maxPila<pila) maxPila=pila;
                                        pila--;
					cnt++;
				}
				for (int p = 0; p < cnt-1; p++){
					programa.add("add;");
                                        if(maxPila<pila) maxPila=pila;
                                        pila--;
				}
				programa.add("lda "+(lvl-a.d.nivelDef)+" "+a.d.pos+";");
                                pila++;
				programa.add("add;");
                                if(maxPila<pila) maxPila=pila;
                                pila--;
				programa.add("ind;");
                            }
			} else if (e.type==5){
				if (e.b.booleano == true){
                                    programa.add("ldc true;");
                                    pila++;
                                }
                                else{
                                    programa.add("ldc false;");
                                    pila++;
                                }
			} else if (e.type==4){
				programa.add("ldc "+ e.n.intValue() +";");
                                pila++;
			} else if (e.type==3){
				switch (e.op){
				case CON:
					traducirExpr(e.eb1, lvl);
					traducirExpr(e.eb2, lvl);
					programa.add("and;");
                                        if(maxPila<pila) maxPila=pila;
                                        pila--;
					break;
				case DIS:
					traducirExpr(e.eb1, lvl);
					traducirExpr(e.eb2, lvl);
					programa.add("or;");
                                        if(maxPila<pila) maxPila=pila;
                                        pila--;
					break;
				case IGUAL:
					traducirExpr(e.eb1, lvl);
					traducirExpr(e.eb2, lvl);
					programa.add("equ;");
                                        if(maxPila<pila) maxPila=pila;
                                        pila--;
					break;
				case MAS:
					traducirExpr(e.eb1, lvl);
					traducirExpr(e.eb2, lvl);
					programa.add("add;");
                                        if(maxPila<pila) maxPila=pila;
                                        pila--;
					break;
				case MAYOR:
					traducirExpr(e.eb1, lvl);
					traducirExpr(e.eb2, lvl);
					programa.add("grt;");
                                        if(maxPila<pila) maxPila=pila;
                                        pila--;
					break;
				case MENOR:
					traducirExpr(e.eb1, lvl);
					traducirExpr(e.eb2, lvl);
					programa.add("les;");
                                        if(maxPila<pila) maxPila=pila;
                                        pila--;
					break;
				case MENOS:
					traducirExpr(e.eb1, lvl);
					traducirExpr(e.eb2, lvl);
					programa.add("sub;");
                                        if(maxPila<pila) maxPila=pila;
                                        pila--;
					break;
                                case MENOROIGUAL:
                                        traducirExpr(e.eb1, lvl);
                                        traducirExpr(e.eb2, lvl);
                                        programa.add("lte;");
                                        if(maxPila<pila) maxPila=pila;
                                        pila--;
                                        break;
                                case MAYOROIGUAL:
                                        traducirExpr(e.eb1,lvl);
                                        traducirExpr(e.eb2,lvl);
                                        programa.add("gte;");
                                        if(maxPila<pila) maxPila=pila;
                                        pila--;
                                        break;
                                case DIFF:
                                        traducirExpr(e.eb1, lvl);
					traducirExpr(e.eb2, lvl);
					programa.add("equ;");
                                        if(maxPila<pila) maxPila=pila;
                                        pila--;
                                        programa.add("not;");
					break;
                                case POR:
					traducirExpr(e.eb1, lvl);
					traducirExpr(e.eb2, lvl);
					programa.add("mul;");
                                        if(maxPila<pila) maxPila=pila;
                                        pila--;
                                        break;
				}
			} else if (e.type==6){
                            traducirExpr(e.eb1, lvl);
                            programa.add("not;");
                        } else if(e.type==2){
                            traducirInstr(e.i, lvl);
                        }

		}
	
	private void traducirInstr(Object i, int lvl){
            if (i instanceof InstAsign) { // Instruccion de asignacion
		if (((InstAsign)i).var.coord ==null){ // En la que se asigna un identificador
                    programa.add("lda "+(lvl-((InstAsign)i).var.d.nivelDef)+" "+((InstAsign)i).var.d.pos+";"); // Obtenemos la dirección
                    pila++;
                    traducirExpr(((InstAsign)i).expr, lvl);
                    programa.add("sto;"); //Asignamos la expresión a la dirección
                    if(maxPila<pila) maxPila=pila;
                    pila=pila-2;
		} else { // No es un identificador, es un acceso a Array
                    Variable a = ((InstAsign)i).var;
                    ListIterator<Object> ie = a.coord.pila.listIterator(a.coord.pila.size());
                    ListIterator<Object> ii = ((Arreglo)a.d.tipo).d.pila.listIterator(((Arreglo)a.d.tipo).d.pila.size()); 
                    int mAcum = 1;
                    List<Integer> lista = new ArrayList<Integer>();
                    while (ii.hasPrevious()){
			int aux = ((NumConst)ii.previous()).intValue();
                        lista.add(mAcum);
			mAcum *= aux;
                    }
                    ListIterator<Integer> ii2 = lista.listIterator();
                    ListIterator<Object> ic = ((Arreglo)a.d.tipo).d.pila.listIterator(((Arreglo)a.d.tipo).d.pila.size());
                    int cnt = 0;
                    while(ie.hasPrevious()){
                        traducirExpr((Expr)ie.previous(), lvl);
                        programa.add("chk 0 "+(((NumConst)ic.previous()).intValue()-1)+";");
                        pila++;
                        programa.add("ldc "+ii2.next()+";");
                        pila++;
			programa.add("mul;");
                        if(maxPila<pila) maxPila=pila;
                        pila--;
			cnt++;
                    }
                    for (int p = 0; p < cnt-1; p++){
			programa.add("add;");
                        if(maxPila<pila) maxPila=pila;
                        pila--;
                    }
                    programa.add("lda "+(lvl-a.d.nivelDef)+" "+a.d.pos+";");
                    pila++;
                    programa.add("add;");
                    if(maxPila<pila) maxPila=pila;
                    pila--;
                    traducirExpr(((InstAsign)i).expr, lvl);
                    programa.add("sto;");
                    if(maxPila<pila) maxPila=pila;
                    pila=pila-2;
		}
            }else if (i instanceof InstIf) {
                if (((InstIf)i).aucontraire==null){
			traducirExpr(((InstIf)i).condicion, lvl);
			int ind = programa.size(); // Donde meteremos la instruccion de salto al final del if
			Iterator<Object> iterator = ((InstIf) i).consecuencia.pila.iterator();
			desfase++;
                        iterator= ((InstIf) i).consecuencia.pila.iterator();
                        while(iterator.hasNext()) { //Traducimos las instrucciones de la cláusula IF que no definen variables ni func
                            Object j= iterator.next();
                            if (!(j instanceof Declaration)) traducirInstr(j, lvl);
			}
			desfase--;
			programa.add(ind, "fjp "+(programa.size()+1+desfase)+";"); // Añadimos el salto donde tocaba a el final +1 (la que estamos añadiendo) + nivel (por si en bucles externos hay más que añadir)
                        if(maxPila<pila) maxPila=pila;
                        pila--;
                } else {
			traducirExpr(((InstIf)i).condicion, lvl);
			int ind1 = programa.size(); // Donde va la instruccion de salto al else
			Iterator<Object> iteratorIf = (((InstIf) i).consecuencia).pila.iterator();
			desfase+=2;
                        iteratorIf= ((InstIf) i).consecuencia.pila.iterator();
                        while(iteratorIf.hasNext()) { //Traducimos las instrucciones de la cláusula IF que no definen variables ni func
                            Object j= iteratorIf.next();
                            if (!(j instanceof Declaration)) traducirInstr(j, lvl);
			}
			desfase-=2;
			int ind2 = programa.size(); // donde va la instruccion de salto al final del else
			programa.add(ind1, "fjp "+(programa.size()+2+desfase)+";"); // Añadirmos la instrucción para saltar al else
                        if(maxPila<pila) maxPila=pila;
                        pila--;
			Iterator<Object> iteratorElse = ((InstIf) i).aucontraire.pila.iterator();
			desfase+=1;
                        iteratorElse= ((InstIf) i).aucontraire.pila.iterator();
                        while(iteratorElse.hasNext()) { //Traducimos las instrucciones de la cláusula Else que no definen variables ni func
                            Object j= iteratorElse.next();
                            if (!(j instanceof Declaration)) traducirInstr(j, lvl);
			}
			desfase-=1;
			programa.add(ind2+1, "ujp "+(programa.size()+1+desfase)+";");
			
		}
            }else if (i instanceof InstWh){
		int ind1 = programa.size(); // Donde volver para volver a hacer el bucle
		traducirExpr(((InstWh)i).cond, lvl); 
		int ind2 = programa.size(); // Donde poner la instruccion de salto para salir
		ListIterator<Object> iteratorW = ((InstWh) i).bloque.pila.listIterator();
		desfase++;
                iteratorW=((InstWh) i).bloque.pila.listIterator();
                while (iteratorW.hasNext()){
                    Object j=iteratorW.next();
                    if (!(j instanceof Declaration)) traducirInstr(j, lvl);
		}
		desfase--;
		programa.add(ind2,"fjp "+ (programa.size()+2+desfase) +";"); // Donde se hacía el salto, se salta al final
                if(maxPila<pila) maxPila=pila;
                pila--;
		programa.add("ujp "+ (ind1+desfase) +";");
            } 
            else if (i instanceof InstDo){
                InstDo llamada=(InstDo) i;
                programa.add("mst "+(lvl-llamada.d.nivelDefinicion)+";");
                int pilafinal=pila;
                pila+=5;
                for (Object j : llamada.atributos.pila){
                    traducirExpr((Expr)j, lvl);
                }
                int dimAtr=0;
                ListIterator<Object> iteratorAtr = ((TipoFunc)llamada.d.tipo).listaTipos.pila.listIterator();
                while(iteratorAtr.hasNext()){
                    TipoG t= (TipoG) iteratorAtr.next();
                    if (t instanceof Arreglo){
                        int dimtoAdd=1;
                        for(int i2=0; i2<((Arreglo)t).d.pila.size(); i2++){
                                    dimtoAdd=dimtoAdd*((NumConst)((Arreglo)t).d.pila.get(i2)).intValue();
                        }
                        dimAtr=dimAtr+dimtoAdd;
                    }
                    else dimAtr+=1;
                }
                programa.add("cup "+dimAtr+" "+llamada.d.direccion+";");
                if(maxPila<pila) maxPila=pila;
                if(((TipoFunc)llamada.d.tipo).tipoSalida==Tipo.NULL) pila-=5;
                else pila-=4;
            }
	}
	
	public void traducir(Symbol s){
                programa.add("ssp 1;");
                programa.add("sep 0;");
                int init=programa.size();
                desfase++;
		traducirListaDeclF((Lista) s.value);
                programa.add(init, "ujp "+(programa.size()+1)+";");
                desfase--;
                if(((TipoFunc)((DeclF)((Lista)s.value).pila.getLast()).tipo).tipoSalida!=Tipo.NULL){
                    programa.add("ldc 0;");
                }
                programa.add("mst 0;");
                programa.add("cup 0 "+((DeclF)((Lista)s.value).pila.getLast()).direccion+";");
                if(((TipoFunc)((DeclF)((Lista)s.value).pila.getLast()).tipo).tipoSalida!=Tipo.NULL){
                    programa.add("sto;");
                }
                programa.add("stp;");
		int i=0;
		Iterator<String> it = programa.iterator();
		String t=" ";
		while(it.hasNext())
		{
			out.println(" {"+t+i+"}  "+it.next());
			i++;
			if (i>=10) t="";
		}
		out.flush();
}
}
