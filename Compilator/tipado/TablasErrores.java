package tipado;

import items.basic.Tipo;

public class TablasErrores {
	
	public Tipo Acceso(Tipo t) throws Exception{
		if (t == Tipo.ENT) return Tipo.ENT;
		throw new Exception("Acceso con un índice que no es de tipo entero");
	}
	
	public Tipo Suma(Tipo t1, Tipo t2) throws Exception{
		if (t1 == Tipo.ENT && t1 == t2){
			return Tipo.ENT;
		}
		throw new Exception("Suma entre expresiones que no son ambas enteros");
	}
	
	public Tipo Condicion(Tipo t, String string) throws Exception{
		if (t == Tipo.BOOL) return Tipo.BOOL;
		throw new Exception("Instrucción \"" + string + "\" evaluando una expresión no condicional");
	}
	
	public Tipo Resta(Tipo t1, Tipo t2) throws Exception{
		if (t1 == Tipo.ENT && t1 == t2){
			return Tipo.ENT;
		}
		throw new Exception("Resta entre expresiones que no son ambas enteros");
	}
	
	public Tipo Multiplicacion(Tipo t1, Tipo t2) throws Exception{
		if (t1 == Tipo.ENT && t1 == t2){
			return Tipo.ENT;
		}
		throw new Exception("Multiplicación entre expresiones que no son ambas enteros");
	}
	
	
	public Tipo And(Tipo t1, Tipo t2) throws Exception{
		if (t1 == Tipo.BOOL && t1 == t2){
			return Tipo.BOOL;
		}
		throw new Exception("Conjunción entre expresiones que no son ambas booleanas");
	}
	
	public Tipo Or(Tipo t1, Tipo t2) throws Exception{
		if (t1 == Tipo.BOOL && t1 == t2){
			return Tipo.BOOL;
		}
		throw new Exception("Disyunción entre expresiones que no son ambas lógicos");
	}
	
	public Tipo Igual(Tipo t1, Tipo t2) throws Exception{
		if (t1 == t2){
			return Tipo.BOOL;
		}
		throw new Exception("Igualdad entre expresiones que no son del mismo tipo");
	}
	
	public Tipo Comparacion(Tipo t1, Tipo t2) throws Exception{
		if (t1 == Tipo.ENT && t1 == t2){
			return Tipo.BOOL;
		}
		throw new Exception("Comparación entre expresiones que no son ambas enteros");
	}
	
	public Tipo Negar(Tipo t) throws Exception{
		if (t == Tipo.BOOL) return Tipo.BOOL;
		throw new Exception("Negación de una expresión no booleana");
	}
	
	public void Asignacion(Tipo t1, Tipo t2) throws Exception{
		if (t1 != t2) throw new Exception("Asignación de una expresión a un identificador de distinto tipo. Tipo esperado "+t1+". Tipo recibido "+t2);
	}
        
}