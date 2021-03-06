package Utils;

import Models.Network;
import Models.PetrisNetwork;

import java.io.*;
import java.util.List;

public class DataSaver {

    private final List<Network> nets;
    private final String fileName;
    private final String path = "./";
    private final String extension = ".petri";
    protected final String filePath;
    private File file;

    public DataSaver(List<Network> nets,String fileName){
        this.nets = nets;
        this.fileName = fileName;
        this.filePath = path + fileName + extension;
        createFile();
    }

    public void writeFile(){
        try {
            FileWriter myWriter = new FileWriter(filePath);
            myWriter.write(writeNets());
            myWriter.close();
//            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void createFile(){
        this.file = new File(filePath);
        try {
            if (file.createNewFile()) {
//                System.out.println("File created: " + file.getName());
            } else {
//                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    protected String writeNets(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Available Networks: " + nets.size());
        for(Network activeNet: nets){
            stringBuilder.append(writeNet(activeNet, new StringBuilder()));
        }
        return stringBuilder.toString();
    }

    protected String writeNet(Network activeNet, StringBuilder stringBuilder){

        int[][] matrixIn = activeNet.getMatrixIn();
        int[][] matrixOut = activeNet.getMatrixOut();
        int row = matrixIn.length;
        int column = matrixIn[0].length;

//        StringBuilder stringBuilder = new StringBuilder();
        String netId = String.valueOf(activeNet.getId());
        String netName = String.valueOf(activeNet.getName());
        stringBuilder.append("\n");
        stringBuilder.append("\tid: " + netId );
        stringBuilder.append("\n");
        stringBuilder.append("\tName: " + netName);
        stringBuilder.append("\n");
        stringBuilder.append("\t\tDimension: " + row + " : " + column);
        stringBuilder.append("\n");
        stringBuilder.append("\t\t\tMatrix in:");

        for(int i = 0; i < row; i++){
            stringBuilder.append("\n\t\t\t\t");
            for (int j = 0; j < column; j++) {
                stringBuilder.append(matrixIn[i][j]);
                stringBuilder.append(" ");
            }
        }

        stringBuilder.append("\n");
        stringBuilder.append("\t\t\tMatrix out:");

        for(int i = 0; i < row; i++){
            stringBuilder.append("\n\t\t\t\t");
            for (int j = 0; j < column; j++) {
                stringBuilder.append(matrixOut[i][j]);
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }
}
