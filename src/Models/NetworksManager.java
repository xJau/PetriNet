package Models;

import Utils.*;
import Utils.Menu;

import static Utils.InputManager.*;

import java.util.*;
import java.util.List;


public class NetworksManager {

    public static class NetworkManager {

    	static DataLoader loader;
        static ArrayList<Network> nets = new ArrayList<>();
        static ArrayList<Network> savedNets = new ArrayList<>();
        static Network activeNetwork;
        static Scanner in = new Scanner(System.in);
        static String fileName = "Networks";
        Menu menu;

        private NetworkManager() {
            this.menu = new Menu();
            loader = new DataLoader(fileName);
            load();
        }


        public static void main(String[] args) {
            NetworkManager manager = new NetworkManager();
            manager.mainMenu();
        }

        private void mainMenu() {

            int input = -1;
            boolean blocker = true;
            while (blocker) {
                menu.mainMenu();
                do {
                    input = inInt();
                    switch (input) {
                        case 0:
                            blocker = false;
                            break;
                        case 1:
                            selectNet();
                            break;
                        case 2:
                            save(fileName);
                            break;
                        default:
                            menu.printValue();
                            break;
                    }
                } while (input < 0 || input > 2);
            }
        }

        public void selectNet() {

            int input = -1;
            if (nets.isEmpty()) nets.add(new Network(0, "Rete zero"));
            int netsSize = nets.size();

            menu.selectNets(nets);

            do {
                input = input + inInt();
                if (input == -1) return;
                else if (input == -2) menu.printValue();
                else if (input == netsSize) createNet();
                else modifyNet(nets.get(input));
            } while (input < 0 || input > netsSize + 1);
        }

        private static void load() {
        	
            nets = loader.readFile();
        }

        public int select(List<? extends Identificable> id) {

            int input;
            if (id.size() == 0) return -2;
            do {
                input = -1;
                input = input + inInt();
                if (input == -2) menu.printValue();
            } while (input < -1 || input > id.size());
            return input;
        }

        private void save(String fileName) {
            ArrayList<Network> savableNets = new ArrayList<>();
            ArrayList<Network> savingNets = new ArrayList<>();
            savableNets.addAll(nets);
            boolean stop = false;
            int input = -1;
//            menu.avvisoPerditaDati();
            do {
                if (savableNets.isEmpty()) {
                    menu.noNet();
                    break;
                }
                menu.selectNetsToSave(savableNets);
                if (input == 0) return;
                input = select(savableNets);
                if (input == -2) {
                    menu.noNet();
                    return;
                }
                if (input == -1) return;
                savingNets.add(savableNets.get(input));
                savableNets.remove(input);
                menu.save();
                do {
                    input = inInt();
                    if (input == 1) stop = true;
                    else if (input == -1) menu.printValue();
                } while (input < 1 || input > 2);

            } while (!stop);
            
            savedNets = savingNets;
            sortNetId(savedNets);
            DataSaver saver = new DataSaver(savedNets, fileName);
            saver.writeFile();
//            load();
            
            /**
             * savedNets = savingNets;
             * DataSaver saver = new DataSaver(savedNets, fileName);
             * saver.writeFile();
             */
        }
        
        
        public void sortNetId(List <Network> n) {
        	for(int i = 0; i<n.size(); i++) {
        		n.get(i).setId(i);
        	}
        }

        public void modifyNet(Network network) {

            activeNetwork = network;
            modifyNet();
        }

        public void modifyNet() {

            int input = -1;
            boolean blocker = true;
            while (blocker) {
                do {
                    activeNetwork.printNet();
                    menu.doToNet();
                    input = inInt();
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
                        case 3:
                            addLink();
                            break;
                        case 4:
                        	setNetName();
                        	break;
                        default:
                            menu.printValue();
                            break;
                    }
                } while (input < 0 || input > 3);
            }
            activeNetwork.generateMatrix();
            if (checkIfNetExists(activeNetwork.getId())) {
                menu.netAlreadyExists();
                modifyNet();
            }
        }

        public void createNet() {

            Network network = new Network(nets.size(), "default");
            activeNetwork = network;
            nets.add(activeNetwork);
            setNetName();
            modifyNet();

        }

        public void addPlace() {
            String ingoing;
            boolean ingoingbool;
            int input = -1;
            ArrayList<Transition> transitions = activeNetwork.getTransitions();
            menu.selectTransitions(transitions);
            input = select(transitions);
            if (input == -1) return;
            menu.placeInGoing();
            do {
                ingoing = inString();
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
            String ingoing;
            boolean ingoingbool;
            int input;
            ArrayList<Place> places = activeNetwork.getPlaces();
            menu.selectPlaces(places);
            input = select(places);
            if (input == -1) return;
            menu.transitionInGoing();
            do {
                ingoing = inString();
                ingoing = ingoing.toLowerCase();
                if (ingoing.equals("y")) {
                    ingoingbool = true;
                    break;
                } else if (ingoing.equals("n")) {
                    ingoingbool = false;
                    break;
                } else menu.yesNo();
            } while (true);

            activeNetwork.addTransition(activeNetwork.getTransitions().size(), activeNetwork.getPlaces().get(input), ingoingbool);
        }

        public void addLink() {
            Link l;
            int inputX;
            int inputY;
            ArrayList<Place> places = activeNetwork.getPlaces();
            ArrayList<Transition> transitions = activeNetwork.getTransitions();
            menu.selezionaPoT();
            do {
                String s = inString();
                if (s.equals("p")) {
                    menu.selectPlaces(places);
                    inputX = select(places);
                    if (inputX == -1) return;
                    menu.selectTransitions(transitions);
                    inputY = select(transitions);
                    if (inputY == -1) return;
                    l = new Link(places.get(inputX), transitions.get(inputY));
                    break;
                } else if (s.equals("t")) {
                    menu.selectTransitions(transitions);
                    inputX = select(transitions);
                    if (inputX == -1) return;
                    menu.selectPlaces(places);
                    inputY = select(places);
                    if (inputY == -1) return;
                    l = new Link(transitions.get(inputX), places.get(inputY));
                    break;
                } else {
                    menu.printValue();
                }
            } while (true);

            if (activeNetwork.checkLinkExist(l)) menu.linkEsiste();
            else {
                activeNetwork.getLinks().add(l);
            }
        }

        public boolean checkIfNetExists(int i) {
            for (Network n : nets)
                if (activeNetwork.equals(n) && n.getId() != i) return true;
            return false;
        }
        
        public void setNetName() {
        	String nuovoNome;
        	menu.insNuovoNome();
        	do {
        		nuovoNome = inString();
        		if(!nuovoNome.toLowerCase().equals("name"))break;
        		menu.printValue();
        	}while(true);
        	activeNetwork.setName(nuovoNome);
        }
        
    }
}
