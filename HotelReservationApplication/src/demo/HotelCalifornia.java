package demo;

import api.AdminResource;
import model.*;
import service.CustomerService;
import service.ReservationService;

import java.util.Date;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;


public class HotelCalifornia {
    public void hotelDemo(){
        customerCollection();
        roomCollection();
        reservationCollection();
    }
    public void customerCollection(){
        String firstName = "first";
        String lastName = "last";
        String email;
        for (int i = 2023; i>2015; i--){
            email = "first" + i + "@gmail.com";
            Customer customer = new Customer(firstName,lastName,email);
            CustomerService.getInstance().addCustomer(customer);
        }

    }
    public void roomCollection(){
        String roomNumber = "100";
        Double roomPrice;
        roomPrice = 0D;
        RoomType roomType = RoomType.SINGLE;
        IRoom freeRoom = new FreeRoom(roomNumber, roomType, roomPrice);
        ReservationService.getInstance().addRoom(freeRoom);
        IRoom room;
        for(int i=101; i<150; i++){
            int rem = i % 3;
            switch (rem){
                case 1-> {
                    roomNumber = String.valueOf(i);
                    roomPrice = 250.0;
                    roomType = RoomType.SINGLE;
                    room = new Room(roomNumber, roomType, roomPrice);
                    ReservationService.getInstance().addRoom(room);
                }
                case 2 -> {
                    roomNumber = String.valueOf(i);
                    roomPrice = 350.0;
                    roomType = RoomType.DOUBLE;
                    room = new Room(roomNumber, roomType, roomPrice);
                    ReservationService.getInstance().addRoom(room);

                }
                  
            }
            
        }

    }
    public void reservationCollection(){
        Customer customer;
        IRoom room;
        Collection<Customer> customers ;
        customers = CustomerService.getInstance().getAllCustomers();
        Iterator<Customer> customerIterator = customers.iterator();
        Collection<IRoom> rooms ;
        rooms = ReservationService.getInstance().getAllTheRooms();
        Iterator<IRoom> roomIterator = rooms.iterator();
        Date checkInDate;
        Date checkOutDate;
        checkInDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(checkInDate);
        for (int i=1; i<7; i++) {
            calendar.add(Calendar.DATE,3);
            checkOutDate = calendar.getTime();
            customer = customerIterator.next();
            room =roomIterator.next();
            Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
            ReservationService.getInstance().addReservation(reservation);
            calendar.add(Calendar.DATE,-2);
            checkInDate = calendar.getTime();
        }




    }


}
