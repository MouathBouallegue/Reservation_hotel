package com.example.reservation_hotel;

public class Room {
    private String roomNumber;
    private String type;
    private boolean isAvailable;
    public Room() {

    }
    public Room(String roomNumber, String type, boolean isAvailable) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.isAvailable = isAvailable;
    }

    // Getters and Setters
    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}

