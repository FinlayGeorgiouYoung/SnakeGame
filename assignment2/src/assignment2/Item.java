package assignment2;

import java.awt.*;


//the Item class extends the Shape class
//it has to have the same variables as the Shape class in it's constructor and it has to define the drawShape method
public class Item extends Shape {

    public Item(int xPos, int yPos, int width, int height) {
        //super is used to get the variables from the class Shape constructor
        super(xPos, yPos, width, height);
    }

    //overriding the drawShape method from the Shape class
    //the method draws a red circle as the item, which will increase the size of the snake and increase the score when eaten
    @Override
    public void drawShape(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(xPos, yPos, width, height);
    }
}
