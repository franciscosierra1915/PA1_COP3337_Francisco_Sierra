/* This is part of the starter code! 
 * You need to complete this class yourself!*/
package util;

import java.sql.Array;
import java.util.*;

public class Gradebook {
	public Gradebook(){
		listOfStudents = new ArrayList<Student>();
	}
    private ArrayList<Student> listOfStudents;
	public void addStudent(Student student){
		listOfStudents.add(student);
	}
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
