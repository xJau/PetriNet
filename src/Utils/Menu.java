package Utils;

import Models.*;
import java.util.List;
import java.util.Scanner;

public class Menu {

    final String NUOVOVALORE = ") Per crearne una nuova";

    Scanner in;

    public Menu(){
        this.in = new Scanner(System.in);
    }


    public void selectNets(List<Network> nets) {

        int netsSize = nets.size();

        printNetwork(nets);
        System.out.println((netsSize + 1) + NUOVOVALORE );
        System.out.println("\n0) For return to the main menu");

    }

    private void printNetwork(List<Network> net) {

        int listSize = net.size();

        for (int i = 0; i < listSize; i++) {
            System.out.println((i + 1) + ") Network n." + net.get(i).getId());
        }
    }
    private void printTransition(List<Transition> transitions) {

        int listSize = transitions.size();

        for (int i = 0; i < listSize; i++) {
            System.out.println((i + 1) + ") Transition n." + transitions.get(i).getId());
        }
    }
    private void printPlaces(List<Place> places) {

        int listSize = places.size();

        for (int i = 0; i < listSize; i++) {
            System.out.println((i + 1) + ") Place n." + places.get(i).getId());
        }
    }

    public void selectPlaces(List<Place> places){

        int placeSize = places.size();

        System.out.println("Select the places to connect to the new transition");
        printPlaces(places);
        System.out.println("\n0) For end");
    }


    public void mainMenu() {
        System.out.println("Do you want to Select a Network (1) or Save/Load (2)?");
    }

    public void saveLoad() {
        System.out.println("Load from local file (1) or save networks on file (2)?");
    }

    public void printValue() {
        System.out.print("Insert a valid value: ");
    }

    public void save() {
        System.out.println("(1) For Save (2) For Continue");
    }

    public void createNet() {
        System.out.println("Do you want to add a Place(1) or a Transition(2), (0) to quit");
    }

    public void selectTransitions(List<Transition> transitions) {

        System.out.println("Select the transition to connect to the new place");
        printTransition(transitions);
        System.out.println("\n0) For end");
    }

    public void placeInGoing() {
        System.out.println("The place is ingoing for the selected transition? (Y/N)");
    }

    public void yesNo(){
        System.out.println("Insert y or n to continue");
    }
}
