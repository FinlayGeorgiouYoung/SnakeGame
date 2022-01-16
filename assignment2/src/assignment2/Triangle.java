package assignment2;

import java.awt.*;

//the Triangle class extends the Shape class
//it has to have the same variables as the Shape class in it's constructor and it has to define the drawShape method
public class Triangle extends Shape{

    public Triangle(int xPos, int yPos, int width, int height) {
        //super is used to get the variables from the class Shape constructor
        super(xPos, yPos, width, height);
    }

    //overriding the drawShape method from the Shape class
    //the method draws a polygon which creates a triangle. It will be used as a pickup, which will speed up the snakes movement
    @Override
    public void drawShape(Graphics g) {

        //x and y arrays with 3 different coordinates are needed to creates a triangle polygon
        int[] xPoints = {xPos, xPos+width/2 , xPos+width};
        int[] yPoints = {yPos+height, yPos, yPos+height};

        g.setColor(Color.WHITE);
        g.fillPolygon(xPoints, yPoints, 3);

    }
}
