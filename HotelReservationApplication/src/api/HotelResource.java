package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;


public class HotelResource {
    public static Customer getCustomer(String email){
        return CustomerService.getInstance().getACustomer(email);
    }
    public static void createCustomer(String firstName, String lastName, String email){
        CustomerService.getInstance().createACustomer(firstName,lastName,email);
    }
    public static IRoom getRoom(String roomNumber){
        return  ReservationService.getInstance().getARoom(roomNumber);
    }
    public static Reservation bookARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        return ReservationService.getInstance().reserveARoom(customer, room, checkInDate, checkOutDate);
    }
    public static Collection<Reservation> getCustomersReservations(String customerEmail){
        return ReservationService.getInstance().getCustomerReservation(customerEmail);
    }
    public static Collection<IRoom> findARoom(Date checkInDate, Date checkOutDate){
        return ReservationService.getInstance().findRooms(checkInDate,checkOutDate);
    }
}
