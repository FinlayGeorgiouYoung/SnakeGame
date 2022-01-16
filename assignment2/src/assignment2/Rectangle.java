package assignment2;

import java.awt.*;

//the Rectangle class extends the Shape class
//it has to have the same variables as the Shape class in it's constructor and it has to define the drawShape method
public class Rectangle extends Shape {

    public Rectangle(int xPos, int yPos, int width, int height) {
        //super is used to get the variables from the class Shape constructor
        super(xPos, yPos, width, height);
    }

    //overriding the drawShape method from the Shape class
    //the method draws a rectangle, used for designing the GameOver panel
    @Override
    public void drawShape(Graphics g) {
        g.fillRect(xPos, yPos, width, height);
    }

}
