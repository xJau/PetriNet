import Models.Network;
import Utils.DataLoader;
import Utils.DataSaver;
import static Utils.MatrixOperation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static List<Network> nets = new ArrayList<>();
    static List<Network> savedNets = new ArrayList<>();
    static Network activeNetwork;
    static Scanner in = new Scanner(System.in);
    static String fileName = "Networks";


    public static void main(String[] args) {
        mainMenu();
    }

    private static void selectNet() {

        int input = -1;
        int netsSize = nets.size();

        for (int i = 0; i < netsSize; i++) {
            System.out.println((i + 1) + ") Network n." + nets.get(i).getId());
        }
        System.out.println((netsSize + 1) + ") For create a new one");
        System.out.println("\n0) For return to the main menu");
        do {
            input = input + in.nextInt();
            if (input == -1) return;
            else if (input == netsSize) createNet();
        } while (input < 0 || input > netsSize + 1);

    }

    private static void mainMenu() {

        int input = -1;
        boolean blocker = true;

        while (blocker) {
            System.out.println("Do you want to Select a Network (1) or Save/Load (2)?");
            do {
                input = in.nextInt();
                switch (input) {
                    case 0:
                        blocker = false;
                        break;
                    case 1:
                        selectNet();
                        break;
                    case 2:
                        saveLoad();
                        break;
                    default:
                        System.out.print("Insert a valid value: ");
                        break;
                }
            } while (input < 0 || input > 2);
        }

    }

    private static void saveLoad() {

        int input = -1;
        boolean blocker = true;

        while (blocker) {
            System.out.println("Load from local file (1) or save networks on file (2)?");
            do {
                input = in.nextInt();
                switch (input) {
                    case 0:
                        return;
                    case 1:
                        load();
                        return;
                    case 2:
                        save(fileName);
                        return;
                    default:
                        System.out.print("Insert a valid value: ");
                        break;
                }
            } while (input < 0 || input > 2);
        }

    }

    private static void load() {

        DataLoader loader = new DataLoader(fileName);
        loader.loadFile();
    }

    private static void save(String fileName) {
        List<Network> savableNets = nets;
        List<Network> savingNets = new ArrayList<>();
        int netsSize;
        boolean stop = false;
        int input;
        do {
            netsSize = savableNets.size();
            if(savableNets.isEmpty()) break;
            for (int i = 0; i < netsSize; i++) {
                System.out.println((i + 1) + ") Network n." + savableNets.get(i).getId());
            }
            System.out.println("\n0) For return to the main menu");
            do {
                input = -1;
                if(netsSize == 0) break;
                input = input + in.nextInt();
                if (input == -1) return;
            } while (input < 0 || input > netsSize);

            savingNets.add(savableNets.get(input));
            savableNets.remove(input);
            System.out.println("(1) For Save (2) For Continue");
            do {
                input = in.nextInt();
                if(input == 1) stop = true;
            } while (input < 0 || input > 2);

        } while (!stop);

        savedNets = savingNets;

        DataSaver saver = new DataSaver(savedNets,fileName);
        saver.writeFile();

    }

    private static void createNet() {

        int input = -1;

        Network network = new Network(nets.size());
        activeNetwork = network;
        boolean blocker = true;

        while (blocker) {
            activeNetwork.printNet();
            System.out.println("Do you want to add a Place(1) or a Transition(2), (0) to quit");
            do {
                input = in.nextInt();
                switch (input) {
                    case 0:
                        blocker = false;
                        break;
                    case 1:
                        addPlace();
                        break;
                    case 2:
                        addTransition();
                        break;
                    default:
                        System.out.print("Insert a valid value: ");
                        break;
                }
            } while (input < 0 || input > 2);
        }

        activeNetwork.generateMatrix();

        nets.add(activeNetwork);

    }

    private static void addPlace() {
        String ingoing;
        boolean ingoingbool;
        int input = -1;
        int transSize = activeNetwork.getTransitions().size();

        System.out.println("Select the transition to connect to the new place");
        for (int i = 0; i < transSize; i++) {
            System.out.println((i + 1) + ") " + activeNetwork.getTransitions().get(i));
        }
        System.out.println("\n0) For end");
        do {
            input = input + in.nextInt();
            if (input == -1) return;
        } while (input < 0 || input > transSize);
        System.out.println("The place is ingoing for the selected transition? (Y/N)");
        do {
            ingoing = in.next();
            ingoing = ingoing.toLowerCase();
            if (ingoing.equals("y")) {
                ingoingbool = true;
                break;
            } else if (ingoing.equals("n")) {
                ingoingbool = false;
                break;
            } else System.out.println("Insert y or n to continue");
        } while (true);

        activeNetwork.addPlace(activeNetwork.getPlaces().size(), activeNetwork.getTransitions().get(input), ingoingbool);

    }

    private static void addTransition() {
        int ingoing = -1;
        int outgoing = -1;
        int placeSize = activeNetwork.getPlaces().size();

        System.out.println("Select the place to connect to the new place");
        for (int i = 0; i < placeSize; i++) {
            System.out.println((i + 1) + ") " + activeNetwork.getPlaces().get(i));
        }
        System.out.println("\n0) For end");
        do {
            ingoing = ingoing + in.nextInt();
            if (ingoing == -1) return;
        } while (ingoing < 0 || ingoing > placeSize);

        for (int i = 0; i < placeSize; i++) {
            System.out.println((i + 1) + ") " + activeNetwork.getPlaces().get(i));
        }
        System.out.println("\n0) For end");
        do {
            outgoing = outgoing + in.nextInt();
            if (outgoing == -1) return;
        } while (outgoing < 0 || outgoing > placeSize);

        activeNetwork.addTransition(activeNetwork.getTransitions().size(), activeNetwork.getPlaces().get(ingoing), activeNetwork.getPlaces().get(outgoing));

    }

}
