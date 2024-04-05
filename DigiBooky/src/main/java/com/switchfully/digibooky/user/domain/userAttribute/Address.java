package com.switchfully.digibooky.user.domain.userAttribute;

public class Address {
    private String streetName;
    private String streetNumber;
    private String zipCode;
    private String city;

    public Address(String streetName, String streetNumber, String zipCode, String city) {
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.city = city;
    }
}
