package com.i3AdvancedTech.i3connect;

public class BuyerModel {
    private String CropName, Buyer_Id, Farmer_Id;
    private float SellingPrice,CropQty;

    public BuyerModel() { }

    public BuyerModel(String cropName, String buyer_Id, String farmer_Id, float sellingPrice, float cropQty) {
        CropName = cropName;
        Buyer_Id = buyer_Id;
        Farmer_Id = farmer_Id;
        SellingPrice = sellingPrice;
        CropQty = cropQty;
    }

    public String getCropName() {
        return CropName;
    }

    public void setCropName(String cropName) {
        CropName = cropName;
    }

    public String getBuyer_Id() {
        return Buyer_Id;
    }

    public void setBuyer_Id(String buyer_Id) {
        Buyer_Id = buyer_Id;
    }

    public String getFarmer_Id() {
        return Farmer_Id;
    }

    public void setFarmer_Id(String farmer_Id) {
        Farmer_Id = farmer_Id;
    }

    public float getSellingPrice() {
        return SellingPrice;
    }

    public void setSellingPrice(float sellingPrice) {
        SellingPrice = sellingPrice;
    }

    public float getCropQty() {
        return CropQty;
    }

    public void setCropQty(float cropQty) {
        CropQty = cropQty;
    }
}


