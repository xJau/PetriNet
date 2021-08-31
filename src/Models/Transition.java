package Models;

public class Transition extends Node implements Identificabile {
    public Transition(int id) {
        super(id);
    }

    @Override
    public String toString() {
        return "Transition"+getId();
    }

}
