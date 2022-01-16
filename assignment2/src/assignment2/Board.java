package assignment2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

//class Board extends JPanel as it will be the panel where the game takes place
//class Board implements KeyListener to give controls to the game
public class Board extends JPanel implements KeyListener{

    int boardSize = 600;
    int gridSize = 20;
    int[] gridX;
    int[] gridY;

    boolean left = false;
    boolean right = false;
    boolean up = false;
    boolean down = false;

    boolean gameNotStarted = true;

    Random randomGenerator = new Random();

    int score = 0;
    int counter;
    Timer gameTimer;
    Timer triangleTimer;
    Timer effectTimer;
    Timer pieTimer;

    Snake snake;
    Item item;
    Triangle triangle;
    Pie pie;

    ArrayList<Shape> gameShapes = new ArrayList<>();

    //Board has a constructor which sets up the JPanel and adds a mouseListener to the JPanel
    public Board(){

        setSize(boardSize, boardSize);
        setBackground(Color.BLACK);
        setFocusable(true);
        //a KeyListener is added, using the class as it's keyListener because it implements KeyListener
        addKeyListener(this);
        //a MouseListener is added which is used to start the game(all of the timers) when a certain area of the start screen is clicked
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //if the area of the "CLICK HERE TO START" text on the start screen is clicked the timers are started and so the game starts
                if(e.getX()>=150 && e.getX()<445 && e.getY()>=400 && e.getY()<=440){
                    //gameNotStarted is set to false so the paint component won't draw the start screen after the user clicks a certain area
                    gameNotStarted = false;
                    //timers are started when the user clicks a certain area to start the game
                    gameTimer.start();
                    triangleTimer.start();
                    pieTimer.start();
                }
            }
        });

        //when an instance of Board is created, it calls the startGame method
        startGame();

    }



    //the startGame method is used to set the grid, create the different shapes needed in the game(e.g. the snake and items) and create all of the timers and their ActionListeners
    public void startGame(){

        //all shapes are added to a collection(an ArrayList)
        gameShapes.add(item);
        gameShapes.add(snake);
        gameShapes.add(triangle);
        gameShapes.add(pie);

        //sets the grid up. In this case it's a 20 by 20 grid
        gridX = new int[gridSize];
        gridY = new int[gridSize];
        //each x point and y point on the grid is assigned to the gridX and gridY arrays
        for(int i = 0; i< gridX.length; i++){
            gridX[i] = (boardSize/gridSize)*(i+1);
        }
        for(int i = 0; i< gridY.length; i++){
            gridY[i] = (boardSize/gridSize)*(i+1);
        }


        //an instance of Snake is created
        //parameters passed: the center of the board as the snakes x and y position parameters, the size of each part of the snake(width and height), the size of the board, and the space between each cell in the grid
        snake = new Snake(gridX[(gridX.length/2)-2], gridY[(gridY.length/2)-1], 20, 20, boardSize, boardSize / gridSize);

        //an instance of Item is created
        //parameters passed: random positions in the grid as the items x and y position parameters, and the size of the item(width and height)
        item = new Item(gridX[randomGenerator.nextInt(gridX.length-1)], gridY[randomGenerator.nextInt(gridY.length-1)],20,20);


        //the gameTimer is used to recursively call the action in the action listener every 100 milliseconds
        //the gameTimer controls the main game e.g. the snake and items
        gameTimer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                //each direction has a boolean value associated with it
                //if a direction is true, then the snake will call method bodyMovement and the method associated with that direction e.g. right()
                //this will allow each part of the snake to take the position of the part in front and the head to move in direction that is true
                if(right == true){
                    snake.bodyMovement();
                    snake.right();
                }
                if(left == true){
                    snake.bodyMovement();
                    snake.left();
                }
                if(up == true){
                    snake.bodyMovement();
                    snake.up();
                }
                if(down == true){
                    snake.bodyMovement();
                    snake.down();
                }


                //if the counter is at 1 it shows that the effect of eating a triangle has lasted for 5 seconds
                //the snake is put back to normal speed after 5 seconds
                if(counter == 1){
                    effectTimer.stop();
                    gameTimer.setDelay(100);
                    counter = 0;
                }

                //methods are called to see if the snake has eaten any pickups, or if the snake has hit itself
                itemEaten();
                triangleEaten();
                pieEaten();
                endGame();

                //each time the ActionListener is called by the timer the Board is repainted to allow the user to see the changes made
                repaint();
            }
        });


        //the triangleTimer is used to recursively call the action in the action listener every 15 to 20 seconds
        //the triangleTimer controls the creation of the triangle pick ups
        triangleTimer = new Timer(randomGenerator.nextInt(5000) + 15000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //the triangle is given random x and y coordinates from the grid
                int xPos = gridX[randomGenerator.nextInt(gridX.length-1)];
                int yPos = gridY[randomGenerator.nextInt(gridY.length-1)];
                try {
                    //if a triangle is given the same position on the grid as an item or pie, then a new random position will be chosen
                    while (xPos == pie.xPos && yPos == pie.yPos || xPos == item.xPos && yPos == item.yPos) {
                        xPos = gridX[randomGenerator.nextInt(gridX.length-1)];
                        yPos = gridY[randomGenerator.nextInt(gridY.length-1)];
                    }
                }
                //NullPointerException has to be caught because it will be thrown if a triangle hasn't been created yet
                catch (NullPointerException ex){}

                //a new Triangle is created using the x and y coordinates generated above
                triangle = new Triangle(xPos, yPos, 20, 20);

            }
        });


        //the effectTimer is used to indicate when 5 seconds is up once the timer has been started
        //this is used for making the effect of eating a triangle last for 5 seconds
        effectTimer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //when the counter is at 1 it will show that 5 seconds have past since the timer was started
                counter++;
            }
        });


        //the pieTimer is used to recursively call the action in the action listener every 15 to 25 seconds
        //the pieTimer controls the creation of the pie pick ups
        pieTimer = new Timer(randomGenerator.nextInt(10000) + 15000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //the pie is given random x and y coordinates from the grid
                int xPos = gridX[randomGenerator.nextInt(gridX.length-1)];
                int yPos = gridY[randomGenerator.nextInt(gridY.length-1)];
                try {
                    //if a pie is given the same position on the grid as an item or triangle, then a new random position will be chosen
                    while (xPos == triangle.xPos && yPos == triangle.yPos || xPos == item.xPos && yPos == item.yPos) {
                        xPos = gridX[randomGenerator.nextInt(gridX.length-1)];
                        yPos = gridY[randomGenerator.nextInt(gridY.length-1)];
                    }
                }
                //NullPointerException has to be caught because it will be thrown if a triangle hasn't been created yet
                catch (NullPointerException ex){}

                //a new Pie is created using the x and y coordinates generated above
                pie = new Pie(xPos, yPos, 20, 20, 0, 280);

            }

        });

        //the initial delay is set to 5 seconds. After a counter will be incremented by 1 showing 5 seconds have past
        effectTimer.setInitialDelay(5000);



    }


    //the itemEaten method checks if the head of the snake is at the same point in the grid as an item
    //if it is, the snake's length is increased by one square, the item changes to a random position and the score is incremented by 10
    public void itemEaten(){

        if(snake.snakePos[0][0] == item.xPos && snake.snakePos[0][1] == item.yPos){

            snake.snakeLength++;
            item.xPos = gridX[randomGenerator.nextInt(gridX.length-1)];
            item.yPos = gridY[randomGenerator.nextInt(gridY.length-1)];
            score += 10;

        }

    }

    //the triangleEaten method checks if the head of the snake is at the same point in the grid as a triangle
    //if it is, the triangle is moved outside of the grid and the gameTimer's delay is decreased, speeding up the snakes movement
    public void triangleEaten(){
        try {
            if (snake.snakePos[0][0] == triangle.xPos && snake.snakePos[0][1] == triangle.yPos) {
                //speeds up the snakes movement
                gameTimer.setDelay(60);
                //once a triangle is eaten the effect timer is used to keep track of the time the effect on the snake has lasted for
                //after 5 seconds the snake is put back to normal speed
                effectTimer.start();
                //the triangle is moved outside of the grid so it can no longer be seen or have an effect
                triangle.xPos = 1000;
                triangle.yPos = 1000;
            }
        }
        //NullPointerException has to be caught, as it will be thrown if a triangle doesn't exist yet
        catch(NullPointerException e){ }

    }

    //the pieEaten method checks if the head of the snake is at the same point in the grid as a pie
    //if it is, the pie is moved outside of the grid and the score is incremented by 40
    public void pieEaten(){
        try {
            if (snake.snakePos[0][0] == pie.xPos && snake.snakePos[0][1] == pie.yPos) {
                //adds 40 to the players score
                score += 40;
                //the pie is moved outside of the grid so it can no longer be seen or have an effect
                pie.xPos = 1000;
                pie.yPos = 1000;
            }
        }
        catch(NullPointerException e){

        }

    }



    //the endGame method checks if the head of the snake is at the same position as any of the parts of the snake
    //if it is, the gameTimer will stop, ending the game
    public void endGame(){
         //loops through the positions of each part of the snakes body from back to front and checks if the head is in the same position as any of the parts
         for(int i = snake.snakeLength; i>0; i--){
            if(snake.snakePos[0][0] == snake.snakePos[i][0] && snake.snakePos[0][1] == snake.snakePos[i][1]){
                //ends the game
                gameTimer.stop();
            }
        }

    }



    //paintComponent is used to draw the start screen and the all of the shapes which make up the game
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        //if the game hasn't started, a start screen will be drawn, with a title and a piece of text saying "CLICK HERE TO START"
        if(gameNotStarted == true) {
            //Rectangle class is used to draw long rectangles making the start screen more aesthetic
            Rectangle redLine1 = new Rectangle(0, 120, 600, 5);
            Rectangle redLine2 = new Rectangle(0,40,600,5);
            Rectangle greenLine1 = new Rectangle(0,370,600,5);
            Rectangle greenLine2 = new Rectangle(0,470,600,5);

            g.setColor(Color.BLACK);
            g.fillRect(0,0,600,700);
            g.setColor(Color.GREEN);
            g.setFont(new Font("Berlin Sans FB", Font.BOLD, 50));
            g.drawString("SNAKE", 210,100);

            g.setFont(new Font("Berlin Sans FB", Font.PLAIN, 30));
            g.drawString("CLICK HERE TO START", 150, 430);

            g.setColor(Color.RED);
            redLine1.drawShape(g);
            redLine2.drawShape(g);

            greenLine1.drawShape(g);
            greenLine2.drawShape(g);

        }
        //if the game has started, the shapes needed to create the game will be drawn to the board
        else{
            try {
                snake.drawShape(g);
                item.drawShape(g);
                triangle.drawShape(g);
                pie.drawShape(g);
            }
            //NullPointerException is caught because it will be thrown if a triangle or pie haven't been created yet
            catch (NullPointerException e) { }
        }
    }






    @Override
    public void keyTyped(KeyEvent e) { }


    //the keyPressed method is overridden and used to make arrow keys change boolean variables relating to their direction
    @Override public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        //if the key pressed is the same as one of the direction keys and the boolean value for the opposite direction is false,
        //then the boolean values for all other directions will be changed to false and the value for the direction pressed will be made true
        //the ActionListener, linked to the gameTimer, uses the boolean variables which are changed in the KeyListener to move the snake in the directions pressed
        if(key == KeyEvent.VK_RIGHT && left == false){
            left = false;
            up = false;
            down = false;
            right = true;
        }

        if(key == KeyEvent.VK_LEFT && right == false){
            right = false;
            up = false;
            down = false;
            left = true;
        }

        if(key == KeyEvent.VK_UP && down == false){
            right = false;
            left = false;
            down = false;
            up = true;
        }

        if(key == KeyEvent.VK_DOWN && up == false){
            right = false;
            left = false;
            up = false;
            down = true;
            }
    }
    @Override
    public void keyReleased(KeyEvent e) {}







}





