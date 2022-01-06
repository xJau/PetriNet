package Models;

import static Utils.InputManager.readInt;

import java.util.ArrayList;
import java.util.Arrays;
import Utils.Menu;

public class PetrisNetSimulator {

	public void simulate(PetrisNetwork pn) {
		int[] marking = Arrays.copyOf(pn.getMarking(), pn.getMarking().length);
		int input;
		do {
			Menu.print(Menu.SIMULATORE_MENU);
			input = readInt();
			if(input == 1) {
				int idT = selectTransition(pn, marking);
				if(idT == -1) {
					Menu.print(Menu.NESSUNA_TRANSIZIONE_ABILITATA);
					return;
				}
				iteration(pn, idT, marking);
				Menu.printNetStructure(pn);
				Menu.printPetriNetMarking(pn, marking);
			}
			else Menu.print(Menu.INSERIMENTO_VALIDO);
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
	
	public int selectTransition(PetrisNetwork pn, int[] marking) {
		ArrayList<Transition> enableTransitions = findEnableTransitions(pn, marking);
		int input;
		
		if(enableTransitions.size() == 1) {
			Menu.print(enableTransitions);
			Menu.print(Menu.UNA_SOLA_TRANSIZIONE_ABILITATA);
			return enableTransitions.get(0).getId();
		}
		else if(enableTransitions.isEmpty())return -1;
		else {
			Menu.print(Menu.SELEZIONA_TRANSIZIONE_PER_SCATTO);
			Menu.print(enableTransitions);
			do {
				input = -1;
                input = input + readInt();
                if (input < 0 || input > enableTransitions.size()-1) Menu.print(Menu.INSERIMENTO_VALIDO);
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
	
	public void simulate(PriorityPetrisNetwork pnp) {
		int[] marking = Arrays.copyOf(pnp.getMarking(), pnp.getMarking().length);
		int input;
		boolean stop = false;
		
		do {
			Menu.print(Menu.SIMULATORE_MENU);
			input = readInt();
			if(input == 1) {
				int idT = selectTransition(pnp, marking);
				if(idT == -1) {
					Menu.print(Menu.NESSUNA_TRANSIZIONE_ABILITATA);
					return;
				}
				iteration(pnp, idT, marking);
				Menu.printNetStructure(pnp);
				Menu.printPetriNetMarking(pnp, marking);
				Menu.printTransitionPriority(pnp, pnp.getPriority());
			}
			else if(input == 0)stop = true;
			else Menu.print(Menu.INSERIMENTO_VALIDO);
		}while(!stop);
	}
	
	public int selectTransition(PriorityPetrisNetwork pnp, int[] marking) {
		ArrayList<Transition> enableTransitions = findEnableTransitions(pnp, marking);
		int input;
		
		if(enableTransitions.size() == 1) {
			Menu.print(enableTransitions);
			Menu.print(Menu.UNA_SOLA_TRANSIZIONE_ABILITATA);
			return enableTransitions.get(0).getId();
		}
		else if(enableTransitions.isEmpty()) return -1;
		else {
			Menu.print(Menu.SELEZIONA_TRANSIZIONE_PER_SCATTO);
			Menu.print(enableTransitions);
			do {
				input = -1;
                input = input + readInt();
                if (input < 0 || input > enableTransitions.size()-1) Menu.print(Menu.INSERIMENTO_VALIDO);
            } while (input < 0 || input > enableTransitions.size()-1);
			return enableTransitions.get(input).getId();
		}
	}	
}
