package Controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import Model.Booking;
import Model.Passenger;

public class DetailController {

	Booking booked;
	boolean spanishPerson, out, in;

	public boolean createBooking(String from, String to, String outTime, String inTime, Date outDate, Date inDate, Double outPrice, Double inPrice) {
		if(from!=null)
			if(to!=null)
				if(outTime!=null)
					if(inTime!=null)
						if(outDate!=null)
							if(inDate!=null)
								if(outPrice!=null) 
									if(inPrice!=null){
										booked = new Booking(from, to, outTime, inTime, outDate, inDate, outPrice, inPrice);
										return true;
									}
		return false;
	}

	public boolean createBooking(String from, String to, String outTime, Date outDate, Double outPrice) {
		if(from!=null)
			if(to!=null)
				if(outTime!=null)
					if(outDate!=null)
						if(outPrice!=null) {
							booked = new Booking(from, to, outTime, outDate, outPrice);
							return true;
						}
		return false;
	}

	public void setBooking(int t) {
		booked.setUpPass(t);
	}

	public double getTotal() {
		if(!checkRebate())
			return booked.getTotal();
		return booked.getTotal() - booked.getRebate();
	}

	public boolean addPerson(String name, Date dOB, String nat, boolean out, boolean in) {
		if(name!=null)
			if(dOB!=null)
				if(nat!=null) {
					Passenger temp = new Passenger(name, dOB, nat, out, in);
					booked.addPassenger(temp);
					return true;
				}
		return false;
	}

	public String getFlightDetails() {
		SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");
		String temp = "From: " + booked.getFrom();
		boolean rFlight = true;
		if(booked.getInDate()==null)
			rFlight = false;
		temp += " - To: " + booked.getTo();
		temp += "\nOutbound Date: " + dt1.format(booked.getOutDate());
		if(rFlight)
			temp += " - Inbound Date: " + dt1.format(booked.getInDate());
		temp += "\nOutbound Time: " + booked.getOutTime();
		if(rFlight)
			temp += " - Inbound Time: " + booked.getInTime();
		return temp;
	}

	public String getDetails(int i) { 
		SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");
		String temp = "Name: " + getPass(i);
		String temp2 = dt1.format(getPassenger(i).getDOB());
		if(PriceCalculator.calculateAge(PriceCalculator.getDOB(getPassenger(i).getDOB()), LocalDate.now())<18)
			temp += " - Date of Birth: " + temp2;
		String temp3 = "Luggage: ";
		if(getPassenger(i).getOutLug())
			temp3 += "Outbound/";
		if(getPassenger(i).getInLug())
			temp3 += "Inbound";
		if(!getPassenger(i).getOutLug()&&!getPassenger(i).getInLug())
			temp3 +="No Luggage";
		temp += " - " + temp3;
		return temp;
	}

	public Booking getBook() {
		return booked;
	}

	public String getPass(int i) {
		return getPassenger(i).getName();
	}

	public Passenger getPassenger(int i) {
		return booked.getPassenger(i);
	}

	public int getSize() {
		return booked.getNumPass();
	}

	public void increaseRebate() {
		if(getOut())
			booked.increaseRebate();
		if(getIn())
			booked.increaseRebate();
	}

	public void setSpanish() {
		spanishPerson = true;
	}

	public boolean checkRebate() {
		if(spanishPerson && (out || in))
			return true;
		return false;
	}

	public void resetRebate() {
		booked.resetRebate();
	}

	public void setOut(boolean out) {
		this.out = out;
	}

	public boolean getOut() {
		return out;
	}
	public void setIn(boolean in) {
		this.in = in;
	}

	public boolean getIn() {
		return in;
	}
}