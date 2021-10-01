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
		boolean error;
		Scanner s = new Scanner(System.in);
		do {
			error = false;
			try {
				a = s.next();
			}
			catch(Exception e) {
				error = true;
				System.out.println("Input non valido, riprovare:");
				a = "e";
			}
		}while(error == true);
		return a;
	}
}
