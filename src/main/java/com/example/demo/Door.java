package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class Door {
    private String material;

    public Door() {
        this.material = "Wood";
    }

    public Door(String material) {
        this.material = material;
    }

    @Override
    public String toString() {
        return "Door{" +
                "material='" + material + '\'' +
                '}';
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
