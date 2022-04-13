package com.elmoselhy.solution.commons;

import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

public class Validator {
    public static boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    public static boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 6;
    }




    public static boolean isValidPhoneNumber(String number){
        return number.length()>6;
    }

    public static TranslateAnimation shakeError() {
        TranslateAnimation shake = new TranslateAnimation(0, 10, 0, 0);
        shake.setDuration(500);
        shake.setInterpolator(new CycleInterpolator(7));
        return shake;
    }
}
