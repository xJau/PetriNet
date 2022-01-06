package Models;

import static Models.PetrisNetworksManager.convertToNet;
import static Models.PriorityPetrisNetworkManager.convertToPetrisNet;
import static Utils.InputManager.readInt;
import Utils.Menu;
import Utils.DataLoader;
import java.util.ArrayList;

public class NetImporter {
	public static Network importNet(ArrayList<Network> nets) {
    	DataLoader d = new DataLoader("/Networks", "Importare");
    	String s = selectFile(d);

    	if(s == null) {
    		Menu.printError(Menu.NESSUN_FILE);
    		return null;
    	}

    	d.selectFile(s);
    	Menu.print(s);

    	ArrayList<Network> output;
    	try{
    		output = d.readFile();
			if(output.isEmpty()) throw new Exception();
    	}
    	catch(Exception e){
    		Menu.printError(Menu.FILE_VUOTO_O_NON_COMPATIBILE);
    		return null;
    	}

    	Network net = output.get(0);
    	if(
		  net.getName().isEmpty() ||
		  !net.checkIfEntriesAreCorrect()
		){
    		Menu.printError(Menu.FILE_VUOTO_O_NON_COMPATIBILE);
    		return null;
    	}
    	if(checkIfNetExists(nets.size(), net, nets)) {
    		Menu.printError(Menu.NET_ALREADY_EXISTS);
    		return null;
    	}
    	if(!net.checkConnectivity()) {
    		Menu.printError(Menu.RETE_NON_CONNESSA);
    		return null;
    	}
    	return net;
    }
	
    
    public static PetrisNetwork importPetrisNet(ArrayList<PetrisNetwork> pnets, ArrayList<Network> savedNets) {
    	DataLoader d = new DataLoader("/PetrisNetworks", "Importare");
    	String s = selectFile(d);
    	if(s == null) {
    		Menu.printError(Menu.NESSUN_FILE);
    		return null;
    	}
    	d.selectFile(s);
    	Menu.print(s);
    	ArrayList<PetrisNetwork> output;
    	try{
    		output = d.readPetrisFile();
			if(output.isEmpty()) throw new Exception();
    	}
    	catch(Exception e){
    		Menu.printError(Menu.FILE_VUOTO_O_NON_COMPATIBILE);
    		return null;
    	}
    	PetrisNetwork net = output.get(0);
    	if(
		  net.getName().isEmpty() || 
		  !net.checkMarking() ||
		  !net.checkIfEntriesAreCorrect()
		) {
    		Menu.printError(Menu.FILE_VUOTO_O_NON_COMPATIBILE);
    		return null;
    	}
    	if(checkIfNetExists(pnets.size(), net, pnets)) {
    		Menu.printError(Menu.NET_ALREADY_EXISTS);
    		return null;
    	}
    	if(!checkIfNetExists(savedNets.size(), convertToNet(net, savedNets.size()), savedNets)) {
    		Menu.printError(Menu.NET_FATHER_DONT_EXIST);
    		return null;
    	}
    	if(!net.checkConnectivity()) {
    		Menu.printError(Menu.RETE_NON_CONNESSA);
    		return null;
    	}    	
    	return net;
    }
    
    public static PriorityPetrisNetwork importPriorityPetrisNet(ArrayList<PriorityPetrisNetwork> pnpnets, ArrayList<PetrisNetwork> savedpNets) {
    	DataLoader d = new DataLoader("/PriorityPetrisNetworks", "Importare");
    	String s = selectFile(d);
    	if(s == null) {
    		Menu.printError(Menu.NESSUN_FILE);
    		return null;
    	}
    	d.selectFile(s);
    	Menu.print(s);

    	ArrayList<PriorityPetrisNetwork> output;
    	try{
    		output = d.readPriorityPetrisFile();
			if(output.isEmpty()) {
				throw new Exception();
			}
    	}
    	catch(Exception e){
    		Menu.printError(Menu.FILE_VUOTO_O_NON_COMPATIBILE);
    		return null;
    	}

    	PriorityPetrisNetwork net = output.get(0);
    	if(
		  net.getName().isEmpty() ||
		  !net.checkMarking() ||
		  !net.checkIfEntriesAreCorrect() ||
		  !net.checkPriority()
		) {
    		Menu.printError(Menu.FILE_VUOTO_O_NON_COMPATIBILE);
    		return null;
    	}
    	if(checkIfNetExists(pnpnets.size(), net, pnpnets)) {
    		Menu.printError(Menu.NET_ALREADY_EXISTS);
    		return null;
    	}
    	if(!checkIfNetExists(savedpNets.size(), convertToPetrisNet(net, savedpNets.size()), savedpNets)) {
    		Menu.printError(Menu.NET_FATHER_DONT_EXIST);
    		return null;
    	}
    	if(!net.checkConnectivity()) {
    		Menu.printError(Menu.RETE_NON_CONNESSA);
    		return null;
    	}
    	return net;
    }
    
    public static String selectFile(DataLoader d) {
    	boolean stop;
    	int i = 1;
    	String[] nameList = d.directoryInspection();
    	
    	if(nameList.length == 0) return null;
    	
    	for(String s : nameList) {
    		Menu.print(i + ")" + s);
    		i++;
    	}

    	do{
    		stop = true;
    		i = readInt();
    		if(i < 1 || i > nameList.length) {
    			Menu.print(Menu.INSERIMENTO_VALIDO);
    			stop = false;
    		}
    	}while(!stop);
    	String s = nameList[i-1];
    	return s;
    }
    
    private static boolean checkIfNetExists(int i, Network net, ArrayList<? extends Network> nets) {
        for (Network n : nets) {
			if (net.equals(n) && n.getId() != i) return true;
		}
        return false;
    }
    
}
	
