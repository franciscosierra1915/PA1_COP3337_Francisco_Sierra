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
 * The {@code Student} class represents a student with a first name, last name,
 * a unique 7-digit PID (no leading zeros), and an associated {@link Grade}.
 * <p>
 * Example:
 * <pre>
 *     Grade g = new Grade(95);
 *     Student s = new Student("Alice", "Smith", 1234567, g);
 * </pre>
 * </p>
 *
 * Each {@code Student} object is immutable in terms of name and PID
 * (these fields are declared {@code final}), but the {@code Grade} may be updated.
 *
 * @author Francisco
 * @version 1.0
 */
public class Student {

    /** The student's first name (single word, starts with a capital letter). */
    private final String firstName;

    /** The student's last name (single word, starts with a capital letter, may contain one period). */
    private final String lastName;

    /** The student's PID (7-digit integer with no leading zero). */
    private final int pid;

    /** The student's grade, containing both numeric score and letter grade. */
    private Grade grade;

    /**
     * Constructs a new {@code Student} object with the provided details.
     *
     * @param firstName the student's first name
     * @param lastName the student's last name
     * @param pid the student's PID (7 digits, no leading zero)
     * @param grade the student's {@link Grade} object
     */
    public Student(String firstName, String lastName, int pid, Grade grade){
        this.firstName = firstName;
        this.lastName = lastName;
        this.pid = pid;
        this.grade = grade;
    }

    /**
     * Returns the student's first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the student's last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the student's PID.
     *
     * @return the 7-digit PID
     */
    public int getPid() {
        return pid;
    }

    /**
     * Returns the student's {@link Grade}.
     *
     * @return the {@code Grade} object
     */
    public Grade getGrade() {
        return grade;
    }

    /**
     * Updates the student's {@link Grade}.
     *
     * @param grade the new grade to assign
     */
    public void setGrade(Grade grade){
        this.grade = grade;
    }
}
