package Models;

public class Place extends Node {
    public Place(int id) {
        super(id);
    }

    @Override
    public String toString() {
        return "Posto n." + getId();
    }
    
    public boolean equals(Object p) {
    	if(p instanceof Place)return super.equals(p);
    	return false;
    }


}
