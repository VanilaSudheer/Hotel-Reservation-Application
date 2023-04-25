package model;

public class FreeRoom extends Room {

    public FreeRoom(String roomNumber, RoomType roomType, Double roomPrice) {
        super(roomNumber, roomType, roomPrice);
    }

    @Override
    public String toString(){
        return  " " + roomNumber +
                " | " + roomType +
                "  | FREE!";
    }
}
