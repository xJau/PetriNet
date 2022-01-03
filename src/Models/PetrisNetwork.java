package Models;

import java.util.ArrayList;

public class PetrisNetwork extends Network {
	
		private int[] marking;
		
		public PetrisNetwork() {
			super();
		}
		
		public PetrisNetwork(Network n, int id, String name, int[] marking) {
			super(id, name, n.getMatrixIn(), n.getMatrixOut());
			this.marking = marking;
		}
		
		public PetrisNetwork(int id, String name, int[][] matrixIn, int[][] matrixOut, int[] marking) {
			super(id, name, matrixIn, matrixOut);
			this.marking = marking;
		}
		
		public int[] getMarking() {
			return marking;
		}
		
		public int getMark(int i) {
			return marking[i];
		}
		
		public void setWeight(int[] newLinksWeight) {
			int i = 0;
			int [][]mIn = getMatrixIn();
			int [][]mOut = getMatrixOut();
			ArrayList<Link> l = getLinks();
			for (Link link : l) {
				int ingoingId = link.getInGoingNode().getId();
				int outgoingId = link.getOutGoingNode().getId();
	            if (link.getOutGoingNode() instanceof Transition)
	                mIn[ingoingId][outgoingId] = newLinksWeight[i];
	            else if (link.getInGoingNode() instanceof Transition)
	               mOut[outgoingId][ingoingId] = newLinksWeight[i];
	            i++;
	        }
		}
		
		public boolean equals(Object net) {
			if(!super.equals(net)) return false;
			int[] m = ((PetrisNetwork) net).getMarking();
			if(m.length != marking.length) return false;
			for(int i = 0; i<marking.length; i++)
				if(m[i] != marking[i])return false;
			return true;
		}
		
		public boolean checkMarking() {
			for(int i = 0; i < marking.length; i++)
				if(marking[i] < 0)return false;
			return true;
		}
		

}
