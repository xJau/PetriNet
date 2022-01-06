package Models;

import static Utils.InputManager.readInt;

import java.util.List;

import Utils.Menu;

public class Selector {
	
	public static int select(List<? extends Identifiable> id) {
        int input;
        if (id.size() == 0) return -2;
        do {
            input = -1;
            input = input + readInt();
            if (input == -2 || input > id.size()-1) Menu.print(Menu.INSERIMENTO_VALIDO);
        } while (input < -1 || input > id.size()-1);
        return input;
    }
}
