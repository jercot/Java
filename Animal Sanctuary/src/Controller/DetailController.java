package Controller;

import Model.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Date;
import java.text.SimpleDateFormat;

public class DetailController {
    private Animal animal;
    private Person person;
    private Category category, adoptCat;
    private Lost lost;
    private Found found;
    private Adoption adopt;
    private String section;

    public DetailController(Animal a){
        animal = a;
        person = animal.getAnimalCat().getContact();
        category = animal.getAnimalCat();
        adoptCat = animal.getAdoption();
    }

    public DetailController(Person p){
        person = p;
    }

    public DetailController(Person p, String s){
        person = p;
        section = s;
    }

    public int getAnimalID() {
        return animal.getID();
    }

    public int getAnimalAge() {
        return animal.getAge();
    }

    public String getAnimalStringAge() {
        if(animal.getAge() == -2)
            return "Age Unknown";
        String age = "";
        age += animal.getAge();
        return age;
    }

    public String getAnimalGender() {
        return animal.getGenderTable();
    }

    public String getAnimalDescription() {
        return animal.getDesc();
    }

    public String getAnimalType() {
        return animal.getType();
    }

    public String getAnimalName() {
        return animal.getName();
    }

    public String getAnimalBreed() {
        return animal.getBreed();
    }

    public Image getAnimalPicture() {
        return animal.getPicture();
    }

    public ImageView getAnimalImage() {
        ImageView temp = new ImageView(animal.getPicture());
        temp.setFitWidth(100);
        temp.setFitHeight(100);        
        return temp;
    }

    public String getAnimalSection() {
        if(category instanceof Lost)
            return "Lost";
        if(category instanceof Found)
            if(animal.getAdoption() == null)
                return "Found";
        return "Adoption";
    }

    public String getPersonName() {
        return person.getName();
    }

    public String getPersonAddress() {
        return person.getAddress();
    }

    public String getPersonPhone() {
        return person.getPhone();
    }

    public String getPersonEmail() {
        return person.getEmail();
    }

    public String getPersonSection() {
        return section;
    }

    public Date getAnimalLongDate() {
        return category.getDate();
    }

    public String getAnimalDate() {
        return new SimpleDateFormat("dd-MM-yyyy").format(category.getDate());
    }

    public String getAnimalLocation() {
        if(category instanceof Lost) {
            lost = (Lost)category;
            return lost.getLocation();
        }
        if(category instanceof Found) {
            found = (Found)category;
            return found.getLocation();
        }
        return null;
    }

    public String getNeutered() {
        adopt = (Adoption)adoptCat;
        if(adopt.getNeutered())
            return "Yes";
        return "No";
    }

    public String getChipped() {
        adopt = (Adoption)adoptCat;
        if(adopt.getChipped())
            return "Yes";
        return "No";
    }

    public String getVaccinated() {
        adopt = (Adoption)adoptCat;
        if(adopt.getVaccinated())
            return "Yes";
        return "No";
    }

    public String getReserved() {
        adopt = (Adoption)adoptCat;
        if(adopt.getReserved())
            return "Yes";
        return "No";
    }

    public String getStatus() {
        adopt = (Adoption)adoptCat;
        return adopt.getStatus();
    }

    public Date getDate() {
        adopt = (Adoption)adoptCat;
        return adopt.getAdoptionDate();//new SimpleDateFormat("dd-MM-yyyy").format(adopt.getAdoptionDate());
    }

    public Animal getAnimal(){
        return animal;
    }

    public Person getPerson() {
        return person;
    }
}