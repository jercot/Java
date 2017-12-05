package Model;

import java.util.*;
@SuppressWarnings("serial")
public abstract class Category implements java.io.Serializable {
    private Date date;
    private Person contact;
    
    public Category(Date d) {
        setDate(d);
    }
    
    public Category(Date d, Person c) {
        this(d);
        setContact(c);
    }
    
    public void setDate(Date d) {
        date = d;
    }
    
    public void setContact(Person c) {
        contact = c;
    }
    
    public Date getDate() {
        return date;
    }
    
    public Person getContact() {
        return contact;
    }
    
}
