package com.smartgateapps.logic.model;

/**
 * Created by Raafat on 23/03/2016.
 */
public class OurServices {

    public final static String GET_OUR_SERVICES_URL = "http://www.logic.com.lb/ajax/AppHandler/get_services";
    private boolean HasError;
    private String ErrorMessage;
    private AboutUsReturnedData ReturnData;

    public OurServices() {
    }

    public OurServices(boolean hasError, String errorMessage, AboutUsReturnedData returnData) {
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

    public AboutUsReturnedData getReturnData() {
        return ReturnData;
    }

    public void setReturnData(AboutUsReturnedData returnData) {
        ReturnData = returnData;
    }

    public static class AboutUsReturnedData{
        private String Title;
        private String HTML;

        public AboutUsReturnedData() {
        }

        public AboutUsReturnedData(String title, String HTML) {
            Title = title;
            this.HTML = HTML;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getHTML() {
            return HTML;
        }

        public void setHTML(String HTML) {
            this.HTML = HTML;
        }
    }
}
