package errores;

@SuppressWarnings("serial")
public class ExcpTipos extends Exception {

	public ExcpTipos(String string, int f) {
		super("Se ha detectado un ERROR" + '\n' + "Tipo: TIPADO"
				+ '\n' + "Fila: " + f + '\n' + "Descripcion: " + string);
	}

}
