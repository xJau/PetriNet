package Models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import static Utils.MatrixOperation.*;

public class Network {

    private int id;
    private List<Place> places;
    private List<Transition> transitions;
    private List<Link> links;
    private int[][] matrixIn;
    private int[][] matrixOut;

    public Network(int id) {
        this.id = id;
        this.places = new ArrayList<>();
        this.transitions = new ArrayList<>();
        this.links = new ArrayList<>();
        Place place = new Place(places.size());
        this.places.add(place);
        Transition transition = new Transition(transitions.size());
        int transitionId = this.transitions.size();
        addTransition(transitionId, place, true);
    }

    private void connect(Node in, Node out) {
        Link link = new Link(in, out);
        links.add(link);
    }

    public List<Place> getPlaces() {
        return places;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    private boolean checkConnectivity() {
    	int[][] m = matrixSum(matrixIn, matrixOut);
    	if(checkColumns(m, 0) || checkRows(m, 0))return false;
        return true;
    }

    public void generateMatrix(){
        int tSize =transitions.size();
        int pSize =places.size();
        this.matrixIn = new int[pSize][tSize];
        this.matrixOut = new int[pSize][tSize];

        for(Link link: links){
            if(link.getOutGoingNode() instanceof Transition)
            matrixIn[link.getInGoingNode().getId()][link.getOutGoingNode().getId()] = 1;
            else if(link.getInGoingNode() instanceof Transition)
                matrixOut[link.getOutGoingNode().getId()][link.getInGoingNode().getId()] = 1;
        }
    }

    public void addPlace(int id, Transition transition, boolean ingoing) {
        Place place = new Place(id);
        places.add(place);
        if (ingoing) {
            connect(transition, place);
        } else {
            connect(place, transition);
        }
    }

    public void addTransition(int id, Place place, boolean ingoing) {
        Transition transition = new Transition(id);
        transitions.add(transition);
        if (ingoing) {
            connect(place, transition);
        } else {

            connect(transition, place);
        }
    }
    public void addTransition(int id, Place in, Place out) {
        Transition transition = new Transition(id);
        transitions.add(transition);
        connect(in,transition);
        connect(transition,out);
    }

    public void printNet() {
        for (Link link : links) {
            String in = link.getInGoingNode().toString();
            String out = link.getOutGoingNode().toString();
            System.out.println(in + " --> " + out);

        }
    }

    public int[][] getMatrixIn() {
        return matrixIn;
    }

    public int[][] getMatrixOut() {
        return matrixOut;
    }

    public int getId() {
        return id;
    }

    public boolean equals(Object net) {
        if (this == net) return true;
        if (net == null || getClass() != net.getClass()) return false;
        if(!matEquals(matrixIn,((Network)net).getMatrixIn()))return false;
        if(!matEquals(matrixOut,((Network)net).getMatrixOut()))return false;
        return true;
    }
    
}
