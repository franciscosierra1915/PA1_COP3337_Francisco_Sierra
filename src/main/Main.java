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

package main;

import util.*;

import java.util.*;
import java.util.regex.*;

/**
 * Console entry-point for the Gradebook application.
 * <p>
 * This program runs in two phases:
 * <ol>
 *   <li><b>Input phase</b> – Repeatedly prompts for student info
 *       (first name, last name, PID, score). Each value is validated.
 *       Typing {@code DONE} at any prompt exits the input phase.</li>
 *   <li><b>Command phase</b> – Accepts text commands to query and mutate
 *       the gradebook, e.g., {@code min score}, {@code max letter},
 *       {@code average score}, {@code median letter}, {@code letter <PID>},
 *       {@code name <PID>}, {@code change <PID> <score>},
 *       {@code tab scores}, {@code tab letters}, and {@code quit}.</li>
 * </ol>
 *
 * <p>Validation rules:</p>
 * <ul>
 *   <li>First name: capitalized, letters only (no spaces).</li>
 *   <li>Last name: capitalized, letters only, may contain at most one period.</li>
 *   <li>PID: exactly 7 digits, no leading zero.</li>
 *   <li>Score: integer in [0, 100].</li>
 * </ul>
 *
 * <p>Examples:</p>
 * <pre>
 *   Enter students. Type DONE at any prompt to finish input.
 *   First name (CapOnly, no spaces): Alice
 *   Last name (CapOnly, one optional '.'): Smith
 *   PID (7 digits, no leading 0): 1234567
 *   Score (0-100): 95
 *   Added.
 *
 *   Enter commands (type 'help' for list).
 *   > average score
 *   95.00
 *   > letter 1234567
 *   A
 *   > quit
 *   Bye!
 * </pre>
 *
 * @author Francisco
 * @version 1.0
 */
public class Main {
    /**
     * Regex for a first name: capital letter followed by letters only.
     */
    private static final Pattern FIRST_RE = Pattern.compile("^[A-Z][a-zA-Z]*$");

    /**
     * Regex for a last name: capital letter, letters only, with one optional
     * period somewhere in the word. A separate runtime check ensures at most
     * one period appears.
     */
    private static final Pattern LAST_RE = Pattern.compile("^[A-Z](?:[a-zA-Z]*\\.?[a-zA-Z]*)$");

    /**
     * Regex for a PID: 7 digits, first digit nonzero (no leading zero).
     */
    private static final Pattern PID_RE = Pattern.compile("^[1-9][0-9]{6}$");

    /**
     * Program entry point. Creates a {@link Gradebook}, then runs the input
     * and command loops on standard input/output.
     *
     * @param args not used
     */
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);    // Read user input from standard input
        Gradebook gb = new Gradebook();         // Container for all students

        System.out.println("Welcome to my grade book!");
        System.out.println("Please enter the information of the first student using the following format:");
        System.out.println("firstName lastName PID grade");
        System.out.println("Press Enter when you are done.");
        while (true) { // Input phase: loop until user types DONE
            // Prompt for first name and allow sentinel to exit input phase
            String first = prompt(sc, "First name (CapOnly, no spaces): ");
            if (isDone(first)) break;  // Exit input phase if user typed DONE
            // Validate first name format (capitalized, letters only)
            if (!FIRST_RE.matcher(first).matches()) {
                System.out.println("Invalid first name.");
                continue; // Restart loop and re-prompt from first name
            }

            // Prompt for last name; allow sentinel to exit input phase
            String last = prompt(sc, "Last name (CapOnly, one optional '.'): ");
            if (isDone(last)) break; // Exit input phase if user typed DONE
            // Validate last name format (capitalized, letters only, at most one period)
            if (!LAST_RE.matcher(last).matches() || countChar(last, '.') > 1) {
                System.out.println("Invalid last name.");
                continue; // Restart loop and re-prompt from first name
            }

            // Prompt for PID; allow sentinel to exit input phase
            String pidStr = prompt(sc, "PID (7 digits, no leading 0): ");
            if (isDone(pidStr)) break; // Exit input phase if user typed DONE
            // Validate PID format (7 digits, first digit nonzero)
            if (!PID_RE.matcher(pidStr).matches()) {
                System.out.println("Invalid PID.");
                continue; // Restart loop and re-prompt from first name
            }
            int pid = Integer.parseInt(pidStr); // Safe to parse after regex validation

            // Prompt for score; allow sentinel to exit input phase
            String scoreStr = prompt(sc, "Score (0-100): ");
            if (isDone(scoreStr)) break; // Exit input phase if user typed DONE
            // Validate score is an integer in [0, 100]
            Integer score = parseScore(scoreStr);
            if (score == null) {
                System.out.println("Invalid score.");
                continue; // Restart loop and re-prompt from first name
            }

            // All fields are valid: construct and add the student record
            gb.addStudent(new Student(first, last, pid, new Grade(score)));
            System.out.println("Please enter the information of the next student using the same format.");
            System.out.println("If there is no more students, please enter the keyword “DONE”.");
            System.out.println("Press Enter when you are done.");
        }

        System.out.println("\nPlease enter a new command (type 'help' for list).");
        while (true) { // Command phase: loop until user quits
            String line = prompt(sc, "> ").trim(); // Read a full command line
            if (line.isEmpty()) continue; // Ignore blank lines and prompt again

            // Tokenize: first token is the command, the rest are arguments
            String[] parts = line.split("\\s+");
            String cmd = parts[0].toLowerCase();

            try {
                switch (cmd) {
                    case "help":
                        // Print the list of supported commands and usage
                        printHelp();
                        break;
                    case "quit":
                        // Exit the program immediately
                        System.out.println("Bye!");
                        return;

                    case "min":
                        // min score  -> numeric minimum
                        // min letter -> letter for minimum score boundary
                        if (parts.length >= 2 && parts[1].equalsIgnoreCase("score")) System.out.println(gb.minScore());
                        else if (parts.length >= 2 && parts[1].equalsIgnoreCase("letter"))
                            System.out.println(gb.minLetter());
                        else System.out.println("Usage: min score|letter");
                        break;

                    case "max":
                        // max score  -> numeric maximum
                        // max letter -> letter for maximum score boundary
                        if (parts.length >= 2 && parts[1].equalsIgnoreCase("score")) System.out.println(gb.maxScore());
                        else if (parts.length >= 2 && parts[1].equalsIgnoreCase("letter"))
                            System.out.println(gb.maxLetter());
                        else System.out.println("Usage: max score|letter");
                        break;

                    case "average":
                        // average score  -> numeric average (two decimals)
                        // average letter -> letter from average score
                        if (parts.length >= 2 && parts[1].equalsIgnoreCase("score"))
                            System.out.printf("%.2f%n", gb.averageScore());
                        else if (parts.length >= 2 && parts[1].equalsIgnoreCase("letter"))
                            System.out.println(gb.averageLetter());
                        else System.out.println("Usage: average score|letter");
                        break;

                    case "median":
                        // median score  -> numeric median (two decimals)
                        // median letter -> letter from median score
                        if (parts.length >= 2 && parts[1].equalsIgnoreCase("score"))
                            System.out.printf("%.2f%n", (double) gb.calculateMedian());
                        else if (parts.length >= 2 && parts[1].equalsIgnoreCase("letter"))
                            System.out.println(gb.medianLetter());
                        else System.out.println("Usage: median score|letter");
                        break;

                    case "letter":
                        // letter <PID> -> print the student's letter grade
                        if (parts.length == 2 && PID_RE.matcher(parts[1]).matches()) {
                            Student s = gb.findByPid(Integer.parseInt(parts[1]));
                            System.out.println(s == null ? "Not found" : s.getGrade().getLetterGrade());
                        } else System.out.println("Usage: letter <PID>");
                        break;

                    case "name":
                        // name <PID> -> print "First Last" for that student
                        if (parts.length == 2 && PID_RE.matcher(parts[1]).matches()) {
                            Student s = gb.findByPid(Integer.parseInt(parts[1]));
                            System.out.println(s == null ? "Not found" : (s.getFirstName() + " " + s.getLastName()));
                        } else System.out.println("Usage: name <PID>");
                        break;

                    case "change":
                        // change <PID> <score> -> update score (and letter) for that student
                        if (parts.length == 3 && PID_RE.matcher(parts[1]).matches()) {
                            Integer score = parseScore(parts[2]);
                            if (score == null) {
                                System.out.println("Score must be 0-100");
                                break;
                            }
                            boolean ok = gb.changeScore(Integer.parseInt(parts[1]), score);
                            System.out.println(ok ? "OK" : "Not found");
                        } else System.out.println("Usage: change <PID> <score>");
                        break;

                    case "tab":
                        // tab scores  -> print table with numeric scores
                        // tab letters -> print table with letter grades
                        if (parts.length >= 2 && parts[1].equalsIgnoreCase("scores"))
                            gb.printAllStudentsScores(System.out);
                        else if (parts.length >= 2 && parts[1].equalsIgnoreCase("letters"))
                            gb.printAllStudentsLetters(System.out);
                        else System.out.println("Usage: tab scores|letters");
                        break;

                    default:
                        // Command not recognized
                        System.out.println("Unknown. Type 'help'.");
                }
            } catch (IllegalStateException ise) {
                // Thrown by gradebook methods (e.g., stats on empty set)
                System.out.println("No students loaded.");
            }
        }
    }

    /**
     * Prompts on the provided scanner and returns the user input (without trimming).
     *
     * @param sc  the scanner to read from
     * @param msg the prompt message to display
     * @return the line of input entered by the user (could be empty)
     */
    private static String prompt(Scanner sc, String msg) {
        System.out.print(msg);
        return sc.nextLine();
    }

    /**
     * Returns whether the user entered the sentinel value {@code DONE} (any case).
     *
     * @param s the string to test
     * @return {@code true} if {@code s} equals "DONE" ignoring case and surrounding spaces; otherwise {@code false}
     */
    private static boolean isDone(String s) {
        return s != null && s.trim().equalsIgnoreCase("DONE");
    }

    /**
     * Parses an integer score and validates that it is in range [0, 100].
     *
     * @param s the input string
     * @return the parsed score if valid; otherwise {@code null}
     */
    private static Integer parseScore(String s) {
        try {
            int v = Integer.parseInt(s);
            return (v < 0 || v > 100) ? null : v;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Counts how many times the given character appears in a string.
     *
     * @param s the string to scan (must not be {@code null})
     * @param c the character to count
     * @return the number of occurrences of {@code c} in {@code s}
     */
    private static int countChar(String s, char c) {
        int k = 0;
        for (char ch : s.toCharArray()) if (ch == c) k++;
        return k;
    }

    /**
     * Prints the list of supported commands and their syntax to standard output.
     */
    private static void printHelp() {
        System.out.println("Commands: min score|letter, max score|letter, average score|letter,");
        System.out.println(" median score|letter, letter <PID>, name <PID>, change <PID> <score>,");
        System.out.println(" tab scores|letters, quit");
    }
}
