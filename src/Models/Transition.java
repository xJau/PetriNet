package Models;

public class Transition extends Node {
    public Transition(int id) {
        super(id);
    }

    @Override
    public String toString() {
        return "Transizione n."+getId();
    }
    
    public boolean equals(Object t) {
    	if(t instanceof Transition)return super.equals(t);
    	return false;
    }

}
