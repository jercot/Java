package Model;

import java.util.*;
@SuppressWarnings("serial")
public class Adoption extends Category implements java.io.Serializable {
	private boolean neutered, chipped, vaccinated, reserved;
    private String status;
    private ArrayList<Person> interested  = new ArrayList<Person>();
    private Date adoptionDate;

    public Adoption(Date d) {
        super(d);
    }

    public void setNeutered(boolean n) {
        neutered = n;
    }

    public void setChipped(boolean c) {
        chipped = c;
    }

    public void setVaccinated(boolean v) {
        vaccinated = v;
    }

    public void setStatus(String s) {
        status = s;
    }

    public void setReserved(boolean r) {
        reserved = r;
    }

    public void setInterested(Person i) {
        interested.add(i);
    }

    public void setAdoptionDate(Date a) {
        adoptionDate = a;
    }

    public boolean getNeutered() {
        return neutered;
    }

    public boolean getChipped() {
        return chipped;
    }

    public boolean getVaccinated() {
        return vaccinated;
    }

    public String getStatus() {
        return status;
    }

    public boolean getReserved() {
        return reserved;
    }

    public Person getInterested(int i) {
        if(i<interested.size())
            if(interested.get(i) != null)
                return interested.get(i);
        return null;
    }
    
    public int getLength() {
        return interested.size();
    }

    public Date getAdoptionDate() {
        return adoptionDate;
    }
}