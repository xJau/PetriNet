package Utils;

import java.util.*;

public class InputUtils {
	
	public static int inInteger() {
		int i;
		Scanner s = new Scanner(System.in);
		try {
			i = s.nextInt();
		}
		catch(Exception e) {
			return -1;
		}
		return i;
	}
	
	public static String inputString() {
		String a;
		Scanner s = new Scanner(System.in);
		try {
			a = s.next();
		}
		catch(Exception e) {
			a = "e";
		}
		return a;
	}
	
	public static String inYorN() {
		String a;
		boolean check;
		do {
			a = inputString();
			a.toLowerCase();
			if(a.equals("y") || a.equals("n"))check = true;
			else {
				System.out.println("Inserisci (y) o (n)");
				check = false;
			}
		}while(!check);
		return a;
	}
	
	
	
}
