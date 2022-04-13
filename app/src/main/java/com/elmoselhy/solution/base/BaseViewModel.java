package com.elmoselhy.solution.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();

}
