package main;                              // 🗂️ App package

import util.*;                             // 📦 Use Grade/Student/Gradebook
import java.util.*;                        // 🧰 Scanner
import java.util.regex.*;                  // 🔤 Regex validation

public class Main {
    private static final Pattern FIRST_RE = Pattern.compile("^[A-Z][a-zA-Z]*$");              // 👤 First (Cap + letters)
    private static final Pattern LAST_RE  = Pattern.compile("^[A-Z](?:[a-zA-Z]*\\.?[a-zA-Z]*)$"); // 👥 Last (≤1 '.')
    private static final Pattern PID_RE   = Pattern.compile("^[1-9][0-9]{6}$");               // 🪪 PID: 7 digits, no leading 0

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);   // 🎤 Read keyboard
        Gradebook gb = new Gradebook();        // 📚 Store students

        System.out.println("Enter students. Type DONE at any prompt to finish input."); // 📝 Instructions
        while (true) {                         // 🔁 Input loop
            String first = prompt(sc, "First name (CapOnly, no spaces): ");
            if (isDone(first)) break;
            if (!FIRST_RE.matcher(first).matches()) { System.out.println("❌ Invalid first name."); continue; }

            String last = prompt(sc, "Last name (CapOnly, one optional '.'): ");
            if (isDone(last)) break;
            if (!LAST_RE.matcher(last).matches() || countChar(last, '.') > 1) { System.out.println("❌ Invalid last name."); continue; }

            String pidStr = prompt(sc, "PID (7 digits, no leading 0): ");
            if (isDone(pidStr)) break;
            if (!PID_RE.matcher(pidStr).matches()) { System.out.println("❌ Invalid PID."); continue; }
            int pid = Integer.parseInt(pidStr);

            String scoreStr = prompt(sc, "Score (0-100): ");
            if (isDone(scoreStr)) break;
            Integer score = parseScore(scoreStr);
            if (score == null) { System.out.println("❌ Invalid score."); continue; }

            gb.addStudent(new Student(first, last, pid, new Grade(score))); // ➕ Add valid student
            System.out.println("✅ Added.");
        }

        System.out.println("\nEnter commands (type 'help' for list).");   // 🧭 Command loop
        while (true) {
            String line = prompt(sc, "> ").trim();
            if (line.isEmpty()) continue;
            String[] parts = line.split("\\s+");
            String cmd = parts[0].toLowerCase();

            try {
                switch (cmd) {
                    case "help":   printHelp(); break;                     // 🆘
                    case "quit":   System.out.println("Bye!"); return;     // 🚪

                    case "min":    // ⤵️
                        if (parts.length>=2 && parts[1].equalsIgnoreCase("score")) System.out.println(gb.minScore());
                        else if (parts.length>=2 && parts[1].equalsIgnoreCase("letter")) System.out.println(gb.minLetter());
                        else System.out.println("Usage: min score|letter");
                        break;

                    case "max":    // ⤴️
                        if (parts.length>=2 && parts[1].equalsIgnoreCase("score")) System.out.println(gb.maxScore());
                        else if (parts.length>=2 && parts[1].equalsIgnoreCase("letter")) System.out.println(gb.maxLetter());
                        else System.out.println("Usage: max score|letter");
                        break;

                    case "average": // ➗
                        if (parts.length>=2 && parts[1].equalsIgnoreCase("score")) System.out.printf("%.2f%n", gb.averageScore());
                        else if (parts.length>=2 && parts[1].equalsIgnoreCase("letter")) System.out.println(gb.averageLetter());
                        else System.out.println("Usage: average score|letter");
                        break;

                    case "median":  // 🔸
                        if (parts.length>=2 && parts[1].equalsIgnoreCase("score")) System.out.printf("%.2f%n", (double)gb.calculateMedian());
                        else if (parts.length>=2 && parts[1].equalsIgnoreCase("letter")) System.out.println(gb.medianLetter());
                        else System.out.println("Usage: median score|letter");
                        break;

                    case "letter":  // 🔤 letter <PID>
                        if (parts.length==2 && PID_RE.matcher(parts[1]).matches()) {
                            Student s = gb.findByPid(Integer.parseInt(parts[1]));
                            System.out.println(s==null ? "Not found" : s.getGrade().getLetterGrade());
                        } else System.out.println("Usage: letter <PID>");
                        break;

                    case "name":    // 🧑‍🤝‍🧑 name <PID>
                        if (parts.length==2 && PID_RE.matcher(parts[1]).matches()) {
                            Student s = gb.findByPid(Integer.parseInt(parts[1]));
                            System.out.println(s==null ? "Not found" : (s.getFirstName() + " " + s.getLastName()));
                        } else System.out.println("Usage: name <PID>");
                        break;

                    case "change":  // 🔁 change <PID> <score>
                        if (parts.length==3 && PID_RE.matcher(parts[1]).matches()) {
                            Integer score = parseScore(parts[2]);
                            if (score == null) { System.out.println("Score must be 0-100"); break; }
                            boolean ok = gb.changeScore(Integer.parseInt(parts[1]), score);
                            System.out.println(ok ? "OK" : "Not found");
                        } else System.out.println("Usage: change <PID> <score>");
                        break;

                    case "tab":     // 🧾 tab scores|letters
                        if (parts.length>=2 && parts[1].equalsIgnoreCase("scores")) gb.printAllStudentsScores(System.out);
                        else if (parts.length>=2 && parts[1].equalsIgnoreCase("letters")) gb.printAllStudentsLetters(System.out);
                        else System.out.println("Usage: tab scores|letters");
                        break;

                    default: System.out.println("Unknown. Type 'help'.");
                }
            } catch (IllegalStateException ise) {
                System.out.println("No students loaded.");
            }
        }
    }

    // 🧰 Small helpers
    private static String prompt(Scanner sc, String msg) { System.out.print(msg); return sc.nextLine(); }
    private static boolean isDone(String s) { return s != null && s.trim().equalsIgnoreCase("DONE"); }
    private static Integer parseScore(String s) { try { int v = Integer.parseInt(s); return (v<0||v>100)?null:v; } catch (NumberFormatException e){ return null; } }
    private static int countChar(String s, char c){ int k=0; for(char ch: s.toCharArray()) if(ch==c) k++; return k; }
    private static void printHelp() {
        System.out.println("Commands: min score|letter, max score|letter, average score|letter,");
        System.out.println(" median score|letter, letter <PID>, name <PID>, change <PID> <score>,");
        System.out.println(" tab scores|letters, quit");
    }
}
