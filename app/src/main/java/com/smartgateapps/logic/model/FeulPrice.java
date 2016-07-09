package com.smartgateapps.logic.model;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.smartgateapps.logic.MyApplication;

import java.util.List;

/**
 * Created by Raafat on 28/02/2016.
 */
public class FeulPrice {

    public static final String GET_FUEL_PRICE_URL = "http://www.logic.com.lb/ajax/AppHandler/get_prices";

    private boolean HasError;
    private String ErrorMessage;
    private List<FeulPriceReturnd> ReturnData;

    public FeulPrice(){}
    public FeulPrice(boolean hasError, String errorMessage, List<FeulPriceReturnd> returnData) {
        HasError = hasError;
        ErrorMessage = errorMessage;
        ReturnData = returnData;
    }

    public boolean isHasError() {
        return HasError;
    }

    public void setHasError(boolean hasError) {
        HasError = hasError;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public List<FeulPriceReturnd> getReturnData() {
        return ReturnData;
    }

    public void setReturnData(List<FeulPriceReturnd> returnData) {
        ReturnData = returnData;
    }

    public static class FeulPriceReturnd{
        private String Title;
        private String Price;

        public FeulPriceReturnd(String title, String price) {
            Title = title;
            Price = price;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String price) {
            Price = price;
        }
    }
}
