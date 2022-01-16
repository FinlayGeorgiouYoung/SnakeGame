package assignment2;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

//class GameOver extends JPanel as it will be the panel shown when the game ends
public class GameOver extends JPanel {

    int score;

    //GameOver has a constructor which takes an integer variable called score as a parameter
    //the constructor sets up the JPanel and adds what is needed
    public GameOver(int score){
        this.score = score;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.BLACK);
        setSize(600,700);

        JLabel gameOverText = new JLabel("GAME OVER!");
        gameOverText.setFont(new Font("Berlin Sans FB", Font.BOLD, 50));
        gameOverText.setForeground(Color.RED);
        gameOverText.setAlignmentX(Component.CENTER_ALIGNMENT);

        //adds spacing between components
        add(Box.createRigidArea(new Dimension(0,50)));
        add(gameOverText);

        JLabel scoreText = new JLabel("Your score was: " + score);
        scoreText.setFont(new Font("Berlin Sans FB", Font.PLAIN, 30));
        scoreText.setForeground(Color.GREEN);
        scoreText.setAlignmentX(Component.CENTER_ALIGNMENT);

        //adds spacing between components
        add(Box.createRigidArea(new Dimension(0,40)));
        add(scoreText);

        //creates a new instance of ScoreToFile with file scores.txt passed as a parameter
        ScoreToFile file = new ScoreToFile("scores.txt");
        //the returnTop10 method of the ScoreToFile class is used to get the top 10 as a string from the file
        String top10 = file.returnTop10();

        JLabel top10Scores = new JLabel();
        top10Scores.setFont(new Font("Berlin Sans FB", Font.PLAIN, 20));
        top10Scores.setAlignmentX(Component.CENTER_ALIGNMENT);
        top10Scores.setForeground(Color.GREEN);
        //sets the text of the top10Scores label to show the top 10 scores
        top10Scores.setText(top10);

        //adds spacing between components
        add(Box.createRigidArea(new Dimension(0, 100)));
        add(top10Scores);
    }
}