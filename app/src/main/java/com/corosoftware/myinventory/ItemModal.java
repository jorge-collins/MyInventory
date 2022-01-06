package com.corosoftware.myinventory;

public class ItemModal {

    private String description;
    private String brand;
    private String rc;
    private String image;

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

    public ItemModal(String description, String brand, String rc, String image) {

        this.description = description;
        this.brand = brand;
        this.rc = rc;
        this.image = image;
    }

}
