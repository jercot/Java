package Model;

import javafx.scene.image.Image;
@SuppressWarnings("serial")
public class Animal implements java.io.Serializable {
    private static int IDRolling = 1;
    private int age, ID;
    private String aType, description, breed, name;
    private boolean gender;
    private String picture;
    private Category animalCat, adoption;

    public Animal(String t, boolean g, String d, String b) {
        setType(t);
        setGender(g);
        setDesc(d);
        setBreed(b);
        setID(IDRolling);
        IDRolling++;
    }

    public Animal(int a, String t, boolean g, String d, String b) {
        this(t, g, d, b);
        setAge(a);
    }

    public Animal(int a, String t, boolean g, String d, String b, String n) {
        this(a, t, g, d, b);
        setName(n);
    }

    public void setID(int i) {
        ID = i;
    }

    public void setAge(int a) {
        age = a;
    }

    public void setType(String t) {
        aType = t;
    }

    public void setGender(boolean g) {
        gender = g;
    }

    public void setDesc(String d) {
        description = d;
    }

    public void setBreed(String b) {
        breed = b;
    }

    public void setName(String n) {
        name = n;
    }

    public void setPicture(String i) {
        picture = i;
    }

    public void setAnimalCat(Category a) {
        animalCat = a;
    }

    public void setAdoption(Category a) {
        adoption = a;
    }

    public int getID() {
        return ID;
    }

    public int getAge() {
        return age;
    }

    public String getType() {
        return aType;
    }

    public boolean getGender() {
        return gender;
    }

    public String getGenderTable() {
        if (gender)
            return "Male";
        return "Female";
    }

    public String getDesc() {
        return description;
    }

    public String getBreed() {
        return breed;
    }

    public String getName() {
        return name;
    }

    public Image getPicture() {
        if(picture!=null)
            return new Image(picture);
        return null;
    }
    
    public String getPictureURL() {
    	return picture;
    }

    public void fixID() {
        IDRolling--;
    }

    public Category getAnimalCat() {
        return animalCat;
    }

    public Category getAdoption() {
        return adoption;
    }

    public String toString() {
        return getName() + " " + getAge();
    }

    public void print() {
        System.out.println(toString());
    }

    public static void setStatic(int s) {
        IDRolling = s;
    }

    public int getStatic() {
        return IDRolling;
    }
}