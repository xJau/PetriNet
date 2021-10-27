package Utils;

import java.util.List;

import Models.PetrisNetwork;
import Models.PriorityPetrisNetwork;

public class DataPriorityPetriSaver extends DataPetriSaver {

	List<PriorityPetrisNetwork> pnpnets;
	
	public DataPriorityPetriSaver(List<PriorityPetrisNetwork> pnpnets, String fileName) {
		super(null, fileName);
		this.pnpnets = pnpnets;
	}
	
	protected String writeNets(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Available Priority Petri's Networks: " + pnpnets.size());
        for(PriorityPetrisNetwork activeNet: pnpnets){
            stringBuilder.append(writeNet(activeNet, new StringBuilder()));
        }
        return stringBuilder.toString();
    }
	
	protected String writeNet(PriorityPetrisNetwork activeNet, StringBuilder stringBuilder) {
		int l = activeNet.getPriority().length;
    	int[] pr = activeNet.getPriority();
    	super.writeNet(activeNet, stringBuilder);
    	stringBuilder.append("\n");
    	stringBuilder.append("\tPriority Dimension: "+l);
    	stringBuilder.append("\n");
    	stringBuilder.append("\tPriority: ");
    	stringBuilder.append("\n\t\t");
    	for(int i = 0; i<l; i++) {
    		stringBuilder.append(pr[i]);
    		stringBuilder.append(" ");
    	}
    	return stringBuilder.toString();
	}
	
}
