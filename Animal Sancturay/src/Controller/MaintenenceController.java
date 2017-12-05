package Controller;

import View.*;

public class MaintenenceController
{
    private String t = "Type", aType = "Add Type", rType = "Remove Type", b = "Breed",
    aBreed = "Add Breed", rBreed = "Remove Breed", l = "Location", aLoc = "Add Location",
    rLoc = "Remove Location";// s = "Status", aStat = "Add Status", rStat = "Remove Status";
    private MaintenenceLayout maintenenceLayout;
    private DataController data;
    public MaintenenceController(DataController d) {
        data = d;
    }
    
    public void start() {
        maintenenceLayout = new MaintenenceLayout();
        setupTab();
    }
    
    public void setupTab() {
        maintenenceLayout.setup(t, aType, rType, t, b, aBreed, rBreed, l, aLoc, rLoc);
        maintenenceLayout.locationFill(data);
        maintenenceLayout.typeFill(data);
        maintenenceLayout.pageUpdate(data);
    }
    
    public MaintenenceLayout getScroll() {
        return maintenenceLayout;
    }
}