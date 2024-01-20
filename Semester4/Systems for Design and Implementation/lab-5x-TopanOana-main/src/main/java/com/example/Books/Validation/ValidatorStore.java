package com.example.Books.Validation;

import com.example.Books.Exception.StoreValidationException;
import com.example.Books.Model.Store;

public class ValidatorStore implements Validator<Store>{
    @Override
    public void validate(Store store) {
        StringBuilder errors = new StringBuilder();
        if (store.getStoreName().length()<4)
            errors.append("store name too short (length<4)\n");
        if(store.getAddress().length()<4)
            errors.append("store address too short (length<4)\n");
        if(store.getContactNumber().length()!=10)
            errors.append("store contact number not 10 characters long\n");
        if(!(store.getOpeningHour()>=0 && store.getOpeningHour()<=23))
            errors.append("store opening hour not an hour\n");
        if(!(store.getClosingHour()>=0 && store.getClosingHour()<=23))
            errors.append("store closing hour not an hour\n");
        if(store.getClosingHour()<= store.getOpeningHour())
            errors.append("store hours are not valid, closing hour is before opening\n");
        if(!errors.isEmpty())
            throw new StoreValidationException(errors.toString());
    }
}
