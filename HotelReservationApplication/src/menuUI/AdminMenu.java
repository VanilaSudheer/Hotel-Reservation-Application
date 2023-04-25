package menuUI;

import api.AdminResource;
import demo.HotelCalifornia;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.*;

public class AdminMenu extends AnyMenu {

    private final String[] menuItems = new String[] {
            "See all customers",
            "See all rooms",
            "See all reservations",
            "Add a room",
            "test data",
            "Back to main menu"
    };

    public void showAdminMenu() {
        AdminResource adminResource = new AdminResource();
        Collection<Customer> customers;
        Collection<IRoom> rooms;
        boolean displayAdmin;
        do{
            displayAdmin = true;
            int option = makeSelection(menuItems);
            switch (option){
                case 1 -> {
                    customers = adminResource.getAllCustomers();
                    if (customers.isEmpty()) {
                        System.out.println("***No customers***");
                    } else {
                        Iterator<Customer> customerIterator = customers.iterator();
                        System.out.println(" Name                    Email ");
                        System.out.println("----------------------------------------");
                        while (customerIterator.hasNext()) {
                            System.out.println(customerIterator.next());
                        }
                    }
                }
                case 2 -> {
                    rooms = adminResource.getAllRooms();
                    if (rooms.isEmpty() ){
                        System.out.println("***Please create Rooms***");

                    }else {
                        printAllRooms(rooms);
                    }

                }
                case 3->{
                    AdminResource.displayAllReservations();
                    }
                case 4 ->{
                    boolean doMore;
                    List<IRoom> newRooms = new ArrayList<>();
                    do{
                        doMore = false;
                        newRooms.add(createARoom());
                        doMore = addMoreRooms();
                    }while (doMore);
                    adminResource.addRoom(newRooms);
                    printAllRooms(newRooms);

                }
                case 5 ->{
                    HotelCalifornia hotelCalifornia = new HotelCalifornia();
                    hotelCalifornia.hotelDemo();
                    System.out.println("***Populated with test data***");
                }
                case 6 ->{
                    displayAdmin = false;
                }
            }
        }while (displayAdmin == true);
        MainMenu mainMenu = new MainMenu();
        mainMenu.showMainMenu();
    }
    public IRoom createARoom(){
        boolean retryAll;
        IRoom newRoom = null;
        String roomNumber = null;
        RoomType roomType = RoomType.SINGLE;
        Double roomPrice;
        int option ;

        do {
            retryAll = false;
            try {
                roomNumber = roomNumberFromadmin();
                roomType = roomTypeFromAdmin();
                roomPrice = roomPriceFromAdmin();
                newRoom = createNewRoom(roomNumber,roomType,roomPrice);
                if(newRoom == null){
                    throw new Exception("Existing RoomNumber");
                }
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
                retryAll = true;
            }
        } while (retryAll);
        return newRoom;
    }
    public String roomNumberFromadmin() {
        boolean retry;
        String roomNumber = null;
        do {
            retry = false;
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Please enter Room Number");
                roomNumber = scanner.nextLine();
                if(roomNumber.matches("[0-9]+")){
                    return roomNumber;
                }else {
                    throw new Exception("Please enter numbers only");
                }
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
                retry = true;
            }
        }while (retry);
        return roomNumber;
    }
    public RoomType roomTypeFromAdmin() {
        boolean retry;
        int option;
        RoomType roomType = null;
        do{
            retry = false;
            try {
                System.out.println("Please enter room type 1 for SINGLE  2 for DOUBLE: ");
                Scanner scanner = new Scanner(System.in);
                option = scanner.nextInt();
                switch (option) {
                    case 1 -> {
                        roomType = RoomType.SINGLE;
                        break;
                    }
                    case 2 -> {
                        roomType = RoomType.DOUBLE;
                        break;
                    }
                    default -> throw new IllegalStateException("Unexpected value: ");
                }
            } catch (Exception e) {
                retry = true;
            }
        }while(retry);
        return roomType;
    }
    public Double roomPriceFromAdmin(){
        boolean retry = false;
        Double roomPrice;
        roomPrice =0D;
        do{
            retry = false;
            try{
                System.out.println("Please enter price : ");
                roomPrice = Double.parseDouble(new Scanner(System.in).nextLine());

            }catch (Exception e){
                System.out.println("enter numbers");
                retry = true;
            }
        }while (retry);

        return roomPrice;
    }
    public IRoom createNewRoom(String roomNumber,RoomType roomType,Double roomPrice) {
        return AdminResource.createNewRoom(roomNumber, roomType, roomPrice);
    }
    public boolean addMoreRooms(){
        boolean retry;
        boolean doMore = false;
        do {
            retry = false;
            try {
                System.out.println("Would you like to add more rooms?y/n:");
                Scanner scanner = new Scanner(System.in);
                String yesOrNo = scanner.nextLine();
                if (yesOrNo.equals("y") || yesOrNo.equals("n")){
                    if (yesOrNo.equals("y")){
                        doMore = true;
                    }
                }else {
                    throw new Exception("Please enter y/n");
                }
            } catch (Exception e) {
                retry = true;
            }
        }while (retry);
        return doMore;
    }


}
