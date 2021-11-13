package Models;

import Utils.*;
import Utils.Menu;
import static Utils.InputManager.*;

import java.util.*;
import java.util.List;


public class NetworksManager {

    public static class NetworkManager {

        static ArrayList<Network> nets = new ArrayList<>();
        static ArrayList<Network> savedNets = new ArrayList<>();
        static Network activeNetwork;
        static Scanner in = new Scanner(System.in);
        static String fileName = "Networks";
        Menu menu;

        private NetworkManager(){
            this.menu = new Menu();
        }


        public static void main(String[] args) {

            NetworkManager manager = new NetworkManager();

            load();

            manager.mainMenu();
        }

        public void selectNet() {

            int input = -1;
            if (nets.isEmpty()) nets.add(new Network(0));
            int netsSize = nets.size();




            menu.selectNets(nets);

            do {
                input = input + inInt();
                if (input == -1) return;
                else if (input == -2)menu.printValue();
                else if (input == netsSize) createNet();
                else modifyNet(nets.get(input));
            } while (input < 0 || input > netsSize + 1);

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

        /*public void saveLoad() {

            int input = -1;
            boolean blocker = true;

            while (blocker) {
                menu.saveLoad();
                do {
                    input = inInt();
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

        }*/

        private static void load() {

            DataLoader loader = new DataLoader(fileName);
            nets = loader.readFile();
        }
        
        public int select(List<?extends Identificable> id) {
        	int input;
        	do {
                input = -1;
                if(id.size() == 0) break;
                input = input + inInt();
                //if (input == -1) break;
                if(input == -2)menu.printValue();
            } while (input < 0 || input > id.size());
        	return input;
        }
        
        private void save(String fileName) {
            ArrayList<Network> savableNets = nets;
            ArrayList<Network> savingNets = new ArrayList<>();
            //int netsSize;
            boolean stop = false;
            int input=-1;
            do {
               // netsSize = savableNets.size();
                if(savableNets.isEmpty()) {
                    menu.noNet();
                    break;
                }
               menu.selectNetsToSave(savableNets);

                /*do {
                    input = -1;
                    if(netsSize == 0) break;
                    input = input + inInt();
                    if (input == -1) return;
                    else if(input == -2)menu.printValue();
                } while (input < 0 || input > netsSize);*/
               
               input = select(savableNets);
               if(input == -1) {
            	   menu.noNet();
            	   return;      		//messaggio?
               }
               /*if(input == -2) {
            	   menu.noNet();
            	   return;			//messaggio errore?
               }*/
               
                savingNets.add(savableNets.get(input));
                savableNets.remove(input);
                menu.save();
                do {
                    input = inInt();
                    if(input == 1) stop = true;
                    else if(input == -1)menu.printValue();
                } while (input < 1 || input > 2);

            } while (!stop);

            savedNets = savingNets;

            DataSaver saver = new DataSaver(savedNets,fileName);
            saver.writeFile();

        }

        public void modifyNet(Network network){ //da terminare...
        	activeNetwork = network;
        	modifyNet();
        }
        
        public void modifyNet() {
        	
        	int input = -1;
        	boolean blocker = true;

            while (blocker) { //perché questo while?
                activeNetwork.printNet();
                menu.doToNet();
                do {
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
                        default:
                            menu.printValue();
                            break;
                    }
                } while (input < 0 || input > 3);
            }
            activeNetwork.generateMatrix();
            if(checkIfNetExists(activeNetwork.getId())) {
            	menu.netAlreadyExists();
            	modifyNet();
            }
        }

        public void createNet() {

            //int input = -1;

            Network network = new Network(nets.size());
            activeNetwork = network;
            /*boolean blocker = true;

            while (blocker) {
                activeNetwork.printNet();
                menu.doToNet();
                do {
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
                        default:
                            menu.printValue();
                            break;
                    }
                } while (input < 0 || input > 3);
            }*/
            nets.add(activeNetwork);
            modifyNet();

        }

        public void addPlace() {
            String ingoing;
            boolean ingoingbool;
            int input = -1;
            ArrayList<Transition> transitions = activeNetwork.getTransitions();
            //int transSize = transitions.size();
            menu.selectTransitions(transitions);
            /*do {
                input = input + inInt();
                if (input == -1) return;
                else if(input == -2)menu.printValue();
            } while (input < 0 || input > transSize);*/
            input = select(transitions);
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
            int input;
            ArrayList<Place> places = activeNetwork.getPlaces();
            //int placeSize = places.size();
            menu.selectPlaces(places);
            /*do {
                input = input + inInt();
                if (input == -1) return;
            } while (input < 0 || input > placeSize);*/
            input = select(places);
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
        		if(s.equals("p")) {
        			menu.selectPlaces(places);
        			inputX = select(places);
        			menu.selectTransitions(transitions);
        			inputY = select(transitions);
        			l = new Link(places.get(inputX), transitions.get(inputY));
        			break;
        		}
        		else if(s.equals("t")) {
        			menu.selectTransitions(transitions);
        			inputX = select(transitions);
        			menu.selectPlaces(places);
        			inputY = select(places);
        			l = new Link(transitions.get(inputX), places.get(inputY));
        			break;
        		}
        		else {
        			menu.printValue();
        		}
        	}while(true);

        	if(activeNetwork.checkLinkExist(l))menu.linkEsiste();
        	else {
        		activeNetwork.getLinks().add(l);
        	}
        	
        }
        
        public boolean checkIfNetExists(int i){
        	for(Network n: nets)
        		if(activeNetwork.equals(n) && n.getId() != i)return true;
        	return false;
        }

        
    }
}
