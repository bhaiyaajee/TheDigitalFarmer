package com.i3AdvancedTech.i3connect;

public class CropModel {

    private String CropName, Farmer_Id;
    private float CropPrice,CropQty;

    public CropModel() {}

    public CropModel(String cropName, float cropPrice, float cropQty, String farmer_Id) {
        CropName = cropName;
        Farmer_Id = farmer_Id;
        CropPrice = cropPrice;
        CropQty = cropQty;
    }

    public String getCropName() {
        return CropName;
    }

    public void setCropName(String cropName) {
        CropName = cropName;
    }

    public String getFarmer_Id() {
        return Farmer_Id;
    }

    public void setFarmer_Id(String farmer_Id) {
        Farmer_Id = farmer_Id;
    }

    public float getCropPrice() {
        return CropPrice;
    }

    public void setCropPrice(float cropPrice) {
        CropPrice = cropPrice;
    }

    public float getCropQty() {
        return CropQty;
    }

    public void setCropQty(float cropQty) {
        CropQty = cropQty;
    }
}
