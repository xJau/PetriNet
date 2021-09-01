package Models;

public class Transition extends Node {
    public Transition(int id) {
        super(id);
    }

    @Override
    public String toString() {
        return "Transizione n."+getId();
    }
    
    public boolean equals(Node n) {
    	if(n instanceof Transition)return(this.getId() == n.getId());
    	return false;
    }
    
}
