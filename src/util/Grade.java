/* This is part of the starter code! 
 * You need to complete this class yourself!*/
package util;

public class Grade {
    private int score;
    private String letterGrade;

    public static String scoreToLetter(int score){
        if(score >= 90)
            return "A";
        else
            return "F";
    }

    public Grade(int score) {
        this.score = score;
        this.letterGrade = scoreToLetter(score);
    }

    public int getScore() {
        return score;
    }
    public String getLetterGrade() {
        return letterGrade;
    }
}
