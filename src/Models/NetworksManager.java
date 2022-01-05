package Models;

import Utils.*;
import Utils.Menu;

import static Utils.InputManager.*;
import static Models.PetrisNetworksManager.*;
import static Models.PriorityPetrisNetworkManager.*;
import static Models.NetImporter.*;

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


        private NetworkManager() {
            load();
            savedNets = new ArrayList<>(nets);
            savedpNets = new ArrayList<>(pnets);
            savedpnpNets = new ArrayList<>(pnpnets);
        }

        public static void main(String[] args) {
            NetworkManager manager = new NetworkManager();
            do {
                manager.identification();
            } while (true);
        }
        
        private void identification() {
            ArrayList<Runnable> actions = new ArrayList<>(Arrays.asList(
                () -> mainMenu(),
                () -> userMenu()    
            ));
            Menu.selectMenu(Menu.IDENTIFICATION, actions);
        }

        private void mainMenu() {
            ArrayList<Runnable> actions = new ArrayList<>(Arrays.asList(
                () -> { while(true) { selectNet(); }},
                () -> save(fileName),
                () -> pNetsMenu(pnets, savedNets, savedpNets),
                () -> savePetrisNetworks(),
                () -> pnpMenu(pnpnets, savedpNets, savedpnpNets),
                () -> savePriorityPetrisNetwork(),
                () -> importFile()                
            ));
            Menu.selectMenu(Menu.SELEZIONE_AZIONE_NET, actions);
        }
        
        private void userMenu() {
            ArrayList<Runnable> actions = new ArrayList<>(Arrays.asList(
                () -> selectPNet(savedpNets),
                () -> selectPPNet(savedpnpNets)
            ));
            Menu.selectMenu(Menu.USER_MENU, actions);
        }
            
       
		public void selectNet() {
            int input;
            if (nets.isEmpty()) nets.add(new Network(0, "Rete zero"));
            int netsSize = nets.size();

            Menu.selectNets(nets);

            do {
            	input = -1;
                input = input + readInt();
                if (input == -1) return;
                else if (input < 0 || input > netsSize) Menu.print(Menu.INSERIMENTO_VALIDO);
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

        public int select(List<? extends Identifiable> id) {
            int input;
            if (id.size() == 0) return -2;
            do {
                input = -1;
                input = input + readInt();
                if (input == -2 || input > id.size()-1) Menu.print(Menu.INSERIMENTO_VALIDO);
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
                    Menu.print(Menu.NO_RETI);
                    break;
                }
                Menu.selectNetsToSave(savableNets);
                if (input == 0) return;
                input = select(savableNets);
                if (input == -2) {
                    Menu.print(Menu.NO_RETI);
                    return;
                }
                if (input == -1) return;
                savingNets.add(savableNets.get(input));
                savableNets.remove(input);
                Menu.print(Menu.SALVA_O_CONTINUA);
                do {
                    input = readInt();
                    if (input == 1) stop = true;
                    else if (input < 1 || input > 2) Menu.print(Menu.INSERIMENTO_VALIDO);
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
            ArrayList<Runnable> actions = new ArrayList<>(Arrays.asList(
                () -> addPlace(),
                () -> addTransition(),
                () -> addLink(),
                () -> setNetName()            
            ));
            Menu.printNetStructure(activeNetwork);
            Menu.selectMenu(Menu.AGGIUNGI, actions);
            activeNetwork.generateMatrix();
            if (checkIfNetExists(activeNetwork.getId(), activeNetwork, nets)) {
                Menu.print(Menu.NET_ALREADY_EXISTS);
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
            Menu.selectTransitions(transitions);
            input = select(transitions);
            if (input == -1) return;
            Menu.print(Menu.P_INGRESSO_O_USCITA);
            do {
                Menu.print(Menu.S_N);
                ingoing = inString().toLowerCase();
            } while (!ingoing.equals("n") && !ingoing.equals("y"));

            activeNetwork.addPlace(activeNetwork.getPlaces().size(), activeNetwork.getTransitions().get(input), ingoing.equals("y"));
        }

        public void addTransition() {
            String ingoing;
            int input;
            ArrayList<Place> places = activeNetwork.getPlaces();
            Menu.selectPlaces(places);
            input = select(places);
            if (input == -1) return;
            Menu.print(Menu.T_INGRESSO_O_USCITA);
            do {
                Menu.print(Menu.S_N);
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
            Menu.print(Menu.SELETIONA_POT);
            do {
                String s = inString();
                if (s.equals("p")) {
                    Menu.selectPlaces(places);
                    inputX = select(places);
                    if (inputX == -1) return;
                    Menu.selectTransitions(transitions);
                    inputY = select(transitions);
                    if (inputY == -1) return;
                    l = new Link(places.get(inputX), transitions.get(inputY));
                    break;
                } else if (s.equals("t")) {
                    Menu.selectTransitions(transitions);
                    inputX = select(transitions);
                    if (inputX == -1) return;
                    Menu.selectPlaces(places);
                    inputY = select(places);
                    if (inputY == -1) return;
                    l = new Link(transitions.get(inputX), places.get(inputY));
                    break;
                } else {
                    Menu.print(Menu.INSERIMENTO_VALIDO);
                }
            } while (true);

            if (activeNetwork.checkLinkExist(l))
                Menu.print(Menu.LINK_GIA_ESISTENTE);
            else activeNetwork.getLinks().add(l);
        }
       
        public boolean checkIfNetExists(int i, Network net, ArrayList<? extends Network> nets) {
            for (Network n : nets)
                if (net.equals(n) && n.getId() != i) return true;
            return false;
        }
        
        public void setNetName() {
        	String nuovoNome;
        	Menu.print(Menu.ASSEGNA_NOME_NET);
        	do {
        		nuovoNome = inString();
        		if(!nuovoNome.toLowerCase().replaceAll("[^a-z]", "").equals("name")) break;
        		Menu.print(Menu.INSERIMENTO_VALIDO);
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
        
        public void importFile() {
            ArrayList<Runnable> actions = new ArrayList<>(Arrays.asList(
                () -> {
                    Network n = importNet(nets);
                    if(n != null)nets.add(n);
                },
                () -> {
                    PetrisNetwork pn = importPetrisNet(pnets, savedNets);
                    if(pn != null)pnets.add(pn);
                },
                () -> {
                    PriorityPetrisNetwork pnp = importPriorityPetrisNet(pnpnets, savedpNets);
                    if(pnp != null)pnpnets.add(pnp);
                }        
            ));
            Menu.selectMenu(Menu.IMPORT_MENU, actions);
        }
        
    }

}
