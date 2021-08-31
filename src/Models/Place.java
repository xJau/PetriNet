package Models;

public class Place extends Node implements Identificabile {
    public Place(int id) {
        super(id);
    }

    @Override
    public String toString() {
        return "Place"+getId();
    }

}
