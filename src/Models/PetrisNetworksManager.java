package Models;
import java.util.ArrayList;
import java.util.List;

import static Utils.InputManager.*;

import Utils.DataLoader;
import Utils.DataPetriSaver;
import Utils.DataSaver;
import Utils.Menu;
import static Utils.MatrixOperation.*;

public class PetrisNetworksManager {
	    
	static Menu menu = new Menu();
	
	public static void pNetsMenu(ArrayList<PetrisNetwork> pn, ArrayList<Network> n, ArrayList<PetrisNetwork> savedpNets) {
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
                        selectPNet(savedpNets);
                        break;
                    case 2:
                        menu.print(pn);
                        break;
                    case 3:
                        createPNet(pn, n);
                        break;
                    default:
                        menu.printValue();
                        break;
                }
            } while (input < 0 || input > 3);
        }
	}
	
	public static void pNetsUserMenu(ArrayList<PetrisNetwork> savedpNets) {
		int input;
		boolean blocker = true;
        while (blocker) {
            menu.pnUserMenu();
            do {
                input = inInt();
                switch (input) {
                    case 0:
                        blocker = false;
                        break;
                    case 1:
                        selectPNet(savedpNets);
                        break;
                    default:
                        menu.printValue();
                        break;
                }
            } while (input < 0 || input > 1);
        }
	}
	
	
	public static void createPNet(ArrayList<PetrisNetwork> pn, ArrayList<Network> n) {
		if(n.isEmpty()) {
			menu.noNetForPetris();
			return;
		}
		menu.selecNEtforPetris();
		menu.print(n);
		int input = select(n);
		if(input == -1)return;
		System.out.println(input);
		createPNet(pn, n.get(input));
		
	}
	
	public static void createPNet(ArrayList<PetrisNetwork> pn, Network n) {
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
		for(int i = 0; i < linksWeight.length; i++) {
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
		
		PetrisNetwork petri = new PetrisNetwork(n, id, name, marking);
		petri.setWeight(linksWeight);
		if(checkIfNetExists(petri, pn)) {
			menu.netAlreadyExists();
			return;
		}
		pn.add(petri);
	}
	
	
	
	public static void selectPNet(ArrayList<PetrisNetwork> pn) {
		int input;
		if(pn.isEmpty()) {
			menu.noPetrisNets();
			return;
		}
		
        int pnSize = pn.size();
        menu.print(pn);

        do {
        	input = -1;
            input = input + inInt();
            if (input < 0 || input > pnSize-1) menu.printValue();
            else use(pn.get(input));
        } while (input < 0 || input > pnSize-1);
		
		
	}
	
	public static void use(PetrisNetwork pn) {
		PetrisNetSimulator pns = new PetrisNetSimulator(); 
		menu.printNetStructure(pn);
		menu.printPetriNetMarking(pn);
		
		pns.simulate(pn);
		
	}
	
	public static Network convertToNet(PetrisNetwork pn, int id) {
		int[][] matrixIn = changeNonNullMatrixEnrtriesToOne(pn.getMatrixIn());
		int[][] matrixOut = changeNonNullMatrixEnrtriesToOne(pn.getMatrixOut());
		
		return new Network(id, pn.getName(), matrixIn, matrixOut);
	}
	
	public static ArrayList<PetrisNetwork> savePnets(String fileName , ArrayList<PetrisNetwork> pnets, ArrayList<PetrisNetwork> savedpNets) {
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
        
        savedpNets = savingNets;
        sortNetId(savedpNets);
        DataPetriSaver saver = new DataPetriSaver(savedpNets, fileName);
        saver.writeFile();
        return savedpNets;
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
	
	private static boolean checkIfNetExists(Network net, ArrayList<? extends Network> nets) {
        for (Network n : nets)
            if (net.equals(n)) return true;
        return false;
    }
	
}
