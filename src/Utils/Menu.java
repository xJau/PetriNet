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
    private final String SELEZIONE_AZIONE_NET = "1) Selezionare una rete\n2) Salvare una o più reti\n3) Gestione reti di petri\n4) Salvare una o più "
    		+ "reti di petri\n5) Gestione reti di petri con priorità\n6) Salvare una o più reti di petri con priorità\n7) Importa rete da file\n0) Per chiudere.\n";
    private final String INSERIMENTO_VALIDO = "Inserire un valore valido:\n";
    private final String SALVA_O_CONTINUA = "(1) Per Salvare\n(2) Per selezionare un'altra rete da salvare";
    private final String AGGIUNGI = "Vuoi aggiungere un Posto (1), una Transizione (2), un Link (3) o cambiare nome (4) alla rete? (0) Per uscire\n";
    private final String SELEZIONA_TRANSIZIONE = "Seleziona la transizione da collegare";
    private final String P_INGRESSO_O_USCITA = "La transizione selezionata è in ingresso al posto appena creato? (Y/N)";
    private final String T_INGRESSO_O_USCITA = "Il posto selezionato è in ingresso alla transizione appena creata? (Y/N)";
    private final String S_N = "Inserisci (y) o (n) per continuare";
    private final String SELETIONA_POT = "Vuoi avere un posto(p) come origine o una transizione(t)?";
    private final String LINK_GIA_ESISTENTE = "\nIl link che vuoi aggiungere è già presente nella rete\n";
    private final String NO_RETI = "Non ci sono reti salvabili";
    private final String NET_ALREADY_EXISTS = "La rete così ottenuta esiste già\n";
    private final String ASSEGNA_NOME_NET = "Inserire il nome che si desidera assegnare alla rete";
    private final String AVVISO_PERDITA_DATI = "ATTENZIONE: in caso di chiusura del programma "
    		+ "le reti che non sono state selezionate durante la fase di\nsalvataggio andranno perse";
    private final String INSERIRE_MARCATURA_INIZIALE = "Inserire una marcatura iniziale ai posti elencati di seguito: ";
    private final String PN_MENU = "\n(1)Per visualizzare ed eventualmente simulare una rete di petri specifica\n(2)Visualizzare l'elenco "
    		+ "delle reti di petri esistenti\n(3)Creare una rete di petri\n(0)per chiudere.\n";
    private final String INSERIRE_PESI_LINK = "Inserire il peso desiderato ai seguenti link: ";
    private final String NO_NETS_PER_PETRI = "Non ci sono reti da usare come base per una reti di petri, per continuare creane una.";
    private final String NO_PETRIS_NETS = "Non ci sono reti di petri selezionabili, creane una per poter accedere a questa voce.";
    private final String SELEZIONA_RETE_PER_PETRI = "\nSelezionare la rete che si desidera utilizzare come base per la creazione della rete di petri, (0) per uscire\n";		
    private final String SELEZIONA_TRANSIZIONE_PER_SCATTO = "Selezionare la trasizione che si desidera far scattare";
    private final String UNA_SOLA_TRANSIZIONE_ABILITATA = "\nUnica transizione abilitata, esecuzione iterazione\n";
    private final String SIMULATORE_MENU = "\n(1)Per eseguire un'iterazione\n(0)Per chiudere.\n";
    private final String NESSUNA_TRANSIZIONE_ABILITATA = "\nNessuna transizione abilitata allo scatto\n";
    private final String PNP_MENU = "\n(1)Per visualizzare ed eventualmente simulare una rete di petri con priorità specifica\n(2)Visualizzare l'elenco "
    		+ "delle reti di petri con priorità esistenti\n(3)Creare una rete di petri con priorità\n(0)per chiudere.\n";
    private final String NO_PRIORITY_NETS_PER_PETRI = "Non ci sono reti di petri da usare come base per una reti di petri con priorita, per continuare creane una.";
    private final String SELEZIONA_RETE_DI_PETRI_PER_PETRI_CON_PRIORITA= "\nSelezionare la rete che si desidera utilizzare come base per la creazione"
    		+ " della rete di petri, (0) per uscire\n";
    private final String INSERIRE_PRIORITA_TRANSIZIONI = "Inserire la priorità desiderata alle transizioni elencate di seguito";
    private final String NO_PRIORITY_PETRIS_NETS = "Non ci sono reti di petri con priorità selezionabili, creane una per poter accedere a questa voce.";
    private final String IMPORT_MENU = "\n(1)Per importare una rete\n(2)Per importare una rete di petri\n(3)Per importare una rete con priorità\n(0)Per chiudere\n";
    private final String NET_FATHER_DONT_EXIST = "La rete padre non è presente tra quelle salvate in forma persistente, "
    		+ "quindi non è possibile importare la rete desiderata";
    private final String FILE_VUOTO_O_NON_COMPATIBILE = "Il file selezionato è vuoto o non compatibile";
    private final String RETE_NON_CONNESSA = "La rete selezionata non è connessa";
    
    
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
    }
    
    public void printPetriNetMarking(PetrisNetwork n) {
    	int[] m = n.getMarking();
    	for(int i = 0; i < m.length; i++) {
    		System.out.println(n.getPlaces().get(i).toString()+ " marcatura = "+ m[i]);
    	}
    }
    
    public void printPetriNetMarking(PetrisNetwork n, int[] m) {
    	for(int i = 0; i < m.length; i++) {
    		System.out.println(n.getPlaces().get(i).toString()+ " marcatura = "+ m[i]);
    	}
    }
    
    public void print(ArrayList<? extends Identificable> id) {
    	if(id.isEmpty()) {
    		print("Lista vuota");
    		return;
    	}
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
//        avvisoPerditaDati();
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

        System.out.println(SELEZIONA_TRANSIZIONE);
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


	public void noNetForPetris() {
		System.out.println(NO_NETS_PER_PETRI);
		
	}


	public void noPetrisNets() {
		System.out.println(NO_PETRIS_NETS);
		
	}
	
	public void selecNEtforPetris() {
		System.out.println(SELEZIONA_RETE_PER_PETRI);
		
	}


	public void selTrantionForFire() {
		System.out.println(SELEZIONA_TRANSIZIONE_PER_SCATTO);
		
	}


	public void onlyOneEnableTransition() {
		System.out.println(UNA_SOLA_TRANSIZIONE_ABILITATA);
		
	}


	public void simulatorMenu() {
		System.out.println(SIMULATORE_MENU);
		
	}


	public void noEnableTransitions() {
		System.out.println(NESSUNA_TRANSIZIONE_ABILITATA);
		
	}


	public void pnpMenu() {
		System.out.println(PNP_MENU);
		
	}


	public void noPNetForPPetris() {
		System.out.println(NO_PRIORITY_NETS_PER_PETRI);
		
	}


	public void selecPNEtforPPetris() {
		System.out.println(SELEZIONA_RETE_DI_PETRI_PER_PETRI_CON_PRIORITA);
		
	}


	public void inserisciPriorità() {
		System.out.println(INSERIRE_PRIORITA_TRANSIZIONI);
		
	}


	public void noPriorityPetrisNets() {
		System.out.println(NO_PRIORITY_PETRIS_NETS);
		
	}


	public void printTransitionPriority(PriorityPetrisNetwork pnp, int[] priority) {
		for(int i = 0; i < priority.length; i++) {
    		System.out.println(pnp.getTransitions().get(i).toString()+ " priorità = "+ priority[i]);
    	}
		
	}


	public void importMenu() {
		System.out.println(IMPORT_MENU);
		
	}


	public void netFatherDontExist() {
		System.out.println(NET_FATHER_DONT_EXIST);
		
	}


	public void emptyOrIncompatible() {
		System.out.println(FILE_VUOTO_O_NON_COMPATIBILE);
		
	}


	public void netNotConnected() {
		System.out.println(RETE_NON_CONNESSA);
		
	}
	
}
