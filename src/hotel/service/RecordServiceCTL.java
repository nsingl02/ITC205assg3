package hotel.service;


import hotel.entities.Booking;
import hotel.entities.Hotel;
import hotel.entities.ServiceType;
import hotel.utils.IOUtils;

public class RecordServiceCTL {
	
	private static enum State {ROOM, SERVICE, CHARGE, CANCELLED, COMPLETED}

	private static final int ServiceType = 0;
	
	private Hotel hotel;
	private RecordServiceUI recordServiceUI;
	private State state;
	
	private Booking booking;
	private int roomNumber;
	private String serviceDescription;


	public RecordServiceCTL(Hotel hotel) {
		this.recordServiceUI = new RecordServiceUI(this);
		state = State.ROOM;
		this.hotel = hotel;
	}

	
	public void run() {		
		IOUtils.trace("PayForServiceCTL: run");
		recordServiceUI.run();
	}


	public void roomNumberEntered(int roomNumber) {
		if (state != State.ROOM) {
			String mesg = String.format("PayForServiceCTL: roomNumberEntered : bad state : %s", state);
			throw new RuntimeException(mesg);
		}
		booking = hotel.findActiveBookingByRoomId(roomNumber);
		if (booking == null) {
			String mesg = String.format("No active booking for room id: %d", roomNumber);
			recordServiceUI.displayMessage(mesg);
		}
		else {
			this.roomNumber = roomNumber;
			state = State.SERVICE;
			recordServiceUI.setState(RecordServiceUI.State.SERVICE);
		}
	} 
	
	
	public void serviceDetailsEntered(ServiceType serviceType, double cost) {
		if (state != State.SERVICE){
			throw new RuntimeException("State is not Service");
		}
		booking.addServiceCharge(serviceType, cost);
		recordServiceUI.displayServiceChargeMessage(roomNumber, cost, serviceDescription);
		state = State.COMPLETED;
		recordServiceUI.setState(RecordServiceUI.State.COMPLETED);
		// TODO Auto-generated method stub
	}
		// TODO Auto-generated method stub
	


	public void cancel() {
		recordServiceUI.displayMessage("Pay for service cancelled");
		state = State.CANCELLED;
		recordServiceUI.setState(RecordServiceUI.State.CANCELLED);
	}


	public void completed() {
		recordServiceUI.displayMessage("Pay for service completed");
	}


	

}
