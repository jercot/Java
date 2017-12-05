package Controller;

import java.util.ArrayList;

public class Type
{
    private String type;
    private final ArrayList<String> breed;

    public Type(String t) {
        setType(t);
        breed = new ArrayList<>();
    }
    
    public void setType(String t) {
        type = t;
    }
    
    public String getType() {
        return type;
    }
    
    public void addBreed(String b) {
        breed.add(b);
    }
    
    public void removeBreed(String b) {
         breed.remove(b);
    }
    
    public ArrayList<String> getBreeds() {
        return breed;
    }
}
