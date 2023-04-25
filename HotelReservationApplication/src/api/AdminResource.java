package api;

import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;


public class AdminResource {
    public Collection<Customer> getAllCustomers(){
        return CustomerService.getInstance().getAllCustomers();
    }
    public Collection<IRoom> getAllRooms(){
        return ReservationService.getInstance().getAllTheRooms();
        }
    public static void displayAllReservations(){
        ReservationService.getInstance().displayAllReservations();

    }
    public void addRoom(List<IRoom> rooms){
        for(IRoom room : rooms){
            ReservationService.getInstance().addRoom(room);
        }
    }
    public static IRoom createNewRoom(String roomNumber, RoomType roomType, Double roomPrice){
        return ReservationService.getInstance().createNewRoom(roomNumber,roomType,roomPrice);
    }
}
