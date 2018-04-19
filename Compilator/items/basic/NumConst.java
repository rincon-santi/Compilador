package items.basic;

public class NumConst {
    private String numero;
    
    public NumConst(String n){
        numero=n;   
    }

    @Override
    public String toString(){
        return "Constante numérica: "+numero;    
    }
}
