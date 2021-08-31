package Models;

import java.util.ArrayList;
import java.util.List;

import static Utils.MatrixOperation.*;

public class Network implements Identificabile {

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

    public Network(int id, int[][] matrixIn, int[][] matrixOut) {
        this.id = id;
        this.matrixIn = matrixIn;
        this.matrixOut = matrixOut;
        this.places = new ArrayList<>();
        this.transitions = new ArrayList<>();
        this.links = new ArrayList<>();
        addPlaces(matrixIn.length);
        addTransitions(matrixIn[0].length);
        interConnect();
    }

    private void connect(Node in, Node out) {
        Link link = new Link(in, out);
        links.add(link);
    }

    private void interConnect() {
        for (int i = 0; i < matrixIn.length; i++) {
            Place place = places.get(i);
            for (int j = 0; j < matrixIn[0].length; j++) {
                if (matrixIn[i][j] != 0) {
                    Transition transition = transitions.get(j);
                    connect(place, transition);
                }
            }
        }
        for (int i = 0; i < matrixOut.length; i++) {
            Place place = places.get(i);
            for (int j = 0; j < matrixOut[0].length; j++) {
                if (matrixOut[i][j] != 0) {
                    Transition transition = transitions.get(j);
                    connect(transition, place);
                }
            }
        }
    }

    public List<Place> getPlaces() {
        return places;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    private boolean checkConnectivity() {
        int[][] m = matrixSum(matrixIn, matrixOut);
        if (checkColumns(m, 0) || checkRows(m, 0)) return false;
        return true;
    }

    public void generateMatrix() {
        int tSize = transitions.size();
        int pSize = places.size();
        this.matrixIn = new int[pSize][tSize];
        this.matrixOut = new int[pSize][tSize];

        for (Link link : links) {
            if (link.getOutGoingNode() instanceof Transition)
                matrixIn[link.getInGoingNode().getId()][link.getOutGoingNode().getId()] = 1;
            else if (link.getInGoingNode() instanceof Transition)
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

    private void addPlaces(int numNodes) {
        for (int i = 0; i < numNodes; i++) {
            Place place = new Place(i);
            places.add(place);
        }
    }

    private void addTransitions(int numNodes) {
        for (int i = 0; i < numNodes; i++) {
            Transition transition = new Transition(i);
            transitions.add(transition);
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
        connect(in, transition);
        connect(transition, out);
    }

    public void printNet() {
        for (Link link : links) {
            String in = link.getInGoingNode().toString();
            String out = link.getOutGoingNode().toString();
            System.out.println(in + " --> " + out);

        }
    }

    public void setMatrixIn(int[][] matrixIn) {
        this.matrixIn = matrixIn;
    }

    public void setMatrixOut(int[][] matrixOut) {
        this.matrixOut = matrixOut;
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
        if (!matEquals(matrixIn, ((Network) net).getMatrixIn())) return false;
        if (!matEquals(matrixOut, ((Network) net).getMatrixOut())) return false;
        return true;
    }

}
