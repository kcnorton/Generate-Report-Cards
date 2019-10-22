
/**
 * Write a description of Tester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.io.*;

public class Tester {
    //test method
    public void testGradeCalculation(){
        try{
            //test read courses
            FileReader courses1 = new FileReader("test-files/courses1.csv");
            ReportCard rc1 = new ReportCard();
            rc1.readCourses(courses1);
            System.out.println("The course map is " + rc1.courseMap);
            //test read students
            FileReader students1 = new FileReader("test-files/students1.csv");
            rc1.readStudents(students1);
            System.out.println("The student map is " + rc1.studentMap);
            //test read tests
            FileReader tests1 = new FileReader("test-files/tests1.csv");
            rc1.readTests(tests1);
            System.out.println("The test map is " + rc1.testMap);
            //test read marks
            FileReader marks1 = new FileReader("test-files/marks1.csv");
            rc1.readMarks(marks1);
            System.out.println("The mark map is " + rc1.markMap);
            
            //test that the correct grade was calculated
            double test1 = rc1.calculateFinalGrade(1,2);
            System.out.println("the final grade is " + test1);
        }
        catch(FileNotFoundException e){
            System.out.println("FILENOTFOUND");
        }
    }
    
    //test whether an error is thrown for test weights adding up to less than 100 for course 3
    public void testGradeCalculation2(){
        try{
            //test read courses
            FileReader courses1 = new FileReader("test-files/courses1.csv");
            ReportCard rc1 = new ReportCard();
            rc1.readCourses(courses1);
            //test read students
            FileReader students1 = new FileReader("test-files/students1.csv");
            rc1.readStudents(students1);
            //test read tests
            FileReader tests1 = new FileReader("test-files/tests1.csv");
            rc1.readTests(tests1);
            //test read marks
            FileReader marks1 = new FileReader("test-files/marks1.csv");
            rc1.readMarks(marks1);
            //test whether an error is thrown for test weights adding up to less than 100 for course 3
            double test2 = rc1.calculateFinalGrade(3,3);
        }
        catch(FileNotFoundException e){
            System.out.println("FILENOTFOUND");
        }
    }
    //test whether an error is thrown for a student 1 who has not completed course 3
    public void testGradeCalculation3(){
        try{
            //test read courses
            FileReader courses1 = new FileReader("test-files/courses1.csv");
            ReportCard rc1 = new ReportCard();
            rc1.readCourses(courses1);
            //test read students
            FileReader students1 = new FileReader("test-files/students1.csv");
            rc1.readStudents(students1);
            //test read tests
            FileReader tests1 = new FileReader("test-files/tests1.csv");
            rc1.readTests(tests1);
            //test read marks
            FileReader marks1 = new FileReader("test-files/marks1.csv");
            rc1.readMarks(marks1);
            //test whether an error is thrown for a student 1 who has not completed course 3
            double test3 = rc1.calculateFinalGrade(1,3);
        }
        catch(FileNotFoundException e){
            System.out.println("FILENOTFOUND");
        }
    }
    //test average of all courses
    public void testAverageGrade(){
        try{
            //test read courses
            FileReader courses1 = new FileReader("test-files/courses1.csv");
            ReportCard rc1 = new ReportCard();
            rc1.readCourses(courses1);
            //test read students
            FileReader students1 = new FileReader("test-files/students1.csv");
            rc1.readStudents(students1);
            //test read tests
            FileReader tests1 = new FileReader("test-files/tests1.csv");
            rc1.readTests(tests1);
            //test read marks
            FileReader marks1 = new FileReader("test-files/marks1.csv");
            rc1.readMarks(marks1);
            //test the correct average for student 2
            double avg = rc1.averageOfAllCourses(2);
            System.out.println("The average of all courses is " + avg);
        }
        catch(FileNotFoundException e){
            System.out.println("FILENOTFOUND");
        }
    }

}
