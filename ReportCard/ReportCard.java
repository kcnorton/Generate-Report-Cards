
/**
 * Write a description of ReportCard here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import java.io.*;
import java.lang.*; //for integer to int conversion
import java.text.*; //for decimal formatting

public class ReportCard {
    //main method
    public static void main(String[] args){
        //create Report Card object
        ReportCard rc = new ReportCard();
        //read in files
        rc.readAllFiles();
        //print report card
        rc.makeReportCard();
    }
    //fields
    public HashMap<Integer,ArrayList<String>> courseMap;
    public HashMap<Integer,String> studentMap;
    public HashMap<Integer,ArrayList<Integer>> testMap;
    public HashMap<ArrayList<Integer>,Integer> markMap;
    
    //constructor
    public ReportCard(){
        courseMap = new HashMap<Integer,ArrayList<String>>();
        studentMap = new HashMap<Integer,String>();
        testMap = new HashMap<Integer,ArrayList<Integer>>();
        markMap = new HashMap<ArrayList<Integer>,Integer>();
    }
    
    //methods
    //read in courses file
    public void readCourses(FileReader fr){
        try{
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            //iterate over CSV file
            while((line = br.readLine()) != null && !line.isEmpty()){
                //split fields into array
                String[] fields = line.split(",");
                //check if hashmap has key
                if(!(courseMap.containsKey(fields[0]))){
                    //if not, make new arraylist value
                    ArrayList<String> value = new ArrayList<String>();
                    //add name and teacher to arraylist
                    value.add(fields[1]);
                    value.add(fields[2]);
                    //add key-value pair to hashmap
                    courseMap.put(Integer.parseInt(fields[0]),value);
                }
            }
        }
        catch(IOException e)
        {
                    System.out.println("IOException " + e);
        }
    }
    
    //read in students file
    public void readStudents(FileReader fr){
        try{
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            //iterate over CSV file
            while((line = br.readLine()) != null && !line.isEmpty()){
                //split fields into array
                String[] fields = line.split(",");
                //check if hashmap has key
                if(!(studentMap.containsKey(fields[0]))){
                    //add key-value pair to hashmap
                    studentMap.put(Integer.parseInt(fields[0]),fields[1]);
                }
            }
        }
        catch(IOException e)
        {
                    System.out.println("IOException " + e);
        }
    }
    
    //read in tests file
    public void readTests(FileReader fr){
        try{
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            //iterate over CSV file
            while((line = br.readLine()) != null && !line.isEmpty()){
                //split fields into array
                String[] fields = line.split(",");
                //check if hashmap has key
                if(!(testMap.containsKey(fields[0]))){
                    //if not, make new arraylist value
                    ArrayList<Integer> value = new ArrayList<Integer>();
                    //add course id and weight to arraylist
                    value.add(Integer.parseInt(fields[1]));
                    value.add(Integer.parseInt(fields[2]));
                    //add key-value pair to hashmap
                    testMap.put(Integer.parseInt(fields[0]),value);
                }
            }
        }
        catch(IOException e)
        {
                    System.out.println("IOException " + e);
        }
    }
    
    //read in marks file
    public void readMarks(FileReader fr){
        try{
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            //iterate over CSV file
            while((line = br.readLine()) != null && !line.isEmpty()){
                //split fields into array
                String[] fields = line.split(",");
                //make new arraylist value
                ArrayList<Integer> key = new ArrayList<Integer>();
                //add testID and studnetID to arraylist
                key.add(Integer.parseInt(fields[0]));
                key.add(Integer.parseInt(fields[1]));
                //check if hashmap has key
                if(!(markMap.containsKey(key))){
                    //add key-value pair to hashmap
                    markMap.put(key,Integer.parseInt(fields[2]));
                }
            }
        }
        catch(IOException e)
        {
            System.out.println("IOException " + e);
        }
    }
    
    //read all files
    public void readAllFiles(){
        try{
            //read in courses
            FileReader courses = new FileReader("backend-assessment/courses.csv");
            readCourses(courses);
            //read in students
            FileReader students = new FileReader("backend-assessment/students.csv");
            readStudents(students);
            //read in tests
            FileReader tests = new FileReader("backend-assessment/tests.csv");
            readTests(tests);
            //read in marks
            FileReader marks = new FileReader("backend-assessment/marks.csv");
            readMarks(marks);
        }
        catch(IOException e)
        {
            System.out.println("IOException " + e);
        }
    }
    
    //get student average of all courses
    public double averageOfAllCourses(Integer studentID){
        //calculate the average of all courses taken by student with studentID
        //initialize arraylist for testIDs
        ArrayList<Integer> testIDs = new ArrayList<Integer>();
        //iterate through markMap to find all testIDs with studentID
        for(ArrayList<Integer> key : markMap.keySet()){
            if(key.get(1).equals(studentID)){
                //convert object to integer
                Integer testInteger = Integer.parseInt(key.get(0).toString());
                //convert integer to int
                int testInt = testInteger.intValue();
                testIDs.add(testInt);
            }
        }
        //initialize usedList for courseIDs
        ArrayList<Integer> usedCourses = new ArrayList<Integer>();
        //initialize totalAverage
        double totalAverage = 0;
        //initialize totalSum
        double totalSum = 0;
        //initialize count
        int count = 0;
        //iterate over testMap
        for(Integer testID : testMap.keySet()){
            //initialize arraylist to hold test values
            ArrayList<Integer> testValues = new ArrayList<Integer>();
            testValues = testMap.get(testID);
            //check that courseID has not been used && matches testID
            if(testIDs.contains(testID) && (!(usedCourses.contains(testValues.get(0))))){
                //add course to usedCourses
                usedCourses.add(testValues.get(0));
                //call calculateFinalGrade
                //add grade to totalAverage
                totalSum += calculateFinalGrade(studentID,testValues.get(0));
                //increase counter
                count++;
            }
        }
        //calculate average of all courses
        totalAverage = totalSum/count;
        return totalAverage;
    }
    
    //print final grade list
    public void printFinalGradeList(Integer studentID,PrintWriter writer){
        DecimalFormat formatter = new DecimalFormat("#0.00");
        //get final grade list
        HashMap<Integer,Double> finalGradeList = getFinalGradeList(studentID);
        //iterate over list
        for(Integer courseID : finalGradeList.keySet()){
            //get course name and teacher from courseMap
            ArrayList<String> courseValues = courseMap.get(courseID);
            String name = courseValues.get(0);
            String teacher = courseValues.get(1);
            double finalGrade = finalGradeList.get(courseID);
            writer.println("Course: " + name + ", Teacher: " + teacher);
            writer.println("Final Grade: " + formatter.format(finalGrade) + "%\n");
        }
    }
    //get final grade map
    public HashMap<Integer,Double> getFinalGradeList(Integer studentID){
        //get courseID to from studentID
        //initialize courseID arraylist
        ArrayList<Integer> courseIDs = new ArrayList<Integer>();
        //initialize usedList for courseIDs
        ArrayList<Integer> usedCourses = new ArrayList<Integer>();
        //iterate over testMap to get testIDs
        for(Integer testID : testMap.keySet()){
            //initialize an arraylist to keep studentID and testIDs
            ArrayList<Integer> studentTestIDs = new ArrayList<Integer>();
            studentTestIDs.add(testID);
            studentTestIDs.add(studentID);
            //check that the markMap contains a key matching the student and test ID arraylist
            if(markMap.containsKey(studentTestIDs)){
                //get courseID
                ArrayList<Integer> testValues = testMap.get(testID);
                if(!(usedCourses.contains(testValues.get(0)))){
                    courseIDs.add(testValues.get(0));
                    usedCourses.add(testValues.get(0));
                }
            }
        } 
        //initialize finalGradeMap
        HashMap<Integer,Double> finalGradeMap = new HashMap<Integer,Double>();
        //iterate over courseIDs to calculated final grade
        for(Integer courseID : courseIDs){
            //calculated final grade
            double finalGrade = calculateFinalGrade(studentID,courseID);
            finalGradeMap.put(courseID,finalGrade);
        }
        return finalGradeMap;
    }
    //calculate course final grade for certain studentID
    public double calculateFinalGrade(Integer studentID, Integer courseID){
        //calculate final grade for student in this course
        //initialize weightSum
        int weightSum = 0;
        //initialize test count
        int testCount = 0;
        //get all test ids and weights for given courseID and studentID
        HashMap<Integer,Integer> weights = new HashMap<Integer,Integer>();
        //iterate over testMap to get values
        for(Integer testID : testMap.keySet()){
            //initialize arraylist to keep test values
            ArrayList<Integer> testValues = new ArrayList<Integer>();
            testValues = testMap.get(testID);
            //check if course id matches the desired courseID
            if(courseID.equals(testValues.get(0))){
                //add test id and weight to the weights hashmap
                weights.put(testID,testValues.get(1));
                //add weight to sum
                weightSum += testValues.get(1).intValue();
                testCount++;
            }
        }
        //check that weightSum equals 100
        if(weightSum != 100){
            //throw error message
            throw new Error("Test weights do not add up to 100 for this course!");
        }
        //initialize finalGrade and taken test count
        double finalGrade = 0.0;
        int takenCount = 0;
        //iterate over the weights hashmap to find correct testIDs
        for(Integer testID : weights.keySet()){
            //initialize an arraylist to keep studentID and testIDs
            ArrayList<Integer> studentTestIDs = new ArrayList<Integer>();
            studentTestIDs.add(testID);
            studentTestIDs.add(studentID);
            //check that the markMap contains a key matching the student and test ID arraylist
            if(markMap.containsKey(studentTestIDs)){
                //get mark
                double mark = markMap.get(studentTestIDs);
                //check that student took this test
                if(mark >= 0){
                    Integer weight = weights.get(testID);
                    double w = weight.doubleValue();
                    finalGrade += ((w/100)*mark);
                    takenCount++;
                }
            }
        } 
        //check that taken count matces test count
        if(testCount > takenCount){
            //throw error because student has not completed the course
            throw new Error("Student has not completed this course!");
        }
        //return final grade
        return finalGrade;
    }
    //make report card
    public void makeReportCard(){
        DecimalFormat formatter = new DecimalFormat("#0.00");
        try{
            //create text file
            PrintWriter writer = new PrintWriter("report-card.txt");
            //iterate over each student id
            for(Integer studentID : studentMap.keySet()){
                //first line prints student id and name
                writer.println("Student Id: " + studentID + ", name: " + studentMap.get(studentID));
                //second line prints average of all courses the student is enrolled in
                //call get averageOfAllCourses
                double avg = averageOfAllCourses(studentID);
                writer.println("Total Average:\t" + formatter.format(avg) + "%\n");
                //list all courses and student's grade in each course
                printFinalGradeList(studentID,writer);
            }
            writer.flush();
            writer.close();
        }
        catch(FileNotFoundException e){
            System.out.println("FileNotFoundException " + e);
        }
    }

    
    
    
    
    
    
    
}
