package com.nguyenquocthai.real_time_tracker.Model;

public class Users {
    private String id,firstname, lastname,circle_id,email, password,  date, image_url,notification;
    private double latitude, longitude;

    public Users(String id, String firstname, String lastname, String circle_id, String email, String password, String date, String image_url, double latitude, double longitude) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.circle_id = circle_id;
        this.email = email;
        this.password = password;
        this.date = date;
        this.image_url = image_url;
        this.latitude = latitude;
        this.longitude = longitude;
        //this.notification=notification;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCircle_id() {
        return circle_id;
    }

    public void setCircle_id(String circle_id) {
        this.circle_id = circle_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }
}
