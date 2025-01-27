package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class Window {
    private String material;
    private String size;

    public Window() {
        this.material = "Glass";
        this.size = "Medium";
    }

    public Window(String material, String size) {
        this.material = material;
        this.size = size;
    }

    @Override
    public String toString() {
        return "Window{" +
                "material='" + material + '\'' +
                ", size='" + size + '\'' +
                '}';
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
