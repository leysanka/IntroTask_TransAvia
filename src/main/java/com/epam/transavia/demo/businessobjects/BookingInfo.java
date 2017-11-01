package com.epam.transavia.demo.businessobjects;

import java.time.LocalDateTime;

public class BookingInfo {

    private String bookingNumber;
    private String lastName;
    private String flightDate;
    private String flyingFrom;
    private String flyingTo;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
    private String totalPaymentAmount;
    private String totalPriceAmount;



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

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public String getTotalPaymentAmount() {
        return totalPaymentAmount;
    }

    public void setTotalPaymentAmount(String totalPaymentAmount) {
        this.totalPaymentAmount = totalPaymentAmount;
    }

    public String getTotalPriceAmount() {
        return totalPriceAmount;
    }

    public void setTotalPriceAmount(String totalPriceAmount) {
        this.totalPriceAmount = totalPriceAmount;
    }

    public String getFlyingRoute(){
        return "Flying from: " + this.getFlyingFrom() + "; \n Flying to: " + this.getFlyingTo() + ";";
    }
}
