package Controller;

import Model.*;

import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
//import javafx.stage.FileChooser;
//import javafx.stage.FileChooser.ExtensionFilter;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.BufferedReader;
import java.io.FileReader;

public class LoadController
{
    public void loadTypeBreed(String s, ArrayList<Type> a) {
        try {
            File file = new File(s);
			BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line= reader.readLine()) != null)
                addType(line, a);
            reader.close();
        }
        catch (Exception e) {
            System.out.println("Type Load Failed");
        }
    }

    public void addType(String type, ArrayList<Type> a) {
        String[] types = type.split("/");
        Type temp = new Type(types[0]);
        for(int i=1; i<types.length; i++)
            temp.addBreed(types[i]);
        a.add(temp);
    }

    public void loadLocation(String s, ArrayList<String> l) {
        try {
            File f = new File(s);
            if(f.exists()) {
                Scanner scanner = new Scanner(f);
                while(scanner.hasNext()) {
                    l.add(scanner.nextLine());
                }
                scanner.close();
            }
        }
        catch (Exception e) {
            System.out.println("Location Load Failed");
        }
    }

    public AnimalList loadAnimals(String s, AnimalList current) {
       // FileChooser fileC = new FileChooser();
       // fileC.setTitle("Load Animals From File");
       // fileC.getExtensionFilters().add(new ExtensionFilter("SER files", "*.ser"));
        File f = new File(s);//fileC.showOpenDialog(null);
        if(f!=null) 
            try {
                FileInputStream fileIn = new FileInputStream(f.getPath());
                ObjectInputStream in = new ObjectInputStream(fileIn);
                AnimalList temp = (AnimalList)in.readObject();
                temp.setID();
                in.close();
                fileIn.close();
                return temp;
            }
            catch(Exception i) {
                System.out.println("File Load Failed");
            }
        return current;
    }
}
