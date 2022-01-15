package com.corosoftware.myinventory;

public class ItemModal {

    private String description;
    private String brand;
    private String rc;
    private String image;
    private String location;
    private String gps;
    private int id;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getRc() {
        return rc;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public String getGps() { return gps; }

    public void setGps(String gps) { this.gps = gps; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Constructor
    public ItemModal(String description, String brand, String rc, String image, String location, String gps) {

        this.description = description;
        this.brand = brand;
        this.rc = rc;
        this.image = image;
        this.location = location;
        this.gps = gps;
    }

}
