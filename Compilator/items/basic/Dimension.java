package items.basic;

public class Dimension{
    private Dimension dimdcha, dimizqda;

    public Dimension(){}
    
    public Dimension(Dimension dcha, Dimension izqda){
        dimdcha=dcha;
        dimizqda=izqda;
    }

    @Override
    public String toString(){
        return "("+dimizqda.toString()+")x("+dimdcha.toString()+")";
    }
}
