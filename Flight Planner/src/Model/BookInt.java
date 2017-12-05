package Model;

import java.util.Date;

public interface BookInt {
	public Passenger getPassenger(int i);
	public String getFrom();
	public String getTo();
	public String getOutTime();
	public String getInTime();
	public Date getOutDate();
	public Date getInDate();
	public Double getOutPrice();
	public Double getInPrice();
}