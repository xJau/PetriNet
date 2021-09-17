package Utils;

import java.util.Scanner;

public class InputManager {
	
	public static int inInt() {
		int i;
		Scanner s = new Scanner(System.in);
		try {
			i = s.nextInt();
		}
		catch(Exception e) {
			i = -1;
		}
		return i;
	}

	public static String inString() {
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
			a = inString();
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
