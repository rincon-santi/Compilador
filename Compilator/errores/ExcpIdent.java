package errores;

import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;

@SuppressWarnings("serial")
public class ExcpIdent extends Exception {

	public ExcpIdent(String string, int f) {
		super("Se ha detectado un ERROR" + '\n' + "Tipo: SEMANTICO"
				+ '\n' + "Fila: "+ f + '\n' + '\n' + "Descripcion: " + string);
	}
	
}
