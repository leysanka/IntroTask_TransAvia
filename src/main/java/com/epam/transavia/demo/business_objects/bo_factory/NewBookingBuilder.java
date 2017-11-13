package com.epam.transavia.demo.business_objects.bo_factory;

import com.epam.transavia.demo.business_objects.NewBooking;

public class NewBookingBuilder {

    private String fromDestination;
    private String toDestination;
    private String departDate;
    private String returnDate;
    private int adultsCount;
    private int childrenCount;
    private int babiesCount;


    public NewBookingBuilder setFromDestination(String destination) {
        this.fromDestination = destination;
        return this;
    }

    public NewBookingBuilder setToDestination(String destination) {
        this.toDestination = destination;
        return this;
    }

    public NewBookingBuilder setDepartDate(String departDate) {
        this.departDate = departDate;
        return this;
    }

    public NewBookingBuilder setReturnDate(String returnDate) {
        this.returnDate = returnDate;
        return this;
    }

    public NewBookingBuilder setAdultsCount(int adultsCount) {
        this.adultsCount = adultsCount;
        return this;
    }

    public NewBookingBuilder setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
        return this;
    }

    public NewBookingBuilder setBabiesCount(int babiesCount) {
        this.babiesCount = babiesCount;
        return this;
    }

    public NewBooking getBooking(){
        return new NewBooking(this);
    }

    public String getFromDestination() {
        return fromDestination;
    }

    public String getToDestination() {
        return toDestination;
    }

    public String getDepartDate() {
        return departDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public int getAdultsCount() {
        return adultsCount;
    }

    public int getChildrenCount() {
        return childrenCount;
    }

    public int getBabiesCount() {
        return babiesCount;
    }
}
