package assignment2;


public class Main {

    public static void main(String[] args) {
        //an instance of Frame is created to make the frame containing the game board
        Frame frame = new Frame();
        //the frame calls the method setUpGame to add the Board and the score panel to the frame
        frame.setUpGame();
    }
}
