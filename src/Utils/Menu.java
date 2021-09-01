package Utils;

import Models.*;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private final String NUOVO_VALORE = ") Per crearne una nuova";
    private final String RITORNA_AL_MAIN_MENU = "Per tornare al menu principale";
    private final String SELEZIONA_POSTI = "Seleziona il posto che vuoi collegare";
    private final String FINE = "Per terminare";
    private final String SELEZIONE_AZIONE_NET = "Vuoi selezionare (1) o Salvare/Caricare (2) una Network? (0) Per chiudere.";
    private final String CARICA_O_SALVA = "Caricare le reti dal file locale (1) o salvare in esso (2)?";
    private final String INSERIMENTO_NON_VALIDO = "Inserire un valore valido(presente tra quelli elencati): ";
    private final String SALVA_O_CONTINUA = "(1) Per Salvare (2) Per Continuare";
    private final String GESTISCI = "Vuoi aggiungere un Posto(1) o una Transizione(2), o collegare due nodi(3)? (0)Per uscire";
    private final String SELEZINA_TRANSIIZONE = "Seleziona la transizione da collegare";
    private final String P_INGRESSO_O_USCITA = "La transizione selezionata è in ingresso al posto? (Y/N)";
    private final String T_INGRESSO_O_USCITA = "Il posto selezionato è in ingresso alla transizione ? (Y/N)";
    private final String S_N = "Inserisci (s) o (n) per continuare";
    private final String POSTO_TRANSIZIONE = "Posto(1) o Transizione(2)";
    

    Scanner in;

    public Menu(){
        this.in = new Scanner(System.in);
    }


    public void selectNets(List<Network> nets) {

        int netsSize = nets.size();

        printNetwork(nets);
        System.out.println((netsSize + 1) + NUOVO_VALORE );
        System.out.println("\n0) "+RITORNA_AL_MAIN_MENU);

    }

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
    }

    public void selectPlaces(Iterable<Place> places){
        System.out.println(SELEZIONA_POSTI);
        printPlaces(places);
        System.out.println("\n0) "+FINE);
    }


    public void mainMenu() {
        System.out.println(SELEZIONE_AZIONE_NET);
    }

    public void saveLoad() {
        System.out.println(CARICA_O_SALVA);
    }

    public void printValue() {
        System.out.print(INSERIMENTO_NON_VALIDO);
    }

    public void save() {
        System.out.println(SALVA_O_CONTINUA);
    }

    public void manage() {
        System.out.println(GESTISCI);
    }

    public void selectTransitions(Iterable<Transition> transitions) {

        System.out.println(SELEZINA_TRANSIIZONE);
        printTransition(transitions);
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
    
    public void postoTransizione() {
    	
    }
    

}
