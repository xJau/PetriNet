package Models;

public class Transition extends Node{
    public Transition(int id) {
        super(id);
    }

    @Override
    public String toString() {
        return "Transition"+getId();
    }
}
