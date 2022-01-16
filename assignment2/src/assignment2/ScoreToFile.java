package assignment2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class ScoreToFile {

    String fileName;

    //ScoreToFile constructor takes an instance of Board as a parameter so it can access the score
    public ScoreToFile(String fileName){
        this.fileName = fileName;
    }

    //the writeToFile method takes file name as a parameter and appends the score from the current instance of Board to the file
    public void writeToFile(Board snakeBoard){

        try {
            //tries to access the input file
            File scores = new File(fileName);
            //if the file doesn't exist it will be created
            if (!scores.exists()) {
                scores.createNewFile();
            }
            //a FilWriter is used to append the users score to the input file
            FileWriter fw = new FileWriter(scores.getAbsoluteFile(), true);
            fw.write(snakeBoard.score + "\n");
            fw.close();
        }

        catch (IOException e) {
            System.out.println("file " + fileName + " not found");
        }

    }



    public String returnTop10(){

        ArrayList<Integer> allScores = new ArrayList<>();
        String top10String = "";

        try {
            //the file scores.txt is attempted to be opened
            File file = new File(fileName);
            Scanner fileScanner = new Scanner(file);
            //each score in the file is added to an ArrayList
            while (fileScanner.hasNext()) {
                allScores.add(Integer.parseInt(fileScanner.nextLine()));
            }
        }
        //FileNotFoundException is caught, because if the file isn't found then it will be thrown
        catch(FileNotFoundException e){
            System.out.println(e);
        }

        //sort all of the scores in ascending order
        Collections.sort(allScores, Collections.reverseOrder());

        //appends top 10 scores to a string
        top10String += "Top 10 Scores:  ";

        //if there is less than 10 scores, the top scores will be appended to a string and 0 will be used as the other scores
        if (allScores.size() < 10) {
            for (int i = 0; i < allScores.size(); i++) {
                top10String += allScores.get(i) + "   ";
            }
            for (int i = allScores.size(); i < 10; i++) {
                top10String += 0 + "   ";
            }
        }

        //appends the top 10 scores to a string
        else {
            for (int i = 0; i < 10; i++) {
                top10String += allScores.get(i) + "   ";
            }
        }

        //a string of the top 10 scores is returned
        return top10String;
    }
}
