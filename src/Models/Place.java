package Models;

public class Place extends Node {
    public Place(int id) {
        super(id);
    }

    @Override
    public String toString() {
        return "Posto n." + getId();
    }

}
