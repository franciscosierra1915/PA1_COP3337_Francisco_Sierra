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

/**
 * A class named Grade that holds a numeric score (between 0 and 100) and its letter  equivalent.
 * Letter boundaries follow the syllabus cut-offs provided by the user.
 * @author Francisco
 * @version 1.0
 */

public class Grade {
    private int score; //between 0 and 100
    private String letterGrade; //"A-", "B+", etc.

    // Build a Grade from a numeric score.
    public Grade(int score){
        setScore(score);
    }

    // @return current numeric score
    public int getScore() {
        return score;
    }

    // @return current letter grade
    public String getLetterGrade() {
        return letterGrade;
    }

    //Update score and recompute letter; throws if out of range
    public void setScore(int score){
        if(score < 0 || score > 100){
            throw new IllegalArgumentException("Score must be between 0 and 100.");
        }
        this.score = score;
        this.letterGrade = letterFromScore(score);
    }

    //Map a numeric score (int/double) to a letter using syllabus cut-offs.
    public static String letterFromScore(double s){
        // A: 95-100, A-: 90-94.99, B+:87-89.99, B: 83-86.99, B-:80-82.99,
        // C+:77-79.99, C: 70-76.99, D: 60-69.99, F: 0-59.99.
        if(s >= 95.0) return "A";
        if(s >= 90.0) return "A-";
        if(s >= 87.0) return "B+";
        if(s >= 83.9) return "B";
        if(s >= 80.0) return "B-";
        if (s >= 77.0) return "C+";
        if (s >= 70.0) return "C";
        if (s >= 60.0) return "D";
        return "F";
    }

}
