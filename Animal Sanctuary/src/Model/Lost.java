package Model;

import java.util.*;
@SuppressWarnings("serial")
public class Lost extends Category implements java.io.Serializable {
    private String location;
    
    public Lost(Date d, String l, Person p) {
        super(d, p);
        setLocation(l);
    }
    
    public void setLocation(String l) {
        location = l;
    }
    
    public String getLocation() {
        return location;
    }
    
    public String toString() {
        return "";
    }
}