package items.basic;

public class Arreglo extends TipoG{
    private final Dimension d;

    public Arreglo(Dimension dim, Tipo tip){
        super.t=tip;
        d=dim;
    }

    @Override
    public String toString(){
        return "Array "+super.t+" de "+d.toString();
    }
}
