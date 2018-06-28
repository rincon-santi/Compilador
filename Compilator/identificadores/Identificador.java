package identificadores;

import items.declaraciones.*;

public class Identificador {
	Decl dec;
        DeclF decf;
	Identificador sigDec;
	
	public Identificador(int actual, Declaration s, Identificador sigDec) {
            if (s instanceof Decl){
		this.dec = (Decl)s;
		this.sigDec = sigDec;
                dec.nivelDef=actual;
                this.decf=null;
            }
            else{
                this.decf = (DeclF)s;
                decf.nivelDefinicion=actual;
		this.sigDec = sigDec;
                this.dec=null;
            }
	}
}
