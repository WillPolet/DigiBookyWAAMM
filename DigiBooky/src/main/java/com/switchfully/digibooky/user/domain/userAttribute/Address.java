package com.switchfully.digibooky.user.domain.userAttribute;

public class Address {
    private String streetName;
    private String streetNumber;
    private String zipCode;
    private String city;

    public Address(){};

    public Address(String streetName, String streetNumber, String zipCode, String city) {
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.city = city;
    }


    public String getStreetName() {
        return streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }
}
