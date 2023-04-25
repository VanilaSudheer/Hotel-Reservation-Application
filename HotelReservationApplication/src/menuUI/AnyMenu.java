package menuUI;

import model.IRoom;

import java.util.Collection;
import java.util.Scanner;

public class AnyMenu {

    public void displayMenu(String[] menuItems) {
        System.out.println("------------------------------");
        int i = 1;
        for (String item : menuItems) {
            System.out.println(i + ". " + item);
            i++;
        }
        System.out.println("------------------------------");
    }

    public int makeSelection(String[] menuItems) {
        boolean retry;
        int selected = 0;
        int length =menuItems.length;
        do {
            retry = false;
            displayMenu(menuItems);
            Scanner scanner = new Scanner(System.in);
            try {
                System.out.println("Please select a number for the menu option");

                int option = scanner.nextInt();
                for (int i = 1; i <= length; i++) {
                    if (option == i) {
                        return i;
                    }
                }
                throw new Exception("Please enter a valid number selection");
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
                retry = true;
            }

        } while (retry);
        return selected;
    }

    public void printAllRooms(Collection<IRoom> rooms) {
        System.out.println(" ROOM | ROOMTYPE | PRICE");
        System.out.println("-------------------------");
        for (IRoom room : rooms){
            System.out.println(room);
            System.out.println("-------------------------");
        }
    }
}
