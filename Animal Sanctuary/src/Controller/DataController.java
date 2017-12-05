package Controller;

import Model.*;

import java.util.Date;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataController
{
	private final long ONE_MONTH = 2419200000l;
	private final SaveController save = new SaveController();
	private final LoadController load = new LoadController();
	private final ArrayList<Type> types = new ArrayList<>();
	private final ArrayList<String> typeData = new ArrayList<>();
	private final ArrayList<String> breedData = new ArrayList<>();
	private final ArrayList<String> location = new ArrayList<>();
	private AnimalList animals = new AnimalList();
	private final ObservableList<Animal> animalData = FXCollections.observableArrayList();
	private final ObservableList<DetailController> lostData = FXCollections.observableArrayList();
	private final ObservableList<DetailController> foundData = FXCollections.observableArrayList();
	private final ObservableList<DetailController> adoptData = FXCollections.observableArrayList();
	private final ObservableList<DetailController> interestedData = FXCollections.observableArrayList();
	private final ObservableList<DetailController> reportData = FXCollections.observableArrayList();
	public DataController() {
	}

	public void addAnimal(Animal a) {
		animals.add(a);
	}

	public ObservableList<DetailController> lostTableData() {
		lostData.clear();
		animals.getLostList(animalData);
		for(int i = 0; i<animalData.size(); i++)
			lostData.add(new DetailController(animalData.get(i)));
		return lostData;
	}

	public ObservableList<DetailController> foundTableData() {
		foundData.clear();
		animals.getFoundList(animalData);
		for(int i = 0; i<animalData.size(); i++)
			foundData.add(new DetailController(animalData.get(i)));
		return foundData;
	}

	public ObservableList<DetailController> adoptTableData() {
		adoptData.clear();
		animals.getAdoptionList(animalData);
		for(int i = 0; i<animalData.size(); i++)
			adoptData.add(new DetailController(animalData.get(i)));
		return adoptData;
	}

	public void updateAdoption() {
		for(int i = 0; i<foundData.size(); i++) {
			if(foundData.get(i).getAnimal().getAdoption()==null) {
				long milliSeconds = foundData.get(i).getAnimalLongDate().getTime();
				if((System.currentTimeMillis() - milliSeconds) > ONE_MONTH) {
					int j = animals.posInList(foundData.get(i).getAnimal());
					Adoption adopt = new Adoption(new Date());
					adopt.setDate(new Date());
					adopt.setStatus("No status set");
					animals.getAnimal(j).setAdoption(adopt);
				}
			}
		}
	}

	public void interestTableData(Adoption a) {
		interestedData.clear();
		int i = 0;
		while(i<a.getLength()) {
			interestedData.add(new DetailController(a.getInterested(i)));
			i++;
		}
	}

	public void setAdoptDetails(Animal a, String animalName, int animalAge, Adoption adopt) {
		int i = animals.posInList(a);
		if(animalName!=null)
			animals.getAnimal(i).setName(animalName);
		//if(animalAge!=-2)
			animals.getAnimal(i).setAge(animalAge);
		animals.getAnimal(i).setAdoption(adopt);
		adoptTableData();
	}

	public void setAdoptInterested(Animal a, Adoption adopt) {
		int i = animals.posInList(a);
		animals.getAnimal(i).setAdoption(adopt);
		adoptTableData();
	}

	public ObservableList<DetailController> getAdoptData() {
		return adoptData;
	}

	public ObservableList<DetailController> getInterestData() {
		return interestedData;
	}

	public ObservableList<DetailController> getReport() {
		return reportData;
	}

	public void reportTableDataPerson(ArrayList<DetailController> t) {
		reportData.clear();
		for(int i=0; i<t.size(); i++)
			reportData.add(t.get(i));
	}

	public void reportTableDataAnimal(ArrayList<Animal> t) {
		reportData.clear();
		for(int i=0; i<t.size(); i++)
			reportData.add(new DetailController(t.get(i)));
	}

	public ArrayList<String> getType() {
		typeData.clear();
		for(Type t: types)
			typeData.add(t.getType());
		return typeData;
	}

	public Type getType(String t) {
		for(int i=0; i<types.size(); i++)
			if(t.equals(types.get(i).getType()))
				return types.get(i);
		return null;
	}

	public void addType(String s) {
		types.add(new Type(s));
		saveType();
	}

	public void removeType(String s) {
		for(int i=0; i<types.size(); i++)
			if(types.get(i).getType().equals(s))
				types.remove(i);
		saveType();
	}

	public ArrayList<String> getBreed(String t) {
		breedData.clear();
		Type temp = getType(t);
		for(String s: temp.getBreeds())
			breedData.add(s);
		return breedData;
	}

	public void addBreed(String t, String b) {
		for(int i=0; i<types.size(); i++)
			if(t.equals(types.get(i).getType()))
				types.get(i).addBreed(b);
		saveType();
	}

	public void removeBreed(String t, String b) {
		for(int i=0; i<types.size(); i++)
			if(t.equals(types.get(i).getType()))
				types.get(i).removeBreed(b);
		saveType();
	}

	public void addLocation(String l) {
		location.add(l);
		saveLocation();
	}

	public ArrayList<String> getLocation() {
		return location;
	}

	public void removeLocation(String l) {
		location.remove(l);
		saveLocation();
	}

	public void removeAnimal(DetailController d) {
		Animal a = d.getAnimal();
		animals.addRemoved(a.getAnimalCat().getContact());
		if(a.getAdoption()!=null) {
			Adoption adopt = (Adoption)a.getAdoption();
			for(int i=0; i<adopt.getLength(); i++)
				animals.addRemoved(adopt.getInterested(i));
		}
		animals.remove(a);
		fillTables();
	}

	public void saveType() {
		save.saveTypeBreed("Data/TypesAndBreeds.txt", types);
	}

	public void loadType() {
		load.loadTypeBreed("Data/TypesAndBreeds.txt", types);
	}

	public void saveLocation() {
		save.saveLocation("Data/Locations.txt", location);
	}

	public void loadLocation() {
		load.loadLocation("Data/Locations.txt", location);
	}

	public void saveAnimalsFile() {
		save.saveAnimals("Data/SavedData.ser", animals);
	}

	public void loadAnimalsFile() {
		animals = load.loadAnimals("Data/SavedData.ser", animals);
		if(animals!=null) {
			updateAdoption();
			fillTables();
		}
	}

	public void saveAnimalsDatabase() {
		DatabaseController data = startDatabase();
		data.saveDatabase(animals);
	}

	public void loadAnimalsDatabase() {
		DatabaseController data = startDatabase();
		animals = data.loadDatabase();
		if(animals!=null) {
			updateAdoption();
			fillTables();
		}
	}

	public AnimalList getAnimals() {
		return animals;
	}

	public void fillTables() {
		lostTableData();
		foundTableData();
		adoptTableData();
	}

	public DatabaseController startDatabase() {
		DatabaseController data = new DatabaseController();
		data.connectDatabase();
		return data;
	}
}
