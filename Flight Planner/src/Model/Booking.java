package Model;

import java.util.Date;

public class Booking implements BookInt{

	private String from, to, outTime, inTime;
	private Date outDate, inDate;
	private Double outPrice, inPrice, totalPrice, rebate;
	private Passenger[] passengers;
	private int count;

	public Booking(String from, String to, String outTime, Date outDate, Double outPrice) {
		setFrom(from);
		setTo(to);
		setOutTime(outTime);
		setOutDate(outDate);
		setOutPrice(outPrice);
	}

	public Booking(String from, String to, String outTime, String inTime, Date outDate, Date inDate, Double outPrice, Double inPrice) {
		this(from, to, outTime, outDate, outPrice);
		setInTime(inTime);
		setInDate(inDate);
		setInPrice(inPrice);
	}

	public void setUpPass(int t) {
		resetRebate();
		passengers = new Passenger[t];
		count = 0;
	}

	public void addPassenger(Passenger p) {
		if(count<passengers.length)
			passengers[count++] = p;
	}
	
	public Passenger getPassenger(int i) {
		if(i<getNumPass())
			return passengers[i];
		return null;
	}
	
	public int getNumPass() {
		return passengers.length;
	}
	
	public void increaseRebate(){
		rebate += 5;
	}
	
	public double getRebate() {
		return rebate;
	}
	
	public void setTotal(Double t) {
		totalPrice = t;
	}
	
	public void resetRebate() {
		rebate = 0.00;
	}
	
	public Double getTotal() {
		return totalPrice;
	}

	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getOutTime() {
		return outTime;
	}
	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}
	public String getInTime() {
		return inTime;
	}
	public void setInTime(String inTime) {
		this.inTime = inTime;
	}
	public Date getOutDate() {
		return outDate;
	}
	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}
	public Date getInDate() {
		return inDate;
	}
	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}
	public Double getOutPrice() {
		return outPrice;
	}
	public void setOutPrice(Double outPrice) {
		this.outPrice = outPrice;
	}

	public Double getInPrice() {
		return inPrice;
	}

	public void setInPrice(Double inPrice) {
		this.inPrice = inPrice;
	}
}