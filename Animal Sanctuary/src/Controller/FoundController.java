package Controller;

import Model.*;
import View.*;

import java.util.Date;

public class FoundController
{    
    private String title = "Submit Found Animal Report", personD = "Person Details - Fill all sections", name = "Name", address = "Address", phone = "Phone Number", email = "Email Address",
    animalD = "Animal Details", aName = "Name- if known", age = "Age - if known", type = "Type of Animal", gender = "Gender", breed = "Breed - select type first", desc = "Description",
    extraD = "Extra Details", dateSeen = "Date found", location = "Location found", inList = "Animal already in List", uploadImage = "Attach image - optional",
    viewTitle = "Found Animal Details", viewAnimal = "Animal Details", viewPerson = "Finder Details", viewOther = "Other Details";
    private String animalName, animalType, animalBreed, animalDescription, personName, personAddress, personPhone, personEmail, animalLocation;
    private int animalAge;
    private boolean animalGender;
    private Date date;
    private TabLayout tabLayout;
    private String image;
    private DataController data;
    public FoundController(DataController d) {
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
        tabLayout.table(data.foundTableData());
        tabLayout.foundAnchor(data);
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
        if(n.equals(""))
            animalName = "Name Unknown";
        else
            animalName = n;
        if(a==-2||(a>=0 && a<=20))
            animalAge = a;
        else 
            return false;
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

    public void addFoundAnimal() {
        Animal a = new Animal(animalAge, animalType, animalGender, animalDescription, animalBreed, animalName);
        if(image!=null)
            a.setPicture(getImage());
        Person p = new Person();
        p.setName(personName);
        p.setAddress(personAddress);
        p.setPhone(personPhone);
        p.setEmail(personEmail);
        Found f = new Found(date, animalLocation, p);
        a.setAnimalCat(f);
        data.addAnimal(a);
        data.foundTableData();
        image = null;
    }

    public void setImage(String i) {
        image = i;
    }

    public String getImage() {
        return image;
    }
}