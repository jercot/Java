package Controller;

import View.*;
import Model.*;

import java.util.ArrayList;

public class ReportController {
	private ReportLayout layout;
	private DataController data;
	private final long ONE_DAY = 86400000l;
	private ArrayList<Animal> reportTable;
	public ReportController(DataController d) {
		data = d;
		reportTable = new ArrayList<>();
	}

	public void start() {
		layout = new ReportLayout();
		setupTab();
	}

	public void setupTab() {
		layout.mainView();
		layout.viewSection(data.getReport());   
		layout.reportStart(data);
	}

	public ReportLayout getScroll() {
		return layout;
	}

	public void getSponsors() {
		ArrayList<Animal> temp = data.getAnimals().getAnimalList();
		ArrayList<DetailController> people = new ArrayList<>();
		for(int i=0; i<temp.size(); i++)
			if(temp.get(i).getAnimalCat()instanceof Lost) {
				Lost l = (Lost)temp.get(i).getAnimalCat();
				people.add(new DetailController(l.getContact(), "Lost"));
			}
			else if(temp.get(i).getAnimalCat()instanceof Found) {
				Found f = (Found)temp.get(i).getAnimalCat();
				people.add(new DetailController(f.getContact(), "Found"));
				if(temp.get(i).getAdoption()!=null) {
					Adoption a = (Adoption)temp.get(i).getAdoption();
					for(int j=0; j<a.getLength();j++)
						people.add(new DetailController(a.getInterested(j), "Adoption"));
				}
			}
		ArrayList<Person> removed = data.getAnimals().getRemoved();
		for(int i=0; i<removed.size(); i++)
			people.add(new DetailController(removed.get(i), "Removed"));
		data.reportTableDataPerson(people);
	}

	public void clear() {
		reportTable.clear();
	}

	public void getAllAnimals(String t) {
		clear();
		if(t.equals("All"))
			for(int i=0; i<data.getAnimals().getAnimalList().size();i++)
				reportTable.add(data.getAnimals().getAnimal(i));
		else {
			for(int i=0; i<data.getAnimals().getAnimalList().size(); i++)
				if(data.getAnimals().getAnimalList().get(i).getType().equals(t))
					reportTable.add(data.getAnimals().getAnimalList().get(i));
		}
		sortAnimalsAge();
	}

	public void sortAnimalsAge() {
		for(int i=0; i<reportTable.size(); i++)
			for(int j=0; j<reportTable.size()-1; j++) {
				if(reportTable.get(j).getAge()<reportTable.get(j+1).getAge()) {
					Animal temp = reportTable.get(j);
					reportTable.remove(j);
					reportTable.add(j+1, temp);
				}
			}
	}

	public void getAnimalsLocation(String c, String l) { //fix to choose an animal.
		clear();
		if(c.equals("Lost"))
			if(l.equals("All"))
				for(int i=0; i<data.getAnimals().getAnimalList().size(); i++)
					if(data.getAnimals().getAnimal(i).getAnimalCat() instanceof Lost)
						reportTable.add(data.getAnimals().getAnimalList().get(i));
					else;
			else {
				for(int i=0; i<data.getAnimals().getAnimalList().size(); i++)
					if(data.getAnimals().getAnimal(i).getAnimalCat() instanceof Lost) {
						Lost lost = (Lost)data.getAnimals().getAnimal(i).getAnimalCat();
						if(lost.getLocation().equals(l))
							reportTable.add(data.getAnimals().getAnimalList().get(i));
					}
			}
		else if(c.equals("Found"))
			if(l.equals("All"))
				for(int i=0; i<data.getAnimals().getAnimalList().size(); i++)
					if(data.getAnimals().getAnimal(i).getAdoption()==null)
						if(data.getAnimals().getAnimal(i).getAnimalCat() instanceof Found)
							reportTable.add(data.getAnimals().getAnimalList().get(i));
						else;
					else;
			else {
				for(int i=0; i<data.getAnimals().getAnimalList().size(); i++)
					if(data.getAnimals().getAnimal(i).getAdoption()==null)
						if(data.getAnimals().getAnimal(i).getAnimalCat() instanceof Found) {
							Found found = (Found)data.getAnimals().getAnimal(i).getAnimalCat();
							if(found.getLocation().equals(l))
								reportTable.add(data.getAnimals().getAnimalList().get(i));
						}
			}
	}

	public void getAnimalsLocationDate(long d, String l) { //fix to just cats
		clear();
		if(l.equals("All"))
			for(int i=0; i<data.getAnimals().getAnimalList().size(); i++)
				if(data.getAnimals().getAnimal(i).getAnimalCat() instanceof Lost)
					reportTable.add(data.getAnimals().getAnimalList().get(i));
				else;
		else {
			for(int i=0; i<data.getAnimals().getAnimalList().size(); i++)
				if(data.getAnimals().getAnimal(i).getAnimalCat() instanceof Lost) {
					Lost lost = (Lost)data.getAnimals().getAnimal(i).getAnimalCat();
					long day = lost.getDate().getTime();
					if(lost.getLocation().equals(l))
						if(day-d<ONE_DAY && day-d>=0)
							reportTable.add(data.getAnimals().getAnimalList().get(i));
				}
		}
	}

	public void getAnimalsRange(long l, long h) {
		clear();
		for(int i=0; i<data.getAnimals().getAnimalList().size(); i++)
			if(data.getAnimals().getAnimal(i).getAnimalCat() instanceof Found) {
				long range = data.getAnimals().getAnimal(i).getAnimalCat().getDate().getTime();
				if(range > l && range < h)
					reportTable.add(data.getAnimals().getAnimal(i));
			}
		sortAnimalsGender(reportTable);
	}

	public void sortAnimalsGender(ArrayList<Animal> s) {
		for(int i=0; i<s.size(); i++)
			for(int j=0; j<s.size()-1; j++) {
				int one = (boolean)s.get(j).getGender() ? 1 : 0;
				int two = (boolean)s.get(j+1).getGender() ? 1 : 0;
				if(one<two) {
					Animal temp = s.get(j);
					s.remove(j);
					s.add(j+1, temp);
				}
			}
	}

	public void getAnimalsRangeLocation(String loc, long l, long h) { //fix to cats or dogs
		getAnimalsLocation("Found", loc);
		ArrayList<Animal> one = new ArrayList<>();
		for(int i=0; i<reportTable.size();i++)
			one.add(reportTable.get(i));
		getAnimalsRange(l, h);
		ArrayList<Animal> two = new ArrayList<>();
		for(int i=0; i<reportTable.size();i++)
			two.add(reportTable.get(i));
		reportTable.clear();
		for(int i=0; i<one.size(); i++)
			for(int j=0; j<two.size(); j++)
				if(one.get(i).getID() == two.get(j).getID())
					reportTable.add(one.get(i));
	}

	public void getAnimalsAdoptionAll() {
		clear();
		for(int i=0; i<data.getAnimals().getAnimalList().size();i++)
			if(data.getAnimals().getAnimal(i).getAdoption()!=null) {
				Adoption a = (Adoption)data.getAnimals().getAnimal(i).getAdoption();
				if(a.getAdoptionDate()!=null)
					reportTable.add(data.getAnimals().getAnimal(i));
			}
		sortAnimalsName();
	}

	public void getAnimalsAdoptionType(String t) {
		clear();
		for(int i=0; i<data.getAnimals().getAnimalList().size();i++)
			if(data.getAnimals().getAnimal(i).getType().equals(t)) {
				if(data.getAnimals().getAnimal(i).getAdoption()!=null) {
					Adoption a = (Adoption)data.getAnimals().getAnimal(i).getAdoption();
					if(a.getAdoptionDate()!=null)
						if(a.getAdoptionDate().getTime()<System.currentTimeMillis())
							reportTable.add(data.getAnimals().getAnimal(i));
				}
			}
		sortAnimalsAge();
	}

	public void getAnimalsAdoptionTraining() {
		clear();
		for(int i=0; i<data.getAnimals().getAnimalList().size();i++)
			if(data.getAnimals().getAnimal(i).getType().equals("Dog")) {
				if(data.getAnimals().getAnimal(i).getAge()==0)
					if(data.getAnimals().getAnimal(i).getAdoption()!=null) {
						Adoption a = (Adoption)data.getAnimals().getAnimal(i).getAdoption();
						if(a.getStatus().contains("Training"))
							reportTable.add(data.getAnimals().getAnimal(i));
					}
			}
	}

	public void fillTable() {
		data.reportTableDataAnimal(reportTable);
	}

	public void sortAnimalsName() {
		for(int i=0; i<reportTable.size(); i++)
			for(int j=0; j<reportTable.size()-1; j++) {
				if(reportTable.get(j).getName().compareToIgnoreCase(reportTable.get(j+1).getName())>0) {
					Animal temp = reportTable.get(j);
					reportTable.remove(j);
					reportTable.add(j+1, temp);
				}
			}
	}
}
