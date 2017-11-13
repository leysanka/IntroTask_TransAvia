package com.epam.transavia.demo.business_objects.bo_factory;

import com.epam.transavia.demo.business_objects.NewBooking;
import com.epam.transavia.demo.util.DateTimeHelper;

public class BookingCreator {

    public NewBooking constructRoundTripWithAllPassengersTypes(NewBookingBuilder bookingBuilder) {

        bookingBuilder.setFromDestination("Edinburgh, United Kingdom");
        bookingBuilder.setToDestination("Paris (Orly South), France");
        bookingBuilder.setDepartDate(DateTimeHelper.calculateDateNowPlusLag(1));
        bookingBuilder.setReturnDate(DateTimeHelper.calculateDateNowPlusLag(8));
        bookingBuilder.setAdultsCount(2);
        bookingBuilder.setChildrenCount(1);
        bookingBuilder.setBabiesCount(1);

        return bookingBuilder.getBooking();
    }

}
