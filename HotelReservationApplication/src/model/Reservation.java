package model;

import java.text.SimpleDateFormat;
import java.util.*;

public class Reservation {
    private final Customer customer;
    private final IRoom room;
    private final Date checkInDate;
    private final Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public IRoom getRoom() {
        return room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public String toString(){
        SimpleDateFormat sdf = new  SimpleDateFormat("MM/dd/yyyy");

        return  customer + " | " + room +
                " | "+ sdf.format(checkInDate) + " | " + sdf.format(checkOutDate);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation that)) return false;
        return getCustomer().equals(that.getCustomer()) &&
                getRoom().equals(that.getRoom()) &&
                getCheckInDate().equals(that.getCheckInDate()) &&
                getCheckOutDate().equals(that.getCheckOutDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCustomer());
    }

}
