package Model;

import java.util.Date;

public class Passenger implements PassInt {

	private String name, nationality;
	private Date dOB;
	private boolean outLug, inLug;
	
	public Passenger(String n, Date d, String na, boolean o, boolean i) {
		setName(n);
		setDOB(d);
		setNat(na);
		setOutLug(o);
		setInLug(i);
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public String getName() {
		return name;
	}
	
	public void setDOB(Date d) {
		dOB = d;
	}

	public Date getDOB() {
		return dOB;
	}
	
	public void setNat(String n) {
		nationality = n;
	}

	public String getNat() {
		return nationality;
	}

	public void setOutLug(boolean o) {
		outLug = o;
	}
	
	public boolean getOutLug() {
		return outLug;
	}

	public void setInLug(boolean i) {
		inLug = i;
	}
	
	public boolean getInLug() {
		return inLug;
	}
}