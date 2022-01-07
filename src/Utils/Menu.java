package Utils;

import Models.*;
import View.UI;

import java.util.ArrayList;

public class Menu {

	public UI ui;
	public static final String NUOVO_VALORE = ") Per crearne una nuova";
    public static final String RITORNA_AL_MAIN_MENU = "Per tornare al menu' principale";
    public static final String SELEZIONA_POSTI = "Seleziona il posto da collegare";
    public static final String FINE = "Per terminare";
    public static final String SELEZIONE_AZIONE_NET = "1) Selezionare una rete\n" +
            "2) Salvare una o piu' reti\n" +
            "3) Gestione reti di petri\n" +
            "4) Salvare una o piu' reti di petri\n" +
            "5) Gestione reti di petri con priorita'\n" +
            "6) Salvare una o piu' reti di petri con priorita'\n" +
            "7) Importa rete da file" +
            "\n0) Per chiudere.\n";
    public static final String INSERIMENTO_VALIDO = "Inserire un valore valido:\n";
    public static final String SALVA_O_CONTINUA = "(1) Per Salvare\n" +
            "(2) Per selezionare un'altra rete da salvare";
    public static final String AGGIUNGI = "Vuoi aggiungere un Posto (1), una Transizione (2), un Link (3) o cambiare nome (4) alla rete? (0) Per uscire\n";
    public static final String SELEZIONA_TRANSIZIONE = "Seleziona la transizione da collegare";
    public static final String P_INGRESSO_O_USCITA = "La transizione selezionata e' in ingresso al posto appena creato? (Y/N)";
    public static final String T_INGRESSO_O_USCITA = "Il posto selezionato e' in ingresso alla transizione appena creata? (Y/N)";
    public static final String S_N = "Inserisci (y) o (n) per continuare";
    public static final String SELETIONA_POT = "Vuoi avere un (p)osto come origine o una (t)ransizione?";
    public static final String LINK_GIA_ESISTENTE = "\nIl link che vuoi aggiungere e' gia' presente nella rete\n";
    public static final String NO_RETI = "Non ci sono reti salvabili";
    public static final String NET_ALREADY_EXISTS = "La rete cosi' ottenuta esiste gia'\n";
    public static final String ASSEGNA_NOME_NET = "Inserire il nome che si desidera assegnare alla rete";
    public static final String INSERIRE_MARCATURA_INIZIALE = "Inserire una marcatura iniziale ai posti elencati di seguito: ";
    public static final String PN_MENU = "\n(1)Per visualizzare ed eventualmente simulare una rete di petri specifica\n" +
            "(2)Visualizzare l'elenco delle reti di petri esistenti\n" +
            "(3)Creare una rete di petri\n" +
            "(0)Per chiudere.\n";
    public static final String INSERIRE_PESI_LINK = "Inserire il peso desiderato ai seguenti link: ";
    public static final String NO_NETS_PER_PETRI = "Non ci sono reti da usare come base per una reti di petri, per continuare creane una.";
    public static final String NO_PETRIS_NETS = "Non ci sono reti di petri selezionabili, creane una per poter accedere a questa voce.";
    public static final String SELEZIONA_RETE_PER_PETRI = "\nSelezionare la rete che si desidera utilizzare come base per la creazione della rete di petri, (0) per uscire\n";		
    public static final String SELEZIONA_TRANSIZIONE_PER_SCATTO = "Selezionare la trasizione che si desidera far scattare";
    public static final String UNA_SOLA_TRANSIZIONE_ABILITATA = "\nUnica transizione abilitata, esecuzione iterazione\n";
    public static final String SIMULATORE_MENU = "\n(1)Per eseguire un'iterazione\n" +
            "(0)Per chiudere.\n";
    public static final String NESSUNA_TRANSIZIONE_ABILITATA = "\nNessuna transizione abilitata allo scatto\n";
    public static final String PNP_MENU = "\n(1)Per visualizzare ed eventualmente simulare una rete di petri con priorita' specifica\n" +
            "(2)Visualizzare l'elenco delle reti di petri con priorita' esistenti\n" +
            "(3)Creare una rete di petri con priorita'\n" +
            "(0)per chiudere.\n";
    public static final String NO_PRIORITY_NETS_PER_PETRI = "Non ci sono reti di petri da usare come base per una reti di petri con priorita, per continuare creane una.";
    public static final String SELEZIONA_RETE_DI_PETRI_PER_PETRI_CON_PRIORITA= "\nSelezionare la rete che si desidera utilizzare come base per la creazione"
     		+ " della rete di petri, (0) per uscire\n";
    public static final String INSERIRE_PRIORITA_TRANSIZIONI = "Inserire la priorita' desiderata alle transizioni elencate di seguito";
    public static final String NO_PRIORITY_PETRIS_NETS = "Non ci sono reti di petri con priorita' selezionabili, creane una per poter accedere a questa voce.";
    public static final String IMPORT_MENU = "\n(1)Per importare una rete\n" +
            "(2)Per importare una rete di petri\n" +
            "(3)Per importare una rete con priorita'\n" +
            "(0)Per chiudere\n";
    public static final String NET_FATHER_DONT_EXIST = "La rete padre non e' presente tra quelle salvate in forma persistente, quindi non e' possibile importare la rete desiderata";
    public static final String FILE_VUOTO_O_NON_COMPATIBILE = "Il file selezionato e' vuoto o non compatibile";
    public static final String RETE_NON_CONNESSA = "La rete selezionata non e' connessa";
    public static final String USER_MENU = "\n(1)Per visualizzare ed eventualmente simulare una rete di petri specifica\n" +
            "(2)Per visualizzare ed eventualmente simulare una rete di petri con priorita' specifica\n" +
            "(0)Per chiudere";
    public static final String IDENTIFICATION = "(1)Configuratore\n" +
            "(2)Fruitore\n" +
            "(0)Chiudi";
    public static final String NESSUN_FILE = "Non ci sono reti importabili";
    
    public Menu(UI ui) {
    	this.ui = ui;
    }

    public void selectNets(ArrayList<? extends Network> nets) {

        int netsSize = nets.size();

        println(nets);
        println((netsSize + 1) + NUOVO_VALORE );
        println("\n0) "+RITORNA_AL_MAIN_MENU);

    }
    public void selectNetsToSave(ArrayList<? extends Network> nets) {
        println(nets);
        println("\n0) "+RITORNA_AL_MAIN_MENU);
    }
    
    public void printNetStructure(Network n) {
        for (Link link : n.getLinks()) {
            String in = link.getInGoingNode().toString();
            String out = link.getOutGoingNode().toString();
            println(in + " --> " + out);
        }
    }
    
    public void printNetStructure(PetrisNetwork n) {
    	int [][]mIn = n.getMatrixIn();
		int [][]mOut = n.getMatrixOut();
        for (Link link : n.getLinks()) {
            String in = link.getInGoingNode().toString();
            String out = link.getOutGoingNode().toString();
            if (link.getOutGoingNode() instanceof Transition)
            	println(in + " --> " + out + " peso = "+ mIn[link.getInGoingNode().getId()][link.getOutGoingNode().getId()]);
            else if (link.getInGoingNode() instanceof Transition)
            	println(in + " --> " + out +" peso = "+ mOut[link.getOutGoingNode().getId()][link.getInGoingNode().getId()]);
        }
    }
    
    public void printPetriNetMarking(PetrisNetwork n) {
    	int[] m = n.getMarking();
    	for(int i = 0; i < m.length; i++) {
    		println(n.getPlaces().get(i).toString()+ " marcatura = "+ m[i]);
    	}
    }
    
    public void printPetriNetMarking(PetrisNetwork n, int[] m) {
    	for(int i = 0; i < m.length; i++) {
    		println(n.getPlaces().get(i).toString()+ " marcatura = "+ m[i]);
    	}
    }
    
    public void println(ArrayList<? extends Identifiable> id) {
    	if(id.isEmpty()) {
    		println("Lista vuota");
    		return;
    	}
        for (int i = 0; i < id.size(); i++) {
        	println((i + 1)  + ") " + id.get(i).toString());
        }
    }
    
    public void println(String s) {
    	ui.println(s);
    }
    public void print(String s) {
    	ui.print(s);
    }

    public void selectPlaces(ArrayList<Place> places){
    	println(SELEZIONA_POSTI);
        println(places);
        println("\n0) "+FINE);
    }

	public void printError(String err){
		println("ERRORE: " + err);
	}

    public void selectTransitions(ArrayList<Transition> transitions) {

    	println(SELEZIONA_TRANSIZIONE);
        println(transitions);
        println("\n0) "+FINE);
    }

	public void printTransitionPriority(PriorityPetrisNetwork pnp, int[] priority) {
		for(int i = 0; i < priority.length; i++) {
			println(pnp.getTransitions().get(i).toString()+ " priorit� = "+ priority[i]);
    	}
		
	}


	public void selectMenu(String menuText, ArrayList<Runnable> list) {
		while(true) {
            println(menuText);
            int nOfElements = list.size();
            int value = -1;
            do {
                print("> ");
                value = InputManager.readInt();
                if(value < 0 || value > nOfElements) {
                    println(Menu.INSERIMENTO_VALIDO);
                    continue;
                }
                break;
            } while(true);
            if(value == 0) return;
            list.get(value-1).run();
        }
	}
}
