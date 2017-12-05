package Controller;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import Model.Adoption;
import Model.Animal;
import Model.AnimalList;
import Model.Found;
import Model.Lost;
import Model.Person;
import java.util.*;

public class DatabaseController {
	private Connection con;
	public void connectDatabase() {
		try {
			String path = new File("").getAbsolutePath();
			path = path.substring(0, path.lastIndexOf('\\'));
			String URl = "jdbc:derby:" + path + "\\Database;create=true";
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			con = DriverManager.getConnection(URl);
		}
		catch(Exception e){
			System.out.println("Database Connection Failed");
		}

	}

	public AnimalList loadDatabase() {
		try {
			AnimalList animals = new AnimalList();
			int max = 1;
			Statement outter = con.createStatement();
			Statement stmt = con.createStatement();
			String qry = "SELECT * FROM Animal";
			ResultSet rsAnimal = outter.executeQuery(qry);

			while(rsAnimal.next()) {
				int ID = rsAnimal.getInt(1);
				int age = rsAnimal.getInt(2);
				String type = rsAnimal.getString(3);
				boolean gender = getBoolean(rsAnimal.getString(4));
				String desc = rsAnimal.getString(5);
				String breed = rsAnimal.getString(6);
				String name = rsAnimal.getString(7);
				String image = rsAnimal.getString(8);
				String category = rsAnimal.getString(9);
				Animal a = new Animal(age, type, gender, desc, breed, name);
				a.setID(ID);
				if(!image.equals("null"))
					a.setPicture(image);
				String search = "SELECT * FROM Person where animalID = "+ ID;
				ResultSet rsPerson = stmt.executeQuery(search);
				ArrayList<Person> people = new ArrayList<>();
				while(rsPerson.next()) {
					String pName = rsPerson.getString(2);
					String pAddress = rsPerson.getString(3);
					String phone = rsPerson.getString(4);
					String email = rsPerson.getString(5);
					Person p = new Person();
					p.setName(pName);
					p.setAddress(pAddress);
					p.setPhone(phone);
					p.setEmail(email);
					people.add(p);
				}

				search = "SELECT * FROM Location where animalID = "+ ID;
				ResultSet rsLocation = stmt.executeQuery(search);
				while(rsLocation.next()) {
					Date date = rsLocation.getDate(2);
					String location = rsLocation.getString(3);
					if(category.equals("Lost"))
						a.setAnimalCat(new Lost(date, location, people.get(0)));
					else//if(category.equals("Found"))
						a.setAnimalCat(new Found(date, location, people.get(0)));
				}

				search = "SELECT * FROM Status where animalID = "+ ID;
				ResultSet rsStatus = stmt.executeQuery(search);
				while(rsStatus.next()) {
					boolean neutered = getBoolean(rsStatus.getString(2));
					boolean chipped = getBoolean(rsStatus.getString(3));
					boolean vac = getBoolean(rsStatus.getString(4));
					String status = rsStatus.getString(5);
					boolean res = getBoolean(rsStatus.getString(6));
					Date date = rsStatus.getDate(7);
					Adoption adopt = new Adoption(date);
					adopt.setNeutered(neutered);
					adopt.setChipped(chipped);
					adopt.setVaccinated(vac);
					adopt.setStatus(status);
					adopt.setReserved(res);
					for(int i=1; i<people.size(); i++)
						adopt.setInterested(people.get(i));
					a.setAdoption(adopt);
				}
				animals.add(a);
				if(ID>max)
					max=ID+1;
			}
			String search = "SELECT * FROM Person where animalID = "+ -1;
			ResultSet rsRemoved = stmt.executeQuery(search);
			while (rsRemoved.next()){
				String pName = rsRemoved.getString(2);
				String pAddress = rsRemoved.getString(3);
				String phone = rsRemoved.getString(4);
				String email = rsRemoved.getString(5);
				Person p = new Person();
				p.setName(pName);
				p.setAddress(pAddress);
				p.setPhone(phone);
				p.setEmail(email);
				animals.getRemoved().add(p);
			}
			Animal.setStatic(max);
			return animals;
		}
		catch(Exception IO) {
			System.out.println("Database Load Failed");
		}
		Animal.setStatic(1);
		return new AnimalList();
	}

	public boolean getBoolean(String bool) {
		if(bool.equals("F"))
			return false;
		return true;
	}

	public void saveDatabase(AnimalList animals) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM Animal");
			stmt.executeUpdate("DELETE FROM Location");
			stmt.executeUpdate("DELETE FROM Person");
			stmt.executeUpdate("DELETE FROM Status");
			for(int i=0;i< animals.getLength(); i++)
				addAnimal(animals.getAnimal(i), stmt);
			for(int i=0; i<animals.getRemoved().size(); i++) {
				String person = addPerson(-1, animals.getRemoved().get(i));
				executeUpdate(person, stmt);
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

	public void addAnimal(Animal a, Statement stmt) {
		Adoption adopt = null;
		//Person
		int id = a.getID();
		boolean getAdopt = false;
		String insertPerson;
		ArrayList<Person> people = new ArrayList<>();
		people.add(a.getAnimalCat().getContact());
		if(a.getAdoption()!=null) {
			adopt = (Adoption)a.getAdoption();
			getAdopt = true;
			for(int i=0; i<adopt.getLength(); i++)
				people.add(adopt.getInterested(i));
		}
		for(int j=0; j<people.size(); j++) {
			insertPerson = addPerson(id, people.get(j));
			executeUpdate(insertPerson, stmt);
		}

		//Location
		String location = "";
		String date = new SimpleDateFormat("dd.MM.yyyy").format(a.getAnimalCat().getDate());
		if(a.getAnimalCat() instanceof Lost) {
			Lost l = (Lost)a.getAnimalCat();
			location = l.getLocation();
		}
		else if(a.getAnimalCat() instanceof Found) {
			Found f = (Found)a.getAnimalCat();
			location = f.getLocation();
		}
		String insertLocation = "INSERT INTO Location VALUES("+ id +",'"+ date +"','" + location +"')";
		executeUpdate(insertLocation, stmt);
		//Status
		if(getAdopt) {
			char n, c, v, r;
			n = getChar(adopt.getNeutered());
			c = getChar(adopt.getChipped());
			v = getChar(adopt.getVaccinated());
			r = getChar(adopt.getReserved());
			String status = adopt.getStatus();
			String insertStatus;
			if(adopt.getAdoptionDate()!=null) {
				String adoptDate = new SimpleDateFormat("dd.MM.yyyy").format(adopt.getAdoptionDate());
				insertStatus = "INSERT INTO Status VALUES("+ id +",'"+ n +"','"+ c +"','"+ v +"','"+ status +"','"+ r +"','"+ adoptDate +"')";
			}
			else {
				insertStatus = "INSERT INTO Status VALUES("+ id +",'"+ n +"','"+ c +"','"+ v +"','"+ status +"','"+ r +"',"+ null +")";
			}
			executeUpdate(insertStatus, stmt);
		}

		//Animal
		String type = a.getType(), desc = a.getDesc(), breed = a.getBreed(), name = a.getName(), picture = a.getPictureURL();
		int age = a.getAge();
		char gender = getChar(a.getGender());
		String cat = null;
		if(getAdopt)
			cat = "Adoption";
		else if(a.getAnimalCat() instanceof Lost)
			cat = "Lost";
		else if(a.getAnimalCat() instanceof Found)
			cat = "Found";
		String insertAnimal = "INSERT INTO Animal VALUES("+ id +","+ age +",'"+ type +"','"+ gender +"','"+ desc +"','"+ breed +"','"+ name +"','"+ picture +"','"+ cat +"')";
		executeUpdate(insertAnimal, stmt);
	}

	public String addPerson(int id, Person p) {
		String insertPerson = null;
		String name = p.getName(), address = p.getAddress(), phone = p.getPhone(), email = p.getEmail();
		insertPerson = "INSERT INTO Person VALUES("+ id +",'"+ name +"','"+ address +"','"+ phone +"','"+ email +"')";
		return insertPerson;
	}

	public char getChar(boolean check) {
		if(!check)
			return 'F';
		return 'T';
	}

	public void executeUpdate(String insert, Statement stmt) {
		try {
			stmt.executeUpdate(insert);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
