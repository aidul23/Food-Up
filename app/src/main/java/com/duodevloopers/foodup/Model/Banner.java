package com.duodevloopers.foodup.Model;

public class Banner {
    private String limiterOffer,offerQuantity,offerLimit;
    private int image;

    public Banner(String limiterOffer, String offerQuantity, String offerLimit, int image) {
        this.limiterOffer = limiterOffer;
        this.offerQuantity = offerQuantity;
        this.offerLimit = offerLimit;
        this.image = image;
    }

    public String getLimiterOffer() {
        return limiterOffer;
    }

    public void setLimiterOffer(String limiterOffer) {
        this.limiterOffer = limiterOffer;
    }

    public String getOfferQuantity() {
        return offerQuantity;
    }

    public void setOfferQuantity(String offerQuantity) {
        this.offerQuantity = offerQuantity;
    }

    public String getOfferLimit() {
        return offerLimit;
    }

    public void setOfferLimit(String offerLimit) {
        this.offerLimit = offerLimit;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
