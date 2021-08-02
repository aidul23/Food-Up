package com.duodevloopers.foodup;

import com.duodevloopers.foodup.Model.PopularItem;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<Integer> subTotal = new MutableLiveData<>();

    // selected item
    private final MutableLiveData<PopularItem> popularItemMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<PopularItem> getPopularItemMutableLiveData() {
        return popularItemMutableLiveData;
    }

    public void increaseSubtotal(int cost){
            int currentCost = subTotal.getValue();
            subTotal.postValue(currentCost + cost);
    }

    public void decreaseSubTotal(int cost){
            int currentCost = subTotal.getValue();
            subTotal.postValue(currentCost - cost);
    }

    public MutableLiveData<Integer> getSubTotal() {
        return subTotal;
    }
}
