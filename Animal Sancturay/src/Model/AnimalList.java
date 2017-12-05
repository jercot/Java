package Model;

import java.util.*;
import javafx.collections.ObservableList;

@SuppressWarnings("serial")
public class AnimalList implements java.io.Serializable {
    private ArrayList<Animal> list = new ArrayList<Animal>();
    private ArrayList<Person> removed = new ArrayList<>();
    private int idAmount;

    public AnimalList() {
        idAmount = 0;
    }

    public Animal getAnimal(int i) {
        if(i<list.size())
            if(list.get(i)!= null)
                return list.get(i);
        return null;
    }

    public void add(Animal a) {
        list.add(a);
    }

    public void remove(Animal a) {
        //for(int i=0;i<list.size();i++)
        // if(list.get(i).getID() == a.getID())
        list.remove(a);
    }

    public int getLength() {
        return list.size();
    }

    public void getLostList(ObservableList<Animal> data) {
        data.clear();
        for (int i = 0; i<list.size(); i++)
            if(getAnimal(i).getAnimalCat() instanceof Lost) {
                data.add(getAnimal(i));
            }
    }

    public void getFoundList(ObservableList<Animal> data) {
        data.clear();
        for (int i = 0; i<list.size(); i++)
            if(getAnimal(i).getAnimalCat() instanceof Found && getAnimal(i).getAdoption()==null)
                data.add(getAnimal(i));
    }

    public void getAdoptionList(ObservableList<Animal> data) {
        data.clear();
        for (int i = 0; i<list.size(); i++)
            if(getAnimal(i).getAdoption()!=null)
                data.add(getAnimal(i));
    }

    public ArrayList<Animal> getAnimalList() {
        return list;
    }

    public boolean isInList(Animal a) {
        for(Animal l: list)
            if(l.getName().equals(a.getName()) && l.getBreed().equals(a.getBreed())) {
                return true;
            }
        return false;
    }

    public int posInList(Animal a) {
        for(int i=0;i<list.size();i++)
            if(list.get(i).getID() == a.getID())
                return i;
        return -1;
    }

    public void getID() {
        if(list.size()>0)
            idAmount = list.get(0).getStatic();
    }

    public void setID() {
        Animal.setStatic(idAmount);
    }
    
    public void addRemoved(Person p) {
    	removed.add(p);
    }
    
    public ArrayList<Person> getRemoved() {
    	return removed;
    }
}
