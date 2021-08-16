package com.duodevloopers.foodup.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class SummaryItemPojo implements Parcelable {
    private List<RestaurantItemPojo> list;

    public List<RestaurantItemPojo> getList() {
        return list;
    }

    public void setList(List<RestaurantItemPojo> list) {
        this.list = list;
    }

    public static Creator<SummaryItemPojo> getCREATOR() {
        return CREATOR;
    }

    public SummaryItemPojo(List<RestaurantItemPojo> list) {
        this.list = list;
    }

    protected SummaryItemPojo(Parcel in) {
        list = in.createTypedArrayList(RestaurantItemPojo.CREATOR);
    }

    public static final Creator<SummaryItemPojo> CREATOR = new Creator<SummaryItemPojo>() {
        @Override
        public SummaryItemPojo createFromParcel(Parcel in) {
            return new SummaryItemPojo(in);
        }

        @Override
        public SummaryItemPojo[] newArray(int size) {
            return new SummaryItemPojo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(list);
    }
}
