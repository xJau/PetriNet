package Models;

public class Place extends Node {
    public Place(int id) {
        super(id);
    }

    @Override
    public String toString() {
        return "Posto n." + getId();
    }
    
    public boolean equals(Node n) {
    	if(n instanceof Place)return(this.getId() == n.getId());
    	return false;
    }

}
