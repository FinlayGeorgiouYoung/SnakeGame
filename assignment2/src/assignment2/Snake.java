package assignment2;

import java.awt.*;

//the Snake class extends the Shape class
//it has to have the same variables as the Shape class in it's constructor and it has to define the drawShape method
public class Snake extends Shape {

    int boardSize;
    int gridSpacing;

    int maxSnakeLength = 400;
    int snakeLength = 1;

    //2D array of coordinates of the position of each part of the snake
    //e.g. snakePos[0][0] = x coordinate of the head and snakePos[0][1] = y coordinate of the head
    int snakePos[][] = new int[maxSnakeLength][2];


    public Snake(int xPos, int yPos, int width, int height, int boardSize, int gridSpacing) {
        //super is used to get the variables from the class Shape constructor
        super(xPos, yPos, width, height);

        //boardSize and gridSpacing will be used to help with the movement of the snake in methods below
        this.boardSize = boardSize;
        this.gridSpacing = gridSpacing;

        //assigns the input x and y positions to the position of the snakes head(the first square)
        snakePos[0][0] = xPos;
        snakePos[0][1] = yPos;
    }

    //the bodyMovement method allows the body of the snake to follow the head
    public void bodyMovement(){
        //loops through each part of the snake starting form the back. The position of the part in front is assigned to the current part
        for(int i = snakeLength; i>0; i--){
            //the x and y coordinates of the snake-part/square ahead are assigned to the current snake part/square
            //so each snake-part/square takes the position of the snake-part/square ahead
            snakePos[i][0] = snakePos[i-1][0];
            snakePos[i][1] = snakePos[i-1][1];
        }
    }


    //the right method is used to move the snake right by a grid space
    //if the snake reaches the end of the right side of the board, it will keep going right from the far left side
    public void right(){
        //if the head of the snake has reached the far right of the board, the snakes head is moved to the far left
        if(snakePos[0][0] == boardSize - gridSpacing){
            snakePos[0][0] = 0;
        }
        //if the head of the snake hasn't reached the far right of the board, then the snake is moved right by a grid space
        else {
            snakePos[0][0] += gridSpacing;
        }
    }

    //the left method is used to move the snake left by a grid space
    //if the snake reaches the end of the left side of the board, it will keep going left from the far right side
    public void left(){
        //if the head of the snake has reached the far left of the board, the snakes head is moved to the far right
        if(snakePos[0][0] == 0){
            snakePos[0][0] = boardSize - gridSpacing;
        }
        //if the head of the snake hasn't reached the far left of the board, then the snake is moved left by a grid space
        else {
            snakePos[0][0] -= gridSpacing;
        }
    }

    //the up method is used to move the snake up by a grid space
    //if the snake reaches the top of the board, it will keep going up from the bottom
    public void up(){
        //if the head of the snake has reached the top of the board, the snakes head is moved to the bottom
        if(snakePos[0][1] == 0){
            snakePos[0][1] = boardSize;
        }
        //if the head of the snake hasn't reached the top of the board, then the snake is moved up by a grid space
        else{
            snakePos[0][1] -= gridSpacing;
        }
    }

    //the down method is used to move the snake down by a grid space
    //if the snake reaches the bottom of the board, it will keep going down from the top
    public void down(){
        //if the head of the snake has reached the bottom of the board, the snakes head is moved to the top
        if(snakePos[0][1] == boardSize){
            snakePos[0][1] = 0;
        }
        //if the head of the snake hasn't reached the bottom of the board, then the snake is moved down by a grid space
        else{
            snakePos[0][1] += gridSpacing;
        }
    }

    //overriding the drawShape method from the Shape class
    //the method draws an amount of squares depending on the length of the snake
    @Override
    public void drawShape(Graphics g){

        //draws the head of the snake
        g.setColor(new Color(25, 191, 0));
        g.fillRect(snakePos[0][0], snakePos[0][1], width, height);

        //draws the body of the snake
        for(int i = 1; i < snakeLength; i++) {
            g.setColor(new Color(17, 143, 0));
            g.fillRect(snakePos[i][0], snakePos[i][1], width, height);
        }
    }
}



