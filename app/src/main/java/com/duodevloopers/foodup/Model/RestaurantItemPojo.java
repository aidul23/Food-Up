package com.duodevloopers.foodup.Model;

public class RestaurantItemPojo {
    private int mImage;
    private String mFoodName;
    private int mFoodPrice;

    public RestaurantItemPojo(int mImage, String mFoodName, int mFoodPrice) {
        this.mImage = mImage;
        this.mFoodName = mFoodName;
        this.mFoodPrice = mFoodPrice;
    }

    public int getmImage() {
        return mImage;
    }

    public void setmImage(int mImage) {
        this.mImage = mImage;
    }

    public String getmFoodName() {
        return mFoodName;
    }

    public void setmFoodName(String mFoodName) {
        this.mFoodName = mFoodName;
    }

    public int getmFoodPrice() {
        return mFoodPrice;
    }

    public void setmFoodPrice(int mFoodPrice) {
        this.mFoodPrice = mFoodPrice;
    }
}
