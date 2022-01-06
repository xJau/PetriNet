package Models;

public class Link {
    private Node inGoingNode;
    private Node outGoingNode;

    public Link(Node in,Node out){
        this.inGoingNode = in;
        this.outGoingNode = out;
    }

    public Node getInGoingNode() { return inGoingNode; }
    public Node getOutGoingNode() { return outGoingNode; }
    
    public String toString() {
    	return inGoingNode.toString() + " --> " + outGoingNode.toString();
    }

	@Override
	public boolean equals(Object l) {
        if (this == l) return true;
        if (l == null || getClass() != l.getClass()) return false;
		return this.inGoingNode.equals(((Link)l).getInGoingNode()) && this.outGoingNode.equals(((Link)l).getOutGoingNode());
	}
}
