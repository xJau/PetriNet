package Models;

import static Utils.InputManager.inInt;
import static Utils.InputManager.inString;
import static Utils.MatrixOperation.changeNonNullMatrixEnrtriesToOne;

import java.util.ArrayList;
import java.util.List;

import Utils.DataPetriSaver;
import Utils.DataPriorityPetriSaver;
import Utils.Menu;

public class PriorityPetrisNetworkManager {
	
	static Menu menu = new Menu();
	
	public static void pnpMenu(ArrayList<PriorityPetrisNetwork> pnp, ArrayList<PetrisNetwork> pn, ArrayList<PriorityPetrisNetwork> savedpnpNets) {
		int input;
		boolean blocker = true;
        while (blocker) {
            menu.pnpMenu();
            do {
                input = inInt();
                switch (input) {
                    case 0:
                        blocker = false;
                        break;
                    case 1:
                        selectPPNet(savedpnpNets);
                        break;
                    case 2:
                        menu.print(pnp);
                        break;
                    case 3:
                        createPPNet(pnp, pn);
                        break;
                    default:
                        menu.printValue();
                        break;
                }
            } while (input < 0 || input > 3);
        }
	}

	private static void createPPNet(ArrayList<PriorityPetrisNetwork> pnp, ArrayList<PetrisNetwork> pn) {
		if(pn.isEmpty()) {
			menu.noPNetForPPetris();
			return;
		}
		menu.selecPNEtforPPetris();
		menu.print(pn);
		int input = select(pn);
		if(input == -1)return;
		System.out.println(input);
		createPNet(pnp, pn.get(input));
	}

	private static void createPNet(ArrayList<PriorityPetrisNetwork> pnp, PetrisNetwork pn) {
		String name;
		int id = pnp.size();
		int[] priority = new int[pn.getTransitions().size()];
		
		menu.inserisciPriorità();
		for(int i = 0; i < priority.length; i++) {
			menu.print(pn.getTransitions().get(i).toString());
			priority[i] = inInt();
			if(priority[i]<1) {
				menu.printValue();
				i--;
			}
		}
		
		menu.insNuovoNome();
		do {
			name = inString();
			if(!name.toLowerCase().replaceAll("[^a-z]", "").equals("name"))break;
			menu.printValue();
		}while(true);
		
		PriorityPetrisNetwork pPetri = new PriorityPetrisNetwork(pn, id, name, priority);
		if(checkIfNetExists(pPetri, pnp)) {
			menu.netAlreadyExists();
			return;
		}
		pnp.add(pPetri);
		
	}

	private static void selectPPNet(ArrayList<PriorityPetrisNetwork> pnp) {
		int input;
		if(pnp.isEmpty()) {
			menu.noPriorityPetrisNets();
			return;
		}
		
        int pnSize = pnp.size();
        menu.print(pnp);

        do {
        	input = -1;
            input = input + inInt();
            if (input < 0 || input > pnSize-1) menu.printValue();
            else use(pnp.get(input));
        } while (input < 0 || input > pnSize-1);
	}
	
	private static void use(PriorityPetrisNetwork pnp) {
		PetrisNetSimulator pns = new PetrisNetSimulator(); 
		menu.printNetStructure(pnp);
		menu.printPetriNetMarking(pnp);
		menu.printTransitionPriority(pnp, pnp.getPriority());
		
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
                menu.noNet();
                break;
            }
            menu.selectNetsToSave(savableNets);
            if (input == 0) return null;
            input = select(savableNets);
            if (input == -2) {
                menu.noNet();
                return null;
            }
            if (input == -1) return null;
            savingNets.add(savableNets.get(input));
            savableNets.remove(input);
            menu.save();
            do {
                input = inInt();
                if (input == 1) stop = true;
                else if (input < 1 || input > 2) menu.printValue();
            } while (input < 1 || input > 2);

        } while (!stop);
        
        savedpnpNets = savingNets;
        sortNetId(savedpnpNets);
        DataPetriSaver saver = new DataPriorityPetriSaver(savedpnpNets, fileName);
        saver.writeFile();
        return savedpnpNets;
    }
	

	private static int select(List<? extends Identificable> id) {

        int input;
        if (id.size() == 0) return -2;
        do {
            input = -1;
            input = input + inInt();
            if (input == -2 || input > id.size()-1) menu.printValue();
        } while (input < -1 || input > id.size()-1);
        return input;
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
