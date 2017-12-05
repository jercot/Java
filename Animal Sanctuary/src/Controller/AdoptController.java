package Controller;

import Model.*;
import View.*;

import java.util.Date;

public class AdoptController
{    
    private String title = "Animal Adoption", personD = "Person Details - Fill all details", name = "Name", address = "Address", phone = "Phone Number",
    email = "Email Address", animalD = "Adoption Details", aName = "Name", age = "Age - if known", uploadImage = "Attach image - optional",
    viewTitle = "Adopt Animal Details", viewAnimal = "Animal Details";
    private String personName, personAddress, personPhone, personEmail, animalName, status;
    private int animalAge;
    private boolean neutered, chipped, vaccinated, reserved, updated = false;
    private Date date;
    private TabLayout tabLayout;
    private String image;
    private DataController data;
    public AdoptController(DataController d) {
        data = d;
    }

    public void start() {
        tabLayout = new TabLayout();
        setupTab();
    }

    public void setupTab() {
        tabLayout.personDetails(title, personD, name, address, phone, email, animalD, aName, age);
        tabLayout.table(data.adoptTableData());
        tabLayout.adoptDetails(uploadImage);
        tabLayout.adoptAnchor(data);
        tabLayout.viewAnimal(viewTitle, viewAnimal);
        tabLayout.viewAdopt();
        tabLayout.interestTable(data.getInterestData());
    }

    public boolean setAdoption(String n, int a, boolean neut, boolean chip, boolean vac, boolean res, String stat, Date d){
        if(a==-2||(a>=0 && a<=20))
            animalAge = a;
        else 
            return false;
        neutered = neut;
        chipped = chip;
        vaccinated = vac;
        reserved = res;
        if(!n.equals("")) {
            animalName = n;
            if(stat!=null) {
                status = stat;
                if(d!=null){
                    date = d;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean setAdoption(int a, boolean neut, boolean chip, boolean vac, boolean res, String stat, Date d){
        if(a==-2||(a>=0 && a<=20))
            animalAge = a;
        else 
            return false;
        neutered = neut;
        chipped = chip;
        vaccinated = vac;
        reserved = res;
        if(stat!=null) {
            status = stat;
            if(d!=null){
                date = d;
                return true;
            }
        }
        return false;
    }
    
    public void updateAdoption(DetailController d) {
        Animal a = d.getAnimal();
        if(image!=null)
            a.setPicture(getImage());
        Adoption adopt = (Adoption)a.getAdoption();
        adopt.setNeutered(neutered);
        adopt.setChipped(chipped);
        adopt.setVaccinated(vaccinated);
        adopt.setReserved(reserved);
        adopt.setStatus(status);
        adopt.setAdoptionDate(date);
        data.setAdoptDetails(a, animalName, animalAge, adopt);
        image = null;
        updated = true;
    }
    
    public boolean getUpdate() {
    	return updated;
    }
    
    public void setUpdate() {
    	updated = false;
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
    
    public void updateInterested(DetailController d){
        Animal a = d.getAnimal();
        Person p = new Person();
        p.setName(personName);
        p.setAddress(personAddress);
        p.setPhone(personPhone);
        p.setEmail(personEmail);
        Adoption adopt = (Adoption)a.getAdoption();
        adopt.setInterested(p);
        data.setAdoptInterested(a, adopt);
    }
    
    public void viewInterested(DetailController d) {
        Animal a = d.getAnimal();
        Adoption adopt = (Adoption)a.getAdoption();
        data.interestTableData(adopt);
    }

    public TabLayout getScroll() {
        return tabLayout;
    }

    public void setImage(String i) {
        image = i;
    }

    public String getImage() {
        return image;
    }
}