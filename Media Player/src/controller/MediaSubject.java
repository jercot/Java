package controller;

import java.util.ArrayList;

import model.Observer;

public class MediaSubject {
	
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	private long lastUpdate;
	
	public void setUpdate(long time) {
		lastUpdate = time;
		notifyObservers();
	}
	
	public long getUpdate() {
		return lastUpdate;
	}
	
	public void attachObserver(Observer o) {
		observers.add(o);
	}
	
	public void notifyObservers() {
		for(Observer o: observers) {
			o.print();
			System.out.print(" ");
		}
		System.out.println();
	}
}