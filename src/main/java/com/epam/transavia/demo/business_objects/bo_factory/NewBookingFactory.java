package com.epam.transavia.demo.business_objects.bo_factory;

import com.epam.transavia.demo.business_objects.NewBooking;

public class NewBookingFactory {

    public static NewBooking createNotFlyingBooking() {
        NewBooking newBooking = new NewBooking();
        newBooking.setFromDestination("Dubai, United Arab Emirates");
        newBooking.setToDestination("Agadir, Morocco");
        newBooking.setBookingError("Unfortunately we do not fly from Dubai, United Arab Emirates to Agadir, Morocco." +
                                   " However, we do fly from Dubai, United Arab Emirates to other destinations." +
                                   " Please change your destination and try again.");
        return newBooking;
    }

    public static NewBooking createAlwaysFoundRouteBooking() {
        NewBooking newBooking = new NewBooking();
        newBooking.setFromDestination("Amsterdam (Schiphol), Netherlands");
        newBooking.setToDestination("Nice, France");
        return newBooking;
    }
}
