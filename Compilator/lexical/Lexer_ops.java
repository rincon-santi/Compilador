package lexical;

import java_cup.runtime.Symbol;
import syntactical.SintacticSym;

public class Lexer_ops {
	private Lexanalizer sec;
	public Lexer_ops(Lexanalizer sec) {
		this.sec = sec;   
	}
	public Symbol unidadNumeroEntero() {
		return sec.symbol(sec.lexema(),SintacticSym.NUMENT, sec.lexema()); 
	}
	public Symbol unidadIgual() {
		return sec.symbol("===",SintacticSym.IGUAL, "===");     
	}    
	public Symbol unidadMayor() {
		return sec.symbol(">",SintacticSym.MAYOR, ">");     
	}    
	public Symbol unidadMenor() {
		return sec.symbol("<",SintacticSym.MENOR, "<");     
	}
	public Symbol unidadConjuncion() {
		return sec.symbol("y",SintacticSym.CON, "y");     
	}     
	public Symbol unidadDisyuncion() {
		return sec.symbol("o",SintacticSym.DIS, "o");     
	}    
	public Symbol unidadNegacion() {
		return sec.symbol("no",SintacticSym.NEG, "no");     
	}
    public Symbol unidadMayorOIgual() {
        return sec.symbol(">=",SintacticSym.MAYOROIGUAL,">=");
    }
    public Symbol unidadMenorOIgual() {
        return sec.symbol("=<",SintacticSym.MENOROIGUAL,"=<");
    }
    public Symbol unidadDistinto() {
        return sec.symbol("=/=",SintacticSym.DIFF);
    }  
	public Symbol unidadOperadorMas() {
		return sec.symbol("mas",SintacticSym.MAS, "mas"); 
	} 
	public Symbol unidadOperadorMenos() {
		return sec.symbol("menos",SintacticSym.MENOS, "menos"); 
	} 
	public Symbol unidadOperadorPor() {
		return sec.symbol("por",SintacticSym.POR, "por"); 
	} 
	public Symbol unidadOperadorAsignacion() {
		return sec.symbol("<-",SintacticSym.ASIGN, "<-"); 
	} 
	public Symbol unidadTipoBool() {
		return sec.symbol("booleano",SintacticSym.BOOL, "booleano");     
	}   
	public Symbol unidadTipoEntero() {
		return sec.symbol("entero",SintacticSym.ENT, "entero");     
	}
    public Symbol unidadTipoNull() {
        return sec.symbol("vacio",SintacticSym.NULL,"vacio");
    }
    public Symbol unidadTipoArray() {
        return sec.symbol("array",SintacticSym.ARRAY,"array");
    }
    public Symbol unidadWildcard() {
        return sec.symbol("_",SintacticSym.WILDCARD,"_");
    }
	public Symbol unidadTrue() {
		return sec.symbol("true",SintacticSym.TRUE, "true");     
	}   
	public Symbol unidadFalse() {
		return sec.symbol("false",SintacticSym.FALSE, "false");     
	}  
	public Symbol unidadIdV() {
		return sec.symbol(sec.lexema(),SintacticSym.IDV, sec.lexema());     
	} 
	public Symbol unidadParentesisApertura() {
		return sec.symbol("(",SintacticSym.PAP, "("); 
	} 
	public Symbol unidadParentesisCierre() {
		return sec.symbol(")",SintacticSym.PCI, ")"); 
	} 
    public Symbol unidadRef(){
        return sec.symbol("/r",SintacticSym.REF, "/r");
    }
	public Symbol unidadIf() {
		return sec.symbol("Suponiendo",SintacticSym.IF, "Suponiendo"); 
	} 
	public Symbol unidadElse() {
		return sec.symbol("contrariamente",SintacticSym.ELSE, "contrariamente");     
	} 
	public Symbol unidadWhile() {
		return sec.symbol("Mientras",SintacticSym.WHILE, "Mientras");     
	} 
	public Symbol unidadAccesoArray() {
		return sec.symbol("[",SintacticSym.ACCESO, "[");     
	}     
    public Symbol unidadCierreAcceso() {
        return sec.symbol("]",SintacticSym.CIERRE, "]");
    }
	public Symbol unidadSeparadorDimensiones() {
		return sec.symbol("x",SintacticSym.DIMSEP, "x");
	}
    public Symbol unidadSep() {
        return sec.symbol(",",SintacticSym.SEP, ",");
    }
    public Symbol unidadDeclaracionVar(){
        return sec.symbol("Sea",SintacticSym.DECLVAR,"Sea");
    }
    public Symbol unidadTipadoVar(){
        return sec.symbol("un",SintacticSym.TIPVAR,"un");
    }
    public Symbol unidadPrincipioFuncion(){
        return sec.symbol("Definamos",SintacticSym.PRINCFUN,"Definamos");
    }
    public Symbol unidadEntradaTipadoFuncion(){
        return sec.symbol(":",SintacticSym.ENTRTIPFUNC,":");
    }
    public Symbol unidadSalidaTipadoFuncion(){
        return sec.symbol("->",SintacticSym.SALTIPFUNC,"->");
    }
    public Symbol unidadEntradaImplFuncion(){
        return sec.symbol("enviando",SintacticSym.ENTRIMPLFUNC,"enviando");
    }
    public Symbol unidadSalidaImplFuncion(){
        return sec.symbol("|->",SintacticSym.SALIMPLFUNC,"|->");
    }
    public Symbol unidadResultadoFuncion(){
        return sec.symbol("Devuelve",SintacticSym.RESULTADO,"Devuelve");
    }
    public Symbol unidadLlamadaFuncion(){
        return sec.symbol("evaluando",SintacticSym.LLAMADA,"evaluando");
    }
    public Symbol unidadParametrosLlamada(){
        return sec.symbol("en",SintacticSym.PARAM,"en");
    }
    public Symbol unidadCorcheteApertura(){
        return sec.symbol("{",SintacticSym.CORCHAPERTURA,"{");
    }
    public Symbol unidadCorcheteCierre(){
        return sec.symbol("}",SintacticSym.CORCHCIERRE,"}");
    }
    public Symbol unidadFinSentencia(){
        return sec.symbol(".",SintacticSym.FIN,".");
    }
	public Symbol unidadEOF() {
		return sec.symbol("<EOF>",SintacticSym.EOF, "<EOF>");
	}
}
