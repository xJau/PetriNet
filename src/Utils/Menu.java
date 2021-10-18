package Utils;

import Models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private final String NUOVO_VALORE = ") Per crearne una nuova";
    private final String RITORNA_AL_MAIN_MENU = "Per tornare al menù principale";
    private final String SELEZIONA_POSTI = "Seleziona il posto da collegare";
    private final String FINE = "Per terminare";
    private final String SELEZIONE_AZIONE_NET = "Vuoi selezionare (1), Salvare (2) una Network o selezionare (3), salvare (4) una "
    		+ "Petri's Network? (0) Per chiudere.";
    private final String INSERIMENTO_VALIDO = "Inserire un valore valido: ";
    private final String SALVA_O_CONTINUA = "(1) Per Salvare (2) Per selezionare un'altra Network da salvare";
    private final String AGGIUNGI = "Vuoi aggiungere un Posto (1), una Transizione (2), un Link (3) o cambiare nome (4) alla Network? (0) Per uscire";
    private final String SELEZINA_TRANSIIZONE = "Seleziona la transizione da collegare";
    private final String P_INGRESSO_O_USCITA = "La transizione seleizonata è in ingresso al posto appena creato? (Y/N)";
    private final String T_INGRESSO_O_USCITA = "Il posto selezionato è in ingresso alla transizione appena creata? (Y/N)";
    private final String S_N = "Inserisci (y) o (n) per continuare";
    private final String SELETIONA_POT = "Vuoi avere un posto(p) come origine o una transizione(t)?";
    private final String LINK_GIA_ESISTENTE = "Il link che vuoi aggiungere è già presente nella rete";
    private final String NO_RETI = "Non ci sono reti salvabili";
    private final String NET_ALREADY_EXISTS = "La rete così ottenuta esiste già";
    private final String ASSEGNA_NOME_NET = "Inserire il nome che si desidera assegnare alla rete";
    private final String AVVISO_PERDITA_DATI = "ATTENZIONE: in caso di chiusura del programma "
    		+ "le reti che non sono state selezionate durante la fase di\nsalvataggio andranno perse";
    private final String INSERIRE_MARCATURA_INIZIALE = "Inserire una marcatura iniziale ai posti elencati di seguito: ";
    private final String PN_MENU = "(1)Per visualizzare la struttura di una rete di petri specidica, (2) per visualizzare l'elenco"
    		+ "delle reti di petri esistenti (0)per chiudere.";
    private final String INSERIRE_PESI_LINK = "Inserire il peso desiderato ai seguenti link: ";
    		
    Scanner in;

    public Menu(){
        this.in = new Scanner(System.in);
    }


    public void selectNets(ArrayList<? extends Network> nets) {

        int netsSize = nets.size();

        print(nets);
        System.out.println((netsSize + 1) + NUOVO_VALORE );
        System.out.println("\n0) "+RITORNA_AL_MAIN_MENU);

    }
    public void selectNetsToSave(ArrayList<? extends Network> nets) {


        print(nets);
        System.out.println("\n0) "+RITORNA_AL_MAIN_MENU);
    }
    
    public void printNetStructure(Network n) {
        for (Link link : n.getLinks()) {
            String in = link.getInGoingNode().toString();
            String out = link.getOutGoingNode().toString();
            System.out.println(in + " --> " + out);

        }
    }
    
    public void printNetStructure(PetrisNetwork n) {
    	int [][]mIn = n.getMatrixIn();
		int [][]mOut = n.getMatrixOut();
        for (Link link : n.getLinks()) {
            String in = link.getInGoingNode().toString();
            String out = link.getOutGoingNode().toString();
            if (link.getOutGoingNode() instanceof Transition)
            	System.out.println(in + " --> " + out + " peso = "+ mIn[link.getInGoingNode().getId()][link.getOutGoingNode().getId()]);
            else if (link.getInGoingNode() instanceof Transition)
            	System.out.println(in + " --> " + out +" peso = "+ mOut[link.getOutGoingNode().getId()][link.getInGoingNode().getId()]);
        }
        printPetriNetMarking(n);
    }
    
    public void printPetriNetMarking(PetrisNetwork n) {
    	int[] m = n.getMarking();
    	for(int i = 0; i < m.length; i++) {
    		System.out.println(n.getPlaces().get(i).toString()+ " marcatura = "+ m[i]);
    	}
    }
    
    public void print(ArrayList<? extends Identificable> id) {

        for (int i = 0; i < id.size(); i++) {
            System.out.println((i + 1)  + ") " + id.get(i).toString());
        }
    }
    
    public void print(String s) {
    	System.out.println(s);
    }

    public void selectPlaces(ArrayList<Place> places){
        System.out.println(SELEZIONA_POSTI);
        print(places);
        System.out.println("\n0) "+FINE);
    }


    public void mainMenu() {
        System.out.println(SELEZIONE_AZIONE_NET);
        avvisoPerditaDati();
    }

    public void printValue() {
        System.out.print(INSERIMENTO_VALIDO);
    }

    public void save() {
        System.out.println(SALVA_O_CONTINUA);
    }

    public void doToNet() {
        System.out.println(AGGIUNGI);
    }

    public void selectTransitions(ArrayList<Transition> transitions) {

        System.out.println(SELEZINA_TRANSIIZONE);
        print(transitions);
        System.out.println("\n0) "+FINE);
    }

    public void placeInGoing() {
        System.out.println(P_INGRESSO_O_USCITA);
    }
    
    public void transitionInGoing() {
        System.out.println(T_INGRESSO_O_USCITA);
    }


    public void yesNo(){
        System.out.println(S_N);
    }


	public void selezionaPoT() {
		System.out.println(SELETIONA_POT);	
	}


	public void linkEsiste() {
		System.out.println(LINK_GIA_ESISTENTE);	
	}


	public void noNet() {
		System.out.println(NO_RETI);	
		
	}


	public void netAlreadyExists() {
		System.out.println(NET_ALREADY_EXISTS);
		
	}


	public void insNuovoNome() {
		System.out.println(ASSEGNA_NOME_NET);
		
	}


	public void avvisoPerditaDati() {
		System.out.println(AVVISO_PERDITA_DATI);
		
	}


	public void inserisciMarcatura() {
		System.out.println(INSERIRE_MARCATURA_INIZIALE);
		
	}


	public void pnMenu() {
		System.out.println(PN_MENU);
		
	}


	public void inserisciPesi() {
		System.out.println(INSERIRE_PESI_LINK);
		
	}
	
}
