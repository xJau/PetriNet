package Models;

import static Utils.InputManager.readInt;
import static Utils.InputManager.inString;
import static Models.Selector.select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Utils.DataPetriSaver;
import Utils.DataPriorityPetriSaver;
import Utils.Menu;

public class PriorityPetrisNetworkManager {
	
	public static void pnpMenu(ArrayList<PriorityPetrisNetwork> pnp, ArrayList<PetrisNetwork> pn, ArrayList<PriorityPetrisNetwork> savedpnpNets) {
        ArrayList<Runnable> actions = new ArrayList<>(Arrays.asList(
			() -> selectPPNet(savedpnpNets),
			() -> Menu.print(pnp),
			() -> createPPNet(pnp, pn)
		));
		Menu.selectMenu(Menu.PNP_MENU, actions);
	}
	
	private static void createPPNet(ArrayList<PriorityPetrisNetwork> pnp, ArrayList<PetrisNetwork> pn) {
		if(pn.isEmpty()) {
			Menu.print(Menu.NO_PRIORITY_NETS_PER_PETRI);
			return;
		}
		Menu.print(Menu.SELEZIONA_RETE_DI_PETRI_PER_PETRI_CON_PRIORITA);
		Menu.print(pn);
		int input = select(pn);
		if(input == -1)return;
		System.out.println(input);
		createPNet(pnp, pn.get(input));
	}

	private static void createPNet(ArrayList<PriorityPetrisNetwork> pnp, PetrisNetwork pn) {
		String name;
		int id = pnp.size();
		int[] priority = new int[pn.getTransitions().size()];
		
		Menu.print(Menu.INSERIRE_PRIORITA_TRANSIZIONI);
		for(int i = 0; i < priority.length; i++) {
			Menu.print(pn.getTransitions().get(i).toString());
			priority[i] = readInt();
			if(priority[i]<1) {
				Menu.print(Menu.INSERIMENTO_VALIDO);
				i--;
			}
		}
		
		Menu.print(Menu.ASSEGNA_NOME_NET);
		do {
			name = inString();
			if(!name.toLowerCase().replaceAll("[^a-z]", "").equals("name"))break;
			Menu.print(Menu.INSERIMENTO_VALIDO);
		}while(true);
		
		PriorityPetrisNetwork pPetri = new PriorityPetrisNetwork(pn, id, name, priority);
		if(checkIfNetExists(pPetri, pnp)) {
			Menu.print(Menu.NET_ALREADY_EXISTS);
			return;
		}
		pnp.add(pPetri);
		
	}

	public static void selectPPNet(ArrayList<PriorityPetrisNetwork> pnp) {
		int input;
		if(pnp.isEmpty()) {
			Menu.print(Menu.NO_PRIORITY_PETRIS_NETS);
			return;
		}
		
        int pnSize = pnp.size();
        Menu.print(pnp);

        do {
        	input = -1;
            input = input + readInt();
            if (input < 0 || input > pnSize-1) Menu.print(Menu.INSERIMENTO_VALIDO);
            else use(pnp.get(input));
        } while (input < 0 || input > pnSize-1);
	}
	
	private static void use(PriorityPetrisNetwork pnp) {
		PetrisNetSimulator pns = new PetrisNetSimulator(); 
		Menu.printNetStructure(pnp);
		Menu.printPetriNetMarking(pnp);
		Menu.printTransitionPriority(pnp, pnp.getPriority());		
		pns.simulate(pnp);
	}
	
	public static Network convertToPetrisNet(PriorityPetrisNetwork pn, int id) {
		return new PetrisNetwork(id, pn.getName(), pn.getMatrixIn(), pn.getMatrixOut(), pn.getMarking());
	}
	
	public static ArrayList<PriorityPetrisNetwork> savePnpnets(String fileName , ArrayList<PriorityPetrisNetwork> pnets, ArrayList<PriorityPetrisNetwork> savedpnpNets) {
        ArrayList<PriorityPetrisNetwork> savableNets = new ArrayList<>();
        ArrayList<PriorityPetrisNetwork> savingNets = new ArrayList<>();
        savableNets.addAll(pnets);
        boolean stop = false;
        int input = -1;
        do {
            if (savableNets.isEmpty()) {
                Menu.print(Menu.NO_RETI);
                break;
            }
            Menu.selectNetsToSave(savableNets);
            if (input == 0) return null;
            input = select(savableNets);
            if (input == -2) {
                Menu.print(Menu.NO_RETI);
                return null;
            }
            if (input == -1) return null;
            savingNets.add(savableNets.get(input));
            savableNets.remove(input);
            Menu.print(Menu.SALVA_O_CONTINUA);
            do {
                input = readInt();
                if (input == 1) stop = true;
                else if (input < 1 || input > 2) Menu.print(Menu.INSERIMENTO_VALIDO);
            } while (input < 1 || input > 2);

        } while (!stop);
        
        savedpnpNets = savingNets;
        sortNetId(savedpnpNets);
        DataPetriSaver saver = new DataPriorityPetriSaver(savedpnpNets, fileName);
        saver.writeFile();
        return savedpnpNets;
    }
	
	private static void sortNetId(List <? extends Network> n) {
    	for(int i = 0; i<n.size(); i++) {
    		n.get(i).setId(i);
    	}
    }
	
	private static boolean checkIfNetExists(PriorityPetrisNetwork net, ArrayList<? extends Network> nets) {
        for (Network n : nets)
            if (net.equals(n)) return true;
        return false;
    }	
}
