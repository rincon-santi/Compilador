package items.basic;

public class Valor{
    private int type;
    private Variable v;
    private NumConst n;
    private BoolConst b;
    
    public Valor(Variable var){
        type=1;
        v=var;
    }
    
    public Valor(NumConst nu){
        type=2;
        n=nu;
    }
    
    public Valor(BoolConst bu){
        type=3;
        b=bu;
    }
    
    @Override
    public String toString(){
        switch(type){
            case 1:
                return "Valor "+v.toString();
            case 2:
                return "Valor "+n.toString();
            case 3:
                return "Valor "+b.toString();
            default:
                return "VALOR NO VALIDO";
        }
    }
}
