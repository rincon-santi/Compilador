package items.basic;

public class TipoG{
    public Tipo t;
    public int fila;

    public TipoG(Tipo tipo, int f){
        t=tipo;
        fila=f;
    }
    
    public TipoG(){}
    
    @Override
    public String toString(){
        return "Tipo "+t;
    }
}
