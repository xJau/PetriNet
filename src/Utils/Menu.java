package Utils;

import Models.*;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private final String NUOVO_VALORE = ") Per crearne una nuova";
    private final String RITORNA_AL_MAIN_MENU = "Per tornare al menù principale";
    private final String SELEZIONA_POSTI = "Seleziona i posti da collegare alla nuova transizione";
    private final String FINE = "Per terminare";
    private final String SELEZIONE_AZIONE_NET = "Vuoi selezionare (1) o Salvare/Caricare (2) una Network?";
    private final String CARICA_O_SALVA = "Caricare le reti dal file locale (1) o salvare in esso (2)?";
    private final String INSERIMENTO_VALIDO = "Inserire un valore valido: ";
    private final String SALVA_O_CONTINUA = "(1) Per Salvare (2) Per Continuare";
    private final String AGGIUNGI_NODO = "Vuoi aggiungere un Posto(1) o una Transizione(2), (0) per uscire";
    private final String SELEZINA_TRANSIIZONE = "Seleziona la transizione da collegare al nuovo posto";
    private final String P_INGRESSO_O_USCITA = "La transizione seleizonata è in ingresso al posto appena creato? (Y/N)";
    private final String T_INGRESSO_O_USCITA = "Il posto seleizonato è in ingresso alla transizione appena creata? (Y/N)";
    private final String S_N = "Inserisci (s) o (n) per continuare";
    

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

    private void printNetwork(List<Network> net) {

        int listSize = net.size();

        for (int i = 0; i < listSize; i++) {
            System.out.println((i + 1) + ") Network n." + net.get(i).getId());
        }
    }
    private void printTransition(List<Transition> transitions) {

        int listSize = transitions.size();

        for (int i = 0; i < listSize; i++) {
            System.out.println((i + 1) + ") Transition n." + transitions.get(i).getId());
        }
    }
    private void printPlaces(List<Place> places) {

        int listSize = places.size();

        for (int i = 0; i < listSize; i++) {
            System.out.println((i + 1) + ") Place n." + places.get(i).getId());
        }
    }

    public void selectPlaces(List<Place> places){
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
        System.out.print(INSERIMENTO_VALIDO);
    }

    public void save() {
        System.out.println(SALVA_O_CONTINUA);
    }

    public void createNet() {
        System.out.println(AGGIUNGI_NODO);
    }

    public void selectTransitions(List<Transition> transitions) {

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
}
