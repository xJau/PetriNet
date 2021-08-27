package Utils;

public class MatrixOperation {
	
	public static boolean matEquals(int[][] x, int [][] y) {
		if(x.length != y.length)return false;
		if(x[0].length != y[0].length)return false;
		for(int i = 0; i<x.length; i++) {
			for(int j = 0; j<x[0].length; j++)if(x[i][j] != y[i][j])return false;
		}
		return true;
	}
	
	public static boolean checkColumns(int [][] x, int n) {
		for(int i = 0; i<x[0].length; i++) {
			for(int j = 0; j<x.length; j++)
				if(x[j][i] != n)return false;
		}
		return true;
		//TODO: eventualmente restituire vettore con indice delle colonne che hanno tutti gli elementi pari a n
	}
	public static boolean checkRows(int [][] x, int n) {
		for(int i = 0; i<x.length; i++) {
			for(int j = 0; j<x[0].length; j++)
				if(x[i][j] != n)return false;
		}
		return true;
		//TODO: eventualmente restituire vettore con indice delle righe che hanno tutti gli elementi pari a n
	}
	
	public static int[][] matrixSum(int[][] x, int[][] y){
		int[][] m = new int[x.length][x[0].length];
		for(int i = 0; i<x.length; i++) {
			for(int j = 0; j<x[0].length; j++)
				m[i][j] = x[i][j]+y[i][j];
		}
		return m;
	}
	
	
}
