package Models;

import Utils.*;
import Utils.Menu;

import java.util.*;
import java.util.List;

public class NetworksManager {

    public static class NetworkManager {

        static List<Network> nets = new ArrayList<>();
        static List<Network> savedNets = new ArrayList<>();
        static Network activeNetwork;
        static Scanner in = new Scanner(System.in);
        static String fileName = "Networks";
        Menu menu;

        private NetworkManager(){
            this.menu = new Menu();
        }


        public static void main(String[] args) {

            NetworkManager manager = new NetworkManager();

            manager.mainMenu();
        }

        public void selectNet() {

            int input = -1;
            int netsSize = nets.size();

            menu.selectNets(nets);

            do {
                input = input + in.nextInt();
                if (input == -1) return;
                else if (input == netsSize) createNet();
            } while (input < 0 || input > netsSize + 1);

        }

        private void mainMenu() {

            int input = -1;
            boolean blocker = true;

            while (blocker) {
                menu.mainMenu();
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

        public void saveLoad() {

            int input = -1;
            boolean blocker = true;

            while (blocker) {
                menu.saveLoad();
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
                            menu.printValue();
                            break;
                    }
                } while (input < 0 || input > 2);
            }

        }

        private static void load() {

            DataLoader loader = new DataLoader(fileName);
            nets = loader.readFile();
        }

        private void save(String fileName) {
            List<Network> savableNets = nets;
            List<Network> savingNets = new ArrayList<>();
            int netsSize;
            boolean stop = false;
            int input;
            do {
                netsSize = savableNets.size();
                if(savableNets.isEmpty()) break;
               menu.selectNets(savableNets);
                do {
                    input = -1;
                    if(netsSize == 0) break;
                    input = input + in.nextInt();
                    if (input == -1) return;
                } while (input < 0 || input > netsSize);

                savingNets.add(savableNets.get(input));
                savableNets.remove(input);
                menu.save();
                do {
                    input = in.nextInt();
                    if(input == 1) stop = true;
                } while (input < 0 || input > 2);

            } while (!stop);

            savedNets = savingNets;

            DataSaver saver = new DataSaver(savedNets,fileName);
            saver.writeFile();

        }

        public static void modifyNet(Network network){

        }

        public void createNet() {

            int input = -1;

            Network network = new Network(nets.size());
            activeNetwork = network;
            boolean blocker = true;

            while (blocker) {
                activeNetwork.printNet();
                menu.createNet();
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
                            menu.printValue();
                            break;
                    }
                } while (input < 0 || input > 2);
            }

            activeNetwork.generateMatrix();

            nets.add(activeNetwork);

        }

        public void addPlace() {
            String ingoing;
            boolean ingoingbool;
            int input = -1;
            List<Transition> transitions = activeNetwork.getTransitions();
            int transSize = transitions.size();
            menu.selectTransitions(transitions);
            do {
                input = input + in.nextInt();
                if (input == -1) return;
            } while (input < 0 || input > transSize);
            menu.placeInGoing();
            do {
                ingoing = in.next();
                ingoing = ingoing.toLowerCase();
                if (ingoing.equals("y")) {
                    ingoingbool = true;
                    break;
                } else if (ingoing.equals("n")) {
                    ingoingbool = false;
                    break;
                } else menu.yesNo();
            } while (true);

            activeNetwork.addPlace(activeNetwork.getPlaces().size(), activeNetwork.getTransitions().get(input), ingoingbool);

        }

        public void addTransition() {
            int ingoing = -1;
            int outgoing = -1;
            List<Place> places = activeNetwork.getPlaces();
            int placeSize = places.size();
            menu.selectPlaces(places);
            do {
                ingoing = ingoing + in.nextInt();
                if (ingoing == -1) return;
            } while (ingoing < 0 || ingoing > placeSize);
            menu.selectPlaces(places);
            do {
                outgoing = outgoing + in.nextInt();
                if (outgoing == -1) return;
            } while (outgoing < 0 || outgoing > placeSize);

            activeNetwork.addTransition(activeNetwork.getTransitions().size(), activeNetwork.getPlaces().get(ingoing), activeNetwork.getPlaces().get(outgoing));

        }

    }
}
