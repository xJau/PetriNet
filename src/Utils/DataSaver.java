package Utils;

import Models.Network;

import java.io.*;
import java.util.List;

public class DataSaver {

    private final List<Network> nets;
    private final String fileName;
    private final String path = "./";
    private final String extension = ".petri";
    private final String filePath;
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
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

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

    private String writeNets(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Available Networks: " + nets.size());
        for(Network activeNet: nets){
            stringBuilder.append(writeNet(activeNet));
        }
        return stringBuilder.toString();
    }

    private String writeNet(Network activeNet){

        int[][] matrixIn = activeNet.getMatrixIn();
        int[][] matrixOut = activeNet.getMatrixOut();
        int row = matrixIn.length;
        int column = matrixIn[0].length;

        StringBuilder stringBuilder = new StringBuilder();
        String netId =String.valueOf(activeNet.getId());
        String netName =String.valueOf(activeNet.getName());
        stringBuilder.append("\n");
        stringBuilder.append("id: " + netId );
        stringBuilder.append("\n");
        stringBuilder.append("Name: " + netName);
        stringBuilder.append("\n");
        stringBuilder.append("\t\t\tDimension: " + row + " : " + column);
        stringBuilder.append("\n");
        stringBuilder.append("\t\t\t\tMatrix in:");

        for(int i = 0; i < row; i++){
            stringBuilder.append("\n\t\t\t\t\t");
            for (int j = 0; j < column; j++) {
                stringBuilder.append(matrixIn[i][j]);
                stringBuilder.append(" ");
            }
        }

        stringBuilder.append("\n");
        stringBuilder.append("\t\t\t\tMatrix out:");


        for(int i = 0; i < row; i++){
            stringBuilder.append("\n\t\t\t\t\t");
            for (int j = 0; j < column; j++) {
                stringBuilder.append(matrixOut[i][j]);
                stringBuilder.append(" ");
            }
        }

        return stringBuilder.toString();
    }

}
