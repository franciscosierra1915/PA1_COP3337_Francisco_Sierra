/*
HEADER COMMENT BLOCK
Author: Francisco Sierra
Course: COP 3337 Programming II
Date: 09/03/2025
Assignment: Programming Assignment 1: Designing Classes
Instructor: Kiavash Bahreini
Description: A program for storing the grades of students in a java.util.ArrayList and calculating/presenting some statistics of grades.
*/
package util;
import java.sql.Array;
import java.util.*;

/** Gradebook holds many Students and computes statistics & tabular outputs. */
public class Gradebook {

    private final ArrayList<Student> listOfStudents = new ArrayList<>();

    public int size() {return listOfStudents.size();}

    public void addStudent(Student s){ listOfStudents.add(s);}

    public Student findByPid(int pid){
        for(Student s : listOfStudents){
            if(s.getPid() == pid) return s;
        }
        return null;
    }

    public int minScore(){
        requireNotEmpty();
        int min = Integer.MAX_VALUE;
        for(Student s : listOfStudents){
            int sc = s.getGrade().getScore();
            if(sc < min){
                min = sc;
            }
        }
        return min;
    }

    public int maxScore(){
        requireNotEmpty();
        int max = Integer.MIN_VALUE;
        for(Student s : listOfStudents){
            int sc = s.getGrade().getScore();
            if(sc > max){
                max = sc;
            }
        }
        return max;
    }

    public String minLetter(){
        return Grade.letterFromScore(minScore());
    }
    public String maxLetter(){
        return Grade.letterFromScore(maxScore());
    }

    public double averageScore(){
        requireNotEmpty();
        long sum = 0;
        for(Student s : listOfStudents) sum += s.getGrade().getScore();
        return sum / (double) listOfStudents.size();
    }

    public String averageLetter(){
        return Grade.letterFromScore(averageScore());
    }

    public float calculateMedian(){
        requireNotEmpty();
        int n = listOfStudents.size();
        int[] scores = new int[n];
        int i = 0;
        for (Student s : listOfStudents){
            scores[i++] = s.getGrade().getScore();
        }
        Arrays.sort(scores);
        if(n % 2 == 0){
            return (float)((scores[n/2] + scores[n/2 - 1]) / 2.0);
        } else{
            return scores[n/2];
        }
    }

    public String medianLetter(){
        return Grade.letterFromScore(calculateMedian());
    }

    public boolean changeScore(int pid, int newScore){
        Student s = findByPid(pid);
        if( s == null) return false;
        s.getGrade().setScore(newScore);
        return true;
    }

    public void printAllStudentsScores(java.io.PrintStream out){
        for (Student s : listOfStudents){
            out.printf("%s\t%s\t%d\t%d%n", s.getFirstName(), s.getLastName(), s.getPid(), s.getGrade().getScore());
        }
    }


    public void printAllStudentsLetters(java.io.PrintStream out) {
        for (Student s : listOfStudents) {
            out.printf("%s\t%s\t%d\t%s%n", s.getFirstName(), s.getLastName(), s.getPid(), s.getGrade().getLetterGrade());
        }
    }

    private void requireNotEmpty(){
        if(listOfStudents.isEmpty()) throw new IllegalStateException("No student loaded.");
    }
}
