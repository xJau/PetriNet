package Models;

import static Utils.InputManager.readInt;

import java.util.ArrayList;
import java.util.Arrays;
import Utils.Menu;

public class PetrisNetSimulator {

	public void simulate(PetrisNetwork pn, Menu menu) {
		int[] marking = Arrays.copyOf(pn.getMarking(), pn.getMarking().length);
		int input;
		do {
			menu.print(Menu.SIMULATORE_MENU);
			input = readInt();
			if(input == 1) {
				int idT = selectTransition(pn, marking, menu);
				if(idT == -1) {
					menu.print(Menu.NESSUNA_TRANSIZIONE_ABILITATA);
					return;
				}
				iteration(pn, idT, marking);
				menu.printNetStructure(pn);
				menu.printPetriNetMarking(pn, marking);
			}
			else menu.print(Menu.INSERIMENTO_VALIDO);
		}while(input != 0);
	}
	

	public void iteration(PetrisNetwork pn, int idT, int[] marking) {
		int[][] matrixIn = pn.getMatrixIn();
		int[][] matrixOut = pn.getMatrixOut();
		
		for(int i = 0; i<matrixIn.length; i++) {
			marking[i] = marking[i] - matrixIn[i][idT];
			marking[i] = marking[i] + matrixOut[i][idT];
		}
		
	}
	
	public int selectTransition(PetrisNetwork pn, int[] marking, Menu menu) {
		ArrayList<Transition> enableTransitions = findEnableTransitions(pn, marking);
		int input;
		
		if(enableTransitions.size() == 1) {
			menu.print(enableTransitions);
			menu.print(Menu.UNA_SOLA_TRANSIZIONE_ABILITATA);
			return enableTransitions.get(0).getId();
		}
		else if(enableTransitions.isEmpty())return -1;
		else {
			menu.print(Menu.SELEZIONA_TRANSIZIONE_PER_SCATTO);
			menu.print(enableTransitions);
			do {
				input = -1;
                input = input + readInt();
                if (input < 0 || input > enableTransitions.size()-1) menu.print(Menu.INSERIMENTO_VALIDO);
            } while (input < 0 || input > enableTransitions.size()-1);
			return enableTransitions.get(input).getId();
		}
	}
	
	public ArrayList<Transition> findEnableTransitions(PetrisNetwork pn, int[] marking) {
		int[][] matrixIn = pn.getMatrixIn();
		ArrayList<Transition> enableTransitions = new ArrayList<>();
		boolean check;
		for(int i = 0; i<matrixIn[0].length; i++) {
			check = true;
			for(int j = 0; j<matrixIn.length; j++) {
				if(matrixIn[j][i] > marking[j]) {
					check = false;
					break;
				}
			}
			if(check)enableTransitions.add(pn.getTransitions().get(i));
		}
		return enableTransitions;
	}
	
	public ArrayList<Transition> findEnableTransitions(PriorityPetrisNetwork pnp, int[] marking){
		ArrayList<Transition> enableTransitions = findEnableTransitions((PetrisNetwork) pnp, marking);
		ArrayList<Transition> temp = new ArrayList<>();
		int[] priority = pnp.getPriority();
		int max = enableTransitions.get(0).getId();
		for(int i = 0; i<enableTransitions.size(); i++) {
			if(priority[enableTransitions.get(i).getId()] > priority[max])max = enableTransitions.get(i).getId();
		}
		max = priority[max];
		for(int i = 0; i<enableTransitions.size(); i++)
			if(priority[enableTransitions.get(i).getId()] == max)temp.add(enableTransitions.get(i));
		enableTransitions = temp;
		
		return enableTransitions;
	}
	
	public void simulate(PriorityPetrisNetwork pnp, Menu menu) {
		int[] marking = Arrays.copyOf(pnp.getMarking(), pnp.getMarking().length);
		int input;
		boolean stop = false;
		
		do {
			menu.print(Menu.SIMULATORE_MENU);
			input = readInt();
			if(input == 1) {
				int idT = selectTransition(pnp, marking, menu);
				if(idT == -1) {
					menu.print(Menu.NESSUNA_TRANSIZIONE_ABILITATA);
					return;
				}
				iteration(pnp, idT, marking);
				menu.printNetStructure(pnp);
				menu.printPetriNetMarking(pnp, marking);
				menu.printTransitionPriority(pnp, pnp.getPriority());
			}
			else if(input == 0)stop = true;
			else menu.print(Menu.INSERIMENTO_VALIDO);
		}while(!stop);
	}
	
	public int selectTransition(PriorityPetrisNetwork pnp, int[] marking, Menu menu) {
		ArrayList<Transition> enableTransitions = findEnableTransitions(pnp, marking);
		int input;
		
		if(enableTransitions.size() == 1) {
			menu.print(enableTransitions);
			menu.print(Menu.UNA_SOLA_TRANSIZIONE_ABILITATA);
			return enableTransitions.get(0).getId();
		}
		else if(enableTransitions.isEmpty()) return -1;
		else {
			menu.print(Menu.SELEZIONA_TRANSIZIONE_PER_SCATTO);
			menu.print(enableTransitions);
			do {
				input = -1;
                input = input + readInt();
                if (input < 0 || input > enableTransitions.size()-1) menu.print(Menu.INSERIMENTO_VALIDO);
            } while (input < 0 || input > enableTransitions.size()-1);
			return enableTransitions.get(input).getId();
		}
	}	
}
