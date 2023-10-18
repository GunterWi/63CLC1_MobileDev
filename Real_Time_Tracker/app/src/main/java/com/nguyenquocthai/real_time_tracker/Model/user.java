package com.nguyenquocthai.real_time_tracker.Model;

public class user {
    private String id,firstname, lastname,circle_id,email, password,  date, image_url;
    private double latitude, longitude;

    public user(String id, String firstname, String lastname, String circle_id, String email, String password, String date, String image_url, double latitude, double longitude) {
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
    }
}
