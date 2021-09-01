package Utils;

import Models.Network;


import java.io.File;
import java.io.FileNotFoundException;
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
            System.out.println(filePath + " è stata caricata");
        } else System.out.println(filePath + " non esiste");
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

    public List<Network> readFile() {
        List<Network> networks;
        int netNumber;
        int activeNetNumber;
        Network activeNet;
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
        return null;
    }

    private List<Network> loadNetworks(Scanner scanner, String data) {
        List<Network> networks = new ArrayList<>();
        int netNumber;
        netNumber = Integer.valueOf(data.replaceAll("[^0-9]", ""));
        System.out.println(Integer.toString(netNumber) + " networks caricate");

        for (int i = 0; i < netNumber; i++) {
            Network net = loadNetwork(scanner);
            networks.add(net);
        }
        return networks;
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

    public Network loadNetwork(Scanner scanner) {
        Network net = null;
        int column = 0;
        int rows = 0;
        int netNumber = -1;
        int[][] matrixIn = null;
        int[][] matrixOut;

        while (scanner.hasNext()) {
            String data = scanner.nextLine();
            if (data.contains("Network name")) {
                netNumber = Integer.valueOf(data.replaceAll("[^0-9]", ""));
                System.out.println("Network n." + Integer.toString(netNumber) + " caricata");
            } else if (data.contains("Dimension")) {
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
                net = new Network(netNumber,matrixIn,matrixOut);
                return net;
            }

        }

        return net;
    }
}
