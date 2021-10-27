package Utils;

import Models.Network;
import Models.PetrisNetwork;
import Models.PriorityPetrisNetwork;

import static Utils.NetFabric.*;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataLoader {

    private final String fileName;
    private final String path = "./";
    private final String extension = ".petri";
    private final String filePath;
    private File file;

    public DataLoader(String fileName) {
        this.fileName = fileName;
        this.filePath = path + fileName + extension;
        this.file = new File(filePath);
        loadFile();
    }

    private void loadFile() {
        this.file = new File(filePath);
        if (file.exists()) {
//            System.out.println(filePath + " è stata caricata");
        } else createFile();
    }
  
    private void createFile(){
        this.file = new File(filePath);
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void printFile() {
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (
                FileNotFoundException e) {
            System.out.println("Errore Rilevato.");
            e.printStackTrace();
        }
    }


    public ArrayList<Network> readFile() {
        ArrayList<Network> networks;
        try {
            Scanner scanner = new Scanner(file);
//            scanner.useDelimiter(":");

            while (scanner.hasNext()) {
                String data = scanner.nextLine();


                if (data.contains("Available Networks")) {
                    networks = loadNetworks(scanner, data);
                    scanner.close();
                    return networks;
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Errore Rilevato.");
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    public ArrayList<PetrisNetwork> readPetrisFile() {
        ArrayList<PetrisNetwork> pNetworks;
        try {
            Scanner scanner = new Scanner(file);
//            scanner.useDelimiter(":");

            while (scanner.hasNext()) {
                String data = scanner.nextLine();


                if (data.contains("Available Petri's Networks")) {
                	pNetworks = loadPetrisNetworks(scanner, data);
                    scanner.close();
                    return pNetworks;
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Errore Rilevato.");
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    public ArrayList<PriorityPetrisNetwork> readPriorityPetrisFile() {
        ArrayList<PriorityPetrisNetwork> pnpNetworks;
        try {
            Scanner scanner = new Scanner(file);
//            scanner.useDelimiter(":");

            while (scanner.hasNext()) {
                String data = scanner.nextLine();


                if (data.contains("Available Priority Petri's Networks")) {
                	pnpNetworks = loadPriorityPetrisNetworks(scanner, data);
                    scanner.close();
                    return pnpNetworks;
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Errore Rilevato.");
            e.printStackTrace();
        }
        return new ArrayList<>();
    }



    private ArrayList<Network> loadNetworks(Scanner scanner, String data) {
        ArrayList<Network> networks = new ArrayList<>();
        int netNumber;
        netNumber = Integer.valueOf(data.replaceAll("[^0-9]", ""));
//        System.out.println(Integer.toString(netNumber) + " networks caricate");

        for (int i = 0; i < netNumber; i++) {
            Network net = loadNetwork(scanner);
            networks.add(net);
        }
        return networks;
    }
    
    private ArrayList<PetrisNetwork> loadPetrisNetworks(Scanner scanner, String data) {
        ArrayList<PetrisNetwork> pNetworks = new ArrayList<>();
        int netNumber;
        netNumber = Integer.valueOf(data.replaceAll("[^0-9]", ""));
//        System.out.println(Integer.toString(netNumber) + " petri's networks caricate");

        for (int i = 0; i < netNumber; i++) {
            PetrisNetwork net = loadPetrisNetwork(scanner);
            pNetworks.add(net);
        }
        return pNetworks;
    }
    
    private ArrayList<PriorityPetrisNetwork> loadPriorityPetrisNetworks(Scanner scanner, String data) {
        ArrayList<PriorityPetrisNetwork> pNetworks = new ArrayList<>();
        int netNumber;
        netNumber = Integer.valueOf(data.replaceAll("[^0-9]", ""));
//        System.out.println(Integer.toString(netNumber) + " petri's networks caricate");

        for (int i = 0; i < netNumber; i++) {
            PriorityPetrisNetwork net = loadPriorityPetrisNetwork(scanner);
            pNetworks.add(net);
        }
        return pNetworks;
    }

    

    private int[][] loadMatrix(int rows, int columns, Scanner scanner) {
        int[][] matrix = new int[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }

        return matrix;
    }
    
    private int[] loadVector(int lenght, Scanner scanner) {
    	int[] vector = new int[lenght];
    	for(int i = 0; i<lenght; i++)vector[i] = scanner.nextInt();
    	return vector;
    }
    
    
    public Network loadNetwork(Scanner scanner) {
        Network net = null;
        int column = 0;
        int rows = 0;
        int netNumber = -1;
        String netName = "error";
        int[][] matrixIn = null;
        int[][] matrixOut;

        while (scanner.hasNext()) {
            String data = scanner.nextLine();
            if (data.contains("id")) {
                netNumber = Integer.valueOf(data.replaceAll("[^0-9]", ""));
            } else if (data.contains("Name")) {
            	netName = data.replaceAll("Name: ", "").trim();
            }  else if (data.contains("Dimension")) {
                String dimScannerValue = data.replaceAll("[^0-9:]", "");
                Scanner dimensioneScanner = new Scanner(dimScannerValue);
                dimensioneScanner.useDelimiter(":");
                dimScannerValue = dimensioneScanner.next();
                int dimensione = Integer.valueOf(dimScannerValue.replaceAll("[^0-9]", ""));
                rows = dimensione;
                dimScannerValue = dimensioneScanner.next();
                dimensione = Integer.valueOf(dimScannerValue.replaceAll("[^0-9]", ""));
                column = dimensione;
            } else if (data.contains("Matrix in")) {
                matrixIn = loadMatrix(rows, column, scanner);
            } else if (data.contains("Matrix out")) {
                matrixOut = loadMatrix(rows, column, scanner);
                net = new Network(netNumber, netName, matrixIn, matrixOut);
                return net;
            }

        }

        return net;
    }
    
    public int[] loadPetrisMarking(Scanner scanner) {
    	int mDim = 0;
    	int m[] = null;
    	
    	while(scanner.hasNext()) {
    		String data = scanner.nextLine();
    		if(data.contains("Marking Dimension: ")) {
        		String dimScannerValue = data.replaceAll("[^0-9]", "");
        		mDim = Integer.valueOf(dimScannerValue);
        	} else if(data.contains("Marking")) {
        		m = loadVector(mDim, scanner);
        		return m;
        	}
    	}
    	return m;
    }
    
    public PetrisNetwork loadPetrisNetwork(Scanner scanner) {
    	int[] marking;
    	Network net = loadNetwork(scanner);
    	marking = loadPetrisMarking(scanner);
    	return new PetrisNetwork(net, net.getId(), net.getName(), marking);
    }
    

    public int[] loadPetrisTransitionsPriority(Scanner scanner) {
    	int prDim = 0;
    	int pr[] = null;
    	
    	while(scanner.hasNext()) {
    		String data = scanner.nextLine();
    		if(data.contains("Priority Dimension: ")) {
        		String dimScannerValue = data.replaceAll("[^0-9]", "");
        		prDim = Integer.valueOf(dimScannerValue);
        	} else if(data.contains("Priority")) {
        		pr = loadVector(prDim, scanner);
        		return pr;
        	}
    	}
    	return pr;
    }
    
    public PriorityPetrisNetwork loadPriorityPetrisNetwork(Scanner scanner) {
    	int[] priority;
    	PetrisNetwork net = loadPetrisNetwork(scanner);
    	priority = loadPetrisTransitionsPriority(scanner);
    	return new PriorityPetrisNetwork(net, net.getId(), net.getName(), priority);
    }
    
}
