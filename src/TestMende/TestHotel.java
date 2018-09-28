package TestMende;

import hotel.credit.CreditCard;
import hotel.credit.CreditCardType;
import hotel.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestHotel {


    CreditCard creditCard;
    ServiceType serviceType;
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
    void setUp() throws ParseException {

        MockitoAnnotations.initMocks(this);
        confirmationNumber = Long.parseLong("10102018");
        guest = new Guest("Daniella","23 Mason",2);
        creditCard = new CreditCard(CreditCardType.VISA,3,3);

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

        cost = 10;

        Executable e = () -> hotel.addServiceCharge(room.getId(),serviceType.ROOM_SERVICE,cost);

        assertThrows(RuntimeException.class, e);

    }

    @Test
    public void testBook() throws ParseException {

        hotel.addRoom(RoomType.TWIN_SHARE,1);
        hotel.addRoom(RoomType.SINGLE,2);
        hotel.addRoom(RoomType.DOUBLE,3);
        confirmationNumber = 1123423423L;
        arrivalDate = dateFormat.parse("12-12-2018");
        numberOfOccupants = 1;
        stayLength = 10;

        Mockito.when(room.book(guest,arrivalDate,stayLength,numberOfOccupants,creditCard)).thenReturn(booking);
        Mockito.when(booking.getConfirmationNumber()).thenReturn(confirmationNumber);

        assertEquals(confirmationNumber,hotel.book(room,guest,arrivalDate,stayLength,numberOfOccupants,creditCard));

        Mockito.verify(room, Mockito.times(1)).book(guest,arrivalDate,stayLength,
                numberOfOccupants,creditCard);
        Mockito.verify(booking, Mockito.times(2));

    }
    @Test
    public void testCheckIn(){

        hotel.addRoom(RoomType.TWIN_SHARE,1);
        hotel.addRoom(RoomType.SINGLE,2);
        hotel.addRoom(RoomType.DOUBLE,3);

        Mockito.when(room.book(guest,arrivalDate,stayLength,numberOfOccupants,creditCard)).thenReturn(booking);
        Mockito.when(booking.getRoom()).thenReturn(room);

        assertEquals(0,hotel.activeBookingsByRoomId.size());
        hotel.checkin(hotel.book(room,guest,arrivalDate,stayLength,numberOfOccupants,creditCard));
        assertEquals(1,hotel.activeBookingsByRoomId.size());

    }
    @Test
    public void testCheckOut(){

        Mockito.when(room.book(guest,arrivalDate,stayLength,numberOfOccupants,creditCard)).thenReturn(booking);
        Mockito.when(booking.getRoom()).thenReturn(room);
        Mockito.when(room.getId()).thenReturn(2);

        hotel.checkin(hotel.book(room,guest,arrivalDate,stayLength,numberOfOccupants,creditCard));
        assertEquals(1,hotel.activeBookingsByRoomId.size());
        hotel.checkout(2);
        assertEquals(0,hotel.activeBookingsByRoomId.size());
    }
    @Test
    public void testServiceCharge(){

        cost = 50;

        Mockito.when(room.book(guest,arrivalDate,stayLength,numberOfOccupants,creditCard)).thenReturn(booking);
        Mockito.when(booking.getRoom()).thenReturn(room);
        Mockito.when(room.getId()).thenReturn(2);

        Mockito.verify(booking, Mockito.times(0))
                .addServiceCharge(ServiceType.ROOM_SERVICE,cost);

        hotel.checkin(hotel.book(room,guest,arrivalDate,stayLength,numberOfOccupants,creditCard));
        hotel.addServiceCharge(2,ServiceType.ROOM_SERVICE,cost);

        Mockito.verify(booking, Mockito.times(1))
                .addServiceCharge(ServiceType.ROOM_SERVICE,cost);
    }


}
