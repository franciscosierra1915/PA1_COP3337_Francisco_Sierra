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

/** Student has a first/last name, a 7-digit PID, and a Grade. */
public class Student {
    private final String firstName;
    private final String lastName;
    private final int pid; // 7-digit, no leading zero.
    private Grade grade;

    public Student(String firstName, String lastName, int pid, Grade grade){
        this.firstName = firstName;
        this.lastName = lastName;
        this.pid = pid;
        this.grade = grade;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public int getPid() {
        return pid;
    }
    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade){
        this.grade = grade;
    }
}
