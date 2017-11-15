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


    public NewBookingBuilder withFromDestination(String destination) {
        this.fromDestination = destination;
        return this;
    }

    public NewBookingBuilder withToDestination(String destination) {
        this.toDestination = destination;
        return this;
    }

    public NewBookingBuilder withDepartDate(String departDate) {
        this.departDate = departDate;
        return this;
    }

    public NewBookingBuilder withReturnDate(String returnDate) {
        this.returnDate = returnDate;
        return this;
    }

    public NewBookingBuilder withAdultsCount(int adultsCount) {
        this.adultsCount = adultsCount;
        return this;
    }

    public NewBookingBuilder withChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
        return this;
    }

    public NewBookingBuilder withBabiesCount(int babiesCount) {
        this.babiesCount = babiesCount;
        return this;
    }

    public NewBooking getBooking() {
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
