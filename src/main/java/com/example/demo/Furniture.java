package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class Furniture {
    private String material;
    private String type;

    public Furniture() {
        this.material = "Wood";
        this.type = "Furniture";
    }

    public Furniture(String material, String type) {
        this.material = material;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Furniture{" +
                "material='" + material + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
