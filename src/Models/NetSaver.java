package Models;

import static Models.Selector.select;
import static Utils.InputManager.readInt;

import java.util.ArrayList;
import java.util.List;

import Utils.DataPetriSaver;
import Utils.DataPriorityPetriSaver;
import Utils.DataSaver;
import Utils.Menu;

public class NetSaver {
	
	protected static ArrayList<Network> save(String fileName, ArrayList<Network> nets, ArrayList<Network> savedNets) {
        ArrayList<Network> savableNets = new ArrayList<>();
        ArrayList<Network> savingNets = new ArrayList<>();
        savableNets.addAll(nets);
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
        
        savedNets = savingNets;
        sortNetId(savedNets);
        DataSaver saver = new DataSaver(savedNets, fileName);
        saver.writeFile();
        return savedNets;
    }
	
	protected static ArrayList<PetrisNetwork> savePnets(String fileName , ArrayList<PetrisNetwork> pnets, ArrayList<PetrisNetwork> savedpNets) {
        ArrayList<PetrisNetwork> savableNets = new ArrayList<>();
        ArrayList<PetrisNetwork> savingNets = new ArrayList<>();
        savableNets.addAll(pnets);
        boolean stop = false;
        int input = -1;
        do {
            if (savableNets.isEmpty()) {
                Menu.print(Menu.NO_RETI);
                break;
            }
            if (input == 0) return null;
			
			Menu.selectNetsToSave(savableNets);
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
        
        savedpNets = savingNets;
        sortNetId(savedpNets);
        DataPetriSaver saver = new DataPetriSaver(savedpNets, fileName);
        saver.writeFile();
        return savedpNets;
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
	
}
