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
    private final String SELEZIONE_AZIONE_NET = "Vuoi selezionare (1) o Salvare/Caricare (2) una Network?";
    private final String CARICA_O_SALVA = "Caricare le reti dal file locale (1) o salvare in esso (2)?";
    private final String INSERIMENTO_VALIDO = "Inserire un valore valido: ";
    private final String SALVA_O_CONTINUA = "(1) Per Salvare (2) Per Continuare";
    private final String AGGIUNGI = "Vuoi aggiungere un Posto (1), una Transizione (2) o un Link (3)? (0) Per uscire";
    private final String SELEZINA_TRANSIIZONE = "Seleziona la transizione da collegare";
    private final String P_INGRESSO_O_USCITA = "La transizione seleizonata è in ingresso al posto appena creato? (Y/N)";
    private final String T_INGRESSO_O_USCITA = "Il posto selezionato è in ingresso alla transizione appena creata? (Y/N)";
    private final String S_N = "Inserisci (y) o (n) per continuare";
    private final String SELETIONA_POT = "Vuoi avere un posto(p) come origine o una transizione(t)?";
    private final String LINK_GIA_ESISTENTE = "Il link che vuoi aggiungere è già presente nella rete";
    private final String NO_RETI = "Non ci sono reti salvabili";

    Scanner in;

    public Menu(){
        this.in = new Scanner(System.in);
    }


    public void selectNets(ArrayList<Network> nets) {

        int netsSize = nets.size();

        //printNetwork(nets);
        print(nets);
        System.out.println((netsSize + 1) + NUOVO_VALORE );
        System.out.println("\n0) "+RITORNA_AL_MAIN_MENU);

    }
    /*
    private void printNetwork(Iterable<Network> nets) {

        for (Network net: nets) {
            System.out.println((net.getId() + 1)  + ") " + net.toString());
        }
    }
    private void printTransition(Iterable<Transition> transitions) {

        for (Transition transition: transitions) {
            System.out.println((transition.getId() + 1)  + ") " + transition.toString());
        }
    }
    private void printPlaces(Iterable<Place> places) {

        for (Place place: places) {
            System.out.println((place.getId() + 1)  + ") " + place.toString());
        }
    }*/
    
    private void print(ArrayList<? extends Identificable> id) {

        for (int i = 0; i < id.size(); i++) {
            System.out.println((i + 1)  + ") " + id.get(i).toString());
        }
    }

    public void selectPlaces(ArrayList<Place> places){
        System.out.println(SELEZIONA_POSTI);
        //printPlaces(places);
        print(places);
        System.out.println("\n0) "+FINE);
    }


    public void mainMenu() {
        System.out.println(SELEZIONE_AZIONE_NET);
    }

    public void saveLoad() {
        System.out.println(CARICA_O_SALVA);
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
        //printTransition(transitions);
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
	
}
