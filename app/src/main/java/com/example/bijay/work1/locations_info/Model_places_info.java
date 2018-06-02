package com.example.bijay.work1.locations_info;

/**
 * Created by Bijay on 5/29/2018.
 */

public class Model_places_info {

 String hospital_name,hospital_address,hospital_lattitude,hospital_longitude,hospital_open,hospital_close;

    public Model_places_info() {

    }

    public Model_places_info(String hospital_name, String hospital_address, String hospital_lattitude, String hospital_longitude, String hospital_open, String hospital_close) {
        this.hospital_name = hospital_name;
        this.hospital_address = hospital_address;
        this.hospital_lattitude = hospital_lattitude;
        this.hospital_longitude = hospital_longitude;
        this.hospital_open = hospital_open;
        this.hospital_close = hospital_close;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public String getHospital_address() {
        return hospital_address;
    }

    public void setHospital_address(String hospital_address) {
        this.hospital_address = hospital_address;
    }

    public String getHospital_lattitude() {
        return hospital_lattitude;
    }

    public void setHospital_lattitude(String hospital_lattitude) {
        this.hospital_lattitude = hospital_lattitude;
    }

    public String getHospital_longitude() {
        return hospital_longitude;
    }

    public void setHospital_longitude(String hospital_longitude) {
        this.hospital_longitude = hospital_longitude;
    }

    public String getHospital_open() {
        return hospital_open;
    }

    public void setHospital_open(String hospital_open) {
        this.hospital_open = hospital_open;
    }

    public String getHospital_close() {
        return hospital_close;
    }

    public void setHospital_close(String hospital_close) {
        this.hospital_close = hospital_close;
    }
}
