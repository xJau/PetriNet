package Utils;

public class MatrixOperation {

    public static boolean matEquals(int[][] x, int[][] y) {
        if (x.length != y.length) return false;
        if (x[0].length != y[0].length) return false;
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x[i].length; j++)
                if (x[i][j] != y[i][j])
                    return false;
        }
        return true;
    }

    public static boolean checkColumns(int[][] x, int n) {
        for (int i = 0, counter = 0; i < x[0].length; i++, counter = 0) {
            for (int j = 0; j < x.length; j++) {
                if (x[j][i] == n) counter++;
            }
            if (counter == x.length) return true;
        }
        return false;
    }

    public static boolean checkRows(int[][] x, int n) {
        for (int i = 0, counter = 0; i < x.length; i++, counter = 0) {
            for (int j = 0; j < x[0].length; j++) {
                if (x[i][j] == n) counter++;
            }
            if (counter == x[0].length) return true;
        }
        return false;
    }

    public static int[][] matrixSum(int[][] x, int[][] y) {
        int[][] m = new int[x.length][x[0].length];
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x[0].length; j++)
                m[i][j] = x[i][j] + y[i][j];
        }
        return m;
    }

    public static void printMatrix(int[][] m) {
        for (int i = 0; i < m.length; i++) {
            System.out.print("\n");
            System.out.print("\t");
            for (int j = 0; j < m[0].length; j++) {
                System.out.print(m[i][j] + " ");
            }
        }
    }

    public static int[][] changeNonNullMatrixEnrtriesToOne(int[][] matrix){
    	int[][] m = new int[matrix.length][matrix[0].length];;
    	for(int i = 0; i < m.length; i++) {
    		for(int j = 0; j<m[i].length; j++){
    			if(matrix[i][j] != 0)m[i][j] = 1;
    			else m[i][j] = 0;
    		}
    	}
    	return m;
    }
    
    public static boolean checkEntriesValues(int[][] m, int n) {
    	for(int i = 0; i<m.length; i++) {
    		for(int j = 0; j<m.length; j++)
    			if(m[i][j] < n)return false;
    	}
    	return true;
    }

}
