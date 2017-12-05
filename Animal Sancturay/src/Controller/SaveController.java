package Controller;

import Model.*;

import java.io.PrintWriter;
import java.util.ArrayList;
//import javafx.stage.FileChooser;
import java.io.File;
//import javafx.stage.FileChooser.ExtensionFilter;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class SaveController {
    public void saveTypeBreed(String s, ArrayList<Type> a) {
        try {
            PrintWriter writer = new PrintWriter(s);
            for(int i=0; i<a.size(); i++) {
                writer.print(a.get(i).getType() + "/");
                for(String t: a.get(i).getBreeds())
                    writer.print(t + "/");
                System.out.println();
            }
            writer.close();
        }

        catch (Exception e) {
            System.out.println("Type Save Failed");
        }
    }

    public void saveLocation(String s, ArrayList<String> l) {
        try {
            PrintWriter writer = new PrintWriter(s);
            for(String t: l)
                writer.println(t);
            writer.close();
        }

        catch (Exception e) {
            System.out.println("Location Save Failed");
        }
    }

    public void saveAnimals(String s, AnimalList animals) {
        animals.getID();
        //FileChooser fileC = new FileChooser();
        //fileC.setTitle("Save Animals To File");
        //fileC.getExtensionFilters().add(new ExtensionFilter("SER files", "*.ser"));
        File f = new File(s);//fileC.showSaveDialog(null);
        if (f != null) {
            try {
                FileOutputStream fileOut = new FileOutputStream(f.getPath());
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(animals);
                out.close();
                fileOut.close();
            }
            catch (Exception ex) {
                System.out.println("File Save Failed");
            }
        }
    }
}
