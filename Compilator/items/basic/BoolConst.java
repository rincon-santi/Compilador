package items.basic;

public class BoolConst{
    private boolean booleano;
    
    public BoolConst(boolean b){
        booleano=b;
    }

    @Override
    public String toString(){
        return "Constante booleana: "+booleano;
    }       
}
