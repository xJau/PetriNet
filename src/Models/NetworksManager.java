package Models;

import Utils.*;
import Utils.Menu;

import static Utils.InputManager.*;
import static Models.PetrisNetworksManager.*;
import static Models.PriorityPetrisNetworkManager.*;

import java.util.*;


public class NetworksManager {

    public static class NetworkManager {

    	static DataLoader loader;
        static ArrayList<Network> nets = new ArrayList<>();
        static ArrayList<PetrisNetwork> pnets = new ArrayList<>();
        static ArrayList<PriorityPetrisNetwork> pnpnets = new ArrayList<>();
        static ArrayList<Network> savedNets;
        static ArrayList<PetrisNetwork> savedpNets;
        static ArrayList<PriorityPetrisNetwork> savedpnpNets;
        static Network activeNetwork;
        static String fileName = "Networks";
        static String pfileName = "Petri_Networks";
        static String pnpfileName = "Priority_Petri_Networks";
        
        Menu menu;

        private NetworkManager() {
            this.menu = new Menu();
            load();
            savedNets = new ArrayList<>(nets);
            savedpNets = new ArrayList<>(pnets);
            savedpnpNets = new ArrayList<>(pnpnets);
            
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
                        case 3:
                        	pNetsMenu(pnets, savedNets, savedpNets);
                        	break;
                        case 4:
                        	savePetrisNetworks();
                        	break;
                        case 5:
                        	pnpMenu(pnpnets, savedpNets, savedpnpNets);
                        	break;
                        case 6:
                        	savePriorityPetrisNetwork();
                        	break;
                        default:
                            menu.printValue();
                            break;
                    }
                } while (input < 0 || input > 6);
            }
        }


		public void selectNet() {

            int input;
            if (nets.isEmpty()) nets.add(new Network(0, "Rete zero"));
            int netsSize = nets.size();

            menu.selectNets(nets);

            do {
            	input = -1;
                input = input + inInt();
                if (input == -1) return;
                else if (input < 0 || input > netsSize) menu.printValue();
                else if (input == netsSize) createNet();
                else modifyNet(nets.get(input));
            } while (input < 0 || input > netsSize);
        }

        private static void load() {
        	
        	loader = new DataLoader(fileName);
            nets = loader.readFile();
            loader = new DataLoader(pfileName);
            pnets = loader.readPetrisFile();
            loader = new DataLoader(pnpfileName);
            pnpnets = loader.readPriorityPetrisFile();
            
        }

        public int select(List<? extends Identificable> id) {

            int input;
            if (id.size() == 0) return -2;
            do {
                input = -1;
                input = input + inInt();
                if (input == -2 || input > id.size()-1) menu.printValue();
            } while (input < -1 || input > id.size()-1);
            return input;
        }

        private void save(String fileName) {
            ArrayList<Network> savableNets = new ArrayList<>();
            ArrayList<Network> savingNets = new ArrayList<>();
            savableNets.addAll(nets);
            boolean stop = false;
            int input = -1;
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
                    else if (input < 1 || input > 2) menu.printValue();
                } while (input < 1 || input > 2);

            } while (!stop);
            
            savedNets = savingNets;
            sortNetId(savedNets);
            DataSaver saver = new DataSaver(savedNets, fileName);
            saver.writeFile();
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
                    menu.printNetStructure(activeNetwork);
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
                } while (input < 0 || input > 4);
            }
            activeNetwork.generateMatrix();
            if (checkIfNetExists(activeNetwork.getId(), activeNetwork, nets)) {
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
       
        public boolean checkIfNetExists(int i, Network net, ArrayList<? extends Network> nets) {
            for (Network n : nets)
                if (net.equals(n) && n.getId() != i) return true;
            return false;
        }
        
        public void setNetName() {
        	String nuovoNome;
        	menu.insNuovoNome();
        	do {
        		nuovoNome = inString();
        		if(!nuovoNome.toLowerCase().replaceAll("[^a-z]", "").equals("name"))break;
        		menu.printValue();
        	}while(true);
        	activeNetwork.setName(nuovoNome);
        }
        
        public void savePetrisNetworks(){
        	ArrayList<PetrisNetwork> sv = savePnets(pfileName, pnets, savedpNets);
        	if(sv == null)return;
        	savedpNets = sv;
        }
        
        private void savePriorityPetrisNetwork() {
        	ArrayList<PriorityPetrisNetwork> sv = savePnpnets(pnpfileName, pnpnets, savedpnpNets);
        	if(sv == null)return;
        	savedpnpNets = sv;
			
		}
        
    }

}
