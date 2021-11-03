package Models;

import static Models.PetrisNetworksManager.convertToNet;
import static Models.PriorityPetrisNetworkManager.convertToPetrisNet;
import static Utils.InputManager.inInt;
import Utils.Menu;


import java.util.ArrayList;

import Utils.DataLoader;

public class NetImporter {

	static Menu menu = new Menu();
	
	public static Network importNet(ArrayList<Network> nets) {
    	DataLoader d = new DataLoader("/Networks", "Importare");
    	String s = selectFile(d);
    	d.selectFile(s);
    	menu.print(s);
    	ArrayList<Network> output;
    	try{
    		output = d.readFile();
    	}
    	catch(Exception e){
    		menu.emptyOrIncompatible();
    		return null;
    	}
    	if(output.isEmpty()) {
    		menu.emptyOrIncompatible();
    		return null;
    	}
    	Network net = output.get(0);
    	if(net.getName().equals("")) {
    		menu.emptyOrIncompatible();
    		return null;
    	}
    	if(checkIfNetExists(nets.size(), net, nets)) {
    		menu.netAlreadyExists();
    		return null;
    	}
    	if(!net.checkConnectivity()) {
    		menu.netNotConnected();
    		return null;
    	}
    	return net;
    }
    
    public static PetrisNetwork importPetrisNet(ArrayList<PetrisNetwork> pnets, ArrayList<Network> savedNets) {
    	DataLoader d = new DataLoader("/PetrisNetworks", "Importare");
    	String s = selectFile(d);
    	d.selectFile(s);
    	menu.print(s);
    	ArrayList<PetrisNetwork> output;
    	try{
    		output = d.readPetrisFile();
    	}
    	catch(Exception e){
    		menu.emptyOrIncompatible();
    		return null;
    	}
    	if(output.isEmpty()) {
    		menu.emptyOrIncompatible();
    		return null;
    	}
    	PetrisNetwork net = output.get(0);
    	if(net.getName().equals("")) {
    		menu.emptyOrIncompatible();
    		return null;
    	}
    	if(checkIfNetExists(pnets.size(), net, pnets)) {
    		menu.netAlreadyExists();
    		return null;
    	}
    	if(!checkIfNetExists(savedNets.size(), convertToNet(net, savedNets.size()), savedNets)) {
    		menu.netFatherDontExist();
    		return null;
    	}
    	if(!net.checkConnectivity()) {
    		menu.netNotConnected();
    		return null;
    	}
    	
    	
    	return net;
    }
    
    public static PriorityPetrisNetwork importPriorityPetrisNet(ArrayList<PriorityPetrisNetwork> pnpnets, ArrayList<PetrisNetwork> savedpNets) {
    	DataLoader d = new DataLoader("/PriorityPetrisNetworks", "Importare");
    	String s = selectFile(d);
    	d.selectFile(s);
    	menu.print(s);
    	ArrayList<PriorityPetrisNetwork> output;
    	try{
    		output = d.readPriorityPetrisFile();
    	}
    	catch(Exception e){
    		menu.emptyOrIncompatible();
    		return null;
    	}
    	if(output.isEmpty()) {
    		menu.emptyOrIncompatible();
    		return null;
    	}
    	PriorityPetrisNetwork net = output.get(0);
    	if(net.getName().equals("")) {
    		menu.emptyOrIncompatible();
    		return null;
    	}
    	if(checkIfNetExists(pnpnets.size(), net, pnpnets)) {
    		menu.netAlreadyExists();
    		return null;
    	}
    	if(!checkIfNetExists(savedpNets.size(), convertToPetrisNet(net, savedpNets.size()), savedpNets)) {
    		menu.netFatherDontExist();
    		return null;
    	}
    	if(!net.checkConnectivity()) {
    		menu.netNotConnected();
    		return null;
    	}
    	return net;
    }
    
    public static String selectFile(DataLoader d) {
    	boolean stop;
    	int i = 1;
    	String[] nameList = d.directoryInspection();
    	
    	for(String s: nameList) {
    		menu.print(i + ")" + s);
    		i++;
    	}
    	do{
    		stop = true;
    		i = inInt();
    		if(i < 1 || i > nameList.length) {
    			menu.printValue();
    			stop = false;
    		}
    	}while(!stop);
    	String s = nameList[i-1];
    	return s;
    }
    
    private static boolean checkIfNetExists(int i, Network net, ArrayList<? extends Network> nets) {
        for (Network n : nets)
            if (net.equals(n) && n.getId() != i) return true;
        return false;
    }
    
}
	
