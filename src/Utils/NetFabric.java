package Utils;


import Models.Network;
import Models.PetrisNetwork;

public class NetFabric {
	
	public static Network createNet(int id, String name, int[][] matrixIn, int[][] matrixOut, int[] marking) {
		Network n = new Network(id, name, matrixIn, matrixOut);
		if(marking != null)return createPetrisNetwork(n, id, name, marking); 
		return n;
	}
	
	public static Network createNetwork(int id, String name, int[][] matrixIn, int[][] matrixOut) {
		return new Network(id, name, matrixIn, matrixOut);
	}
	
	public static PetrisNetwork createPetrisNetwork(Network n, int id, String name, int[] marking) {
		return new PetrisNetwork(n, id, name, marking);
	}
	
}
