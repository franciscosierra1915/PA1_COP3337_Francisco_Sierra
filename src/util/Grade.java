/*
 * HEADER COMMENT BLOCK
 * Author: Francisco Sierra
 * Course: COP 3337 Programming II
 * Date: 09/03/2025
 * Assignment: Programming Assignment 1: Designing Classes
 * Instructor: Kiavash Bahreini
 * Description: A program for storing the grades of students in a java.util.ArrayList
 *              and calculating/presenting some statistics of grades.
 */
package util;

/**
 * The {@code Grade} class represents a single grade with a numeric score (0–100)
 * and its equivalent letter grade. Letter boundaries follow the syllabus cut-offs
 * provided by the instructor.
 * <p>
 * Example:
 * <pre>
 *  Grade g = new Grade(92); // "A-"
 * </pre>
 * </p>
 *
 * @author Francisco
 * @version 1.0
 */

public class Grade {
    /**
     * The numeric score for this grade.
     * Must be between 0 and 100 (inclusive).
     */
    private int score;

    /**
     * The letter grade equivalent of the numeric score.
     * For example: "A", "A-", "B+", etc.
     */
    private String letterGrade;

    /**
     * Constructs a new {@code Grade} object with the given numeric score.
     * Automatically computes the correct letter grade.
     *
     * @param score the numeric score (0–100)
     * @throws IllegalArgumentException if the score is outside [0,100]
     */
    public Grade(int score){
        setScore(score);
    }

    /**
     * Returns the numeric score of this grade.
     *
     * @return the numeric score (0–100)
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns the letter grade equivalent of this grade.
     *
     * @return the letter grade as a string (e.g., "A-", "B+")
     */
    public String getLetterGrade() {
        return letterGrade;
    }

    /**
     * Updates this grade’s numeric score and recomputes its letter equivalent.
     *
     * @param score the new numeric score (0–100)
     * @throws IllegalArgumentException if the score is outside [0,100]
     */
    public void setScore(int score){
        if(score < 0 || score > 100){
            throw new IllegalArgumentException("Score must be between 0 and 100.");
        }
        this.score = score;
        this.letterGrade = letterFromScore(score);
    }

    /**
     * Converts a numeric score to its equivalent letter grade
     * using the syllabus cut-offs.
     *
     * @param s the numeric score (can be int or double, allows averages)
     * @return the letter grade as a string
     */
    public static String letterFromScore(double s){
        // A: 95-100, A-: 90-94.99, B+:87-89.99, B: 83-86.99, B-:80-82.99,
        // C+:77-79.99, C: 70-76.99, D: 60-69.99, F: 0-59.99.
        if(s >= 95.0) return "A";
        if(s >= 90.0) return "A-";
        if(s >= 87.0) return "B+";
        if(s >= 83.0) return "B";
        if(s >= 80.0) return "B-";
        if (s >= 77.0) return "C+";
        if (s >= 70.0) return "C";
        if (s >= 60.0) return "D";
        return "F";
    }

}
