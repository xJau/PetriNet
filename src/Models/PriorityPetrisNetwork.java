package Models;

public class PriorityPetrisNetwork extends PetrisNetwork {
	
	private int[] priority;
	
	public PriorityPetrisNetwork(PetrisNetwork pn, int id, String name, int[] priority) {
		super(pn, id, name, pn.getMarking());
		this.priority = priority;
	}
	
	public int[] getPriority() {
		return priority;
	}
	
	public boolean equals(Object pnp) {
		int[] pr;
		if(!super.equals(pnp))return false;
		pr = ((PriorityPetrisNetwork) pnp).getPriority();
		if(pr.length != priority.length)return false;
		for(int i = 0; i<pr.length; i++)
			if(pr[i] != priority[i])return false;
		return true;
	}
	
	public boolean checkPriority() {
		for(int i = 0; i < priority.length; i++)
			if(priority[i] < 1)return false;
		return true;
	}
	
}
