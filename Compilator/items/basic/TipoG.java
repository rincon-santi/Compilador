package items.basic;

public class TipoG{
    protected Tipo t;

    public TipoG(Tipo tipo){
        t=tipo;
    }
    
    public TipoG(){}
    
    @Override
    public String toString(){
        return "Tipo "+t;
    }
}
