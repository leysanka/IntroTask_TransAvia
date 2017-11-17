package com.epam.transavia.demo.business_objects.bo_factory;

import com.epam.transavia.demo.business_objects.NewBooking;
import com.epam.transavia.demo.util.DateTimeHelper;

public class BookingCreator {

    public NewBooking constructRoundTripWithAllPassengersTypes(NewBookingBuilder bookingBuilder) {

        bookingBuilder.withFromDestination("Edinburgh, United Kingdom");
        bookingBuilder.withToDestination("Paris (Orly South), France");
        bookingBuilder.withDepartDate(DateTimeHelper.generateBookingInputDateAsNowPlusLag(1));
        bookingBuilder.withReturnDate(DateTimeHelper.generateBookingInputDateAsNowPlusLag(8));
        bookingBuilder.withAdultsCount(2);
        bookingBuilder.withChildrenCount(1);
        bookingBuilder.withBabiesCount(1);

        return bookingBuilder.getBooking();
    }

}
