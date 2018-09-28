package TestMende;

import hotel.HotelHelper;
import hotel.booking.BookingCTL;
import hotel.booking.BookingUI;
import hotel.credit.CreditCardType;
import hotel.entities.Hotel;
import hotel.entities.Room;
import hotel.entities.RoomType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestBookingCTL {

    BookingCTL bookingControl;
    Hotel hotel;

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");

    Date date;

    @BeforeEach
    void setUp() throws Exception {

        hotel = HotelHelper.loadHotel();
        bookingControl = new BookingCTL(hotel);
    }

    @Test
    void testcreditCardDetailsEntered() throws ParseException {

        date = dateFormat.parse("12-12-2012");
        bookingControl.phoneNumberEntered(21);
        bookingControl.guestDetailsEntered("simon","2 collin");
        bookingControl.roomTypeAndOccupantsEntered(RoomType.DOUBLE,2);
        bookingControl.bookingTimesEntered(date,3);
        bookingControl.creditDetailsEntered(CreditCardType.VISA,2,2);
        assertEquals(2, hotel.bookingsByConfirmationNumber.size());

    }

    @Test
    void testcreditCardDetailsEnteredCreditNotApprovedException(){

        Executable e = () -> bookingControl.creditDetailsEntered(CreditCardType.MASTERCARD,2,2);
        assertThrows(RuntimeException.class,e);

    }


}
