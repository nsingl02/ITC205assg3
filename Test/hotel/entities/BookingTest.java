package hotel.entities;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;



import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.Mockito.*;
import hotel.credit.CreditCard;
import hotel.entities.Booking;
import hotel.entities.Guest;
import hotel.entities.Hotel;
import hotel.entities.Room;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@ExtendWith(MockitoExtension.class)
class BookingTest{
	@Mock Guest mockGuest;
	@Mock Room mockRoom;
	 Date arrivalDate;
	 int stayLength;
	 int occupantNumber;
	 @Mock CreditCard mockCard;
	
	 long confirmationNumber;
	 Booking booking;
	
	 SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	
	@BeforeEach
	void setUp() throws Exception{
		format = new SimpleDateFormat("dd-MM-yyyy");
		arrivalDate=format.parse("25-09-2018");
		stayLength=1;
		occupantNumber=1;
		confirmationNumber=11009879;
		booking = new Booking(mockGuest, mockRoom, arrivalDate, stayLength, occupantNumber, mockCard);
		
	}
	
	@AfterEach
	void test() {
		booking.checkIn();
		doNothing().when(mockRoom).checkin();
		assertTrue(booking.isCheckedIn());
	}
	
	@Test
	void testBookingCheckInWhenBookingIsPending() {
		booking.checkOut();
		Executable e = () -> booking.checkIn();
		Throwable t = assertThrows(RuntimeException.class,e);
		assertEquals("CheckIn Incomplete",t.getMessage());
	}
	
	
}