package Model;

@SuppressWarnings("serial")
public class Person implements java.io.Serializable {
    private String name, address, phone, email;
    
    public Person() {
    }
    
    public void setName(String s) {
        name = s;
    }
    
    public void setAddress(String a) {
        address = a;
    }
    
    public void setPhone(String p) {
        phone = p;
    }
    
    public void setEmail(String e) {
        email = e;
    }
    
    public String getName() {
        return name;
    }
    
    public String getAddress() {
        return address;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String toString() {
        return getName();
    }
    
    public void print() {
        System.out.println(toString());
    }
}