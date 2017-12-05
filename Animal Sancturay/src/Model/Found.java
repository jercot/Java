package Model;

import java.util.*;
@SuppressWarnings("serial")
public class Found extends Category implements java.io.Serializable{
    private String location;
    private Person owner;

    public Found(Date d, String l, Person p) {
        super(d, p);
        setLocation(l);
    }

    public void setLocation(String l) {
        location =l;
    }
    
    public void setOwner(Person o) {
        owner = o;
    }
    
    public String getLocation() {
        return location;
    }

    public Person getOwner() {
        return owner;
    }
}
