package com.smartgateapps.logic.model;

import java.util.List;

/**
 * Created by Raafat on 23/03/2016.
 */
public class PhonNums {
    public static final String GET_PHONE_NUMS_URL = "http://www.logic.com.lb/ajax/AppHandler/get_phone_numbers";
    private boolean HasError;
    private String ErrorMessage;
    private PhonNumsReturnedData ReturnData;

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

    public PhonNumsReturnedData getReturnData() {
        return ReturnData;
    }

    public void setReturnData(PhonNumsReturnedData returnData) {
        ReturnData = returnData;
    }

    public static class PhonNumsReturnedData{
        private String Landline;
        private String Mobile;

        public String getLandLine() {
            return Landline;
        }

        public void setLandLine(String landLine) {
            Landline = landLine;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String mobile) {
            Mobile = mobile;
        }
    }
}
