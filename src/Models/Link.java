package Models;

public class Link {

    private Node inGoingNode;
    private Node outGoingNode;

    public Link(Node in,Node out){
        this.inGoingNode = in;
        this.outGoingNode = out;
    }

    public Node getInGoingNode() {
        return inGoingNode;
    }

    public Node getOutGoingNode() {
        return outGoingNode;
    }
    
    public boolean equals(Link l) {
    	if(this.inGoingNode.equals(l.getInGoingNode()))return this.outGoingNode.equals(l.getOutGoingNode());
		return false;
    }
    
}
