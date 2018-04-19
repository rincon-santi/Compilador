package items.basic;

public class ConcreteDimension extends Dimension{
    private NumConst c;
   
    public ConcreteDimension(NumConst constante){
        c=constante;
    }
    
    @Override
    public String toString(){
        return "Dimension "+c.toString();
    }
}
