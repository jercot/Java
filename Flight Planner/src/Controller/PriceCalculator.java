package Controller;

import java.util.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

import Model.Booking;

public class PriceCalculator {

	private static int[] pass;

	public static void calculatePrice(DetailController details) {
		pass = new int[4];
		boolean twoWay = false;
		for(int i=0; i<pass.length; i++)
			pass[i] = 0;
		Booking booked = details.getBook();
		Double total = 0.00;
		if(booked.getInDate()!=null)
			twoWay = true;
		int numPeople;
		numPeople = booked.getNumPass();
		for(int i=0; i<numPeople; i++) {
			int t = calculateAge(getDOB(booked.getPassenger(i).getDOB()), getDOB(booked.getOutDate()));
			int t2 = calculateAge(getDOB(booked.getPassenger(i).getDOB()), getDOB(booked.getInDate()));
			if(t < 6 && t > 1) {
				pass[2]++;
				total+=60;
				if(twoWay)
					if(t==t2)
						total+=60;
			}
			else if(t>5) {
				if(t>17)
					pass[0]++;
				if(t<18)
					pass[1]++;
				total+=booked.getOutPrice();
				if(twoWay)
					total+=booked.getInPrice();
			}
			else if(t<2) {
				pass[3]++;
			}
			if(t!=t2) {
				if(t2<6 && t2> 1)
					total+=60;
				else if(t2>5)
					total+=booked.getInPrice();
			}
			if(booked.getPassenger(i).getOutLug())
				total+=15;
			if(booked.getPassenger(i).getInLug())
				total+=15;
		}
		booked.setTotal(total);
	}

	public static int calculateAge(LocalDate birthDate, LocalDate flightDate) {

		if (birthDate != null) {
			return Period.between(birthDate, flightDate).getYears();
		} else {
			return -1;
		}
	}

	public static int[] getPass() {
		return pass;
	}

	public static LocalDate getDOB(Date date) {
		if(date!=null)
			return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return null;
	}
}