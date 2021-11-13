package Models;

import static Utils.InputManager.inInt;

import java.util.ArrayList;
import java.util.Arrays;

import Utils.Menu;

public class PetrisNetSimulator {

	Menu menu;
	
	public PetrisNetSimulator() {
		menu = new Menu();
	}
	
	public void simulate(PetrisNetwork pn) {
		int[] marking = Arrays.copyOf(pn.getMarking(), pn.getMarking().length);
		int input;
		boolean stop = false;
		
		do {
			menu.simulatorMenu();
			input = inInt();
			if(input == 1) {
				int idT = selectTransition(pn, marking);
				if(idT == -1) {
					menu.noEnableTransitions();
					return;
				}
				iteration(pn, idT, marking);
				menu.printNetStructure(pn);
				menu.printPetriNetMarking(pn, marking);
			}
			else if(input == 0)stop = true;
			else menu.printValue();
		}while(!stop);
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
			menu.print(enableTransitions);
			menu.onlyOneEnableTransition();
			return enableTransitions.get(0).getId();
		}
		else if(enableTransitions.isEmpty())return -1;
		else {
			menu.selTrantionForFire();
			menu.print(enableTransitions);
			do {
				input = -1;
                input = input + inInt();
                if (input < 0 || input > enableTransitions.size()-1) menu.printValue();
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
			menu.simulatorMenu();
			input = inInt();
			if(input == 1) {
				int idT = selectTransition(pnp, marking);
				if(idT == -1) {
					menu.noEnableTransitions();
					return;
				}
				iteration(pnp, idT, marking);
				menu.printNetStructure(pnp);
				menu.printPetriNetMarking(pnp, marking);
				menu.printTransitionPriority(pnp, pnp.getPriority());
			}
			else if(input == 0)stop = true;
			else menu.printValue();
		}while(!stop);
	}
	
	public int selectTransition(PriorityPetrisNetwork pnp, int[] marking) {
		ArrayList<Transition> enableTransitions = findEnableTransitions(pnp, marking);
		int input;
		
		if(enableTransitions.size() == 1) {
			menu.print(enableTransitions);
			menu.onlyOneEnableTransition();
			return enableTransitions.get(0).getId();
		}
		else if(enableTransitions.isEmpty())return -1;
		else {
			menu.selTrantionForFire();
			menu.print(enableTransitions);
			do {
				input = -1;
                input = input + inInt();
                if (input < 0 || input > enableTransitions.size()-1) menu.printValue();
            } while (input < 0 || input > enableTransitions.size()-1);
			return enableTransitions.get(input).getId();
		}
		
	}
	
	
}
