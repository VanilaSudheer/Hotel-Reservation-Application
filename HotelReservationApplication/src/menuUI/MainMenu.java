package menuUI;

import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.SimpleDateFormat;
import java.util.*;

import java.util.regex.Pattern;

public class MainMenu extends AnyMenu {
    private final String [] menuItems = new String[] {"Find and reserve a room",
            "See my reservations",
            "Create an account",
            "Admin",
            "Exit"};
    Date checkInDate;
    Date checkOutDate;
    public  void showMainMenu() {
        boolean displayMain;
        do {
            displayMain = true;
            int option = makeSelection(menuItems);
            switch (option) {
                case 1 -> {
                    Date todaysDate = new Date();
                    Collection<IRoom> rooms;
                    IRoom room;
                    Customer validCustomer;
                    IRoom selectedRoom;
                    String email;

                    try {
                        validCustomer = checkCustomer();
                        if(validCustomer == null){
                            //System.out.println();
                            throw new Exception("***Please create an account***");
                        }
                        checkInDate = getADateAfter(todaysDate,"Check in date");
                        checkOutDate = getADateAfter(checkInDate,"Check out date");
                        rooms = findRooms(checkInDate, checkOutDate);
                        if(rooms.isEmpty()) {
                           // System.out.println();
                            throw new Exception("***Sorry, all rooms are reserved***");
                        }

                        selectedRoom = selectARoom(rooms);
                        email = validCustomer.getEmail();
                        makeAReservation(validCustomer,selectedRoom,checkInDate,checkOutDate);
                        System.out.println("***Check in " + checkInDate +
                                " Check out " + checkOutDate + "***");
                        displayReservations(email);

                    }catch (Exception e){
                        System.out.println(e.getLocalizedMessage());
                    }
                }
                case 2 -> {
                    String email = emailFromUser();
                    displayReservations(email);
                }
                case 3 -> {
                    Scanner scanner = new Scanner(System.in);
                    String firstName = null;
                    String lastName = null;
                    String email;
                    firstName = getAString("Please enter your firstname: ");
                    lastName = getAString("Please enter your lastname: ");
                    email = emailFromUser();
                    if(email != null ){
                        if(HotelResource.getCustomer(email) == null){
                            HotelResource.createCustomer(firstName, lastName, email);
                            System.out.println("Thank You ,See you soon");
                        }else {
                            System.out.println("Welcome back this email already exists");
                        }
                    }

                }
                case 4 -> {
                    AdminMenu adminMenu = new AdminMenu();
                    displayMain = false;
                    adminMenu.showAdminMenu();
                }
                case 5 -> {
                    displayMain = false;
                }
            }
        }while(displayMain == true);
    }

    public String getAString(String message){
        boolean retry;
        String userInput = null;
        do {
            retry = false;
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println(message);
                userInput = scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Somthing went wrong,Try again");
                retry = true;
            }
        }while (retry);
        return userInput;
    }
    public String emailFromUser(){
        boolean retry;
        String email;
        do {
            retry = false;
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Please enter your email_id: ");
                email = scanner.nextLine();
                if (regExTester(email)) {
                    return email;
                } else {
                    throw new Exception("Please enter a valid email");
                }
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
                retry = true;
            }
        } while (retry);
        return null;
    }
    public boolean regExTester(String email){
        String emailRegex = "^(.+)@(.+).(.+)";
        Pattern pattern = Pattern.compile(emailRegex);
        if(pattern.matcher(email).matches()) {
            return true;
        } else {
            throw new IllegalArgumentException("Please enter a valid email id");
        }
    }
    public Customer checkCustomer() {
        Customer customer;
        String email = emailFromUser();
        customer = HotelResource.getCustomer(email);
        if (customer != null) {
            return customer;
        } else {
            return null;
        }

    }
    public Date getADateAfter(Date startDate,String message){
        boolean dateRetry;
        String givenDate;
        Date endDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        do{
            try {

                dateRetry = false;
                System.out.println("Please enter " + message + " MM/dd/yyyy: ");
                Scanner scanner = new Scanner(System.in);
                givenDate = scanner.next();
                endDate = sdf.parse(givenDate);
                if (endDate.before(startDate) || startDate.equals(endDate)){
                    throw new Exception("Please select a future date");
                }
            }catch (Exception e){
                dateRetry = true;
                System.out.println(e.getLocalizedMessage());
            }
        }while (dateRetry);
        return endDate;
    }
    public Collection<IRoom> findRooms(Date checkIn, Date checkOut){
        Collection<IRoom> rooms = null;
        int extendDays = 1;
        Calendar calendarIn = Calendar.getInstance();
        Calendar calendarOut = Calendar.getInstance();
        SimpleDateFormat sdf =  new SimpleDateFormat("MM/dd/yyyy");
        calendarIn.setTime(checkIn);
        calendarOut.setTime(checkOut);
        do{
            //first time do only once.if 3 days to extend -do 3 loops
            for (int i = 0; i <extendDays ; i++){
                rooms = HotelResource.findARoom(checkIn, checkOut);
                if(!(rooms.isEmpty()))
                {
                    checkInDate = checkIn;
                    checkOutDate = checkOut;
                    break;
                }
                calendarIn.add(Calendar.DATE,1);
                checkIn = calendarIn.getTime();
                calendarOut.add(Calendar.DATE,1);
                checkOut = calendarOut.getTime();
            }
            if(rooms.isEmpty()){
                extendDays = extendDaysFromUser();
            }else break;

        }while (extendDays != 0);
        return rooms;
    }
    public int extendDaysFromUser(){
        boolean retry;
        int extendDays = 0;

        do{
            retry = false;
            System.out.println(" Would you like to extend search?y/n");
            try {
                Scanner scanner = new Scanner(System.in);
                if(scanner.next().equals("y")) {
                    System.out.println("Please enter number of days to extend search");
                    extendDays = scanner.nextInt();
                }else {
                    throw new Exception("enter y/n");
                }
            }catch (Exception e){
                System.out.println(e.getLocalizedMessage());
                retry = true;
            }
        }while (retry);
        return extendDays;
    }
    public IRoom selectARoom(Collection<IRoom> rooms){
        boolean isnotroom ;
        String selectedRoom;
        IRoom room = null;
        do{
            isnotroom = false;
            try {
                printAllRooms(rooms);
                System.out.println("Please enter a room number from the above :");
                Scanner scanner = new Scanner(System.in);
                selectedRoom = scanner.nextLine();
                room = HotelResource.getRoom(selectedRoom);
                if(room == null){
                    throw new Exception("Please enter valid Room number");
                }
            }catch (Exception e){
                isnotroom = true;
            }
        }while (isnotroom);
        return room;
    }
    public void makeAReservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        Reservation newReservation;
        newReservation = HotelResource.bookARoom(customer,room,checkInDate,checkOutDate);
    }
    public void displayReservations(String email){
        Collection<Reservation> reservations = HotelResource.getCustomersReservations(email);
        if(reservations != null){
            System.out.println("Thank you, here are your reservations");
            System.out.println("------------------------" +
                    "---------------------------------------" +
                    "----------------------------");
            System.out.println("   CUSTOMER" +
                    "                        " +
                    "| ROOM  | ROOMTYPE | PRICE |" +
                    "  CHECK IN  | CHECK OUT ");
            System.out.println("--------------------------" +
                    "---------------------------------------" +
                    "--------------------------");
            Iterator<Reservation> reservationIterator;
            reservationIterator = reservations.iterator();
            while (reservationIterator.hasNext()){
                System.out.println(reservationIterator.next());
            }
        }else {
            System.out.println("No reservation exists");
        }

        }
}
