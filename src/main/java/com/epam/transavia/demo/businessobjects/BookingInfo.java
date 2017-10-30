package com.epam.transavia.demo.businessobjects;

public class BookingInfo {

    private String bookingNumber;
    private String lastName;
    private String flightDate;
    private String flyingFrom;
    private String flyingTo;


    public String getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    public String getFlyingFrom() {
        return flyingFrom;
    }

    public void setFlyingFrom(String flyingFrom) {
        this.flyingFrom = flyingFrom;
    }

    public String getFlyingTo() {
        return flyingTo;
    }

    public void setFlyingTo(String flyingTo) {
        this.flyingTo = flyingTo;
    }

    public String getFlyingRoute(){
        return "Flying from: " + this.getFlyingFrom() + "; \n Flying to: " + this.getFlyingTo() + ";";
    }
}
