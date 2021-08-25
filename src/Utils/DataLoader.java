package Utils;

import Models.Network;

import java.io.File;
import java.util.List;

public class DataLoader {

    List<Network> loadedNets;
    private final String fileName;
    private final String path = "./";
    private final String extension = ".txt";
    private final String filePath;
    private File file;

    public DataLoader(String fileName) {
        this.fileName = fileName;
        this.filePath = path+fileName+extension;
        this.file = new File(filePath);
    }

    public void loadFile() {
        //TODO implement
    }
}
