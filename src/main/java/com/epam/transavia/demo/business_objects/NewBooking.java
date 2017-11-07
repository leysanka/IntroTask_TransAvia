package com.epam.transavia.demo.business_objects;

public class NewBooking {

    private String fromDestination;
    private String toDestination;
    private String departDate;
    private String returnDate;
    private String bookingError;
    private int adultsCount;
    private int childrenCount;
    private int babiesCount;

    public NewBooking(String fromDestination, String toDestination, String departDate, String returnDate, String bookingError, int adultsCount, int childrenCount, int babiesCount) {
        this.fromDestination = fromDestination;
        this.toDestination = toDestination;
        this.departDate = departDate;
        this.returnDate = returnDate;
        this.bookingError = bookingError;
        this.adultsCount = adultsCount;
        this.childrenCount = childrenCount;
        this.babiesCount = babiesCount;
    }

    public NewBooking() {
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
