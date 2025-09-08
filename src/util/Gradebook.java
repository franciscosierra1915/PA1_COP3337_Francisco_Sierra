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

import java.util.*;

/**
 * The {@code Gradebook} class maintains a collection of {@link Student} objects
 * and provides operations to compute statistics (minimum, maximum, average, median),
 * update scores, and print tabular outputs of all students.
 * <p>
 * Example:
 * <pre>
 *     Gradebook gb = new Gradebook();
 *     gb.addStudent(new Student("Alice", "Smith", 1234567, new Grade(95)));
 *     System.out.println(gb.averageScore()); // prints average score
 * </pre>
 * </p>
 * <p>
 * This class enforces that at least one student must be present before
 * statistics are computed. If empty, an {@link IllegalStateException} is thrown.
 *
 * @author Francisco
 * @version 1.0
 */
public class Gradebook {

    /**
     * List of students currently in the gradebook.
     * This is initialized as an empty {@code ArrayList}.
     */
    private final ArrayList<Student> listOfStudents = new ArrayList<>();

    /**
     * Adds a new student to the gradebook.
     *
     * @param s the student to add
     */
    public void addStudent(Student s) {
        listOfStudents.add(s);
    }

    /**
     * Finds a student by PID.
     *
     * @param pid the PID to search for
     * @return the matching {@code Student} if found, otherwise {@code null}
     */
    public Student findByPid(int pid) {
        for (Student s : listOfStudents) {
            if (s.getPid() == pid) return s;
        }
        return null;
    }

    /**
     * Returns the minimum numeric score in the gradebook.
     *
     * @return the minimum score
     * @throws IllegalStateException if no students are loaded
     */
    public int minScore() {
        requireNotEmpty();
        int min = Integer.MAX_VALUE;
        for (Student s : listOfStudents) {
            int sc = s.getGrade().getScore();
            if (sc < min) {
                min = sc;
            }
        }
        return min;
    }

    /**
     * Returns the maximum numeric score in the gradebook.
     *
     * @return the maximum score
     * @throws IllegalStateException if no students are loaded
     */
    public int maxScore() {
        requireNotEmpty();
        int max = Integer.MIN_VALUE;
        for (Student s : listOfStudents) {
            int sc = s.getGrade().getScore();
            if (sc > max) {
                max = sc;
            }
        }
        return max;
    }

    /**
     * Returns the lowest letter grade in the gradebook.
     *
     * @return the lowest letter grade
     */
    public String minLetter() {
        return Grade.letterFromScore(minScore());
    }

    /**
     * Returns the highest letter grade in the gradebook.
     *
     * @return the highest letter grade
     */
    public String maxLetter() {
        return Grade.letterFromScore(maxScore());
    }

    /**
     * Calculates the average numeric score of all students.
     *
     * @return the average score
     * @throws IllegalStateException if no students are loaded
     */
    public double averageScore() {
        requireNotEmpty();
        long sum = 0;
        for (Student s : listOfStudents) sum += s.getGrade().getScore();
        return sum / (double) listOfStudents.size();
    }

    /**
     * Calculates the average letter grade of all students
     * based on the average numeric score.
     *
     * @return the average letter grade
     */
    public String averageLetter() {
        return Grade.letterFromScore(averageScore());
    }

    /**
     * Calculates the median numeric score of all students.
     *
     * @return the median score
     * @throws IllegalStateException if no students are loaded
     */
    public float calculateMedian() {
        requireNotEmpty();
        int n = listOfStudents.size();
        int[] scores = new int[n];
        int i = 0;
        for (Student s : listOfStudents) {
            scores[i++] = s.getGrade().getScore();
        }
        Arrays.sort(scores);
        if (n % 2 == 0) {
            return (float) ((scores[n / 2] + scores[n / 2 - 1]) / 2.0);
        } else {
            return scores[n / 2];
        }
    }

    /**
     * Returns the median letter grade of all students.
     *
     * @return the median letter grade
     */
    public String medianLetter() {
        return Grade.letterFromScore(calculateMedian());
    }

    /**
     * Changes the numeric score of a student with the given PID.
     *
     * @param pid      the student PID
     * @param newScore the new score to assign
     * @return {@code true} if the score was updated, {@code false} if student not found
     * @throws IllegalArgumentException if {@code newScore} is outside [0,100]
     */
    public boolean changeScore(int pid, int newScore) {
        Student s = findByPid(pid);
        if (s == null) return false;
        s.getGrade().setScore(newScore);
        return true;
    }

    /**
     * Prints a tab-separated list of all students showing scores.
     *
     * @param out the output stream to print to
     */
    public void printAllStudentsScores(java.io.PrintStream out) {
        for (Student s : listOfStudents) {
            out.printf("%s\t%s\t%d\t%d%n", s.getFirstName(), s.getLastName(), s.getPid(), s.getGrade().getScore());
        }
    }

    /**
     * Prints a tab-separated list of all students showing letter grades.
     *
     * @param out the output stream to print to
     */
    public void printAllStudentsLetters(java.io.PrintStream out) {
        for (Student s : listOfStudents) {
            out.printf("%s\t%s\t%d\t%s%n", s.getFirstName(), s.getLastName(), s.getPid(), s.getGrade().getLetterGrade());
        }
    }

    /**
     * Ensures the gradebook is not empty.
     *
     * @throws IllegalStateException if no students are present
     */
    private void requireNotEmpty() {
        if (listOfStudents.isEmpty()) throw new IllegalStateException("No student loaded.");
    }
}
