package Utils;

public class MyUtil {
	
	public static boolean matEquals(int[][] x, int [][] y) {
		if(x.length != y.length)return false;
		if(x[0].length != y[0].length)return false;
		for(int i = 0; i<x.length; i++) {
			for(int j = 0; j<x[0].length; j++)if(x[i][j] != y[i][j])return false;
		}
		return true;
	}
	
	
}
