package Models;

import Utils.*;
import Utils.Menu;
import static Utils.InputUtils.*;

import java.util.*;
import java.util.List;

public class NetworksManager {

    public static class NetworkManager {

        static List<Network> nets = new ArrayList<>();
        static List<Network> savedNets = new ArrayList<>();
        static Network activeNetwork;
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
                input = input + inInteger();
                if (input == -1) return;
                else if(input == -2)menu.printValue();
                else if (input == netsSize) createNet();
            } while (input < 0 || input > netsSize + 1);

        }

        private void mainMenu() {

            int input = -1;
            boolean blocker = true;

            while (blocker) {
                menu.mainMenu();
                do {
                    input = inInteger();
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
                        	menu.printValue();
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
                    input = inInteger();
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
                    input = input + inInteger();
                    if (input == -1) return;
                    else if(input == -2)menu.printValue();
                } while (input < 0 || input > netsSize);

                savingNets.add(savableNets.get(input));
                savableNets.remove(input);
                menu.save();
                do {
                    input = inInteger();
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

            int input;

            Network network = new Network(nets.size());
            activeNetwork = network;
            boolean blocker = true;

            while (blocker) {
                activeNetwork.printNet();
                menu.manage();
                do {
                    input = inInteger();
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
        
        public void manageNet(){
        	int input;
            boolean blocker = true;
            while (blocker) {
                activeNetwork.printNet();
                menu.manage();
                do {
                    input = inInteger();
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
                        default:
                            menu.printValue();
                            break;
                    }
                } while (input < 0 || input > 2);
            }

            activeNetwork.generateMatrix();
        
        }

        public void addPlace() {
            String ingoing;
            boolean ingoingbool;
            int input = -1;
            List<Transition> transitions = activeNetwork.getTransitions();
            int transSize = transitions.size();
            menu.selectTransitions(transitions);
            do {
                input = input + inInteger();
                if (input == -1) return;
                if(input == -2)menu.printValue();
            } while (input < 0 || input > transSize);
            menu.placeInGoing();
            ingoing = inYorN();
            if (ingoing.equals("y")) {
            	ingoingbool = true;
            } 
            else {
            	ingoingbool = false;
            }

            activeNetwork.addPlace(activeNetwork.getPlaces().size(), activeNetwork.getTransitions().get(input), ingoingbool);

        }

        /*public void addTransition() {
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

        }*/
        
        public void addTransition() {
        	String ingoing;
            boolean ingoingbool;
            int input =-1;
            List<Place> places = activeNetwork.getPlaces();
            int placeSize = places.size();
            menu.selectPlaces(places);
            do {
                input = input + inInteger();
                if (input == -1) return;
                if(input == -2)menu.printValue();
            } while (input < 0 || input > placeSize);
            menu.transitionInGoing();
            ingoing = inYorN();
            if (ingoing.equals("y")) {
            	ingoingbool = true;
            } 
            else {
            	ingoingbool = false;
            }	

            activeNetwork.addTransition(activeNetwork.getTransitions().size(), activeNetwork.getPlaces().get(input), ingoingbool);
        }
        
        public void addLink() {
        	int input;
        	int placeSize = activeNetwork.getPlaces().size();
        	int transSize = activeNetwork.getTransitions().size();
        	boolean check, ingoingbool;
        	
        	menu.Posto_Transizione();
        	do {
        		check = true;
        		input = inInteger();
        		if(input == 1) {
        			menu.selectPlaces(activeNetwork.getPlaces());
                    do {
                    	input = -1;
                        input = input + inInteger();
                        if (input == -1) return;
                        if(input == -2)menu.printValue();
                    } while (input < 0 || input > placeSize);
                    int i = input;
                    menu.selectTransitions(activeNetwork.getTransitions());
                    do {
                    	input = -1;
                        input = input + inInteger();
                        if (input == -1) return;
                        if(input == -2)menu.printValue();
                    } while (input < 0 || input > transSize);
                    menu.transitionInGoing();
                    String ingoing = inYorN();
                    if (ingoing.equals("y")) {
                    	Link l = new Link(activeNetwork.getTransitions().get(input), activeNetwork.getPlaces().get(i));
                    	activeNetwork.connect(activeNetwork.getTransitions().get(input), activeNetwork.getPlaces().get(i));
                    } 
                    else {
                    	Link l = new Link(activeNetwork.getPlaces().get(i), activeNetwork.getTransitions().get(input));
                    	for()
                    	activeNetwork.connect(activeNetwork.getPlaces().get(i), activeNetwork.getTransitions().get(input));
                    }        
        		}
        		else if(input == 2) {
        			menu.selectTransitions(activeNetwork.getTransitions());
        			do {
                    	input = -1;
                        input = input + inInteger();
                        if (input == -1) return;
                        if(input == -2)menu.printValue();
                    } while (input < 0 || input > transSize);
                    int i = input;
                    menu.selectPlaces(activeNetwork.getPlaces());
                    do {
                    	input = -1;
                        input = input + inInteger();
                        if (input == -1) return;
                        if(input == -2)menu.printValue();
                    } while (input < 0 || input > placeSize);
                    menu.placeInGoing();
                    String ingoing = inYorN();
                    if (ingoing.equals("y")) {
                    	Link l = new Link(activeNetwork.getPlaces().get(i), activeNetwork.getTransitions().get(input));
                    	activeNetwork.connect(activeNetwork.getPlaces().get(i), activeNetwork.getTransitions().get(input));
                    	
                    } 
                    else {
                    	Link l = new Link(activeNetwork.getTransitions().get(input), activeNetwork.getPlaces().get(i));
                    	activeNetwork.connect(activeNetwork.getTransitions().get(input), activeNetwork.getPlaces().get(i));
                    } 
        		}
        		else {
        				menu.printValue();
        				check = false;
        			}
        	}while(!check);	
        }
        
        

    }
}
