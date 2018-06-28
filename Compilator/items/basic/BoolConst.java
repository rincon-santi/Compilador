package items.basic;

public class BoolConst{
    public boolean booleano;
    public int fila;
    
    public BoolConst(boolean b, int f){
        booleano=b;
        fila=f;
    }

    @Override
    public String toString(){
        return "Constante booleana: "+booleano;
    }       
}
