package Models;

import static Utils.InputManager.*;
import static Models.Selector.select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Utils.DataPetriSaver;
import Utils.Menu;
import static Utils.MatrixOperation.*;

public class PetrisNetworksManager {	
	public static void pNetsMenu(ArrayList<PetrisNetwork> pn, ArrayList<Network> n, ArrayList<PetrisNetwork> savedpNets, Menu menu) {
		ArrayList<Runnable> actions = new ArrayList<>(Arrays.asList(
			() -> selectPNet(savedpNets, menu),
			() -> menu.print(pn),
			() -> createPNet(pn, n, menu)
		));
		menu.selectMenu(Menu.PN_MENU, actions);
	}
	
	public static void createPNet(ArrayList<PetrisNetwork> pn, ArrayList<Network> n, Menu menu) {
		if(n.isEmpty()) {
			menu.print(Menu.NO_NETS_PER_PETRI);
			return;
		}
		menu.print(Menu.SELEZIONA_RETE_PER_PETRI);
		menu.print(n);
		int input = select(n, menu);
		if(input == -1) return;
		System.out.println(input);
		createPNet(pn, n.get(input), menu);
		
	}
	
	public static void createPNet(ArrayList<PetrisNetwork> pn, Network n, Menu menu) {
		String name;
		int id = pn.size();
		int[] marking = new int[n.getPlaces().size()];
		int[] linksWeight = new int[n.getLinks().size()];
		
		menu.print(Menu.INSERIRE_MARCATURA_INIZIALE);
		for(int i = 0; i < marking.length; i++) {
			menu.print(n.getPlaces().get(i).toString());
			marking[i] = readInt();
			if(marking[i]<0) {
				menu.print(Menu.INSERIMENTO_VALIDO);
				i--;
			}
		}
		
		menu.print(Menu.INSERIRE_PESI_LINK);
		for(int i = 0; i < linksWeight.length; i++) {
			menu.print(n.getLinks().get(i).toString());
			linksWeight[i] = readInt();
			if(linksWeight[i]<1) {
				menu.print(Menu.INSERIMENTO_VALIDO);
				i--;
			}
		}
		
		
		menu.print(Menu.ASSEGNA_NOME_NET);
		do {
			name = inString();
			if(!name.toLowerCase().replaceAll("[^a-z]", "").equals("name")) break;
			menu.print(Menu.INSERIMENTO_VALIDO);
		}while(true);
		
		PetrisNetwork petri = new PetrisNetwork(n, id, name, marking);
		petri.setWeight(linksWeight);
		if(checkIfNetExists(petri, pn)) {
			menu.print(Menu.NET_ALREADY_EXISTS);
			return;
		}
		pn.add(petri);
	}
	
	
	
	public static void selectPNet(ArrayList<PetrisNetwork> pn, Menu menu) {
		int input;
		if(pn.isEmpty()) {
			menu.print(Menu.NO_PETRIS_NETS);
			return;
		}
		
        int pnSize = pn.size();
        menu.print(pn);

        do {
        	input = -1;
            input = input + readInt();
            if (input < 0 || input > pnSize-1) menu.print(Menu.INSERIMENTO_VALIDO);
            else use(pn.get(input), menu);
        } while (input < 0 || input > pnSize-1);	
	}
	
	public static void use(PetrisNetwork pn, Menu menu) {
		PetrisNetSimulator pns = new PetrisNetSimulator(); 
		menu.printNetStructure(pn);
		menu.printPetriNetMarking(pn);
		
		pns.simulate(pn, menu);	
	}
	
	public static Network convertToNet(PetrisNetwork pn, int id) {
		int[][] matrixIn = changeNonNullMatrixEnrtriesToOne(pn.getMatrixIn());
		int[][] matrixOut = changeNonNullMatrixEnrtriesToOne(pn.getMatrixOut());
		
		return new Network(id, pn.getName(), matrixIn, matrixOut);
	}
	
	private static boolean checkIfNetExists(Network net, ArrayList<? extends Network> nets) {
        for (Network n : nets)
            if (net.equals(n)) return true;
        return false;
    }
	
}
