package assignment2;

import java.awt.*;

//class Shape is an abstract class which contains the coordinates of a shape and the size
//it also contains an abstract method which has to be defined by all classes that extends shape
public abstract class Shape {

    int xPos;
    int yPos;
    int width;
    int height;

    //the constructor takes 4 integers: the x coordinates of a shape, the y coordinates of a shape, the width of a shape and the height of a shape
    public Shape(int xPos, int yPos, int width, int height){
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
    }

    //the drawShape method is abstract and has to be defined by any class that extends shape
    public abstract void drawShape(Graphics g);
}
