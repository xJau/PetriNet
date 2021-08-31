package Models;

public abstract class Node implements Identificabile {

    private int id;

    public Node(int id) {
        this.id = id;

    }

    @Override
    public int getId() {
        return id;
    }
}
