package Models;

public abstract class Node implements Identificable{

    private int id;

    public Node(int id) {
        this.id = id;

    }
    public int getId() {
        return id;
    }
    
	@Override
	public boolean equals(Object node) {
        if (this == node) return true;
        if (node == null || getClass() != node.getClass()) return false;
		return this.id == ((Node)node).getId();
	}
	
}
