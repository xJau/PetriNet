package Models;

import static Utils.InputManager.readInt;
import static Utils.InputManager.inString;
import static Models.Selector.select;

import java.util.ArrayList;
import java.util.Arrays;

import Utils.Menu;

public class PriorityPetrisNetworkManager {
	
	public static void pnpMenu(ArrayList<PriorityPetrisNetwork> pnp, ArrayList<PetrisNetwork> pn, ArrayList<PriorityPetrisNetwork> savedpnpNets, Menu menu) {
        ArrayList<Runnable> actions = new ArrayList<>(Arrays.asList(
			() -> selectPPNet(savedpnpNets, menu),
			() -> menu.println(pnp),
			() -> createPPNet(pnp, pn, menu)
		));
        menu.selectMenu(Menu.PNP_MENU, actions);
	}
	
	private static void createPPNet(ArrayList<PriorityPetrisNetwork> pnp, ArrayList<PetrisNetwork> pn, Menu menu) {
		if(pn.isEmpty()) {
			menu.println(Menu.NO_PRIORITY_NETS_PER_PETRI);
			return;
		}
		menu.println(Menu.SELEZIONA_RETE_DI_PETRI_PER_PETRI_CON_PRIORITA);
		menu.println(pn);
		int input = select(pn, menu);
		if(input == -1)return;
		System.out.println(input);
		createPNet(pnp, pn.get(input), menu);
	}

	private static void createPNet(ArrayList<PriorityPetrisNetwork> pnp, PetrisNetwork pn, Menu menu) {
		String name;
		int id = pnp.size();
		int[] priority = new int[pn.getTransitions().size()];
		
		menu.println(Menu.INSERIRE_PRIORITA_TRANSIZIONI);
		for(int i = 0; i < priority.length; i++) {
			menu.println(pn.getTransitions().get(i).toString());
			priority[i] = readInt();
			if(priority[i]<1) {
				menu.println(Menu.INSERIMENTO_VALIDO);
				i--;
			}
		}
		
		menu.println(Menu.ASSEGNA_NOME_NET);
		do {
			name = inString();
			if(!name.toLowerCase().replaceAll("[^a-z]", "").equals("name"))break;
			menu.println(Menu.INSERIMENTO_VALIDO);
		}while(true);
		
		PriorityPetrisNetwork pPetri = new PriorityPetrisNetwork(pn, id, name, priority);
		if(checkIfNetExists(pPetri, pnp)) {
			menu.println(Menu.NET_ALREADY_EXISTS);
			return;
		}
		pnp.add(pPetri);
		
	}

	public static void selectPPNet(ArrayList<PriorityPetrisNetwork> pnp, Menu menu) {
		int input;
		if(pnp.isEmpty()) {
			menu.println(Menu.NO_PRIORITY_PETRIS_NETS);
			return;
		}
		
        int pnSize = pnp.size();
        menu.println(pnp);

        do {
        	input = -1;
            input = input + readInt();
            if (input < 0 || input > pnSize-1) menu.println(Menu.INSERIMENTO_VALIDO);
            else use(pnp.get(input), menu);
        } while (input < 0 || input > pnSize-1);
	}
	
	private static void use(PriorityPetrisNetwork pnp, Menu menu) {
		PetrisNetSimulator pns = new PetrisNetSimulator(); 
		menu.printNetStructure(pnp);
		menu.printPetriNetMarking(pnp);
		menu.printTransitionPriority(pnp, pnp.getPriority());		
		pns.simulate(pnp, menu);
	}
	
	public static Network convertToPetrisNet(PriorityPetrisNetwork pn, int id) {
		return new PetrisNetwork(id, pn.getName(), pn.getMatrixIn(), pn.getMatrixOut(), pn.getMarking());
	}
	
	private static boolean checkIfNetExists(PriorityPetrisNetwork net, ArrayList<? extends Network> nets) {
        for (Network n : nets)
            if (net.equals(n)) return true;
        return false;
    }	
}
