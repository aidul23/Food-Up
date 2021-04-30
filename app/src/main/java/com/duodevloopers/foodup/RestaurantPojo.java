package com.duodevloopers.foodup;

public class RestaurantPojo {
    private int mImage;
    private String mRestaurantName;
    private String mRestaurantCategory;
    private String mRestaurantRating;

    public RestaurantPojo(int mImage, String mRestaurantName, String mRestaurantCategory, String mRestaurantRating) {
        this.mImage = mImage;
        this.mRestaurantName = mRestaurantName;
        this.mRestaurantCategory = mRestaurantCategory;
        this.mRestaurantRating = mRestaurantRating;
    }

    public int getmImage() {
        return mImage;
    }

    public void setmImage(int mImage) {
        this.mImage = mImage;
    }

    public String getmRestaurantName() {
        return mRestaurantName;
    }

    public void setmRestaurantName(String mRestaurantName) {
        this.mRestaurantName = mRestaurantName;
    }

    public String getmRestaurantCategory() {
        return mRestaurantCategory;
    }

    public void setmRestaurantCategory(String mRestaurantCategory) {
        this.mRestaurantCategory = mRestaurantCategory;
    }

    public String getmRestaurantRating() {
        return mRestaurantRating;
    }

    public void setmRestaurantRating(String mRestaurantRating) {
        this.mRestaurantRating = mRestaurantRating;
    }
}
