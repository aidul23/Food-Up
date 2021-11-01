package com.duodevloopers.foodup.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class RestaurantItemPojo implements Parcelable {
    private int mImage;
    private String mFoodName;
    private String mFoodDes;
    private int mFoodPrice;
    private int quantity;
    private List<RestaurantItemPojo> list = new ArrayList<>();


    public RestaurantItemPojo(int mImage, String mFoodName, String mFoodDes, int mFoodPrice, int quantity) {
        this.mImage = mImage;
        this.mFoodName = mFoodName;
        this.mFoodDes = mFoodDes;
        this.mFoodPrice = mFoodPrice;
        this.quantity = quantity;
    }

    public RestaurantItemPojo(List<RestaurantItemPojo> list) {
        this.list = list;
    }


    protected RestaurantItemPojo(Parcel in) {
        mImage = in.readInt();
        mFoodName = in.readString();
        mFoodDes = in.readString();
        mFoodPrice = in.readInt();
        quantity = in.readInt();
        list = in.createTypedArrayList(RestaurantItemPojo.CREATOR);
    }

    public static final Creator<RestaurantItemPojo> CREATOR = new Creator<RestaurantItemPojo>() {
        @Override
        public RestaurantItemPojo createFromParcel(Parcel in) {
            return new RestaurantItemPojo(in);
        }

        @Override
        public RestaurantItemPojo[] newArray(int size) {
            return new RestaurantItemPojo[size];
        }
    };

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

    public String getmFoodDes() {
        return mFoodDes;
    }

    public void setmFoodDes(String mFoodDes) {
        this.mFoodDes = mFoodDes;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mImage);
        dest.writeString(mFoodName);
        dest.writeString(mFoodDes);
        dest.writeInt(mFoodPrice);
        dest.writeInt(quantity);
        dest.writeTypedList(list);
    }

    @Override
    public String toString() {
        return "RestaurantItemPojo{" +
                "mImage=" + mImage +
                ", mFoodName='" + mFoodName + '\'' +
                ", mFoodDes='" + mFoodDes + '\'' +
                ", mFoodPrice=" + mFoodPrice +
                ", quantity=" + quantity +
                ", list=" + list +
                '}';
    }
}
