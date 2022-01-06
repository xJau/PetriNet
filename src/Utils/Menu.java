package Utils;

import Models.*;
import View.UI;

import java.util.ArrayList;

public class Menu {

	public static final String NUOVO_VALORE = ") Per crearne una nuova";
    public static final String RITORNA_AL_MAIN_MENU = "Per tornare al men� principale";
    public static final String SELEZIONA_POSTI = "Seleziona il posto da collegare";
    public static final String FINE = "Per terminare";
    public static final String SELEZIONE_AZIONE_NET = "1) Selezionare una rete\n2) Salvare una o pi� reti\n3) Gestione reti di petri\n4) Salvare una o pi� "
     		+ "reti di petri\n5) Gestione reti di petri con priorit�\n6) Salvare una o pi� reti di petri con priorit�\n7) Importa rete da file\n0) Per chiudere.\n";
    public static final String INSERIMENTO_VALIDO = "Inserire un valore valido:\n";
    public static final String SALVA_O_CONTINUA = "(1) Per Salvare\n(2) Per selezionare un'altra rete da salvare";
    public static final String AGGIUNGI = "Vuoi aggiungere un Posto (1), una Transizione (2), un Link (3) o cambiare nome (4) alla rete? (0) Per uscire\n";
    public static final String SELEZIONA_TRANSIZIONE = "Seleziona la transizione da collegare";
    public static final String P_INGRESSO_O_USCITA = "La transizione selezionata � in ingresso al posto appena creato? (Y/N)";
    public static final String T_INGRESSO_O_USCITA = "Il posto selezionato � in ingresso alla transizione appena creata? (Y/N)";
    public static final String S_N = "Inserisci (y) o (n) per continuare";
    public static final String SELETIONA_POT = "Vuoi avere un posto(p) come origine o una transizione(t)?";
    public static final String LINK_GIA_ESISTENTE = "\nIl link che vuoi aggiungere � gi� presente nella rete\n";
    public static final String NO_RETI = "Non ci sono reti salvabili";
    public static final String NET_ALREADY_EXISTS = "La rete cos� ottenuta esiste gi�\n";
    public static final String ASSEGNA_NOME_NET = "Inserire il nome che si desidera assegnare alla rete";
    public static final String AVVISO_PERDITA_DATI = "ATTENZIONE: in caso di chiusura del programma "
     		+ "le reti che non sono state selezionate durante la fase di\nsalvataggio andranno perse";
    public static final String INSERIRE_MARCATURA_INIZIALE = "Inserire una marcatura iniziale ai posti elencati di seguito: ";
    public static final String PN_MENU = "\n(1)Per visualizzare ed eventualmente simulare una rete di petri specifica\n(2)Visualizzare l'elenco "
     		+ "delle reti di petri esistenti\n(3)Creare una rete di petri\n(0)Per chiudere.\n";
    public static final String INSERIRE_PESI_LINK = "Inserire il peso desiderato ai seguenti link: ";
    public static final String NO_NETS_PER_PETRI = "Non ci sono reti da usare come base per una reti di petri, per continuare creane una.";
    public static final String NO_PETRIS_NETS = "Non ci sono reti di petri selezionabili, creane una per poter accedere a questa voce.";
    public static final String SELEZIONA_RETE_PER_PETRI = "\nSelezionare la rete che si desidera utilizzare come base per la creazione della rete di petri, (0) per uscire\n";		
    public static final String SELEZIONA_TRANSIZIONE_PER_SCATTO = "Selezionare la trasizione che si desidera far scattare";
    public static final String UNA_SOLA_TRANSIZIONE_ABILITATA = "\nUnica transizione abilitata, esecuzione iterazione\n";
    public static final String SIMULATORE_MENU = "\n(1)Per eseguire un'iterazione\n(0)Per chiudere.\n";
    public static final String NESSUNA_TRANSIZIONE_ABILITATA = "\nNessuna transizione abilitata allo scatto\n";
    public static final String PNP_MENU = "\n(1)Per visualizzare ed eventualmente simulare una rete di petri con priorit� specifica\n(2)Visualizzare l'elenco "
     		+ "delle reti di petri con priorit� esistenti\n(3)Creare una rete di petri con priorit�\n(0)per chiudere.\n";
    public static final String NO_PRIORITY_NETS_PER_PETRI = "Non ci sono reti di petri da usare come base per una reti di petri con priorita, per continuare creane una.";
    public static final String SELEZIONA_RETE_DI_PETRI_PER_PETRI_CON_PRIORITA= "\nSelezionare la rete che si desidera utilizzare come base per la creazione"
     		+ " della rete di petri, (0) per uscire\n";
    public static final String INSERIRE_PRIORITA_TRANSIZIONI = "Inserire la priorit� desiderata alle transizioni elencate di seguito";
    public static final String NO_PRIORITY_PETRIS_NETS = "Non ci sono reti di petri con priorit� selezionabili, creane una per poter accedere a questa voce.";
    public static final String IMPORT_MENU = "\n(1)Per importare una rete\n(2)Per importare una rete di petri\n(3)Per importare una rete con priorit�\n(0)Per chiudere\n";
    public static final String NET_FATHER_DONT_EXIST = "La rete padre non � presente tra quelle salvate in forma persistente, "
     		+ "quindi non � possibile importare la rete desiderata";
    public static final String FILE_VUOTO_O_NON_COMPATIBILE = "Il file selezionato � vuoto o non compatibile";
    public static final String RETE_NON_CONNESSA = "La rete selezionata non � connessa";
    public static final String USER_PN_MENU = "\n(1)Per visualizzare ed eventualmente simulare una rete di petri specifica\n(0)Per chiudere";
    public static final String USER_PNP_MENU = "\n(1)Per visualizzare ed eventualmente simulare una rete di petri con priorit� specifica\n(0)Per chiudere";
    public static final String USER_MENU = "\n(1)Per visualizzare ed eventualmente simulare una rete di petri specifica\n(2)Per visualizzare ed eventualmente simulare"
     		+ " una rete di petri con priorit� specifica\n(0)Per chiudere";
    public static final String IDENTIFICATION = "(1)Configuratore\n(2)Fruitore";
    public static final String NESSUN_FILE = "Non ci sono reti importabili";

    public static void selectNets(ArrayList<? extends Network> nets, UI ui) {

        int netsSize = nets.size();

        print(nets, ui);
        ui.print((netsSize + 1) + NUOVO_VALORE );
        ui.print("\n0) "+RITORNA_AL_MAIN_MENU);

    }
    public static void selectNetsToSave(ArrayList<? extends Network> nets, UI ui) {
        print(nets, ui);
        ui.print("\n0) "+RITORNA_AL_MAIN_MENU);
    }
    
    public static void printNetStructure(Network n, UI ui) {
        for (Link link : n.getLinks()) {
            String in = link.getInGoingNode().toString();
            String out = link.getOutGoingNode().toString();
            ui.print(in + " --> " + out);
        }
    }
    
    public static void printNetStructure(PetrisNetwork n, UI ui) {
    	int [][]mIn = n.getMatrixIn();
		int [][]mOut = n.getMatrixOut();
        for (Link link : n.getLinks()) {
            String in = link.getInGoingNode().toString();
            String out = link.getOutGoingNode().toString();
            if (link.getOutGoingNode() instanceof Transition)
            	ui.print(in + " --> " + out + " peso = "+ mIn[link.getInGoingNode().getId()][link.getOutGoingNode().getId()]);
            else if (link.getInGoingNode() instanceof Transition)
            	ui.print(in + " --> " + out +" peso = "+ mOut[link.getOutGoingNode().getId()][link.getInGoingNode().getId()]);
        }
    }
    
    public static void printPetriNetMarking(PetrisNetwork n, UI ui) {
    	int[] m = n.getMarking();
    	for(int i = 0; i < m.length; i++) {
    		ui.print(n.getPlaces().get(i).toString()+ " marcatura = "+ m[i]);
    	}
    }
    
    public static void printPetriNetMarking(PetrisNetwork n, int[] m, UI ui) {
    	for(int i = 0; i < m.length; i++) {
    		ui.print(n.getPlaces().get(i).toString()+ " marcatura = "+ m[i]);
    	}
    }
    
    public static void print(ArrayList<? extends Identifiable> id, UI ui) {
    	if(id.isEmpty()) {
    		ui.print("Lista vuota");
    		return;
    	}
        for (int i = 0; i < id.size(); i++) {
           ui.print((i + 1)  + ") " + id.get(i).toString());
        }
    }
    
    public static void print(String s, UI ui) {
    	ui.print(s);
    }

    public static void selectPlaces(ArrayList<Place> places, UI ui){
        print(SELEZIONA_POSTI, ui);
        print(places, ui);
        print("\n0) "+FINE, ui);
    }

	public static void printError(String err, UI ui){
		print("ERRORE: " + err, ui);
	}

    public static void selectTransitions(ArrayList<Transition> transitions, UI ui) {

        print(SELEZIONA_TRANSIZIONE, ui);
        print(transitions, ui);
        print("\n0) "+FINE, ui);
    }

	public static void printTransitionPriority(PriorityPetrisNetwork pnp, int[] priority, UI ui) {
		for(int i = 0; i < priority.length; i++) {
    		ui.print(pnp.getTransitions().get(i).toString()+ " priorit� = "+ priority[i]);
    	}
		
	}

	public static void selectMenu(String menuText, ArrayList<Runnable> list, UI ui) {
		Menu.print(menuText, ui);
		int nOfElements = list.size();
		int value = -1;
		do {
			Menu.print("> ", ui);
			value = InputManager.readInt();
			if(value < 0 || value > nOfElements) {
				Menu.print(Menu.INSERIMENTO_VALIDO, ui);
				continue;
			}
			break;
		} while(true);
		if(value == 0) return;
		list.get(value-1).run();
	}
}
