package assignment2;

import java.awt.*;

//the Pie class extends the Shape class
//it has to have the same variables as the Shape class in it's constructor and it has to define the drawShape method
public class Pie extends Shape {

    int startAngle;
    int arcAngle;

    public Pie(int xPos, int yPos, int width, int height, int startAngle, int arcAngle) {
        //super is used to get the variables from the class Shape constructor
        super(xPos, yPos, width, height);

        this.startAngle = startAngle;
        this.arcAngle = arcAngle;
    }

    //overriding the drawShape method from the Shape class
    //the method draws a yellow arc as a pickup, which will give extra points to the persons score
    @Override
    public void drawShape(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillArc(xPos, yPos, width, height, startAngle, arcAngle);
    }
}
