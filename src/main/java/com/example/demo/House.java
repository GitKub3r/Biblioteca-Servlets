package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class House {
    private String direction;
    private List<Room> rooms;

    public House() {
    }

    @Autowired
    public House(List<Room> rooms) {
        this.direction = "123 Default Street";
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "House{" +
                "direction='" + direction + '\'' +
                ", rooms=" + rooms +
                '}';
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
