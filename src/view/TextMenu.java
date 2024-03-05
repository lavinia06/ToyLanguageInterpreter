package view;

import model.ADT.Dictionary.DictionaryInterface;
import model.ADT.Dictionary.MyDictionary;

import java.util.Scanner;

public class TextMenu {

    private DictionaryInterface<String, Command> commands;

    public TextMenu(){
        this.commands = new MyDictionary<String, Command>();
    }

    public void addCommand(Command c){
        this.commands.insert(c.getKey(), c);
    }

    private void printMenu(){
        for(Command c: commands.getAllValues()){
            String line = String.format("%4s : %s", c.getKey(), c.getDescription());
            System.out.println(line);
        }
    }

    public void show(){
        Scanner scanner = new Scanner(System.in);
        boolean finished = false;
        while(!finished){
            printMenu();
            System.out.println("Input the option: ");
            String option = scanner.nextLine();
            Command c = commands.getValue(option);
            if(c == null){
                System.out.println("Invalid Option");
            }
            else {
                if(option == "0") {
                    finished = true;
                }
                c.execute();
            }
        }
        scanner.close();
    }
}
