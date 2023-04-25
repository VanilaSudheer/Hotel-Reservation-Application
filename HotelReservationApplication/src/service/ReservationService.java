package service;

import model.*;

import java.util.*;

public class ReservationService {
    public Map<String, IRoom> allRooms = new HashMap<>();
    public Map<String, Set<Reservation>> allCustomerReservation = new HashMap<>();
    public Set<String> allRoomNumbers = new HashSet<>();
    public Map<Date,Set<String>> reservationCalendar = new HashMap<>();
    private static final ReservationService instance= new ReservationService();

    private ReservationService(){}
    public static ReservationService getInstance(){
        return instance;
    }

    public void addRoom(IRoom room){
        String roomNumber = room.getRoomNumber();
        allRooms.put(roomNumber, room);
        allRoomNumbers.add(roomNumber);

    }
    public IRoom createNewRoom(String roomNumber, RoomType roomType, Double roomPrice){
        IRoom newRoom = null;
        if(allRoomNumbers.contains(roomNumber)){
            return null;
        }else if (roomPrice.equals(0D)){
            newRoom = new FreeRoom(roomNumber,roomType,roomPrice);
        } else {
            newRoom = new Room(roomNumber,roomType,roomPrice);
        }
        return newRoom;
    }
    private void addToReservationCalendar(String roomNumber,Date checkInDate,Date checkOutDate){
        Set<String> reservedRooms = new HashSet<>();
        Date date=checkInDate;
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(checkInDate);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(checkOutDate);
        calendar2.add(Calendar.DATE,1);
        checkOutDate = calendar2.getTime();

        while(date.before(checkOutDate)){
            if (reservationCalendar.get(date) != null){
                reservedRooms = reservationCalendar.get(date);
                reservedRooms.add(roomNumber);
                reservationCalendar.put(date,reservedRooms);
            }else {
                Set<String> roomNum = new HashSet<>();
                roomNum.add(roomNumber);
                reservationCalendar.put(date,roomNum);
            }
            calendar1.add(Calendar.DATE,1);
            date = calendar1.getTime();
        }
    }
    public void addReservation(Reservation reservation){
        Customer customer = reservation.getCustomer();
        String email = customer.getEmail();
        Set<Reservation> currCustomerRes = new HashSet<>();
        Set<Reservation> oldReservations;

        currCustomerRes.add(reservation);
        oldReservations = allCustomerReservation.get(email);
        if (oldReservations != null){
            currCustomerRes.addAll(oldReservations);
        }
        allCustomerReservation.put(email,currCustomerRes);

    }
    boolean validateDate(Date checkIn,Date checkOut){
        return checkIn.before(checkOut);
    }
    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        if(validateDate(checkInDate,checkOutDate)){
            Reservation newReservation = new Reservation(customer, room, checkInDate, checkOutDate);
            addReservation(newReservation);
            addToReservationCalendar(room.getRoomNumber(), checkInDate, checkOutDate);
            return newReservation;
        }else return null;
    }
    public Collection<IRoom> getAllTheRooms() {
        return (Collection<IRoom>) allRooms.values();
    }
    public IRoom getARoom(String roomNumber) {
        return allRooms.get(roomNumber);
    }
    public void displayAllReservations() {
        Collection<Set<Reservation> > allReservations = allCustomerReservation.values();
        if (allReservations.isEmpty()){
            System.out.println("***No reservations***");
        }else{
            System.out.println("------------------------" +
                    "---------------------------------------" +
                    "--------------------------");
            System.out.println("   CUSTOMER" +
                    "                        " +
                    "| ROOM |ROOMTYPE| PRICE |" +
                    "  CHECK IN  | CHECK OUT ");
            System.out.println("------------------------" +
                    "---------------------------------------" +
                    "--------------------------");
            Iterator<Set<Reservation>> reservationIterator;
            reservationIterator = allReservations.iterator();
            while (reservationIterator.hasNext()){
                Set<Reservation> reservations = reservationIterator.next();
                for (Reservation reservation : reservations) {
                    System.out.println(reservation);
                }
            }
        }
    }
    public Collection<Reservation> getCustomerReservation(String email) {
        return allCustomerReservation.get(email);
    }
    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Set<String> allRoomSet = new HashSet<>();
        Set<String> reservedRooms = new HashSet<>();
        Set<String> allReservedRooms = new HashSet<>();
        Collection<IRoom> iRooms = new HashSet<IRoom>();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(checkInDate);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(checkOutDate);
        calendar2.add(Calendar.DATE,1);
        checkOutDate = calendar2.getTime();
        Date date = checkInDate;
        allRoomSet.addAll(allRoomNumbers);
        while (date.before(checkOutDate)){
            reservedRooms=reservationCalendar.get(date);
            if(reservedRooms != null){
                allReservedRooms.addAll(reservedRooms);
            }
            calendar1.add(Calendar.DATE,1);
            date = calendar1.getTime();
        }
        allRoomSet.removeAll(allReservedRooms);
        for(String roomNum : allRoomSet){
            iRooms.add(allRooms.get(roomNum));
        }
        return iRooms;
    }
}

