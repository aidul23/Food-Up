package com.duodevloopers.foodup;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<Integer> subTotal = new MutableLiveData<>();

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
