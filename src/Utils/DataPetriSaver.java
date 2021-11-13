package Utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Models.Network;
import Models.PetrisNetwork;

public class DataPetriSaver extends DataSaver{
	
	List<PetrisNetwork> pnets;
	
	public DataPetriSaver(List<PetrisNetwork> pnets, String fileName) {
		super(null, fileName);
		this.pnets = pnets;
	}
	
	
	protected String writeNets(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Available Petri's Networks: " + pnets.size());
        for(PetrisNetwork activeNet: pnets){
            stringBuilder.append(writeNet(activeNet, new StringBuilder()));
        }
        return stringBuilder.toString();
    }
	
	protected String writeNet(PetrisNetwork activeNet, StringBuilder stringBuilder) {
		int l = activeNet.getMarking().length;
    	int[] m = activeNet.getMarking();
    	super.writeNet(activeNet, stringBuilder);
    	stringBuilder.append("\n");
    	stringBuilder.append("\tMarking Dimension: "+l);
    	stringBuilder.append("\n");
    	stringBuilder.append("\tMarking: ");
    	stringBuilder.append("\n\t\t");
    	for(int i = 0; i<l; i++) {
    		stringBuilder.append(m[i]);
    		stringBuilder.append(" ");
    	}
    	return stringBuilder.toString();
	}
	
	

}
