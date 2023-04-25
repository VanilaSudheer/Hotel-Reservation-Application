package model;

import java.util.Objects;

public class Room implements IRoom {
    protected String roomNumber;
    protected Double roomPrice;
    protected RoomType roomType;

    public Room(String roomNumber,RoomType roomType, Double roomPrice) {
        this.roomNumber = roomNumber;
        this.roomPrice = roomPrice;
        this.roomType = roomType;
    }
    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return roomPrice;
    }

    @Override
    public RoomType getRoomType() {
        return roomType;
    }

    @Override
    public boolean isFree() {
        return this.roomPrice <= 0;
    }

    @Override
    public String toString() {
        return " " + roomNumber +
                "   | " + roomType +
                "   | " + roomPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room room)) return false;
        return getRoomNumber().equals(room.getRoomNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoomNumber());
    }
}
