package com.smartgateapps.logic.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Raafat on 29/02/2016.
 */
public class FeulRequest implements Serializable {

    public static final String ACCESS_URL = "http://www.logic.com.lb/ajax/AppHandler/send_request";

    private String name;
    private String phone;
    private String email;
    private String area;
    private String street;
    private String building;
    private String info;
    private RequestType type;
    private String quantity;
    private String notes;
    private String lat;
    private String lng;


    public FeulRequest() {
    }

    public String prepareUrl() {
        String res = "?" +
                "name=" + this.getEncodedName() + "&" +
                "phone=" + this.getEncodedPhone() + "&" +
                "area=" + this.getEncodedArea() + "&" +
                "street=" + this.getEncodedStreet() + "&" +
                "building=" + this.getEncodedBuilding() + "&" +
                "type=" + ((this.getType() == RequestType.GREEN)?"1":"2")  + "&" +
                "quantity=" + this.getQuantity() + "&" +
                "lat=" + this.getLat() + "&" +
                "lng=" + this.getLng();
        res += (this.getEmail() == null ? "" : "&email=" + this.getEncodedEmail());
        res += (this.getInfo() == null ? "" : "&info=" + this.getEncodedInfo());
        res += (this.getNotes() == null ? "" : "&notes=" + this.getEncoedNotes());
        return ACCESS_URL + res;
    }

    public String getEncodedName() {
        String res = "";
        try {
            res = URLEncoder.encode(this.getName(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return res;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEncodedPhone() {
        String res = "";
        try {
            res = URLEncoder.encode(this.getPhone(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public String getEncodedEmail() {
        String res = "";
        try {
            res = URLEncoder.encode(this.getEmail(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getArea() {
        return area;
    }

    public String getEncodedArea() {
        String res = "";
        try {
            res = URLEncoder.encode(this.getArea(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStreet() {
        return street;
    }

    public String getEncodedStreet() {
        String res = "";
        try {
            res = URLEncoder.encode(this.getStreet(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getEncodedBuilding() {
        String res = "";
        try {
            res = URLEncoder.encode(this.getBuilding(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return res;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getInfo() {
        return info;
    }

    public String getEncodedInfo() {
        String res = "";
        try {
            res = URLEncoder.encode(this.getInfo(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getNotes() {
        return notes;
    }

    public String getEncoedNotes() {
        String res = "";
        try {
            res = URLEncoder.encode(this.getNotes(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }


}
