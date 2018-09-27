import hotel.credit.CreditCard;
import hotel.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestHotel {

    @Mock
    CreditCard creditCard;
    @Mock
    ServiceType serviceType;
    @Mock
    Guest guest;
    @Mock
    Booking booking;
    @Mock
    Room room;

    @InjectMocks
    Hotel hotel;

    Date arrivalDate;

    double cost;

    long confirmationNumber;

    int stayLength;
    int numberOfOccupants;

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");

    @BeforeEach
    void setUp(){

    }

    @Test
    void testCheckInRuntimeException(){

        confirmationNumber = Long.parseLong("10102018");

        Executable e = () -> hotel.checkin(confirmationNumber);

        assertThrows(RuntimeException.class, e);

    }

    @Test
    void testCheckOutRuntimeException(){

        Executable e = () -> hotel.checkout(room.getId());

        assertThrows(RuntimeException.class, e);

    }

    @Test
    void testAddServiceChargeRuntimeException(){

        Executable e = () -> hotel.addServiceCharge(room.getId(),serviceType.ROOM_SERVICE,cost);

        assertThrows(RuntimeException.class, e);
    }

   /* @Test
    void testBook() throws ParseException {

        confirmationNumber = 1123423423L;
        arrivalDate = dateFormat.parse("12-12-2018");
        numberOfOccupants = 1;
        stayLength = 10;

        Mockito.when(room.book(guest,arrivalDate,stayLength,numberOfOccupants,creditCard)).thenReturn(booking);
        Mockito.when(booking.getConfirmationNumber()).thenReturn(confirmationNumber);

        assertEquals(confirmationNumber,hotel.book(room,guest,arrivalDate,stayLength,numberOfOccupants,creditCard));

        Mockito.verify(room, Mockito.times(1)).book(guest,arrivalDate,stayLength,
                numberOfOccupants,creditCard);
        Mockito.verify(booking, Mockito.times(1)).getConfirmationNumber();


    }*/


}
