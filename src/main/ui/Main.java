package ui;

import java.io.FileNotFoundException;

// Starts process and runs ClickerMain
public class Main {
    public static void main(String[] args) throws Exception {
        try {
            new ClickerMain();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }



    }
}
