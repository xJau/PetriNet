package Models;

import Utils.*;
import Utils.Menu;
import View.Console;

import static Utils.InputManager.*;
import static Models.PetrisNetworksManager.*;
import static Models.PriorityPetrisNetworkManager.*;
import static Models.NetImporter.*;
import static Models.Selector.select;
import static Models.NetSaver.*;

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
        private Menu menu;


        private NetworkManager() {
            load();
            savedNets = new ArrayList<>(nets);
            savedpNets = new ArrayList<>(pnets);
            savedpnpNets = new ArrayList<>(pnpnets);
            menu = new Menu(new Console());
        }

        public static void main(String[] args) {
            NetworkManager manager = new NetworkManager();
            manager.identification();
        }
        
        private void identification() {
            ArrayList<Runnable> actions = new ArrayList<>(Arrays.asList(
                () -> mainMenu(),
                () -> userMenu()    
            ));
            menu.selectMenu(Menu.IDENTIFICATION, actions);
        }

        private void mainMenu() {
            ArrayList<Runnable> actions = new ArrayList<>(Arrays.asList(
                () -> selectNet(),
                () -> saveNetworks(),
                () -> pNetsMenu(pnets, savedNets, savedpNets, menu),
                () -> savePetrisNetworks(),
                () -> pnpMenu(pnpnets, savedpNets, savedpnpNets, menu),
                () -> savePriorityPetrisNetwork(),
                () -> importFile()                
            ));
            menu.selectMenu(Menu.SELEZIONE_AZIONE_NET, actions);
        }
        
        private void userMenu() {
            ArrayList<Runnable> actions = new ArrayList<>(Arrays.asList(
                () -> selectPNet(savedpNets, menu),
                () -> selectPPNet(savedpnpNets, menu)
            ));
            menu.selectMenu(Menu.USER_MENU, actions);
        }
            
       
		public void selectNet() {
            int input;
            if (nets.isEmpty()) nets.add(new Network(0, "Rete zero"));
            int netsSize = nets.size();

            menu.selectNets(nets);

            do {
            	input = -1;
                input = input + readInt();
                if (input == -1) return;
                else if (input < 0 || input > netsSize) menu.print(Menu.INSERIMENTO_VALIDO);
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

        private void saveNetworks() {
        	ArrayList<Network> sv = save(fileName, nets, savedNets, menu);
        	if(sv == null)return;
        	savedNets = sv;
        }

        public void modifyNet(Network network) {
            activeNetwork = network;
            modifyNet();
        }

        public void modifyNet() {
            ArrayList<Runnable> actions = new ArrayList<>(Arrays.asList(
                () -> addPlace(),
                () -> addTransition(),
                () -> addLink(),
                () -> setNetName()            
            ));
            menu.printNetStructure(activeNetwork);
            menu.selectMenu(Menu.AGGIUNGI, actions);
            activeNetwork.generateMatrix();
            if (checkIfNetExists(activeNetwork.getId(), activeNetwork, nets)) {
                menu.print(Menu.NET_ALREADY_EXISTS);
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
            int input = -1;
            ArrayList<Transition> transitions = activeNetwork.getTransitions();
            menu.selectTransitions(transitions);
            input = select(transitions, menu);
            if (input == -1) return;
            menu.print(Menu.P_INGRESSO_O_USCITA);
            do {
                menu.print(Menu.S_N);
                ingoing = inString().toLowerCase();
            } while (!ingoing.equals("n") && !ingoing.equals("y"));

            activeNetwork.addPlace(activeNetwork.getPlaces().size(), activeNetwork.getTransitions().get(input), ingoing.equals("y"));
        }

        public void addTransition() {
            String ingoing;
            int input;
            ArrayList<Place> places = activeNetwork.getPlaces();
            menu.selectPlaces(places);
            input = select(places, menu);
            if (input == -1) return;
            menu.print(Menu.T_INGRESSO_O_USCITA);
            do {
                menu.print(Menu.S_N);
                ingoing = inString().toLowerCase();
            } while (!ingoing.equals("n") && !ingoing.equals("y"));

            activeNetwork.addTransition(activeNetwork.getTransitions().size(), activeNetwork.getPlaces().get(input), ingoing.equals("y"));
        }

        public void addLink() {
            Link l;
            int inputX;
            int inputY;
            ArrayList<Place> places = activeNetwork.getPlaces();
            ArrayList<Transition> transitions = activeNetwork.getTransitions();
            menu.print(Menu.SELETIONA_POT);
            do {
                String s = inString();
                if (s.equals("p")) {
                    menu.selectPlaces(places);
                    inputX = select(places, menu);
                    if (inputX == -1) return;
                    menu.selectTransitions(transitions);
                    inputY = select(transitions, menu);
                    if (inputY == -1) return;
                    l = new Link(places.get(inputX), transitions.get(inputY));
                    break;
                } else if (s.equals("t")) {
                	menu.selectTransitions(transitions);
                    inputX = select(transitions, menu);
                    if (inputX == -1) return;
                    menu.selectPlaces(places);
                    inputY = select(places, menu);
                    if (inputY == -1) return;
                    l = new Link(transitions.get(inputX), places.get(inputY));
                    break;
                } else {
                	menu.print(Menu.INSERIMENTO_VALIDO);
                }
            } while (true);

            if (activeNetwork.checkLinkExist(l))
            	menu.print(Menu.LINK_GIA_ESISTENTE);
            else activeNetwork.getLinks().add(l);
        }
       
        public boolean checkIfNetExists(int i, Network net, ArrayList<? extends Network> nets) {
            for (Network n : nets)
                if (net.equals(n) && n.getId() != i) return true;
            return false;
        }
        
        public void setNetName() {
        	String nuovoNome;
        	menu.print(Menu.ASSEGNA_NOME_NET);
        	do {
        		nuovoNome = inString();
        		if(!nuovoNome.toLowerCase().replaceAll("[^a-z]", "").equals("name")) break;
        		menu.print(Menu.INSERIMENTO_VALIDO);
        	}while(true);
        	activeNetwork.setName(nuovoNome);
        }
        
        public void savePetrisNetworks(){
        	ArrayList<PetrisNetwork> sv = savePnets(pfileName, pnets, savedpNets, menu);
        	if(sv == null)return;
        	savedpNets = sv;
        }
        
        private void savePriorityPetrisNetwork() {
        	ArrayList<PriorityPetrisNetwork> sv = savePnpnets(pnpfileName, pnpnets, savedpnpNets, menu);
        	if(sv == null)return;
        	savedpnpNets = sv;
			
		}
        
        public void importFile() {
            ArrayList<Runnable> actions = new ArrayList<>(Arrays.asList(
                () -> {
                    Network n = importNet(nets, menu);
                    if(n != null)nets.add(n);
                },
                () -> {
                    PetrisNetwork pn = importPetrisNet(pnets, savedNets, menu);
                    if(pn != null)pnets.add(pn);
                },
                () -> {
                    PriorityPetrisNetwork pnp = importPriorityPetrisNet(pnpnets, savedpNets, menu);
                    if(pnp != null)pnpnets.add(pnp);
                }        
            ));
            menu.selectMenu(Menu.IMPORT_MENU, actions);
        }
        
    }

}
