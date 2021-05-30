package com.example.carmarket;

public class car {
    private int id;
    private String image;
    private String model;
    private String color;
    private Double dpl;
    private String description;
    private int phone;

    public car(int id, String image, String model, String color, Double dpl, String description ,int phone){
        this.id = id;
        this.image = image;
        this.model = model;
        this.color = color;
        this.dpl = dpl;
        this.description = description;
        this.phone=phone;
    }
    public car(String image, String model, String color, Double dpl, String description,int phone) {
        this.id = id;
        this.image = image;
        this.model = model;
        this.color = color;
        this.dpl = dpl;
        this.description = description;
        this.phone=phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getDpl() {
        return dpl;
    }

    public void setDpl(Double dpl) {
        this.dpl = dpl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
}
