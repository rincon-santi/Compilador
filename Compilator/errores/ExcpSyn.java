package errores;

import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import java_cup.runtime.Symbol;
import syntactical.SintacticSym;

public class ExcpSyn {

	public static void excpSyn(Symbol ul){
		System.err.println("Se ha detectado un ERROR");
		System.err.println("Tipo: SINTACTICO");
		System.err.println("Fila: " + ul.left);
		System.err.println("Elemento inesperado: " + ul.value);
		System.exit(1);
	}
}
