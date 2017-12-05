package Controller;

import View.*;

public class Controller
{
    private String lostAnimals ="Lost Animals", foundAnimals = "Found Animals", adoptAnimals = "Adopt Animal",
    reports = "Reports", maintenence = "Maintenence";
    private FoundController found;
    private LostController lost;
    private AdoptController adopt;
    private ReportController report;
    private MaintenenceController maintain;
    private MainLayout mainView;
    private DataController data = new DataController();
    public void startView(){
        data.loadType();
        data.loadLocation();
        //data.loadAnimals();
        mainView = new MainLayout(data, lostAnimals, foundAnimals, adoptAnimals, reports, maintenence);
        found = new FoundController(data);
        lost = new LostController(data);
        adopt = new AdoptController(data);
        report = new ReportController(data);
        maintain = new MaintenenceController(data);
        found.start();
        lost.start();
        adopt.start();
        report.start();
        maintain.start();
        addTabs();
    }
    
    public void addTabs() {
        mainView.secondTab(lost.getScroll().getScroll());
        mainView.thirdTab(found.getScroll().getScroll());
        mainView.fourthTab(adopt.getScroll().getScroll());
        mainView.fifthTab(report.getScroll().getScroll());
        mainView.sixthTab(maintain.getScroll().getScroll());
    }
}
