package Models;

import java.util.ArrayList;
import java.util.List;

import static Utils.MatrixOperation.*;

public class Network implements Identificable{

    private int id;
    private String name;
    private ArrayList<Place> places;
    private ArrayList<Transition> transitions;
    private ArrayList<Link> links;
    private int[][] matrixIn;
    private int[][] matrixOut;

    /**
     * Costruttore Network da metodo create network
     * genera rete di base con un posto collegato ad una transizione e genera le
     * metrici di adiacenza in ingresso ed in uscita
     * @param id
     * @param name
     */
    public Network(int id, String name) {
        this.id = id;
        this.name = name;
        this.places = new ArrayList<>();
        this.transitions = new ArrayList<>();
        this.links = new ArrayList<>();
        Place place = new Place(places.size());
        this.places.add(place);
        int transitionId = this.transitions.size();
        addTransition(transitionId, place, true);
        generateMatrix();
    }

    /**
     * Costruttore Network da file: prende in ingresso l'id, il nome e le due matrici di adiacenza al fine di creare i
     * posti, transizioni e collegamenti.
     * @param id
     * @param name
     * @param matrixIn
     * @param matrixOut
     */
    public Network(int id, String name, int[][] matrixIn, int[][] matrixOut) {
        this.id = id;
        this.name = name;
        this.matrixIn = matrixIn;
        this.matrixOut = matrixOut;
        this.places = new ArrayList<>();
        this.transitions = new ArrayList<>();
        this.links = new ArrayList<>();
        addPlaces(matrixIn.length);
        addTransitions(matrixIn[0].length);
        interConnect();
        generateMatrix(matrixIn, matrixOut);
    }

    public Network() {
		// TODO Auto-generated constructor stub
	}

	/**
     * Crea collegamento tra i due nodi indicati
     * @param in
     * @param out
     */
    private void connect(Node in, Node out) {
        Link link = new Link(in, out);
        links.add(link);
    }

    /**
     * Date le matrici di ingresso e di uscita genera i link della rete
     */
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

    /**
     * metodo per controllare la connettivita' della rete;
     * non usato perche' la creazione dei nodi previene questo problema
     * @return TRUE se la rete e' connessa, FALSE in caso contrario
     */
    private boolean checkConnectivity() {
        int[][] m = matrixSum(matrixIn, matrixOut);
        if (checkColumns(m, 0) || checkRows(m, 0)) return false;
        return true;
    }

    /**
     * Verifica l'esistenza del Link l nella rete
     * @param l
     * @return TRUE l nella rete, FALSE in caso contrario
     */
    
    public boolean checkLinkExist(Link l){
    	for(Link a: links)if(a.equals(l))return true;
    	return false;
    }

    /**
     * Genera le matrici di adiacenza della rete
     * Le righe delle matrici corrispondono ai Posti e le colonne alle transizioni
     * La Matrice MatrixIn contiene tutti i nodi ENTRANTI alle transizioni
     * La Matrice MatrixOut contiene tutti i nodi USCENTI dalle transizioni
     * Per la popolazione delle due matrici vengono passati tutti i link salvati nella rete
     */

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
    
    public void generateMatrix(int[][] mIn, int[][] mOut) {
        int tSize = transitions.size();
        int pSize = places.size();
        this.matrixIn = new int[pSize][tSize];
        this.matrixOut = new int[pSize][tSize];

        for (Link link : links) {
            if (link.getOutGoingNode() instanceof Transition)
                matrixIn[link.getInGoingNode().getId()][link.getOutGoingNode().getId()] = mIn[link.getInGoingNode().getId()][link.getOutGoingNode().getId()];
            else if (link.getInGoingNode() instanceof Transition)
                matrixOut[link.getOutGoingNode().getId()][link.getInGoingNode().getId()] = mOut[link.getOutGoingNode().getId()][link.getInGoingNode().getId()];
        }
    }

    /**
     * Metodo per la creazione di un posto
     * @param id indica l'identificativo del posto
     * @param transition indica la transizione a cui e' collegato
     * @param ingoing se TRUE transition in ingresso al posto creato, FALSE viceversa
     */

    public void addPlace(int id, Transition transition, boolean ingoing) {
        Place place = new Place(id);
        places.add(place);
        if (ingoing) {
            connect(transition, place);
        } else {
            connect(place, transition);
        }
    }

    /**
     * Metodo usato per la creazione dei posti
     * usato nel costruttore da File
     * @param numNodes indica quanti posti verranno creati con id crescente l'uno rispetto al precedente
     */
    private void addPlaces(int numNodes) {
        for (int i = 0; i < numNodes; i++) {
            Place place = new Place(i);
            places.add(place);
        }
    }

    /**
     * Metodo usato per la creazione delle transizioni
     * usato nel costruttore da File
     * @param numNodes indica quante transizioni verranno creati con id crescente l'uno rispetto al precedente
     */

    private void addTransitions(int numNodes) {
        for (int i = 0; i < numNodes; i++) {
            Transition transition = new Transition(i);
            transitions.add(transition);
        }
    }

    /**
     * Metodo per la creazione di una transizione
     * @param id indica l'identificativo della transizione
     * @param place indica il posto a cui e' collegata
     * @param ingoing se TRUE place in ingresso alla transizione creata, FALSE viceversa
     */

    public void addTransition(int id, Place place, boolean ingoing) {
        Transition transition = new Transition(id);
        transitions.add(transition);
        if (ingoing) {
            connect(place, transition);
        } else {
            connect(transition, place);
        }
    }

    /**
     * metodo per lo stampaggio a video di una transizione
     */
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
    
    public String getName() {
    	return name;
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public ArrayList<Transition> getTransitions() {
        return transitions;
    }

    public ArrayList<Link> getLinks(){
        return links;
    }
    
    public void setId(int id) {
    	this.id = id;
    }
    
    public void setName(String name) {
    	this.name = name;
    }

    public boolean equals(Object net) {
        if (this == net) return true;
        if (net == null || getClass() != net.getClass()) return false;
        if (!matEquals(matrixIn, ((Network) net).getMatrixIn())) return false;
        if (!matEquals(matrixOut, ((Network) net).getMatrixOut())) return false;
        return true;
    }

    @Override
    public String toString() {
        return "Network n." + id + " " + name;
    }
}
