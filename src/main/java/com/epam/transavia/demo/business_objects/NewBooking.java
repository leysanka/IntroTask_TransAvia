package com.epam.transavia.demo.business_objects;

import com.epam.transavia.demo.business_objects.bo_factory.NewBookingBuilder;

public class NewBooking {

    private String fromDestination;
    private String toDestination;
    private String departDate;
    private String returnDate;
    private String bookingError;
    private int adultsCount = 1;
    private int childrenCount = 0;
    private int babiesCount = 0;

    public NewBooking() {
    }

    public NewBooking(NewBookingBuilder bookingBuilder) {
        this.fromDestination = bookingBuilder.getFromDestination();
        this.toDestination = bookingBuilder.getToDestination();
        this.departDate = bookingBuilder.getDepartDate();
        this.returnDate = bookingBuilder.getReturnDate();
        this.adultsCount = bookingBuilder.getAdultsCount();
        this.childrenCount = bookingBuilder.getChildrenCount();
        this.babiesCount = bookingBuilder.getBabiesCount();
    }

    public String getFromDestination() {
        return fromDestination;
    }

    public void setFromDestination(String fromDestination) {
        this.fromDestination = fromDestination;
    }

    public String getToDestination() {
        return toDestination;
    }

    public void setToDestination(String toDestination) {
        this.toDestination = toDestination;
    }

    public String getDepartDate() {
        return departDate;
    }

    public void setDepartDate(String departDate) {
        this.departDate = departDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getBookingError() {
        return bookingError;
    }

    public void setBookingError(String bookingError) {
        this.bookingError = bookingError;
    }

    public int getAdultsCount() {
        return adultsCount;
    }

    public void setAdultsCount(int adultsCount) {
        this.adultsCount = adultsCount;
    }

    public int getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }

    public int getBabiesCount() {
        return babiesCount;
    }

    public void setBabiesCount(int babiesCount) {
        this.babiesCount = babiesCount;
    }
}
