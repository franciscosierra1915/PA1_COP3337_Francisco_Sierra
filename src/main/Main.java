/* This is part of the starter code! 
 * You need to complete this class yourself!*/
package main;
import util.*;
import java.util.*;

public class Main {
    private static boolean checkFirstName(String given){
        //your code here...
        return true;
    }
    private static boolean checkLastName(String given){
        //your code here...
        return true;
    }
    private static boolean checkID(String given){
        //your code here...
        return true;
    }
    private static boolean checkScore(String given){
        //your code here...
        return true;
    }
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        Gradebook gradebook = new Gradebook();
        //input handling phase
        while(true){
            String line = keyboard.nextLine();
            if(line.equals("DONE"))
                break;
            String[] tokens = line.split(" ");
            if(tokens.length != 4){
                //print the message of "TRY AGAIN" ...
                continue;
            }
            if(!checkFirstName(tokens[0])){
                //print the message of "TRY AGAIN" ...
                continue;
            }
            if(!checkLastName(tokens[1])){
                //print the message of "TRY AGAIN" ...
                continue;
            }
            if(!checkID(tokens[2])){
                //print the message of "TRY AGAIN" ...
                continue;
            }
            if(!checkScore(tokens[3])){
                //print the message of "TRY AGAIN" ...
                continue;
            }
            Student student = new Student(tokens[0],
                    tokens[1],
                    Integer.parseInt(tokens[2]),
                    new Grade(Integer.parseInt(tokens[3])));
            gradebook.addStudent(student);
        }
        //command handling phase
        while(true){
            String command = keyboard.nextLine();
            if(command.equals("quit"))
                break;
            if(command.equals("min score")){

            }else if(command.equals("min letter")){

            }else if(command.equals("max score")){

            }else if(command.equals("max letter")){

            }else if(command.equals("average score")){

            }else if(command.equals("average letter")){

            }else if(command.equals("median score")){

            }else if(command.equals("median letter")){

            }else if(command.equals("tab score")){

            }else if(command.equals("tab letter")){

            }else if(command.startsWith("change")){

            }else if(command.startsWith("name")){

            }else if(command.startsWith("letter")){

            }else{
                //print an error message "Illegal command, try again"...
            }
        }
    }

}
