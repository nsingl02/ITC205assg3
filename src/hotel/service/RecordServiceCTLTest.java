package hotel.service;

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

import hotel.HotelHelper;

class RecordServiceCTLTest {
	
	RecordServiceCTL ctl;
	Hotel h;
	
	SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	Date d;
	private Object ServiceType;

	
	@BeforeEach
	void setUp() throws Exception {
		h = HotelHelper.loadHotel();
		ctl = new RecordServiceCTL(h);
	}
	

	
	@Test
	void test() {
		ctl.roomNumberEntered(2);
		ctl.serviceDetailsEntered(ServiceType.BAR_FRIDGE, 8.0);
	
	}

}
