package assignment2;

import javax.swing.*;
import java.awt.*;

//class Frame extends JFrame as it will be used as the frame to hold the game
public class Frame extends JFrame {

    //instances of the panels in the frame are created
    private Board snakeBoard;
    private JPanel infoPanel;
    private GameOver gameOverPanel;

    //Frame has a constructor which sets up the frame
    public Frame(){
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(600, 700);
        //registration number is displayed in the title of the frame
        setTitle("Snake. RegNo: 1704469");
    }


    //the setUpGame method adds a board to the frame and a score panel to the frame
    //the method also has code which allows the users score to be written to a file
    public void setUpGame(){

        //creates an instance of Board, which will be used as the game board where the user will play snake
        snakeBoard = new Board();

        //creates the top panel which will hold a JLabel showing the users current score
        infoPanel = new JPanel();
        infoPanel.setBackground(Color.white);
        infoPanel.setSize(600, 100);
        //creates a JLabel which will show the users score
        JLabel scoreCount = new JLabel("score: " + snakeBoard.score);
        scoreCount.setFont(new Font(scoreCount.getName(), Font.BOLD, 20));
        infoPanel.add(scoreCount);


        //adds the instance of Board(snakeBoard) and the score panel(infoPanel) to the frame
        add(snakeBoard);
        add(infoPanel, BorderLayout.NORTH);
        //snakeBoard requests focus to allow the key listeners to work
        snakeBoard.requestFocusInWindow();
        revalidate();

        //the while loop carries on looping until the timer is started
        //this prevents the code below from being run until the user starts the game
        while (!snakeBoard.gameTimer.isRunning()){}

        //while the game is running, the JLabel scoreCount's text is set to the current score in snakeBoard
        while (snakeBoard.gameTimer.isRunning()) {
            scoreCount.setText("score: " + snakeBoard.score);
        }

        //once the game is over, a ScoreToFile object is created
        //the writeToFile method is used to write the score to a text file called scores.txt
        if (!snakeBoard.gameTimer.isRunning()) {
            //an instance of ScoreToFile is created with the file scores.txt passed as the parameter
            ScoreToFile file = new ScoreToFile("scores.txt");
            //the writeToFile method is used to write the users score to the file when the game ends
            file.writeToFile(snakeBoard);


            //an instance of GameOver is created passing the score in snakeBoard as its argument
            //the Board(snakeBoard) is then removed from the frame and gameOverPanel is added
            gameOverPanel = new GameOver(snakeBoard.score);
            remove(snakeBoard);
            add(gameOverPanel);
            revalidate();
        }
    }
}

