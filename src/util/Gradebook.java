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
import java.util.*;

/** Gradebook holds many Students and computes statistics & tabular outputs. */
public class Gradebook {

    private ArrayList<Student> listOfStudents;
    public double calculateAvg() {
        double sum = 0;
        for(Student s: listOfStudents)
            sum += s.getGrade().getScore();
        return sum / listOfStudents.size();
    }
    public float calculateMedian() {
        int i = 0, n = listOfStudents.size();
        int[] scores = new int[n];
        for(Student s: listOfStudents)
            scores[i++] = s.getGrade().getScore();
        Arrays.sort(scores);
        if (n % 2 == 0)
            return (scores[n / 2] + scores[n / 2 - 1]) / 2.0f;
        else
            return scores[n / 2];
    }
    public void printAllStudents() {
        for(Student s: listOfStudents)
            System.out.printf("%s\t%s\t%d\t%d\n", s.getFirstName(), s.getLastName(), s.getPid(), s.getGrade().getScore());
    }
}
