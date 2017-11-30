package com.epam.transavia.demo.tests.features.api.bo;

import java.util.HashMap;
import java.util.Map;

public class PlaceHolderUser {

    private int id;
    private String name;
    private String username;
    private String email;
    private Map<String, Object> address;
    private String phone;
    private String website;
    private Map<String, String> company;


    public PlaceHolderUser() {

    }

    public static PlaceHolderUser createTestUser(){
        PlaceHolderUser user = new PlaceHolderUser();
        user.setName("John Boo");
        user.setUsername("TestUser");
        user.setCompany(createTestCompany());
        return user;
    }

    private static Map<String, String> createTestCompany() {
       Map<String, String> testCompany = new HashMap<>();
       testCompany.put("name", "test_Comp");
       testCompany.put("catchphrase", "Multi-layered client-server neural-net");
       testCompany.put("bs", "e-markets");
       return testCompany;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, Object> getAddress() {
        return address;
    }

    public void setAddress(Map<String, Object> address) {
        this.address = address;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Map<String, String> getCompany() {
        return company;
    }

    public void setCompany(Map<String, String> company) {
        this.company = company;
    }
}
