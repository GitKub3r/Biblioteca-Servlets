package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Room {
    private String name;
    private List<Window> windows;
    private List<Furniture> furnitures;
    private Door door;

    public Room() {

    }

    @Autowired
    public Room(List<Window> windows, List<Furniture> furnitures, Door door) {
        this.name = "Default Room";
        this.windows = windows;
        this.furnitures = furnitures;
        this.door = door;
    }

    @Override
    public String toString() {
        return "Room{" +
                "name='" + name + '\'' +
                ", windows=" + windows +
                ", furnitures=" + furnitures +
                ", door=" + door +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Window> getWindows() {
        return windows;
    }

    public void setWindows(List<Window> windows) {
        this.windows = windows;
    }

    public List<Furniture> getFurnitures() {
        return furnitures;
    }

    public void setFurnitures(List<Furniture> furnitures) {
        this.furnitures = furnitures;
    }

    public Door getDoor() {
        return door;
    }

    public void setDoor(Door door) {
        this.door = door;
    }
}
