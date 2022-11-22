package com.mindhub.homebanking.utils;

public final class CardUtils {

    public static  String getCardNumber() {
        String cardNumber = (int)(Math.random()*(9999-1000)+1000)+"-"+
                (int)(Math.random()*(9999-1000)+1000)+"-"+
                (int)(Math.random()*(9999-1000)+1000)+"-"+
                (int)(Math.random()*(9999-1000)+1000);
        return cardNumber;
    }

    public static int getCVV(){
        int CVV= (int)(Math.random()*(999-100)+100);
        return CVV;
    }
}

