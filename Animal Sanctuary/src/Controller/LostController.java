package Controller;

import Model.*;
import View.*;

import java.util.Date;

public class LostController
{    
    private String title = "Submit Lost Animal Report", personD = "Person Details - Fill all sections", name = "Name", address = "Address", phone = "Phone Number", email = "Email Address";
    private String animalD = "Animal Details", aName = "Name", age = "Age", type = "Type of Animal", gender = "Gender", breed = "Breed - select type first", desc = "Description",
    extraD = "Extra Details", dateSeen = "Date last seen", location = "Location last seen", inList = "Animal already in List", uploadImage = "Attach image - optional",
    viewTitle = "Lost Animal Details", viewAnimal = "Animal Details", viewPerson = "Owner Details", viewOther = "Other Details";
    private String animalName, animalType, animalBreed, animalDescription, personName, personAddress, personPhone, personEmail, animalLocation;
    private int animalAge;
    private boolean animalGender;
    private Date date;
    private TabLayout tabLayout;
    private String image;
    private DataController data;
    public LostController(DataController d) {
        data = d;
    }
    
    public void start() {
        tabLayout = new TabLayout();
        setupTab();
    }

    public void setupTab() {
        tabLayout.personDetails(title, personD, name, address, phone, email, animalD, aName, age);
        tabLayout.animalDetails(type, gender, breed, desc, data);
        tabLayout.extraDetails(extraD, dateSeen, location, inList, uploadImage, data);
        tabLayout.table(data.lostTableData());
        tabLayout.lostAnchor(data);
        tabLayout.viewAnimal(viewTitle, viewAnimal);
        tabLayout.viewExtra(viewPerson, viewOther);
    }

    public TabLayout getScroll() {
        return tabLayout;
    }

    public boolean setPerson(String n, String a, String p, String e) {
        if(!n.equals("")) {
            personName = n;
            if(!a.equals("")) {
                personAddress = a;
                if(!p.equals("")) {
                    personPhone = p;
                    if(!e.equals("")) {
                        personEmail = e;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean setAnimal(int a, String n, boolean g, String d, String b, String t) {
        if(a!=-1&&a!=-2){
            animalAge = a;
            if(!n.equals("")) {
                animalName = n;
                if(!d.equals("")) {
                    animalDescription = d;
                    if(b!=null) {
                        animalBreed = b;
                        if(t!=null) {
                            animalType = t;
                            animalGender = g;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public boolean setExtra(Date d, String l) {
        if(d!=null) {
            date = d;
            if(l!=null) {
                animalLocation = l;
                return true;
            }
        }
        return false;
    }
    
    public void addLostAnimal() {
        Animal a = new Animal(animalAge, animalType, animalGender, animalDescription, animalBreed, animalName);
        if(image!=null)
            a.setPicture(getImage());
        Person p = new Person();
        p.setName(personName);
        p.setAddress(personAddress);
        p.setPhone(personPhone);
        p.setEmail(personEmail);
        Lost l = new Lost(date, animalLocation, p);
        a.setAnimalCat(l);
        data.addAnimal(a);
        data.lostTableData();
        image = null;
    }
    
    public void addOwner() {
    	Person p = new Person();
    	p.setName(personName);
        p.setAddress(personAddress);
        p.setPhone(personPhone);
        p.setEmail(personEmail);
        data.getAnimals().addRemoved(p);
    }

    public void setImage(String i) {
        image = i;
    }

    public String getImage() {
        return image;
    }
}