package hotel.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hotel.credit.CreditCard;
import hotel.utils.IOUtils;

public class Room {
	
	private enum State {READY, OCCUPIED}
	
	int id;
	RoomType roomType;
	List<Booking> bookings;
	State state;

	
	public Room(int id, RoomType roomType) {
		this.id = id;
		this.roomType = roomType;
		bookings = new ArrayList<>();
		state = State.READY;
	}
	

	public String toString() {
		return String.format("Room : %d, %s", id, roomType);
	}


	public int getId() {
		return id;
	}
	
	public String getDescription() {
		return roomType.getDescription();
	}
	
	
	public RoomType getType() {
		return roomType;
	}
	
	public boolean isAvailable(Date arrivalDate, int stayLength) {
		IOUtils.trace("Room: isAvailable");
		for (Booking b : bookings) {
			if (b.doTimesConflict(arrivalDate, stayLength)) {
				return false;
			}
		}
		return true;
	}
	
	
	public boolean isReady() {
		return state == State.READY;
	}


	public Booking book(Guest guest, Date arrivalDate, int stayLength, int numberOfOccupants, CreditCard creditCard) {

		Booking book = new Booking(guest, this, arrivalDate, stayLength, numberOfOccupants, creditCard);
		bookings.add(book);
		return book;
	}


	public void checkin() {
		// throws a RuntimeException if the rooms state is not READY
		//	After calling this method
		//		1. The rooms state should be OCCUPIED
		//      2. the booking state should be CHECKED_IN

		if (state != State.READY) {
			throw new RuntimeException("The room has not been ready!");
		} else {
			state = State.OCCUPIED;
		}
	}


	public void checkout(Booking booking) {
		// throws a RuntimeException if the rooms state is not OCCUPIED
		//	After calling this method
		//		1. The rooms state should be READY
		//      2. the booking state should be CHECKED_OUT

		if (state != State.OCCUPIED) {
			throw new RuntimeException("The room has not been occupied!");
		} else {
			state = State.READY;
			booking.checkOut();
		}
	}


}
