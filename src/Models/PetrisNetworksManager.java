package Models;
import java.util.ArrayList;
import java.util.List;

import static Utils.InputManager.*;

import Utils.DataLoader;
import Utils.DataPetriSaver;
import Utils.DataSaver;
import Utils.Menu;

public class PetrisNetworksManager {
	    
	static Menu menu = new Menu();
	
	public static void pNetsMenu(ArrayList<PetrisNetwork> pn) {
		int input;
		boolean blocker = true;
        while (blocker) {
            menu.pnMenu();
            do {
                input = inInt();
                switch (input) {
                    case 0:
                        blocker = false;
                        break;
                    case 1:
                        selectPNet(pn);
                        break;
                    case 2:
                        menu.print(pn);
                        break;
                    default:
                        menu.printValue();
                        break;
                }
            } while (input < 0 || input > 2);
        }
	}
	
	public static PetrisNetwork createPNet(ArrayList<PetrisNetwork> pn, Network n) {
		String name;
		int id = pn.size();
		int[] marking = new int[n.getPlaces().size()];
		int[] linksWeight = new int[n.getLinks().size()];
		
		menu.inserisciMarcatura();
		for(int i = 0; i < marking.length; i++) {
			menu.print(n.getPlaces().get(i).toString());
			marking[i] = inInt();
			if(marking[i]<0) {
				menu.printValue();
				i--;
			}
		}
		
		menu.inserisciPesi();
		for(int i = 0; i < marking.length; i++) {
			menu.print(n.getLinks().get(i).toString());
			linksWeight[i] = inInt();
			if(linksWeight[i]<1) {
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
		
		return new PetrisNetwork(n, id, name, marking);
	}
	
	
	
	public static void selectPNet(ArrayList<PetrisNetwork> pn) {
		int[] m = {0};
		int input;
		if(pn.isEmpty())pn.add(new PetrisNetwork(new Network(0, "default"), 0, "default", m));
		
        int pnSize = pn.size();
        menu.selectNets(pn);

        do {
        	input = -1;
            input = input + inInt();
            if (input == -1) return;
            else if (input == -2 || input == pnSize) menu.printValue();
            else use(pn.get(input));
        } while (input < 0 || input > pnSize-1);
		
		
	}
	
	public static void use(PetrisNetwork pn) {
		menu.printNetStructure(pn);
	}
	
	public static void savePnets(String fileName , ArrayList<PetrisNetwork> pnets, ArrayList<PetrisNetwork> savedpNets) {
        ArrayList<PetrisNetwork> savableNets = new ArrayList<>();
        ArrayList<PetrisNetwork> savingNets = new ArrayList<>();
        savableNets.addAll(pnets);
        boolean stop = false;
        int input = -1;
        do {
            if (savableNets.isEmpty()) {
                menu.noNet();
                break;
            }
            menu.selectNetsToSave(savableNets);
            if (input == 0) return;
            input = select(savableNets);
            if (input == -2) {
                menu.noNet();
                return;
            }
            if (input == -1) return;
            savingNets.add(savableNets.get(input));
            savableNets.remove(input);
            menu.save();
            do {
                input = inInt();
                if (input == 1) stop = true;
                else if (input == -1) menu.printValue();
            } while (input < 1 || input > 2);

        } while (!stop);
        
        savedpNets = savingNets;
        sortNetId(savedpNets);
        DataPetriSaver saver = new DataPetriSaver(savedpNets, fileName);
        saver.writeFile();
    }

	private static int select(List<? extends Identificable> id) {

        int input;
        if (id.size() == 0) return -2;
        do {
            input = -1;
            input = input + inInt();
            if (input == -2) menu.printValue();
        } while (input < -1 || input > id.size());
        return input;
    }
	
	private static void sortNetId(List <? extends Network> n) {
    	for(int i = 0; i<n.size(); i++) {
    		n.get(i).setId(i);
    	}
    }
	
}
