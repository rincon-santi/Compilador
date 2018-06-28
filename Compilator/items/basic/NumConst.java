package items.basic;

public class NumConst {
    private String numero;
    public final int fila;
    
    public NumConst(String n, int f){
        numero=n;   
        fila=f;
    }
    
    public int intValue(){
        return Integer.parseInt(numero);
    }

    @Override
    public String toString(){
        return "Constante numérica: "+numero;    
    }
}
